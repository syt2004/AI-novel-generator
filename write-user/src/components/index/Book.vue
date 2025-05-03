<template>


<!--  <div class="book">-->

    <a-grid :cols="{ xs: 1, sm: 2, md: 3, lg: 4, xl: 5, xxl: 6 }" :colGap="12" :rowGap="16" class="book">
      <a-grid-item class="book-div" v-for="(item, index) in data.list" :key="index">
        <div class="book-div-main" @click="goContent(item)">
          <span>{{ item.title }}</span>
          <a-tag  color="blue" bordered style="margin-top: 20px">{{ formatNumber(item.words) }}字</a-tag>
        </div>
        <div class="book-div-footer">
          <a-button type="text" class="book-div-footer-button" @click="goContent(item)">
            查看章节
          </a-button>
          <!--        <el-button v-blur type="text" class="book-div-footer-button" @click="goContent(item)">-->
          <!--          <el-icon>-->
          <!--            <Plus/>-->
          <!--          </el-icon>&nbsp;-->
          <!--        </el-button>-->
          <a-dropdown trigger="hover">
            <a-button v-blur type="text" class="book-div-footer-button">
              作品管理
            </a-button>
            <template #content>
              <a-doption @click="openAddBook(item.id)">作品信息</a-doption>
              <a-doption @click="openBookRole(item.id)">人物管理</a-doption>
<!--              <a-doption @click="exportBookAction(item)">导出作品</a-doption>-->
              <a-doption @click="removeBook(item.id)">删除作品</a-doption>
            </template>
          </a-dropdown>
          <!--            <el-button v-blur type="text" class="book-div-footer-button"><el-icon><Setting /></el-icon> &nbsp;作品管理</el-button>-->
        </div>
      </a-grid-item>
    </a-grid>

<!--    <div class="book-div" v-for="(item, index) in data.list" :key="index">-->

<!--    </div>-->
<!--  </div>-->
  <AddBook ref="addBookRef" @doSth="refreshBook"/>
  <BookRole ref="bookRoleRef"/>

</template>

<script setup>

const formatNumber = (num) => {
  num = Number(num)
  if (num === 0 || (num > 0 && num < 10000)) {
    return num + ''
  } else {
    return (num / 10000).toFixed(2) + '万'
  }
}

import {onMounted, reactive, ref, defineExpose} from 'vue'

import {Plus, Setting} from "@element-plus/icons-vue"
import {listBook, deleteBook, exportBook} from "../../api/book.js"

let data = reactive({
  list: []
})

const router = useRouter()

const exportBookAction = (item) => {
  downloadLoading('/api/book/export', {
    id: item.id
  }, `${item.name}.txt`).then(res => {
  }).catch(error => {
  })
}

import {downloadLoading} from './../../util/request.js'


// 前往内容页
const goContent = (item) => {
  console.log(item)
  let params = item
  router.push({
    path: 'content',
    state: params
  })
}


let btns = reactive({
  list: [
    {
      title: '作品信息',
      page: ''
    }, {
      title: '人物管理',
      page: ''
    }, {
      title: '删除作品',
      page: ''
    }, {
      title: '拆解',
      page: ''
    }, {
      title: '作品导出',
      page: ''
    }, {
      title: '新手教程',
      page: ''
    }
  ]
})

onMounted(() => {
  getList()
})

const getList = () => {
  listBook().then(res => {
    data.list = res.data
  })
}


import AddBook from "../dialog/AddBook.vue"

var addBookRef = ref(null)
const openAddBook = (id) => {
  addBookRef.value.openDialog(id)
}

import BookRole from "../dialog/BookRole.vue"
import {ElMessage, ElMessageBox} from "element-plus"
import {deleteRole} from "../../api/role.js"
import {useRouter} from "vue-router"
var bookRoleRef = ref(null)
const openBookRole = (id) => {
  bookRoleRef.value.openDialog(id)
}

const removeBook = (id) => {
  ElMessageBox.confirm(
      '作品删除后无法恢复，确定要删除这个作品吗？',
      '删除作品',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(() => {
    deleteBook(id).then(res => {
      getList()
      ElMessage({
        message: res.msg,
        type: 'success',
        offset: 200,
        duration: 2000
      })
    })
  }).catch(() => {

  })
}

const refreshBook = () => {
  getList()
}

defineExpose({ refreshBook })

</script>

<style scoped lang="scss">
.book {
  //display: flex;
  flex-flow: wrap;
  padding-top: 20px;
  align-items: center;
  flex-direction: row;
  //justify-content: space-evenly;
}

.book-div {
  //min-width: 250px;
  //max-width: 400px;
  //width: 250px;
  //background-color: #646cff;
  //flex: 0 1 0;
  //flex-basis: ;
  //flex-grow: 1;
  border-radius: 20px;
  border: var(--w-border-color) solid 0.5px;
  margin: 10px;

  //margin: 5px;
  //height: 200px;
  //flex-flow: wrap;

  .book-div-main {
    height: 70px;
    padding: 10px;
    font-size: 16px;
    cursor: pointer;
    color: var(--el-text-color-primary);
    display: flex;
    flex-direction: column;
    align-items: center;

    //background-color: #1a1a1a;
  }

  .book-div-footer {
    display: flex;
    justify-content: center;
    //background-color: #e8e8e8;
    height: 54px;
    align-items: center;
    border-bottom-left-radius: 20px;
    border-bottom-right-radius: 20px;
    background-color: var(--book-btn-bg-color);
    //margin: 16px 12px 16px 12px;
    .book-div-footer-button {
      width: 40%;
      //background-color: #f44336;
      //background-color: var(--book-btn-bg-color);


    }
  }
}

</style>
