<template>
  <div class="navbar-div">
    <div class="navbar-back">
      <el-icon class="navbar-back-icon" :size="30" @click="goBack">
        <Back/>
      </el-icon>
    </div>
    <div class="navbar-title">{{title}}</div>
    <div class="navbar-list">
      <el-tooltip content="设置">
        <TextSetting :size="25" style="margin-left: 20px"/>
      </el-tooltip>




<!--      <el-popover-->
<!--          placement="bottom"-->
<!--          title="Title"-->
<!--          :width="200"-->
<!--          trigger="click"-->
<!--          content="this is content, this is content, this is content"-->
<!--      >-->
<!--        <template #reference>-->
<!--          <svg-icon name="menu" :size="30"/>-->
<!--        </template>-->
<!--      </el-popover>-->

      <el-tooltip content="历史记录">
        <svg-icon name="history" @click="pushHistory"  class="book-div-footer-button" v-blur/>
      </el-tooltip>



      <el-dropdown class="book-div-footer-button">
        <svg-icon name="menu" :size="30"/>
<!--        <el-icon class="navbar-back-icon" :size="30">-->
<!--          <Grid />-->
<!--        </el-icon>-->
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="openAddBook">作品信息</el-dropdown-item>
            <el-dropdown-item @click="openBookRole">人物管理</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>


      <tutorial class="book-div-footer-button"/>

<!--      <span class="book-div-footer-button" @click="jiaocheng">教程</span>-->
<!--      <svg-icon name="history" @click="pushHistory"  class="book-div-footer-button" v-blur/>-->


      <!--      </div>-->
    </div>


  </div>
  <AddBook ref="addBookRef" @doSth="refreshTitle($event, item)"/>
  <BookRole ref="bookRoleRef"/>

</template>

<script setup>
import {Grid, Back} from '@element-plus/icons-vue'
import {useUserStore} from "../../store/user.js"

import {useRouter} from "vue-router"
const router = useRouter()



const props = defineProps({
  title: {
    type: String,
    default: ''
  },
  bookId: {
    type: Number
  }
});

let bookId = ref(props.bookId)
let title = ref(props.title)

// 返回
const goBack = () => {
  router.back()
}

const pushHistory = () => {
  router.push('/history')
}



import AddBook from "../dialog/AddBook.vue"
const addBookRef = ref(null)
const openAddBook = () => {
  addBookRef.value?.openDialog(bookId.value)
}

const refreshTitle = (item) => {
  console.log(item)
  title.value = item.title
}


import BookRole from "../dialog/BookRole.vue"
import TextSetting from "../index/TextSetting.vue";
import SvgIcon from "../SvgIcon.vue";
import Tutorial from "../index/Tutorial.vue";
const bookRoleRef = ref(null)
const openBookRole = () => {
  bookRoleRef.value?.openDialog(bookId.value)
}


const userStore = useUserStore()
const user = storeToRefs(userStore)






</script>

<style scoped lang="scss">
.navbar-div {
  justify-content: space-between;
  align-items: center;
  //align-content: center;
  width: 100%;
  height: 100%;
  flex-direction: row;
  display: flex;
}

.navbar-back {
  color: var(--el-text-color-primary);
  width: 33%;
  display: flex;
  justify-content: flex-start;

}

.navbar-title {
  color: var(--el-text-color-primary);
  width: 33%;
  font-size: 20px;
}

.navbar-list {
  width: 33%;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  align-content: center;

  .book-div-footer-button {
    padding-left: 10px;
  }
}

.nav-list-icon {
  margin-left: 5px;
  margin-right: 5px;
}

.nav-list-icon-content {
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;

  .nav-avatar-button {
    width: 100%;
    margin-top: 5px;
  }
}


</style>
