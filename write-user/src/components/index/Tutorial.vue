<template>
  <span class="t-text" @click="jiaocheng">教程</span>
</template>

<script setup>

const jiaocheng = () => {
  window.open(import.meta.env.VITE_APP_JC,'_blank')
}

</script>

<style scoped lang="scss">

.t-text {
  color: var(--el-text-color-primary);
  font-size: 16px;
}

</style>
