

<template>
  <el-container class="content-container">
    <el-header class="content-header">
      <content-nav-bar :title="title" :book-id="bookId"/>
    </el-header>
      <content-main class="content-main" :book-id="bookId"/>
  </el-container>
</template>


<script setup>
import ContentNavBar from "../components/content/ContentNavBar.vue"
import ContentMain from "../components/content/ContentMain.vue"

const bookId = history.state.id
const title = history.state.title

</script>

<style scoped lang="scss">
.content-container {
  height: 100%;
  width: 100%;
}
.content-header {
  height: var(--nav-bar-height);
  border-bottom: 1px solid transparent;
  border-image: linear-gradient(to bottom,transparent 50%, var(--w-border-color) 50%) 0 0 100%/1px 0;
}
.content-main {
  height: calc(100% - var(--nav-bar-height));
  //margin-top: 1px;
  //top: 0;
  //bottom: 0;
  //background-color: #646cff;



}
</style>
