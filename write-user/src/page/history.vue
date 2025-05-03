

<template>
  <el-container class="history-container">
    <el-header class="history-header">
      <history-nav-bar />
    </el-header>

    <history-main />
  </el-container>
</template>

<script setup>

import HistoryMain from "../components/history/HistoryMain.vue";
import HistoryNavBar from "../components/history/HistoryNavBar.vue";
</script>

<style scoped lang="scss">

.history-container {
  height: 100%;
  width: 100%;
}
.history-header {
  height: var(--nav-bar-height);
  border-bottom: 1px solid transparent;
  border-image: linear-gradient(to bottom,transparent 50%, var(--w-border-color) 50%) 0 0 100%/1px 0;
}
.history-main {
  height: calc(100% - var(--nav-bar-height));
  //margin-top: 1px;
  //top: 0;
  //bottom: 0;
  //background-color: #646cff;



}

</style>
