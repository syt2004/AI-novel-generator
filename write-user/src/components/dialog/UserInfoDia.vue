<template>

  <a-modal v-model:visible="visible"
           @cancel="close"
           unmount-on-close
           :mask-closable="false"
           :hide-cancel="true"
           :closable="false"
           :esc-to-close="false"
           width="80%"
           title="用户信息">
    <el-form :model="form" :rules="rules" ref="userinfoRef" label-position="top">
      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="form.nickname" placeholder="请输入昵称" clearable></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="cancel" v-blur>取消</el-button>
        <el-button type="primary" @click="submit" v-blur>
          提交
        </el-button>
      </div>
    </template>
  </a-modal>
</template>

<script setup>

import {useUserStore} from "../../store/user.js"
import {isBlankValidate, rangeValidate} from "../../util/validate.js"
import {updateUserinfo} from "../../api/login.js"
import {ElMessage} from "element-plus";
const userStore = useUserStore()
const {nickname} = storeToRefs(userStore)


const userinfoRef = ref()

const visible = defineModel();

const form = reactive({
  nickname: nickname.value
});

const rules = {
  nickname: [
    {
      required: true,
      message: '请输入昵称',
      validator: isBlankValidate(),
      trigger: 'blur'
    },
    {
      required: true,
      message: '昵称长度须在5-15之间',
      validator: rangeValidate(5, 15),
      trigger: 'blur'
    }
  ]
}

const showModal = () => {
  visible.value = true
}

const cancel = () => {
  visible.value = false
}

const submit = async () => {
  console.log(1111)
  if (!userinfoRef) return
  await userinfoRef.value.validate(valid => {
    if (valid) {
      updateUserinfo({nickname: form.nickname}).then(res => {
        ElMessage({
          message: res.msg,
          type: 'success',
          offset: 200,
          duration: 2000,
          onClose: () => {
            visible.value = false
            userStore.updateUser()
          }
        })
      }).catch(err => {

      })

    }
  })
}

</script>

<style scoped lang="scss">

</style>
