<template>
  <el-input v-model="content"
            type="textarea"
            clearable
            placeholder="请输入章节内容"
            show-word-limit
            size="large"
            class="c-main-main-content-el-input"
            ref="textareaRef"
            autosize
            :style="{fontSize: textSettingStore.fontSize + 'px', lineHeight: textSettingStore.fontLine * textSettingStore.fontSize + 'px'}"
  />
</template>

<script setup>

import {useTextSettingStore} from "../../store/textSetting.js";
const content = defineModel('content')
const textSettingStore = useTextSettingStore()

</script>

<style scoped lang="scss">

.c-main-main-content-el-input {
  width: 100%;
  background-color: #f44336;
  height: 100%;

}

</style>
