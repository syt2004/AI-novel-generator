

<template>
  <el-container class="main-container">
<!--    <el-alert class="main-container-alert" title="Warning alert" type="warning" />-->
    <el-header class="main-header">
      <el-button size="large" type="primary" plain @click="openAddBook" v-blur>
        <el-icon><document-add/></el-icon> &nbsp;新建作品
      </el-button>
    </el-header>
    <el-main class="main-main">
      <book ref="bookRef"/>
    </el-main>
  </el-container>
  <AddBook ref="addBookRef" @doSth="refreshBook"/>
</template>


<script setup>
import Book from "./Book.vue"
import {DocumentAdd} from "@element-plus/icons-vue"
import AddBook from "../dialog/AddBook.vue"
import {ref} from "vue"

var addBookRef = ref(null)

const openAddBook = () => {
  addBookRef.value.openDialog()
}


const bookRef = ref(null)
const refreshBook = () => {
  bookRef.value.refreshBook()
}

</script>

<style scoped lang="scss">
.main-container {
  //background-color: #535bf2;
  height: 100%;
  width: 100%;


  //.main-container-alert {
  //  height: 60px;
  //  display: flex;
  //  align-items: center;
  //  margin: 0;
  //  padding: 0;
  //}

  .main-header {
    height: 60px;
    justify-content: flex-start;
    align-items: center;
    //background-color: #535bf2;
    display: flex;
    padding-left: 20px;
    //padding-top: 20px;

  }

  .main-main {
    padding: 0;
  }
}
</style>
