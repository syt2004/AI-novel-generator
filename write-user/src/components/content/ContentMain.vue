<template>



  <el-container class="c-main-container" v-loading="loading" id="xiduyf">
    <el-aside class="c-main-aside" v-show="asideStatus" >
      <content-aside @removePart="removePart($event, val)" @sortPart="sortPart" @newPart="newPart" @selectPart="selectPart($event, val)" v-model:selectId="data.id" v-model:list="data.list" @ySortPart="ySortPart($event, val)" />
    </el-aside>
    <el-main class="c-main-main">
      <content-content ref="contentRef" @updatessssPart="updatessssPart($event, data)" @pushAIbreak="pushAIbreak" v-model:aside-status="asideStatus"/>
    </el-main>
  </el-container>
<!--  <a-drawer-->
<!--      :visible="asideStatus"-->
<!--      popup-container="#xiduyf"-->
<!--      v-if="asideDrawerStatus"-->
<!--      placement="left" :width="200" :footer="false" :header="false" style="padding: 0; margin: 0;">-->
<!--    <content-aside @removePart="removePart($event, val)" @sortPart="sortPart" @newPart="newPart" @selectPart="selectPart($event, val)" v-model:selectId="data.id" v-model:list="data.list" @ySortPart="ySortPart($event, val)" style="padding: 0; margin: 0; background-color: #f44336"/>-->
<!--  </a-drawer>-->
</template>

<script setup>

import ContentAside from "./ContentAside.vue"
import ContentContent from "./ContentContent.vue"
import {listPart, addPart, infoPart, deletePart, updatePart, sortPartAction} from '../../api/part.js'
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";


const props = defineProps({
  bookId: Number
});

const asideStatus = ref(true)
const asideDrawerStatus = ref(false)

const data = reactive({
  id: 0,
  list: [],
  part: {}
});

const loading = ref(false)

const bookId = ref(props.bookId)

const newPart = () => {
  let part = {
    bookId: bookId.value,
    partTitle: '未命名章节',
    content: '',
    words: 0
  }
  loading.value = true
  addPart(part).then(res => {
    part.id = res.data.id
    data.list.unshift(part)
    data.id = part.id
    data.part = part
    contentRef.value.sesPart(part)
    loading.value = false
  }).catch(error => {
    loading.value = false
  })
}

const sortPart = () => {
  data.list.reverse()
}

const selectPart = (val) => {
  const listElement = data.list[val]
  if (data.id !== listElement.id) {
    infoPart(listElement).then(res => {
      data.part = res.data
      data.id = res.data.id
      contentRef.value.sesPart(data.part)
    }).catch(error => {

    })
  }
}

const ySortPart = (val) => {
  sortPartAction({ids:val}).then(res => {

  }).catch(error => {

  })
}

const removePart = (val) => {
  const listElement = data.list[val]
  deletePart(listElement).then(res => {
    data.list.splice(val, 1)
    if (data.list) {
      data.id = data.list[0].id
      data.part = data.list[0]
    } else {
      data.id = null
      data.part = {}
    }
    contentRef.value.sesPart(data.part)
    ElMessage({
      message: res.msg,
      type: 'success',
      offset: 200,
      duration: 2000,
    })

  }).catch(error => {

  })
}

onMounted(() => {
  changeAsideDrawerStatus()
  loading.value = true
  listPart({bookId: bookId.value}).then(res => {
    data.list = res.data
    if (data.list) {
      data.id = data.list[0].id
      data.part = data.list[0]
      contentRef.value.sesPart(data.part)
    } else {

    }
    loading.value = false
  }).catch(error => {
    loading.value = false
  })
})

const contentRef = ref(null)

const updatessssPart = (ssssss) => {
  updatePart(ssssss).then(res => {
    data.part = res.data
    data.id = res.data.id
    // contentRef.value.sesPart(data.part)
    for (let i = 0; i < data.list.length; i++) {
      if (data.list[i].id === res.data.id) {
        data.list[i] = res.data
        break
      }
    }
    contentRef.value.sesPart(data.part)
  }).catch(error => {

  })
}

const router = useRouter()
const pushAIbreak = () => {
  let params = {
    listStr: JSON.stringify(data.list),
    bookId: data.part.bookId
  }
  router.push({
    path: '/aibreak',
    state: params
  })
}

const getScreenWidth = () => {
  return window.innerWidth
}

const changeAsideDrawerStatus = () => {
  if (getScreenWidth() > 700) {
    asideDrawerStatus.value = false
  } else {
    asideDrawerStatus.value = true
  }
}

import { debounce } from 'lodash'
const cancalDebounce = debounce(changeAsideDrawerStatus, 500);
window.addEventListener('resize', cancalDebounce)



onUnmounted(() => {
  window.removeEventListener('resize', cancalDebounce);
})

</script>

<style scoped lang="scss">

.c-main-container {
  display: flex;
  height: 100%;
  width: 100%;
  //background-color: #535bf2;

  .c-main-aside {
    width: 200px;
    border-right: 1px solid transparent;
    border-image: linear-gradient(to right,transparent 50%, #e8e8e8 50%) 0 0 100%/1px 0;
    height: calc(100vh - 70px);
    //height: 100%;
    //background-color: #fff;
  }

  .c-main-main {
    height: calc(100vh - 70px);
    padding-top: 0;
    //flex-grow: 1;
    border-left: 1px solid transparent;
    border-image: linear-gradient(to left,transparent 50%, var(--w-border-color) 50%) 0 0 100%/1px 0;
  }
}

</style>
