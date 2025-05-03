<template>
  <el-empty description="该书还没有章节" v-show="!partObj.id"/>
  <div v-show="partObj.id" ref="cmmmm" class="c-m-m-m-m">
    <div ref="cmmmmtop" class="c-m-m-m-m-top">
      <div class="c-main-main-menu">
        <div class="c-main-main-menu-group">
          <el-button type="warning" size="large" @click="changeAsideStatus" v-blur plain v-show="asideStatus">
            收起
          </el-button>
          <el-button type="warning" size="large" @click="changeAsideStatus" v-blur plain v-show="!asideStatus">
            打开
          </el-button>
          <el-button type="primary" size="large" @click="openAIWriteDialog" v-blur plain>
            生成
          </el-button>
          <el-button type="success" size="large" @click="openAIPolishDialog" v-blur plain>
            扩写优化
          </el-button>
          <!--        <el-button type="warning" size="large" @click="openXuxieDialog" v-blur plain>-->
          <!--          AI续写-->
          <!--        </el-button>-->
          <el-button type="danger" size="large" @click="openAiBreak" v-blur plain>
            拆解
          </el-button>
        </div>
        <div class="c-main-main-menu-text">
          <span v-if="partsss.updateTime || partsss.createTime">最后更新&nbsp;{{partsss.updateTime ? partsss.updateTime : partsss.createTime}}</span>
        </div>


      </div>
      <!--    {{part}}-->
      <div class="c-main-main-title">
        <el-input v-model="partObj.partTitle" size="large" clearable placeholder="请输入章节标题" :maxlength="20"
                  show-word-limit/>
      </div>
    </div>


    <div class="c-main-main-content-div">
<!--      {{partObj.content}}-->
<!--      <textarea v-model="partObj.content" style="height: 98%; width: 100%;padding-right: 2px;padding-bottom: 20px;" class="c-main-main-content-div-area"></textarea>-->

<!--      <el-scrollbar class="c-main-main-content-el" >-->
<!--      {{height}}-->
<!--      <div :contenteditable="true">{{partObj.content}}</div>-->
<!--      <content-text-div v-model:content="partObj.content"/>-->
        <el-input v-model="partObj.content"
                  type="textarea"
                  clearable
                  placeholder="请输入章节内容"
                  show-word-limit
                  size="large"
                  class="c-main-main-content-el-input"
                  ref="textareaRef"
                  :style="{fontSize: textSettingStore.fontSize + 'px', lineHeight: textSettingStore.fontLine * textSettingStore.fontSize + 'px'}"
                  :input-style="{height: 'calc(100vh - 70px - 70px - 70px - 12px)',  width: 'calc(100%)', paddingRight: '2px'}"/>

<!--                  />-->
<!--      </el-scrollbar>-->
    </div>







<!--    <div class="c-main-main-search">搜索</div>-->
<!--    <div class="c-main-main-replace">替换</div>-->
<!--    <div class="c-main-main-content">-->

<!--&lt;!&ndash;      <md-editor class="c-main-main-content-text"&ndash;&gt;-->
<!--&lt;!&ndash;                 ref="editorRef"&ndash;&gt;-->
<!--&lt;!&ndash;                 v-model="partObj.content"&ndash;&gt;-->
<!--&lt;!&ndash;                 :preview="false"&ndash;&gt;-->
<!--&lt;!&ndash;                 no-highlight&ndash;&gt;-->
<!--&lt;!&ndash;                 theme="dark"&ndash;&gt;-->
<!--&lt;!&ndash;                 :toolbars="[]"&ndash;&gt;-->
<!--&lt;!&ndash;                />&ndash;&gt;-->
<!--      <el-input v-model="partObj.content"-->
<!--                type="textarea"-->
<!--                clearable-->
<!--                placeholder="请输入章节内容"-->
<!--                show-word-limit-->
<!--                size="large"-->
<!--                resize="none"-->
<!--                style="overflow-x:visible;overflow-y:visible;"-->
<!--                class="c-main-main-content-text"/>-->
<!--&lt;!&ndash;      {{textSettingStore.fontSize}}-{{textSettingStore.fontLine}}&ndash;&gt;-->
<!--&lt;!&ndash;      <textarea v-model="partObj.content" type="text" :style="{fontSize: textSettingStore.fontSize + 'px', lineHeight: textSettingStore.fontLine}" class="c-main-main-content-text"></textarea>&ndash;&gt;-->
<!--    </div>-->
  </div>
  <AIWirite ref="aiWriteRef" @submitTop="submitTop($event, data)"/>
  <ContentText ref="contentTextRef"/>
  <AIPolish ref="aiPolishRef" @submitData="submitData($event, data)"/>

</template>
<script setup>

import ContentTextDiv from './ContentTextDiv.vue'


const asideStatus = defineModel('asideStatus', {
  type: Boolean,
  default: true
});

const changeAsideStatus = () => {

  asideStatus.value = !asideStatus.value
  console.log(asideStatus.value)
}


import {useDescDialogStore} from "../../store/descDialog.js"
const dialogStore = useDescDialogStore()

const textSettingStore = useTextSettingStore()
const textareaRef = ref(null)
const getScreenHeight = () => {
  console.log('getScreenHeight')
  console.log(window.innerHeight)
  return window.innerHeight
}

let tm;
onMounted(() => {
  tm = setInterval(() => {
    if (partsss.partTitle !== partObj.partTitle || partsss.content !== partObj.content) {
      update()
    }
  }, 10000)
})
// let timerr;
//
//
//
// let height = ref(20);
// let onMountedStatus = ref(false)
//
// const getHeight = () => {
//   if (timerr) clearTimeout(timerr)
//   console.log(cmmmm.value)
//   if (cmmmm.value.clientHeight && cmmmmtop.value.clientHeight) {
//     console.log('cmmmm.value.clientHeight')
//     console.log(cmmmm.value.clientHeight)
//     console.log('cmmmmtop.value.clientHeight')
//     console.log(cmmmmtop.value.clientHeight)
//     const ssss = getScreenHeight() - 70 - 20 - cmmmmtop.value.clientHeight
//     height.value = ssss
//     console.log('height.value')
//     console.log(height.value)
//     onMountedStatus.value = true
//     return
//   }
//   timerr = setTimeout(() => {
//     getHeight()
//   }, 500)
// }
//
onUnmounted(() => {
  if (tm) clearTimeout(tm)
})


const editorRef = ref(null)


const submitTop = (data) => {
  contentTextRef.value.openDialog('生成', '/pstr/ai/write', data)
}


const submitData = (data) => {
  contentTextRef.value.openDialog('扩写优化', '/pstr/ai/polish', data)
}


const openXuxieDialog = (data) => {
  contentTextRef.value.openDialog()
}



const partObj = reactive({
  partTitle: '',
  content: '',
  id: null,
  bookId: null,
})


let partsss = reactive({
  partTitle: '',
  content: '',
  createTime: '',
  updateTime: ''

});

const sesPart = (part) => {
  console.log("sesPart")
  partsss.content = part.content
  partsss.partTitle = part.partTitle
  partsss.createTime = part.createTime
  partsss.updateTime = part.updateTime
  partObj.content = part.content
  partObj.partTitle = part.partTitle
  partObj.bookId = part.bookId
  partObj.id = part.id
  console.log(textareaRef.value)
  // textareaRef.value.resizeTextarea()
}

defineExpose({sesPart})


const aiWriteRef = ref(null)
const contentTextRef = ref(null)
const aiPolishRef = ref(null)

const openAIWriteDialog = () => {
  aiWriteRef.value.openWriteDialog(partObj.bookId)
}

import {ElMessage} from "element-plus"


const getSelectedText = (event) => {
  const selection = window.getSelection();
  if (selection && selection.rangeCount > 0) {
    return selection.toString();
  }
  return ''
}

const openAIPolishDialog = () => {
  const selectText = getSelectedText()
  if (selectText.length < 10) {
    ElMessage({
      message: '请先选择要扩写优化的文本, 不能少于10个字',
      type: 'warning',
      offset: 200,
      duration: 2000
    })
    return
  }
  // ElMessage({
  //   message: selectText,
  //   type: 'warning',
  //   offset: 200,
  //   duration: 2000
  // })
  // return;
  aiPolishRef.value.openPolishDialog(partObj.bookId, selectText)
}

const cmmmm = ref()
const cmmmmtop = ref()
// watch([
//   () => partObj.content,
//   () => partObj.partTitle,
//   () => partObj.bookId,
//   () => partObj.id
// ], (newVal, oldVal) => {
//   update()
// })


const emit = defineEmits(['updatessssPart', 'pushAIbreak'])
const update = () => {
  emit('updatessssPart', partObj)
}


const openAiBreak = () => {
  emit('pushAIbreak')
}

import {useSettingStore} from "../../store/setting.js"
import AIWirite from "../dialog/AIWirite.vue"
import AIPolish from "../dialog/AIPolish.vue";
import ContentText from "../dialog/ContentText.vue";
import {useTextSettingStore} from "../../store/textSetting.js"






</script>

<style scoped lang="scss">



.c-m-m-m-m {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  overflow: hidden;

  .c-m-m-m-m-top {
    display: flex;
    flex-direction: column;
  }
}

.c-main-main-menu {
  min-height: 70px;
  width: 100%;
  top: 0;
  display: flex;
  //flex-flow: column;
  flex-wrap: wrap;
  //align-content: center;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid transparent;
  border-image: linear-gradient(to bottom, transparent 50%, var(--w-border-color) 50%) 0 0 100%/1px 0;


  .c-main-main-menu-group {
    //padding: 10px;
    //width: 100%;
    display: flex;
    align-items: center;
    justify-content: flex-start;

  }

  .c-main-main-menu-text {
    //background-color: red;
    display: flex;
    flex-flow: column;
    justify-content: center;
    //align-self: center;
    color: var(--el-text-color-primary);
    font-size: 14px;
    //width: 25%;
  }

  //background-color: #535bf2;
}

.c-main-main-title {
  //padding-top: 10px;
  padding-bottom: 10px;
  height: 50px;
}

.c-main-main-content-div {
  //width: 100%;
  flex-grow: 1;
  text-align: left;
  //display: flex;
  //background-color: #f44336;

  //.c-main-main-content-div-des {
  //  //flex-grow: 1;
  //  width: 100%;
  //}

  .c-main-main-content-el-input {
    //width: 100%;
    //background-color: #f44336;
    //height: 100%;
    //top: 0;
    //bottom: 0;

  }

  .c-main-main-content-el {
    //height: calc(100vh - 70px - 60px - 80px);
    //height: 100%;
    width: 100%;
    //flex-grow: 1;
    //padding: ;
    //background-color: #f44336
  }

  .c-main-main-content-div-area {
    //border: var(--el-input-hover-border-color) 1px solid;
    box-shadow: 0 0 0 1px var(--el-input-border-color, var(--el-border-color)) inset;
    border-radius: 4px;
  }
}






.c-main-main-content {
  //height: calc(100vh - 70px - 60px - 80px);
  height: 200px;
  width: calc(100% - 20px);
  //background-color: #535bf2;

  //.el-textarea__inner {
  //  height: 150px;
  //  overflow-y: auto;
  //  /* overflow-y: auto;兼容ie  */
  //}

  //display: flex;
  .c-main-main-content-text {
    width: 100%;
    //height: 100%;
    height: calc(100vh - 70px - 60px - 80px);
    text-align: left;
    font-size: 16px;
    //overflow-x:visible;
    //overflow-y:visible;
    padding: 10px;
    border: var(--w-border-color) 1px solid;
    outline-color: var(--el-input-focus-border-color);
    //bottom: 0;
    //top: 0;
    //min-height: 200px;
    //display: flex;
  }
}

.c-main-container {
  display: flex;
  height: 100vh;
  width: 100%;
  //background-color: #535bf2;

  .c-main-aside {
    width: 250px;
    border-right: 1px solid transparent;
    border-image: linear-gradient(to right, transparent 50%, #e8e8e8 50%) 0 0 100%/1px 0;
    //height: 100%;
    //background-color: #fff;
  }

  .c-main-main {
    //margin-left: 20px;
    border-left: 1px solid transparent;
    border-image: linear-gradient(to left, transparent 50%, var(--w-border-color) 50%) 0 0 100%/1px 0;
  }
}



</style>
