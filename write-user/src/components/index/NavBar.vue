<template>
  <div class="navbar-div">
    <div class="navbar-list-left">
      <el-popover trigger="hover" :width="200">
        <template #reference>
          <el-avatar class="nav-list-icon" :src="userStore.avatar ? userStore.avatar : avatar"/>
        </template>
        <div class="nav-list-icon-content">
          <div class="nav-list-icon-content-title">
            <div>用户名：{{ user.username }}</div>
            <div>昵称：{{ user.nickname }}</div>
            <div>字数：{{ user.words }}</div>
          </div>
          <div class="nav-avatar-button-group">
            <div>
              <a-button class="nav-avatar-button" type="primary" @click="showUserInfo" v-blur>修改信息</a-button>
<!--              <user-info-dia class="nav-avatar-button"/>-->
              <!--              <el-button class="nav-avatar-button" text @click="changeInfo">修改信息</el-button>-->
            </div>
            <div>
              <a-button class="nav-avatar-button" type="primary" @click="logout" v-blur>退出登录</a-button>
              <!--              <el-button class="nav-avatar-button" text @click="logout">退出登录</el-button>-->
            </div>
          </div>
        </div>
      </el-popover>
      <el-tooltip content="兑换码">
        <svg-icon :size="40" class="nav-list-icon" name="exchange" @click="openExchange" v-blur/>
      </el-tooltip>
    </div>
    <div class="navbar-title">{{title}}</div>
    <div class="navbar-list">
      <theme class="navbar-list-icon"></theme>
      <el-tooltip content="历史记录">
        <svg-icon :size="40" class="navbar-list-icon" name="history" @click="pushHistory" v-blur/>
      </el-tooltip>

      <tutorial class="navbar-list-icon"/>
    </div>


  </div>

  <exchange ref="exchangeRef"/>
  <user-info-dia v-model="showU"/>

</template>

<script setup>


import UserInfoDia from '@/components/dialog/UserInfoDia'

const title = import.meta.env.VITE_APP_TITLE

const showU = ref(false)

const showUserInfo = () => {
  showU.value = true
}

import {useUserStore} from "../../store/user.js"
import Theme from "./Theme.vue"
import avatar from '@/assets/image/avatar.jpg'
const userStore = useUserStore()
const user = storeToRefs(userStore)
import {ElMessageBox} from 'element-plus'
import {removeToken} from '../../util/auth.js'
import {useRouter} from "vue-router"
const router = useRouter()
const logout = () => {
  ElMessageBox.confirm(
      '确定注销并退出系统吗？',
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(() => {
    removeToken()
    userStore.$reset()
    router.push('/login')
  }).catch(() => {

  })


}


onMounted(() => {
  userinfo().then(res => {
    // fullscreenLoading.value = false
    userStore.setUser(res.data)
  })
})

const pushHistory = () => {
  router.push('/history')
}


import Exchange from "../dialog/Exchange.vue"
import {userinfo} from "../../api/login.js";
import SvgIcon from "../SvgIcon.vue";
import Tutorial from "./Tutorial.vue";
const exchangeRef = ref(null)
const openExchange = () => {
  exchangeRef.value?.openExchangeDialog()
}


const changeInfo = () => {

}


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

.navbar-title {
  color: var(--el-text-color-primary);
  width: 33%;
  font-size: 20px;
}

.navbar-list-left {
  width: 33%;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  align-content: center;
}

.navbar-list {
  width: 33%;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  align-content: center;
}

.nav-list-icon {
  margin-left: 5px;
  margin-right: 5px;
  cursor: pointer;
}

.nav-list-icon-content {
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  //width: 300px;

  .nav-list-icon-content-title {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
    padding-left: 20px;
  }

  .nav-avatar-button-group {
    display: flex;
    flex-direction: column;
    width: 100%;

    .nav-avatar-button {
      width: 100%;
      margin-top: 5px;
    }
  }


}

.navbar-list-icon {
  //padding-right: 10px;
  padding-left: 10px;
}


</style>
