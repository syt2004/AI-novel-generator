<template>
  <el-dialog title="兑换商品"
             v-model="visible"
             width="70%" :show-close="false" :close-on-click-modal="false" :close-on-press-escape="false" destroy-on-close>
    <el-form :model="form" :rules="rules" ref="exchangesssRef">
      <el-form-item label="" prop="code">
        <el-input v-model="form.code" clearable placeholder="请输入兑换码"/>
      </el-form-item>
    </el-form>
    <div style="display: flex">
      <span>请联系管理员微信[{{wxCode}}]获取兑换码</span>
    </div>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="click1" v-blur>取消</el-button>
        <el-button type="primary" @click="sumbitCode" v-blur>
          兑换
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>

import {exchange} from "../../api/code.js"
import {ElMessage} from "element-plus"
const visible = ref(false)

const wxCode = import.meta.env.VITE_APP_WX


const form = reactive({
  code: ''
});

const openExchangeDialog = () => {
  visible.value = true
}

defineExpose({ openExchangeDialog })


const rules = reactive({
  code: [
    { required: true, message: '请输入兑换码', trigger: 'blur' },
  ]
})

const exchangesssRef = ref()

const sumbitCode = async () => {
  if (!exchangesssRef) return
  await exchangesssRef.value.validate(valid => {
    if (valid) {
      exchange(form.code).then(res => {
        ElMessage({
          message: res.msg,
          type: 'success',
          offset: 200,
          duration: 2000,
          onClose: () => {
            visible.value = false
          }
        })
      })
    }
  })
}

const click1 = () => {
  visible.value = false
}


</script>

<style scoped lang="scss">

</style>
