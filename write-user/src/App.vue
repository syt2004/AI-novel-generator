

<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script setup>

import {useSettingStore} from "./store/setting.js"
const settingStore = useSettingStore()
import {useDark, useToggle} from "@vueuse/core"
const isDark = useDark()
const toggleDark = useToggle(isDark)


watch(() => settingStore.theme, (newVal, oldVal) => {
  if (newVal === 'dark') {
    toggleDark(true)
    document.body.setAttribute('arco-theme', 'dark')
  } else if (newVal === 'light') {
    toggleDark(false)
    document.body.removeAttribute('arco-theme')
  }
})

onMounted(() => {
  if (settingStore.theme === 'dark') {
    toggleDark(true)
    document.body.setAttribute('arco-theme', 'dark')
  } else if (settingStore.theme === 'light') {
    toggleDark(false)
    document.body.removeAttribute('arco-theme')
  }
})



</script>

<style scoped lang="scss">





</style>
