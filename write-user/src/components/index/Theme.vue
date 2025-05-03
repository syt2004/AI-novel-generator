<template>
    <svg-icon name="sun" v-show="settingStore.theme === 'dark'" @click="change" :size="size"/>
    <svg-icon name="moon" v-show="settingStore.theme === 'light'" @click="change" :size="size"/>
</template>

<script setup>
import {useSettingStore} from "../../store/setting.js"
import SvgIcon from "../SvgIcon.vue";
const settingStore = useSettingStore()

const size = defineModel('size', {
  default: 40,
  type: Number
});

const change = () => {
  if (settingStore.theme === 'dark') {
    settingStore.lightTheme()
  } else {
    settingStore.darkTheme()
  }
}
</script>

<style scoped lang="scss">

</style>
