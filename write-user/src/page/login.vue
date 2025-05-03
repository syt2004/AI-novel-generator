<template>
  <div class="login-div" ref="loginDivRef">
    <el-card class="login-card">
      <template #header>{{ title }}</template>
      <el-form :model="loginModel" :rules="rules" ref="loginFormRef">
        <el-form-item prop="username">
          <el-input
              v-model="loginModel.username"
              placeholder="请输入用户名"
              clearable
              size="large"
          >
            <template #prefix>
              <el-icon><User /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              v-model="loginModel.password"
              placeholder="请输入密码"
              size="large"
              show-password>
            <template #prefix>
              <el-icon><Lock /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button
              class="login-button"
              size="large"
              @click="loginMethod(loginFormRef)">
            登录
          </el-button>
        </el-form-item>
        <el-form-item>
          还没有账号？请&nbsp;&nbsp;<RouterLink to="/register">注册</RouterLink>
        </el-form-item>
      </el-form>

    </el-card>
  </div>
</template>

<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import {reactive, ref} from "vue"
const title = import.meta.env.VITE_APP_TITLE
const loginModel = reactive({
  username: '',
  password: ''
})

import {isBlankValidate} from '../util/validate.js'
const rules = reactive({
  username: [
      { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
      { required: true, message: '请输入密码', trigger: 'blur' }
  ]
})

import {login} from "../api/login.js"
import {useRouter} from 'vue-router'
import {ElMessage} from "element-plus"
import {setToken} from '../util/auth.js'
const router = useRouter()


const loginFormRef = ref()
const loginMethod = async (formEl) => {
  if (!formEl) return
  await formEl.validate(valid => {
    if (valid) {
      login(loginModel.username, loginModel.password).then(res => {
        setToken(res.data.token)
        ElMessage({
          message: '登录成功',
          type: 'success',
          offset: 200,
          duration: 2000,
          onClose: () => {
            router.push('/')
          }
        })
      })
    }
  })

}



</script>

<style scoped lang="scss">
.login-div {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  width: 100%;

  .login__particles {
    height: 100vh;
    width: 100%;
  }
}
.login-card {
  max-width: 500px;
  min-width: 400px;
  z-index: 200;
}
.login-button {
  width: 100%;
}

</style>
