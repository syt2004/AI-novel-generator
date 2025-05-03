

<template>
  <el-dialog :title="form.id ? '作品信息' : '新建作品'"
             v-model="visible"
             width="70%" :show-close="false" :close-on-click-modal="false" :close-on-press-escape="false" destroy-on-close>
    <el-form :model="form" :rules="rules" ref="addBookRef" label-position="top">
      <el-form-item label="作品名称" prop="title">
        <el-input v-model="form.title" clearable placeholder="请输入作品名称" :maxlength="20" show-word-limit/>
      </el-form-item>
      <el-form-item label="作品简介(选填,不影响生成内容)" prop="content">
        <el-input v-model="form.content" type="textarea" clearable placeholder="请输入作品简介" :maxlength="500" show-word-limit size="large" :rows="8"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="click1" v-blur>取消</el-button>
        <el-button type="primary" @click="sumbitCode" v-blur>
          提交
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>

import {ref, reactive, defineExpose} from "vue"
import {addBook, infoBook, updateBook} from "../../api/book.js"
import {ElMessage} from "element-plus"
const visible = ref(false)
const form = reactive({
  title: '',
  content: ''
});

const openDialog = (id) => {
  if (id) {
    infoBook(id).then(res => {
      form.title = res.data.title
      form.content = res.data.synopsis
      form.id = id
    })
  }
  visible.value = true
}

defineExpose({ openDialog })


const rules = reactive({
  title: [
    { required: true, message: '请输入作品名称', trigger: 'blur' },
  ]
})

const addBookRef = ref()

const emit = defineEmits([ "doSth" ]);
const doSth = () => {
  emit('doSth', form);
}

const sumbitCode = async () => {
  if (!addBookRef) return
  await addBookRef.value.validate(valid => {
    if (valid) {
      if (form.id) {
        updateBook(form.id, form.title, form.content).then(res => {
          ElMessage({
            message: res.msg,
            type: 'success',
            offset: 200,
            duration: 2000,
            onClose: () => {
              visible.value = false
              doSth()
            }
          })
        })
      } else {
        addBook(form.title, form.content).then(res => {
          ElMessage({
            message: res.msg,
            type: 'success',
            offset: 200,
            duration: 2000,
            onClose: () => {
              visible.value = false
              doSth()
            }
          })
        })
      }

    }
  })
}

const click1 = () => {
  visible.value = false
}

</script>

<style scoped lang="scss">

</style>
