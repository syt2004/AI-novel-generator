<template>
  <el-container class="c-m-aside-container">
    <el-header class="c-m-aside-header">
      <div class="c-m-a-header-btn">
        <el-button v-blur type="text" @click="newPart" size="large" class="c-m-a-header-btn">
          <el-icon>
            <DocumentAdd/>
          </el-icon>&nbsp;新建章节
        </el-button>
      </div>
<!--      <div class="c-m-a-header-btn">-->
<!--        <el-button v-blur type="text" @click="sortPart" size="large" class="c-m-a-header-btn">-->
<!--          <el-icon>-->
<!--            <DocumentAdd/>-->
<!--          </el-icon>&nbsp;切换排序-->
<!--        </el-button>-->
<!--      </div>-->
    </el-header>
    <el-main class="c-m-aside-main">
      <el-empty description="该书还没有章节" v-if="!list.length"/>
      <div v-else class="c-m-a-main-div">
        <VueDraggable
            v-model="list"
            animation="150"
            group="people"
            ghostClass="ghost"
            @update="onUpdate"
            @add="onAdd"
            @remove="remove">
          <div v-for="(item, index) in list" :key="index" @click="selectPart(index)">
            <div class="c-m-a-main-menu-select c-m-a-main-menu" v-if="item.id === selectId">
              <!--          <div class="c-m-a-main-menu-select c-m-a-main-menu">-->
              <div class="c-m-a-main-menu-select-text">
                <el-text class="c-m-a-main-menu-title" truncated>{{ item.partTitle }}</el-text>
                <el-text class="c-m-a-main-menu-words" truncated>{{ item.words }}</el-text>
              </div>
              <div class="c-m-a-main-menu-select-button">
                <el-icon class="navbar-list-icon" :size="25" @click="removePart(index)">
                  <DeleteFilled/>
                </el-icon>
              </div>
            </div>
            <div class="c-m-a-main-menu-noselect c-m-a-main-menu" v-else>
              <el-text class="c-m-a-main-menu-title" truncated>{{ item.partTitle }}</el-text>
              <el-text class="c-m-a-main-menu-words" truncated>{{ item.words }}</el-text>
            </div>
          </div>

        </VueDraggable>




      </div>
    </el-main>
  </el-container>
</template>

<script setup>

import { VueDraggable } from 'vue-draggable-plus'
const onUpdate = () => {
  console.log('update')
  let sss = []

  for (let i = 0; i < list.value.length; i++) {
    console.log(list.value)
    sss.unshift(list.value[i].id)
  }
  console.log(sss)
  emit('ySortPart', sss)
}
const onAdd = () => {
  console.log('add')
}
const remove = () => {
  console.log('remove')
}

const list = defineModel('list', {
  type: Array
})

const selectId = defineModel('selectId', {
  type: Number
})


import {DocumentAdd, DeleteFilled} from "@element-plus/icons-vue"
import {ElMessage, ElMessageBox} from "element-plus";


const emit = defineEmits(['newPart', 'sortPart', 'selectPart', 'removePart', 'ySortPart']);
// 添加章节
const newPart = () => {
  emit('newPart')
}

const sortPart = () => {
  emit('sortPart')
}

const selectPart = (index) => {
  emit('selectPart', index)
}

const removePart = (index) => {
  ElMessageBox.confirm(
      '章节删除后无法恢复，确定要删除这个章节吗？',
      '删除章节',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(() => {
    emit('removePart', index)
  }).catch(() => {

  })

}

</script>

<style scoped lang="scss">
.c-m-aside-container {
  border-right: 1px solid transparent;
  border-image: linear-gradient(to right, transparent 50%, var(--w-border-color) 50%) 0 0 100%/1px 0;
  width: 100%;
  height: 100%;
  background-color: var(--el-bg-color);
  //background-color: aqua;
  .c-m-aside-header {
    height: 60px;
    width: 100%;
    display: flex;
    //margin-right: 1px;
    //background-color: aqua;
    align-items: center;
    justify-content: space-between;
    border-bottom: 1px solid transparent;
    border-image: linear-gradient(to bottom, transparent 50%, var(--w-border-color) 50%) 0 0 100%/1px 0;

    .c-m-a-header-btn {
      flex-grow: 1;
      font-size: 16px;
    }
  }

  .c-m-aside-main {
    //margin-left: 1px;
    //background-color: #535bf2;
    height: calc(100% - 60px);
    display: flex;
    justify-content: center;
    padding: 0;
    font-size: 14px;

    .c-m-a-main-div {
      display: flex;
      flex-direction: column;
      width: 100%;
      //background-color: #535bf2;
      .c-m-a-main-menu {
        width: 100%;
        border-bottom: 1px solid transparent;
        border-image: linear-gradient(to bottom, transparent 50%, var(--w-border-color) 50%) 0 0 100%/1px 0;
        color: var(--el-text-color-primary);
        //background-color: #535bf2;
      }

      .c-m-a-main-menu-noselect {
        height: 50px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        width: 100%;
        text-align: left;
        //padding-left: 20px;

        .c-m-a-main-menu-title {
          padding-left: 20px;
          display: flex;
          justify-content: flex-start;
          width: 70%;
        }

        .c-m-a-main-menu-words {
          display: flex;
          justify-content: flex-start;
          padding-right: 20px;
          //width: 30%;
          //text-align: right;
        }
      }

      .c-m-a-main-menu-select {
        height: 80px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        background-color: var(--w-content-aside-bg-color);
        text-align: left;

        .c-m-a-main-menu-select-text {
          display: flex;
          flex-direction: column;
          justify-content: center;
          //align-items: flex-start;
          width: 70%;
          padding-left: 20px;
          text-align: left;

          .c-m-a-main-menu-title {
            width: 100%;
            text-align: left;
            //padding-top: 15px;
          }

          .c-m-a-main-menu-words {
            padding-top: 15px;
            width: 100%;
          }
        }

        .c-m-a-main-menu-select-button {
          padding-right: 20px;
        }

      }

    }


  }
}


</style>
