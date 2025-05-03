<template>
  <div class="navbar-div">
    <div class="navbar-back">
      <el-icon class="navbar-back-icon" :size="30" @click="goBack">
        <Back/>
      </el-icon>
    </div>
    <div class="navbar-title">历史记录</div>
    <div class="navbar-list">
<!--      <TextSetting :size="25" style="margin-left: 20px"/>-->



<!--      <el-popover-->
<!--          placement="bottom"-->
<!--          title="Title"-->
<!--          :width="200"-->
<!--          trigger="click"-->
<!--          content="this is content, this is content, this is content"-->
<!--      >-->
<!--        <template #reference>-->
<!--          <svg-icon name="menu" :size="30"/>-->
<!--        </template>-->
<!--      </el-popover>-->


<!--      <el-dropdown class="book-div-footer-button">-->
<!--        <svg-icon name="menu" :size="30"/>-->
<!--&lt;!&ndash;        <el-icon class="navbar-back-icon" :size="30">&ndash;&gt;-->
<!--&lt;!&ndash;          <Grid />&ndash;&gt;-->
<!--&lt;!&ndash;        </el-icon>&ndash;&gt;-->
<!--        <template #dropdown>-->
<!--          <el-dropdown-menu>-->
<!--            <el-dropdown-item @click="openAddBook">作品信息</el-dropdown-item>-->
<!--            <el-dropdown-item @click="openBookRole">人物管理</el-dropdown-item>-->
<!--          </el-dropdown-menu>-->
<!--        </template>-->
<!--      </el-dropdown>-->





      <!--      </div>-->
    </div>


  </div>


</template>

<script setup>
import {Grid, Back} from '@element-plus/icons-vue'


import {useRouter} from "vue-router"
const router = useRouter()


// 返回
const goBack = () => {
  router.back()
}









</script>

<style scoped lang="scss">
.navbar-div {
  justify-content: space-between;
  align-items: center;
  //align-content: center;
  width: 100%;
  height: 100%;
  flex-direction: row;
  display: flex;
}

.navbar-back {
  color: var(--el-text-color-primary);
  width: 33%;
  display: flex;
  justify-content: flex-start;

}

.navbar-title {
  color: var(--el-text-color-primary);
  width: 33%;
  font-size: 20px;
}

.navbar-list {
  width: 33%;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  align-content: center;

  .book-div-footer-button {
    padding-left: 10px;
  }
}

.nav-list-icon {
  margin-left: 5px;
  margin-right: 5px;
}

.nav-list-icon-content {
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;

  .nav-avatar-button {
    width: 100%;
    margin-top: 5px;
  }
}


</style>
