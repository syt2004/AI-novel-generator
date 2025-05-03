<template>
  <el-container v-loading.fullscreen.lock="fullscreenLoading"
                class="index-container">
<!--    <svg-icon name="phone"/>-->
    <el-header class="index-header">
      <nav-bar/>
    </el-header>
    <el-main class="index-main">
      <index-main/>

    </el-main>
  </el-container>



</template>

<script setup>

import {userinfo} from "../api/login.js"
import NavBar from "../components/index/NavBar.vue"
import {useUserStore} from "../store/user.js"
import IndexMain from "../components/index/IndexMain.vue"
const fullscreenLoading = ref(true)
const userStore = useUserStore()



onMounted(() => {
  userinfo().then(res => {
    fullscreenLoading.value = false
    userStore.setUser(res.data)
  })
})





</script>

<style scoped lang="scss">
.index-container {
  height: 100%;
  width: 100%;
}
.index-header {
  height: var(--nav-bar-height);
  border-bottom: 1px solid transparent;
  border-image: linear-gradient(to bottom,transparent 50%, var(--w-border-color) 50%) 0 0 100%/1px 0;
}
.index-main {
  //background-color: #646cff;
  padding: 0;



}
</style>
