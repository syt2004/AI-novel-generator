<template>
  <div class="container">
    <div class="header">
      <div class="header-div header-button">
        <el-icon class="navbar-back-icon" :size="30" @click="close">
          <Back/>
        </el-icon>
      </div>
      <div class="header-div">拆解</div>
      <div class="header-div header-button">

      </div>
    </div>
    <div class="main">
      <div class="aside">

<!--        <div class="a-header">-->
<!--          <el-button-group>-->
<!--            <el-button type="primary" v-blur plain>前5章</el-button>-->
<!--            <el-button type="primary" v-blur plain>前10章</el-button>-->
<!--            <el-button type="primary" v-blur plain>增选10章</el-button>-->
<!--            <el-button type="primary" v-blur plain>重置</el-button>-->
<!--          </el-button-group>-->
<!--        </div>-->
        <div class="a-center">
          <el-scrollbar class="a-center-scrollbar">
            <!--            <p v-for="i of 100">{{i}}</p>-->
            <el-checkbox-group v-model="data.ids" :max="20" @change="change">
              <div v-for="(item, index) in desc.list" :key="index" class="a-center-s-c-g">
                <el-checkbox :label="item.partTitle" :value="item.id" :disabled="item.words === 0"/>
              </div>
            </el-checkbox-group>
          </el-scrollbar>
        </div>
        <div class="a-footer">
          <desc-com :type="data.requiresType" :desc-type="4" :limit="300" :desc="data.requires" :id="data.requiresId" :max-rows="5" :min-rows="1"/>
<!--          <span>拆解要求</span>-->
<!--          <el-tabs-->
<!--              v-model="data.requiresType"-->
<!--              type="card"-->
<!--              style="width: 100%"-->
<!--          >-->
<!--            <el-tab-pane label="内置提示词" :name="1">-->
<!--              <el-select-->
<!--                  v-model="data.requiresId"-->
<!--                  placeholder=""-->
<!--                  v-show="data.requiresType === 1"-->
<!--                  :fit-input-width="true">-->
<!--                <el-option-->
<!--                    v-for="item in desc.requireslist"-->
<!--                    :key="item.id"-->
<!--                    :label="item.name"-->
<!--                    :value="item.id"-->
<!--                    :title="item.des"-->
<!--                >-->
<!--                </el-option>-->
<!--              </el-select>-->
<!--            </el-tab-pane>-->
<!--            <el-tab-pane label="自定义输入" :name="2">-->
<!--              <el-input v-show="data.requiresType === 2" v-model="data.requires" type="textarea" clearable-->
<!--                        placeholder="请输入拆解提示词" :maxlength="300"-->
<!--                        show-word-limit size="large" :autosize="{minRows:1, maxRows: 1}"/>-->
<!--            </el-tab-pane>-->
<!--          </el-tabs>-->

          <div class="a-f-footer">
            <div class="a-f-f-text">&nbsp;</div>
<!--            <div class="a-f-f-text"> 已选{{ data.ids.length }}章,预估消耗{{ count }}字</div>-->
            <div class="a-footer-button-group">
              <div v-show="btnLoading">
                <el-button type="danger" size="large" :loading="btnLoading" v-blur plain>拆解中</el-button>
              </div>
<!--              <el-button-group v-show="!btnLoading" size="large">-->
                <el-button type="primary" v-blur plain @click="chaifen" v-show="!btnLoading">分章拆解</el-button>
                <el-button type="primary" v-blur plain @click="chaihe" v-show="!btnLoading">合并拆解</el-button>
<!--              </el-button-group>-->
            </div>
          </div>
        </div>

      </div>
      <div class="m-main">
        <el-scrollbar class="m-m-scroll">
          <div class="m-m-scroll-text" v-html="desc.texttext" :style="{
            fontSize: textSettingStore.fontSize + 'px'
          }">
          </div>

        </el-scrollbar>



        <el-icon style="z-index: 100; bottom: 50px; right: 50px; position: absolute" size="30" @click="copyText"><CopyDocument /></el-icon>
      </div>


    </div>

<!--    <el-affix position="bottom" :offset="40" z-index="300" style="background-color: red">-->
<!--      <el-icon :size="40"><CopyDocument /></el-icon>-->
<!--    </el-affix>-->


  </div>

</template>

<script setup>


import {myListDesc} from "../api/desc.js"
import {Back, CloseBold, CopyDocument} from "@element-plus/icons-vue"
import {getToken} from "../util/auth.js"
import {fetchEventSource} from '@microsoft/fetch-event-source'
import {ElMessage} from "element-plus"
import useClipboard from "vue-clipboard3"
import {useTextSettingStore} from "../store/textSetting.js"
import {useUserStore} from "../store/user.js"

import DescCom from "../components/DescCom.vue"


const textSettingStore = useTextSettingStore()

const editorOption = {
  modules: {
    // 这里将工具栏隐藏
    toolbar: false,
  },
  linebreaks: 'off'
}
const btnLoading = ref(false)

const {toClipboard} = useClipboard()
const copyText = () => {
  try {
    const sss = desc.texttext.replaceAll('<br/>', '\n')
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
    ElMessage({
      message: '复制失败',
      type: 'error',
      offset: 200,
      duration: 2000
    })
  }
  // navigator.clipboard.writeText(desc.texttext)
}


const data = reactive({
  requiresType: 1,
  requiresId: 0,
  requires: '',
  ids: [],
  bookId: null,
  uuType: 0
})

const count = ref(0)

const texttext = ref('请选择章节拆解')

const change = () => {
  if (data.ids.length === 0) {
    count.value = 0
    return
  }
  let ss = 0
  for (let i = 0; i < desc.list.length; i++) {
    const item = desc.list[i]
    if (data.ids.includes(item.id)) {
      ss += item.words
    }
  }

  count.value = Math.ceil(ss / 2)

}


const desc = reactive({
  requireslist: [],
  list: [],
  texttext: '请选择章节拆解'
})

desc.list = JSON.parse(history.state.listStr)
data.bookId = history.state.bookId

// onMounted(() => {
//   myListDesc({type: 4}).then(res => {
//     desc.requireslist = res.data
//     if (data.requiresId === 0) {
//       console.log('requiress', res.data)
//       data.requiresId = res.data[0].id
//     }
//   }).catch(err => {
//
//   })
// })

const chaihe = () => {
  // const sss = desc.texttext
  // desc.texttext = sss + '111'
  // console.log(texttext.value)
  desc.texttext = ''
  data.uuType = 0
  aibreak()
}


const userStore = useUserStore()

const chaifen = () => {
  // const sss = desc.texttext
  // desc.texttext = sss + '222'
  // console.log(texttext.value)
  desc.texttext = ''
  data.uuType = 1
  aibreak()
}
const controller = new AbortController()
const aibreak = () => {
  if (!data.ids.length) {
    ElMessage({
      message: '请先选择章节',
      type: 'warning',
      offset: 200,
      duration: 2000
    })
    return
  }
  if (userStore.words < 500) {
    ElMessage({
      message: '可使用字数小于500，请充值后使用',
      type: 'warning',
      offset: 200,
      duration: 2000
    })
    return
  }


  fetchEventSource('/pstr/ai/break', {
    method: 'POST',
    body: JSON.stringify(data),
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
        texttext.value = ''
        btnLoading.value = true
      }
    },
    onmessage(msg) {
      const parse = JSON.parse(msg.data);
      console.log(parse.message)
      if (parse.message === 'DONE') {
        btnLoading.value = false
        controller.abort()
        return
      }

      if (parse.message === '' || parse.message === null) {
        return
      }
      const sss = parse.message.replaceAll('\n', '<br/>')
      desc.texttext = desc.texttext + sss
    },
    onclose() {
      console.log('close')
      btnLoading.value = false
      controller.abort()
      userStore.updateUser()
    },
    onerror(error) {
      console.log(error)
      btnLoading.value = false
      controller.abort()
    }
  })
}


const router = useRouter()


const close = () => {
  router.back()
}
</script>

<style scoped lang="scss">

.container {
  //background-color: var(--el-bg-color);
  color: var(--el-text-color-primary);
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;

  .header {
    width: 100%;

    height: var(--nav-bar-height);;
    color: var(--el-text-color-primary);
    border-bottom: 1px solid transparent;
    border-image: linear-gradient(to bottom, transparent 50%, var(--w-border-color) 50%) 0 0 100%/1px 0;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 20px;

    .header-div {
      width: 33%;
    }

    .header-button {
      display: flex;
      justify-content: flex-start;
      margin-left: 20px;
    }
  }

  .main {
    display: flex;
    flex-grow: 1;
    //height: calc(100% - 70px);

    .aside {
      width: 400px;
      border-right: 1px solid transparent;
      border-image: linear-gradient(to right, transparent 50%, var(--w-border-color) 50%) 0 0 100%/1px 0;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: space-around;

      .a-header {
        width: 100%;
        display: flex;
        flex-direction: row;
        align-items: center;
        //flex-grow: 0;
        height: 40px;
        padding-top: 10px;
        padding-bottom: 10px;
      }

      .a-center {
        flex-grow: 1;
        width: 100%;
        padding-left: 25px;
        display: flex;
        flex-direction: column;
        //background-color: #f44336;
        //height: calc(100% - 70px - 120px - 40px);

        .a-center-scrollbar {
          display: flex;
          flex-direction: column;
          width: 100%;
          //height: calc(100% - 70px - 120 - 40px);

          .a-center-s-c-g {
            display: flex;
            padding-left: 15px;
          }
        }
      }

      .a-footer {
        //height: 120px;
        width: 100%;
        //height: 110px;
        //flex-grow: 0;
        //background-color: #535bf2;

        display: flex;
        flex-direction: column;
        align-items: flex-start;
        padding: 10px;

        .a-f-footer {
          display: flex;
          align-items: center;
          flex-direction: column;
          width: 100%;

          .a-f-f-text {
            font-size: 10px;
            line-height: 20px;
            color: var(--el-text-color-primary);
          }

          .a-footer-button-group {
            width: 100%;
            display: flex;
            flex-direction: row;
            justify-content: center;
          }
        }


      }
    }

    //.aside-open{
    //  width: 300px;
    //}
    //
    //.aside-no-open{
    //  width: 0;
    //}
    //
    .m-main {
      width: 100%;
      flex-grow: 1;
      height: calc(100vh - 73px);
      border: var(--w-border-color) 1px solid;
      //padding: 20px;
      //margin: 20px;
      //width: 100%;
      //background-color: #f44336;

      .m-m-scroll {
        //height: calc(100% - 40px);
        //width: calc(100% - 40px);
        //display: flex;
        //background-color: #535bf2;
        //
        left: 20px;
        top: 20px;
        bottom: 20px;
        right: 20px;
        font-size: 18px;
        width: calc(100% - 40px);
        height: calc(100% - 50px);
        border-radius: 10px;
        border: var(--w-border-color) 1px solid;

        .m-m-scroll-text {
          //margin: 20px;
          width: calc(100% - 40px);

          text-align: left;
          border-radius: 10px;
          padding: 10px;
        }
      }


      //display: flex;

      .m-m-s-text {
        border: var(--w-border-color) 1px solid;
        //width: calc(100% - 300px);
        //height: calc(100% - 70px);
        width: 100%;
        height: 100%;
        //display: flex;
        //margin: 20px;
        font-size: 100px;
        display: flex;
        white-space: pre-line;

      }
    }
  }


}

.ql-editor {
  //font-family: 'Arial', sans-serif;
  font-size: 16px;
}

</style>
