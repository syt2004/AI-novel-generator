<template>
  <div class="register-div">
    <el-card class="register-card">
      <template #header>{{title}}</template>
      <el-form :rules="rules" ref="registorFormRef" :model="registerModel">
        <el-form-item prop="username">
          <el-input
              v-model="registerModel.username"
              placeholder="请输入用户名"
              clearable
              size="large">
            <template #prefix>
              <el-icon><User /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              v-model="registerModel.password"
              placeholder="请输入密码"
              size="large"
              show-password>
            <template #prefix>
              <el-icon><Lock /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="tPassword">
          <el-input
              v-model="registerModel.tPassword"
              placeholder="请再次输入密码"
              size="large"
              show-password>
            <template #prefix>
              <el-icon><Lock /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button
              class="register-button"
              size="large"
              @click="submit(registorFormRef)">
            注册
          </el-button>
        </el-form-item>
        <el-form-item>
          已经拥有账号，请&nbsp;&nbsp;<RouterLink to="/login">登录</RouterLink>
        </el-form-item>
      </el-form>

    </el-card>
  </div>

</template>
<script setup>
import { User, Lock } from '@element-plus/icons-vue'

import { reactive, ref  } from 'vue'

import { ElMessage } from 'element-plus'
const title = import.meta.env.VITE_APP_TITLE
const registerModel = reactive ({
  username: '',
  password: '',
  tPassword: ''
})

import {isBlankValidate, rangeValidate} from '../util/validate.js'


const equalValidate = (rule, value, callback) => {
  if (registerModel.password !== value) {
    callback(new Error('两次密码输入不一致'))
  } else {
    callback()
  }
}

const rules = reactive ({
  username: [
    {
      required: true,
      message: '请输入账号',
      validator: isBlankValidate(),
      trigger: 'blur'
    },
    {
      required: true,
      message: '账号长度须在8-15之间',
      validator: rangeValidate(8, 15),
      trigger: 'blur'
    },
  ],
  password: [
    {
      required: true,
      message: '请输入密码',
      validator: isBlankValidate(),
      trigger: 'blur'
    },
    {
      required: true,
      message: '密码长度须在8-15之间',
      validator: rangeValidate(8, 15),
      trigger: 'blur'
    },
  ],
  tPassword: [
    {
      required: true,
      message: '请再次输入密码',
      validator: isBlankValidate(),
      trigger: 'blur'
    },
    {
      required: true,
      message: '密码长度须在8-15之间',
      validator: rangeValidate(8, 15),
      trigger: 'blur'
    },
    {
      required: true,
      message: '两次密码输入不一致',
      validator: equalValidate,
      trigger: 'change'
    },
  ],
})








import {register} from "../api/login.js"
import {useRouter} from 'vue-router'
const router = useRouter()
const registorFormRef = ref()
const submit = async (formEl) => {
  if (!formEl) return
  await formEl.validate(valid => {
    if (valid) {
      register(registerModel.username, registerModel.password, "")
          .then(res => {
            ElMessage({
              message: '注册成功，请前往登录',
              type: 'success',
              offset: 200,
              onClose: () => {
                router.push('/')
              }
            })
          }).catch(error => {

      })
    }
  })
}

</script>


<style scoped lang="scss">
.register-div {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}
.register-card {
  max-width: 500px;
  min-width: 400px;
}
.register-button {
  width: 100%;
}
</style>
