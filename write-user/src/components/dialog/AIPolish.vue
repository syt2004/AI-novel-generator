<template>
  <el-dialog title="扩写优化"
             v-model="visible"
             :show-close="false" :close-on-click-modal="false" :close-on-press-escape="false"
              destroy-on-close>
    <el-scrollbar height="60vh">
      <el-form :model="form" :rules="rules" ref="descDialogRef" label-position="top" :v-loading="loading" class="desc-dialog">
        <el-form-item label="扩写优化文本" prop="content">
          <el-input type="textarea" v-model="form.content" clearable :maxlength="2000"
                    show-word-limit :autosize="{minRows:4, maxRows: 10}"/>
        </el-form-item>
        <el-form-item label="本章出场人物">
          <role-select v-model:bookId="form.bookId" v-model:roless="form.roless"/>
        </el-form-item>
        <el-form-item label="额外信息(例如剧情地点,临时人物)">
          <el-input v-model="form.relation" type="textarea" clearable placeholder="请输入额外信息" :maxlength="500"
                    show-word-limit size="large" :autosize="{minRows:1, maxRows: 10}"/>
        </el-form-item>
        <el-form-item label="扩写优化要求" prop="requires">
          <desc-com v-model:type="form.requiresType"
                    :descType="5"
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
<!--                  popper-class="popper-class"-->
<!--                  :fit-input-width="true"-->
<!--                  >-->
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
<!--              <el-input v-show="form.requiresType === 2" v-model="form.requires" type="textarea" clearable placeholder="请输入生成提示词" :maxlength="300"-->
<!--                        show-word-limit size="large" :autosize="{minRows:1, maxRows: 5}"/>-->
<!--            </el-tab-pane>-->
<!--          </el-tabs>-->

<!--          <el-radio-group v-model="form.requiresType">-->
<!--            <el-radio-button label="快捷选项" :value="1" />-->
<!--            <el-radio-button label="自定义" :value="2" />-->
<!--          </el-radio-group>-->


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
  </el-dialog>

</template>

<script setup>

import {myListDesc} from "../../api/desc.js"
import {listRoleByAI} from "../../api/role.js"
import {defineExpose, ref} from "vue";
import {ElMessage} from "element-plus";
import {useUserStore} from "../../store/user.js";
import RoleSelect from "../RoleSelect.vue";
import DescCom from "../DescCom.vue";


const visible = ref(false)


const emit = defineEmits(['submitData'])


let form = reactive({
  id: null,
  content: '',
  relation: '',
  requiresType: 1,
  requiresId: 0,
  requires: '',
  roles: '',
  roless: [],
  bookId: null,
})

const openPolishDialog = (bookId, content) => {
  resetForm()
  form.bookId = bookId
  form.content = content
  visible.value = true
  open()
}

defineExpose({ openPolishDialog })




const loading = ref(false)




const resetForm = () => {
  form.id = null
  form.content = ''
  form.relation = ''
  form.requiresType = 1
  form.requiresId = 0
  form.requires = ''
  form.roles = ''
  form.roless = []
  form.bookId = null
}

const rules = reactive({
  content: [
    {required: true, message: '请输入扩写优化文本,不能少于10个字', trigger: 'blur'},
  ],
  requires: [
    {required: true, message: '请输入扩写优化要求', trigger: 'blur'},
  ],
})

const descData = reactive({
  requiress: []
})

const open = () => {

  // myListDesc({type: 5}).then(res => {
  //   descData.requiress = res.data
  //     form.requiresId = res.data[0].id
  //     form.requires = res.data[0].des
  //   // console.log(res.data[0])
  // }).catch(err => {
  //
  // })


}



const cancel = () => {
  visible.value = false
}


const userStore = useUserStore()

const descDialogRef = ref()

const sumbit = async () => {
  if (!descDialogRef) return
  await descDialogRef.value.validate(valid => {
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
      emit('submitData', form)
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
