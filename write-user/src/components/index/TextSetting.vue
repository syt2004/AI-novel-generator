<template>

  <svg-icon name="setting" :size="size" @click="showDrawer"/>

  <el-drawer
      v-model="show"
      direction="rtl"
      :size="350"
      :show-close="false"
      destroy-on-close
      :with-header="false"
  >
<!--    <template #header="{ close, titleId, titleClass }">-->
<!--      -->
<!--    </template>-->

    <div class="drawer-header ww-border-bottom">
      <span>设置</span>
      <svg-icon name="close" @click="hiddenDrawer" :size="20" style="padding-top: 5px"/>
    </div>
    <div>
      <el-scrollbar class="drawer-main">
        <div class="d-a-item">
          <div class="d-a-item-title">
            字号
          </div>
          <div class="d-a-item-btn">

            <el-button type="text" @click="fontSizeSub" v-blur>
              <svg-icon name="sub"/>
            </el-button>

            <span>{{fontSize}}</span>

            <el-button type="text" @click="fontSizeAdd" v-blur>
              <svg-icon name="add"/>
            </el-button>

          </div>

        </div>
<!--        <div class="d-a-item">-->
<!--          <div class="d-a-item-title">-->
<!--            字体-->
<!--          </div>-->
<!--          <div class="d-a-item-btn">-->
<!--            <el-select v-model="font" placeholder="请选择字体"/>-->
<!--          </div>-->

<!--        </div>-->
<!--        <div class="d-a-item">-->
<!--          <div class="d-a-item-title">-->
<!--            行距-->
<!--          </div>-->
<!--          <div class="d-a-item-btn">-->

<!--            <el-button type="text" @click="fontLineSub" v-blur>-->
<!--              <svg-icon name="moon"/>-->
<!--            </el-button>-->

<!--            <span>{{fontLine}}</span>-->

<!--            <el-button type="text" @click="fontLineAdd" v-blur>-->
<!--              <svg-icon name="moon"/>-->
<!--            </el-button>-->

<!--          </div>-->
<!--        </div>-->

      </el-scrollbar>
    </div>



  </el-drawer>

</template>


<script setup>


import SvgIcon from "../SvgIcon.vue"
import {useTextSettingStore} from "../../store/textSetting.js"
import BigNumber from "bignumber.js";

const settingStore = useTextSettingStore()

const {fontSize, fontLine} = storeToRefs(settingStore)

const size = defineModel('size', {
  type: Number
})

const show = ref(false);


// 显示文字设置抽屉
const showDrawer = () => {
  show.value = true
}

const hiddenDrawer = () => {
  show.value = false
}

const fontSizeAdd = () => {
  if (fontSize.value < 50) {
    fontSize.value = fontSize.value + 1
  }
}

const fontSizeSub = () => {
  if (fontSize.value > 5) {
    fontSize.value = fontSize.value - 1
  }
}

const fontLineAdd = () => {
  if (fontLine.value < 5) {
    fontLine.value = new BigNumber(fontLine.value).plus(new BigNumber(0.1))
  }
}

const fontLineSub = () => {
  if (fontLine.value > 1) {
    fontLine.value = new BigNumber(fontLine.value).minus(new BigNumber(0.1))
  }
}



</script>

<style scoped lang="scss">

.drawer-header {
  color: var(--el-text-color-primary);
  font-size: 20px;
  display: flex;
  justify-content: space-between;
  height: 50px;
  //padding: 0;
}

.drawer-main {
  color: var(--el-text-color-primary);
  display: flex;
  flex-direction: column;

  .d-a-item {
    display: flex;
    height: var(--nav-bar-height);
    align-items: center;

    .d-a-item-title {
      width: 50px;
      //background-color: #535bf2;

    }

    .d-a-item-btn {
      padding-left: 20px;
      flex-grow: 1;
      display: flex;
      justify-content: space-around;
      align-items: center;
      padding-right: 20px;
    }

  }
}

</style>
