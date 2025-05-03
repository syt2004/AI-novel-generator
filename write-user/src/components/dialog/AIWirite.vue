<template>

  <a-modal v-model:visible="visible"
           @cancel="close"
           unmount-on-close
           :mask-closable="false"
           :hide-cancel="true"
           :closable="false"
           :esc-to-close="false"
  width="80%">
    <template #title>
      生成
    </template>
    <el-scrollbar height="60vh">
      <el-form :model="form" :rules="rules" ref="descDialogRef" label-position="top" :v-loading="loading"
               class="desc-dialog">
        <el-form-item label="小说类型（例如都市爱情、玄幻修真）或小说背景故事" prop="bg">
          <el-input type="textarea" v-model="form.bg" clearable placeholder="请输入小说类型" :maxlength="500"
                    show-word-limit :autosize="{minRows:1, maxRows: 10}"/>
        </el-form-item>
        <el-form-item label="" prop="plot">
          <template #label>
            <span>本章剧情</span>
            <el-tooltip>
              <el-icon :size="15">
                <QuestionFilled/>
              </el-icon>
              <template #content>
                <!--                <el-scrollbar>-->
                <div style="font-size: 14px">
                  <p>
                    这里输入你要生成的剧情细纲，不要太长太复杂，否则生成质量低。也不要太短，否则AI可能自由发挥的比较多。例如:</p>
                  <p>1.唐近楼是一名穿越者，重生为明朝小镇富家子弟，遇到疑似武林高手岳不群入住家族客栈。</p>
                  <p>2.得知山贼可能来袭，唐家准备暂避乡下，唐近楼展现出超越年龄的智慧。</p>
                  <p>
                    3.唐近楼对岳不群产生好奇，暗中观察这位武林高手，同时也在为家人的安全考虑。(生成符合人物性格的对话)</p>
                </div>
                <!--                </el-scrollbar>-->
              </template>
            </el-tooltip>
          </template>
          <el-input v-model="form.plot" type="textarea" clearable placeholder="请输入本章剧情" :maxlength="1000"
                    show-word-limit size="large" :autosize="{minRows:4, maxRows: 10}"/>
        </el-form-item>
        <el-form-item label="本章出场人物">
          <role-select v-model:bookId="form.bookId" v-model:roless="form.roless"/>
        </el-form-item>
        <el-form-item label="额外信息(例如剧情地点,临时人物)">
          <el-input v-model="form.relation" type="textarea" clearable placeholder="请输入额外信息" :maxlength="500"
                    show-word-limit size="large" :autosize="{minRows:1, maxRows: 10}"/>
        </el-form-item>

        <el-form-item label="生成风格">
<!--          <el-tabs-->
<!--              v-model="form.styleType"-->
<!--              type="card"-->
<!--              style="width: 100%"-->
<!--          >-->
          <desc-com v-model:type="form.styleType"
                    :descType="2"
                    v-model:desc="form.style"
                    v-model:id="form.styleId"
                    :limit="500"/>

<!--            <el-tab-pane label="内置提示词" :name="1">-->


<!--              <desc-select v-show="form.styleType === 1"-->
<!--                           v-model:id="form.styleId"-->
<!--                           :descType="2"-->
<!--                           v-model:desc="form.style" />-->
<!--              <el-select-->
<!--                  v-model="form.styleId"-->
<!--                  placeholder=""-->
<!--                  v-show="form.styleType === 1"-->
<!--                  :fit-input-width="true">-->
<!--                <el-option-->
<!--                    v-for="item in descData.styles"-->
<!--                    :key="item.id"-->
<!--                    :label="item.name"-->
<!--                    :value="item.id"-->
<!--                    :title="item.des"-->
<!--                >-->
<!--                </el-option>-->
<!--              </el-select>-->
<!--            </el-tab-pane>-->
<!--            <el-tab-pane label="自定义输入" :name="2">-->
<!--              <el-input v-show="form.styleType === 2" v-model="form.style" type="textarea" clearable-->
<!--                        placeholder="请输入生成风格" :maxlength="200"-->
<!--                        show-word-limit size="large" :autosize="{minRows:1, maxRows: 5}"/>-->
<!--            </el-tab-pane>-->
<!--          </el-tabs>-->
        </el-form-item>
        <el-form-item label="生成提示词">
          <desc-com v-model:type="form.requiresType"
                    :descType="3"
                    v-model:desc="form.requires"
                    v-model:id="form.requiresId"
                    :limit="300"/>

<!--          <el-tabs-->
<!--              v-model="form.requiresType"-->
<!--              type="card"-->
<!--              style="width: 100%"-->
<!--          >-->
<!--            <el-tab-pane label="内置提示词" :name="1">-->
<!--              <el-select-->
<!--                  v-model="form.requiresId"-->
<!--                  placeholder=""-->
<!--                  v-show="form.requiresType === 1"-->
<!--                  :fit-input-width="true">-->
<!--                <el-option-->
<!--                    v-for="item in descData.requiress"-->
<!--                    :key="item.id"-->
<!--                    :label="item.name"-->
<!--                    :value="item.id"-->
<!--                    :title="item.des"-->
<!--                >-->
<!--                </el-option>-->
<!--              </el-select>-->
<!--            </el-tab-pane>-->
<!--            <el-tab-pane label="自定义输入" :name="2">-->
<!--              <el-input v-show="form.requiresType === 2" v-model="form.requires" type="textarea" clearable-->
<!--                        placeholder="请输入生成提示词" :maxlength="300"-->
<!--                        show-word-limit size="large" :autosize="{minRows:1, maxRows: 5}"/>-->
<!--            </el-tab-pane>-->
<!--          </el-tabs>-->
        </el-form-item>
      </el-form>

    </el-scrollbar>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="cancel" v-blur>取消</el-button>
        <el-button type="primary" @click="sumbit" v-blur>
          提交
        </el-button>
      </div>
    </template>
  </a-modal>

<!--  <el-dialog title="生成"-->
<!--             v-model="visible"-->
<!--             :show-close="false" :close-on-click-modal="false" :close-on-press-escape="false" destroy-on-close-->
<!--             @close="close">-->
<!--    <el-scrollbar height="60vh">-->
<!--      <el-form :model="form" :rules="rules" ref="descDialogRef" label-position="top" :v-loading="loading"-->
<!--               class="desc-dialog">-->
<!--        <el-form-item label="小说类型（例如都市爱情、玄幻修真）" prop="bg">-->
<!--          <el-input type="textarea" v-model="form.bg" clearable placeholder="请输入小说类型" :maxlength="500"-->
<!--                    show-word-limit :autosize="{minRows:1, maxRows: 10}"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="本章出场人物">-->
<!--          <role-select v-model:bookId="form.bookId" v-model:roless="form.roless"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="出场人物关系">-->
<!--          <el-input v-model="form.relation" type="textarea" clearable placeholder="请输入出场人物关系" :maxlength="500"-->
<!--                    show-word-limit size="large" :autosize="{minRows:1, maxRows: 10}"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="" prop="plot">-->
<!--          <template #label>-->
<!--            <span>本章剧情</span>-->
<!--            <el-tooltip>-->
<!--              <el-icon :size="15">-->
<!--                <QuestionFilled/>-->
<!--              </el-icon>-->
<!--              <template #content>-->
<!--                <el-scrollbar>-->
<!--                  <div style="width: 300px; font-size: 14px">-->
<!--                    <p>-->
<!--                      这里输入你要生成的剧情细纲，不要太长太复杂，否则生成质量低。也不要太短，否则AI可能自由发挥的比较多。例如:</p>-->
<!--                    <p>1.唐近楼是一名穿越者，重生为明朝小镇富家子弟，遇到疑似武林高手岳不群入住家族客栈。</p>-->
<!--                    <p>2.得知山贼可能来袭，唐家准备暂避乡下，唐近楼展现出超越年龄的智慧。</p>-->
<!--                    <p>-->
<!--                      3.唐近楼对岳不群产生好奇，暗中观察这位武林高手，同时也在为家人的安全考虑。(生成符合人物性格的对话)</p>-->
<!--                  </div>-->
<!--                </el-scrollbar>-->
<!--              </template>-->


<!--            </el-tooltip>-->
<!--          </template>-->
<!--          <el-input v-model="form.plot" type="textarea" clearable placeholder="请输入本章剧情" :maxlength="1000"-->
<!--                    show-word-limit size="large" :autosize="{minRows:4, maxRows: 10}"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="生成风格">-->
<!--          <el-tabs-->
<!--              v-model="form.styleType"-->
<!--              type="card"-->
<!--              style="width: 100%"-->
<!--          >-->
<!--            <el-tab-pane label="内置提示词" :name="1">-->
<!--              <el-select-->
<!--                  v-model="form.styleId"-->
<!--                  placeholder=""-->
<!--                  v-show="form.styleType === 1"-->
<!--                  :fit-input-width="true">-->
<!--                <el-option-->
<!--                    v-for="item in descData.styles"-->
<!--                    :key="item.id"-->
<!--                    :label="item.name"-->
<!--                    :value="item.id"-->
<!--                    :title="item.des"-->
<!--                >-->
<!--                </el-option>-->
<!--              </el-select>-->
<!--            </el-tab-pane>-->
<!--            <el-tab-pane label="自定义输入" :name="2">-->
<!--              <el-input v-show="form.styleType === 2" v-model="form.style" type="textarea" clearable-->
<!--                        placeholder="请输入生成风格" :maxlength="200"-->
<!--                        show-word-limit size="large" :autosize="{minRows:1, maxRows: 5}"/>-->
<!--            </el-tab-pane>-->
<!--          </el-tabs>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="生成提示词">-->

<!--          <el-tabs-->
<!--              v-model="form.requiresType"-->
<!--              type="card"-->
<!--              style="width: 100%"-->
<!--          >-->
<!--            <el-tab-pane label="内置提示词" :name="1">-->
<!--              <el-select-->
<!--                  v-model="form.requiresId"-->
<!--                  placeholder=""-->
<!--                  v-show="form.requiresType === 1"-->
<!--                  :fit-input-width="true">-->
<!--                <el-option-->
<!--                    v-for="item in descData.requiress"-->
<!--                    :key="item.id"-->
<!--                    :label="item.name"-->
<!--                    :value="item.id"-->
<!--                    :title="item.des"-->
<!--                >-->
<!--                </el-option>-->
<!--              </el-select>-->
<!--            </el-tab-pane>-->
<!--            <el-tab-pane label="自定义输入" :name="2">-->
<!--              <el-input v-show="form.requiresType === 2" v-model="form.requires" type="textarea" clearable-->
<!--                        placeholder="请输入生成提示词" :maxlength="300"-->
<!--                        show-word-limit size="large" :autosize="{minRows:1, maxRows: 5}"/>-->
<!--            </el-tab-pane>-->
<!--          </el-tabs>-->
<!--        </el-form-item>-->
<!--      </el-form>-->

<!--    </el-scrollbar>-->
<!--    <template #footer>-->
<!--      <div class="dialog-footer">-->
<!--        <el-button @click="cancel" v-blur>取消</el-button>-->
<!--        <el-button type="primary" @click="sumbit" v-blur>-->
<!--          提交-->
<!--        </el-button>-->
<!--      </div>-->
<!--    </template>-->
<!--  </el-dialog>-->

</template>

<script setup>

import {infoTemplate, updateTemplate} from "../../api/template.js"
import {QuestionFilled} from "@element-plus/icons-vue"
import {myListDesc} from "../../api/desc.js"
import {listRoleByAI} from "../../api/role.js"
import {defineExpose, ref} from "vue";
import {aiWriterAction} from "../../api/ai.js"
import {fetchEventSource} from "@microsoft/fetch-event-source";
import {getToken} from "../../util/auth.js";
import {ElMessage} from "element-plus";
import {useUserStore} from "../../store/user.js"
import RoleSelect from "../RoleSelect.vue";
import DescCom from "../DescCom.vue"
import DescSelect from "../DescSelect.vue"

const visible = ref(false)



//
// let tm;
// onMounted(() => {
//   tm = setInterval(() => {
//     update()
//   }, 10000)
// })
//
// onUnmounted(() => {
//   clearTimeout(tm)
//   tm = null;
// })


let form = reactive({
  id: null,
  bg: '',
  relation: '',
  plot: '',
  styleType: 1,
  styleId: 0,
  style: '',
  requiresType: 1,
  requiresId: 0,
  requires: '',
  roles: '',
  roless: [],
  bookId: null,
})

const openWriteDialog = (bookId) => {
  resetForm()
  // if (id) {
  //   // infoBook(id).then(res => {
  //   //   form.title = res.data.title
  //   //   form.content = res.data.content
  //   //   form.id = id
  //   // })
  // }
  visible.value = true
  open(bookId)
}

defineExpose({openWriteDialog})


const loading = ref(false)
const isUpdate = ref(false)


const resetForm = () => {
  form.id = null
  form.bg = ''
  form.relation = ''
  form.plot = ''
  form.styleType = 1
  form.styleId = 0
  form.style = ''
  form.requiresType = 1
  form.requiresId = 0
  form.requires = ''
  form.roles = ''
  form.roless = []
  form.bookId = null
}

const rules = reactive({
  bg: [
    {required: true, message: '请输入故事背景', trigger: 'blur'},
  ],
  plot: [
    {required: true, message: '请输入本章剧情', trigger: 'blur'},
  ],
  style: [
    {required: true, message: '请输入生成风格', trigger: 'blur'},
  ],
  requires: [
    {required: true, message: '请输入生成提示词', trigger: 'blur'},
  ],
})

const descData = reactive({
  styles: [],
  requiress: [],
})

const open = (bookId) => {
  console.log('open')
  loading.value = true
  infoTemplate({bookId: bookId}).then(res => {
    console.log(res)
    console.log(res.data.id)
    if (res.data.id !== undefined) {
      console.log("data")
      form.id = res.data.id
      form.bg = res.data.bg
      form.relation = res.data.relation
      form.plot = res.data.plot
      form.styleType = res.data.styleType
      form.styleId = res.data.styleId
      form.style = res.data.style
      form.requiresType = res.data.requiresType
      form.requiresId = res.data.requiresId
      form.requires = res.data.requires
      form.roles = res.data.roles
      if (form.roles) {
        form.roless = res.data.roles.split(',')
      } else {
        form.roless = []
      }
    }
    form.bookId = bookId
    otherData()
    loading.value = false
    isUpdate.value = true

    console.log(isUpdate.value)
  }).catch(err => {
    loading.value = false
  })


}


const otherData = () => {
  // myListDesc({type: 2}).then(res => {
  //   descData.styles = res.data
  //   if (form.styleId === 0) {
  //     form.styleId = res.data[0].id
  //   }
  // }).catch(err => {
  //
  // })

  // myListDesc({type: 3}).then(res => {
  //   descData.requiress = res.data
  //   if (form.requiresId === 0) {
  //     form.requiresId = res.data[0].id
  //   }
  // }).catch(err => {
  //
  // })



}

const close = () => {
  resetForm()
  // isUpdate.value = false
}

const cancel = () => {
  visible.value = false
}


watch([
  () => form.bg,
  () => form.relation,
  () => form.plot,
  () => form.styleType,
  () => form.styleId,
  () => form.style,
  () => form.requiresType,
  () => form.requiresId,
  () => form.roless
], (newVal, oldVal) => {
  console.log(2222)
  console.log(isUpdate.value)
  if (isUpdate.value) {
    update()
  }
})

const emit = defineEmits(['submitTop'])
// const update = () => {
//   emit('sumbitTop', partObj)
// }
const userStore = useUserStore()
const descDialogRef = ref()
const controller = new AbortController()
const sumbit = async () => {
  if (!descDialogRef) return
  await descDialogRef.value.validate((valid, fields) => {
    if (valid) {
      if (userStore.words < 500) {
        ElMessage({
          message: '可使用字数小于500，请充值后使用',
          type: 'warning',
          offset: 200,
          duration: 2000
        })
        return
      }
      visible.value = false
      emit('submitTop', form)
    }
  })

}

const update = async () => {
  if (!descDialogRef.value) return
  console.log(descDialogRef)
  await descDialogRef.value.validate((valid, fields) => {
    if (valid) {
      if (form.roless === null || form.roless === [] || form.roless === '') {
        form.roles = ''
      } else {
        form.roles = form.roless.join(',')
      }
      updateTemplate(form).then(res => {
        form.id = res.data.id
      }).catch(err => {

      })
    }
  })
}


</script>

<style scoped lang="scss">

.desc-dialog {
  min-width: 450px;
  width: 100%;
}

</style>
