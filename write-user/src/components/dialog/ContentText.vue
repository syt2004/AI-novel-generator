

<template>
  <el-dialog :title="form.title "
             v-model="visible"
             width="70%" :show-close="false" :close-on-click-modal="false" :close-on-press-escape="false" destroy-on-close top="5" align-center>
<!--    <el-input v-model="form.content" type="textarea" disabled  size="large" :autosize="{ minRows: 10, maxRows: 15}"/>-->
    <el-scrollbar class="content">
      <div v-html="form.content" :style="{
            fontSize: textSettingStore.fontSize + 'px'
          }"></div>
    </el-scrollbar>

    <template #footer>
      <div class="dialog-footer">
        <div v-show="!loading">
          <el-button type="success" size="large" @click="copyText" v-blur plain>复制</el-button>
<!--          <el-button type="warning" size="large" @click="reStart" v-blur plain>重新生成</el-button>-->
          <el-button type="danger" size="large" @click="click1" v-blur plain>关闭</el-button>
        </div>
        <div v-show="loading">
          <el-button type="danger" size="large" :loading="loading" v-blur plain>生成中</el-button>
        </div>

      </div>
    </template>
  </el-dialog>
</template>

<script setup>

import {useTextSettingStore} from "../../store/textSetting.js"

const textSettingStore = useTextSettingStore()

import {ElMessage} from "element-plus"
import {fetchEventSource} from "@microsoft/fetch-event-source"
import {getToken} from "../../util/auth.js"
import useClipboard from "vue-clipboard3"
import {useUserStore} from "../../store/user.js";
const visible = ref(false)
const form = reactive({
  title: '',
  url: '',
  data: {},
  content: ''
});
const controller = new AbortController()

const loading = ref(false)
const openDialog = (title, url, data) => {
  form.data = data
  form.url = url
  form.title = title
  form.content= ''
  visible.value = true
  ai()
}


const {toClipboard} = useClipboard()
const copyText = () => {
  const sss = form.content.replaceAll('<br/>', '\n')
  try {
    toClipboard(sss);
    //实现复制
    // alert("复制成功");
    ElMessage({
      message: '复制成功',
      type: 'success',
      offset: 200,
      duration: 2000
    })
  } catch (e) {
    console.error(e);
  }
  // navigator.clipboard.writeText(desc.texttext)
}

const reStart = () => {
  form.content = ''
  ai()
}

defineExpose({ openDialog })

const userStore = useUserStore();
const ai = () => {
  fetchEventSource(form.url, {
    method: 'POST',
    body: JSON.stringify(form.data),
    signal: controller.signal,
    mode: 'cors',
    headers: {
      'Content-Type': 'application/json',
      'workbench-token': 'Bearer ' + getToken()
    },
    async onopen(response) {
      if (response.status !== 200) {
        ElMessage({
          message: '系统错误',
          type: 'error',
          offset: 200,
          duration: 2000
        })
        controller.abort()
      } else {
        form.content = ''
        loading.value = true
      }
    },
    onmessage(msg) {
      const parse = JSON.parse(msg.data)
      console.log(parse.message)
      if (parse.message === 'DONE') {
        loading.value = false
        controller.abort()
        return
      }
      if (parse.message === '' || parse.message === null) {
        return
      }
      const sss = parse.message.replaceAll('\n', '<br/>')
      form.content = form.content + sss
    },
    onclose() {
      console.log('close')
      loading.value = false
      controller.abort()
      userStore.updateUser()
    },
    onerror(error) {
      console.log(error)
      loading.value = false
      controller.abort()
    }
  })
}

const click1 = () => {
  visible.value = false
}

</script>

<style scoped lang="scss">

.content {
  height: calc(100vh * 0.6);
  width: 100%;
  text-align: left;
}

</style>
