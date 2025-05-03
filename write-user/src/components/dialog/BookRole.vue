<template>
  <el-dialog title="人物管理"
             v-model="visible"
             align-center
             width="70%"
             class="dialog" top="5vh" :close-on-click-modal="false" :close-on-press-escape="false" destroy-on-close>
    <template #header>
      <div class="role-title">
        <el-button type="primary" icon="DocumentAdd" @click="addBookRole" v-blur>添加人物</el-button>
      </div>
    </template>
    <div v-loading="fullscreenLoading" class="role-div-d">
      <el-empty v-if="!data.roles.length" description="暂时还没有人物"/>
      <div class="role-div" v-else>
        <el-container class="role-container">
          <el-aside class="role-aside">
            <el-scrollbar class="role-aside-scroll">
              <div v-for="(item, index) in data.roles" :key="index">
                <div class="c-m-a-main-menu" :class="[index === data.defaultActive? 'c-m-a-main-menu-select':'c-m-a-main-menu-noselect']">
<!--                  -->
                  <!--          <div class="c-m-a-main-menu-select c-m-a-main-menu">-->
                  <div class="c-m-a-main-menu-select-text" @click="selectRole(index)">
                    <el-text class="c-m-a-main-menu-title" truncated>{{ item.name }}</el-text>
                  </div>
                  <div class="c-m-a-main-menu-select-button" v-show="item.isShow === 1">
                    <el-button type="primary" :icon="View" circle v-blur @click="updateData(item, index)"/>
<!--                    <el-icon class="navbar-list-icon" :size="25" @click="removePart(index)">-->
<!--                      <View />-->
<!--                    </el-icon>-->
                  </div>
                  <div class="c-m-a-main-menu-select-button" v-show="item.isShow === 0">
                    <el-button type="primary" :icon="Hide" circle v-blur @click="updateData(item, index)"/>
<!--                    <el-icon class="navbar-list-icon" :size="25" @click="removePart(index)">-->
<!--                      <Hide />-->
<!--                    </el-icon>-->
                  </div>
                </div>
<!--                <div class="c-m-a-main-menu-noselect c-m-a-main-menu" v-else>-->
<!--                  <el-text class="c-m-a-main-menu-title" truncated>{{ item.name }}</el-text>-->
<!--                </div>-->
              </div>
<!--              <el-menu-->
<!--                  :default-active="data.defaultActive"-->
<!--                  class="role-aside-menu">-->
<!--                <el-menu-item :index="index.toString()" v-for="(item, index) in data.roles" :key="index"-->
<!--                              @click="selectRole(index)">{{ item.name }}-->
<!--                </el-menu-item>-->
<!--              </el-menu>-->
            </el-scrollbar>

          </el-aside>
          <el-main class="role-main">
            <el-scrollbar class="role-main-scroll">
              <div class="role-main-scroll-div">
                <el-form ref="formRef" :model="form" :rules="rules" label-position="top">
                  <el-form-item label="人物名称" prop="name">
                    <el-input v-model="form.name" placeholder="人物名称" :maxlength="20" show-word-limit/>
                  </el-form-item>
                  <el-form-item label="性别" prop="sex">
                    <el-radio-group v-model="form.sex">
                      <el-radio-button label="未知" :value="0"/>
                      <el-radio-button label="男" :value="1"/>
                      <el-radio-button label="女" :value="2"/>
                      <el-radio-button label="无" :value="3"/>
                    </el-radio-group>
                  </el-form-item>
                  <el-form-item label="人物性格" prop="characte">
                    <el-input v-model="form.characte" type="textarea" clearable placeholder="请输入作品简介"
                              :maxlength="100" show-word-limit size="large" :rows="3"/>
                  </el-form-item>
                  <el-form-item label="人物信息（请根据剧情同步更新，仅填写剧情用得到的信息）" prop="plot">
                    <el-input v-model="form.plot" type="textarea" clearable placeholder="请输入作品简介"
                              :maxlength="500" show-word-limit size="large" :rows="8"/>
                  </el-form-item>
                </el-form>
                <div class="dialog-footer">
                  <el-button @click="removeRole" v-blur>删除</el-button>
                  <el-button type="primary" @click="sumbitRole" v-blur>
                    提交
                  </el-button>
                </div>
              </div>

            </el-scrollbar>

          </el-main>
        </el-container>
      </div>
    </div>
    <template #footer>
      <a-collapse :default-active-key="[]" show-expand-icon :bordered="false">
        <a-collapse-item header="人物智能识别(目前不稳定,可能会识别失败)" key="1">
          <a-textarea v-model="infoStr" placeholder="请输入待识别的人物简介,可一次性识别多个人物" allow-clear
                      :max-length="1000" show-word-limit :auto-size="{
          minRows:1,
          maxRows:10
          }"/>
          <a-button type="outline" size="large" @click="shibie" :loading="status.sbLoading" v-blur>开始识别</a-button>
        </a-collapse-item>
      </a-collapse>
      <!--      实验内容-->
    </template>

  </el-dialog>
</template>

<script setup>

const updateData = (item, index) => {
  if (item.id) {
    const sss = item
    if (sss.isShow === 0) {
      sss.isShow = 1
    } else {
      sss.isShow = 0
    }
    updateRole(sss).then(res => {
      ElMessage({
        message: item.name + '当前状态:' + (sss.isShow === 1 ? '可见' : '不可见'),
        type: 'success',
        offset: 200,
        duration: 2000
      })
      item.isShow = sss.isShow
    })
  }
}

import {View, Hide} from "@element-plus/icons-vue"

const status = reactive({
  // 识别按钮加载状态
  sbLoading: false
})


const infoStr = ref('')

const shibie = () => {
  if (infoStr.value.length === 0) {
    ElMessage({
      message: '请输入待识别的人物简介',
      type: 'warning',
      offset: 200,
      duration: 2000
    })
    return
  }
  status.sbLoading = true
  aiAddRole({info: infoStr.value, bookId: data.bookId}).then(res => {
    status.sbLoading = false
    getList(data.bookId)
    ElMessage({
      message: res.msg,
      type: 'success',
      offset: 200,
      duration: 2000
    })
  }).catch(err => {
    status.sbLoading = false
  })


}


import {listRole, addRole, updateRole, deleteRole, aiAddRole} from "../../api/role.js"
import {ElMessage, ElMessageBox} from "element-plus"

let fullscreenLoading = ref(true)
const visible = ref(false)
const form = reactive({
  name: '',
  id: null,
  sex: 0,
  characte: '',
  plot: '',
  bookId: null,
  isShow: 1
})

const data = reactive({
  defaultActive: 0,
  bookId: null,
  roles: []
})

const addBookRole = () => {
  if (data.roles) {
    for (let i = 0; i < data.roles.length; i++) {
      let ssss = data.roles[i]
      if (!ssss.id) {
        ElMessage({
          message: '请先保存人物',
          type: 'warning',
          offset: 200,
          duration: 2000
        })
        return
      }
    }
  }
  form.sex = 0
  form.id = null
  form.characte = ''
  form.plot = ''
  form.name = '未命名人物'
  form.bookId = data.bookId
  form.isShow = 1
  data.roles.push(form)
  data.defaultActive = (data.roles.length - 1)

}

const selectRole = (index) => {
  data.defaultActive = index
  let s = data.roles[index]
  form.sex = s.sex
  form.id = s.id
  form.characte = s.characte
  form.plot = s.plot
  form.name = s.name
  form.bookId = data.bookId
  form.isShow = s.isShow
}

const openDialog = (id) => {
  data.defaultActive = 0
  data.roles = []
  data.bookId = id
  visible.value = true
  getList(id)

  //
}

const getList = (id) => {
  fullscreenLoading = true
  listRole(id).then(res => {
    data.roles = res.data
    // for (let i = 0; i < 20; i++) {
    //   data.roles.push({
    //     id: 100+i,
    //     name: 'name' + i,
    //     plot: 'plot' + i,
    //     characte: 'characte' + i
    //   })
    // }


    if (data.roles.length) {
      let s = data.roles[parseInt(data.defaultActive)]
      form.sex = s.sex
      form.id = s.id
      form.characte = s.characte
      form.plot = s.plot
      form.name = s.name
      form.bookId = data.bookId
      form.isShow = s.isShow
    } else {
      form.sex = 0
      form.id = null
      form.characte = ''
      form.plot = ''
      form.name = '未命名人物'
      form.bookId = data.bookId
      form.isShow = 1
    }
    fullscreenLoading = false
  }).catch(error => {
    fullscreenLoading.value = false
  })
}

defineExpose({openDialog})


const rules = reactive({
  name: [
    {required: true, message: '请输入人物姓名', trigger: 'blur'},
  ],
  characte: [
    {required: true, message: '请输入人物性格', trigger: 'blur'},
  ],
  plot: [
    {required: true, message: '请输入人物信息', trigger: 'blur'},
  ],
  sex: [
    {required: true, message: '请选择人物性别', trigger: 'blur'},
  ],

})

const formRef = ref()

const sumbitRole = async () => {
  if (!formRef) return
  await formRef.value.validate(valid => {
    if (valid) {
      if (form.id) {
        updateRole(form).then(res => {
          ElMessage({
            message: res.msg,
            type: 'success',
            offset: 200,
            duration: 2000
          })
          getList(data.bookId)
        })
      } else {
        addRole(form).then(res => {
          ElMessage({
            message: res.msg,
            type: 'success',
            offset: 200,
            duration: 2000
          })
          getList(data.bookId)
        })
      }

    }
  })
}

const removeRole = () => {
  if (form.id) {
    ElMessageBox.confirm(
        '人物删除后无法恢复，确定要删除该人物吗？',
        '删除人物',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }
    ).then(() => {
      deleteRole(form).then(res => {
        data.defaultActive = 0
        getList(data.bookId)
        ElMessage({
          message: res.msg,
          type: 'success',
          offset: 200,
          duration: 2000
        })
      })
    }).catch(() => {

    })

    return

  }

  data.roles.splice(parseInt(data.defaultActive), 1)
  if (data.roles.length) {
    data.defaultActive = 0
    getList(data.bookId)
  } else {
    form.sex = 0
    form.id = null
    form.characte = ''
    form.plot = ''
    form.name = '未命名人物'
    form.bookId = data.bookId
    form.isShow = 1
  }

}

const click1 = () => {
  visible.value = false
}


</script>

<style scoped lang="scss">

.dialog {
  padding: 0;
  //height: 60vh;
}

.role-title {
  display: flex;
}

.role-div-d {
  height: 650px;
  max-height: 60vh;
  //manheight: 60vh;
}

.role-div {
  display: flex;
  //width: 450px;
  height: 100%;

  .role-container {
    //background-color: #535bf2;
    height: 100%;
    width: 100%;

    .role-aside {
      width: 120px;
      height: 100%;
      display: flex;

      .role-aside-scroll {
        width: 100%;
        height: 100%;

        .c-m-a-main-menu {
          width: 100%;
          border-bottom: 1px solid transparent;
          border-image: linear-gradient(to bottom, transparent 50%, var(--w-border-color) 50%) 0 0 100%/1px 0;
          color: var(--el-text-color-primary);
          height: 50px;
          text-align: left;
          display: flex;
          align-items: center;
          justify-content: space-between;
          .c-m-a-main-menu-title {
            display: flex;
            width: 80px;


          }


        }

        .c-m-a-main-menu-noselect {

        }

        .c-m-a-main-menu-select {
          background-color: var(--w-content-aside-bg-color);
        }

        .role-aside-menu {
          width: 100%;
          height: 100%;
        }
      }


    }
  }

  .role-main {
    //background-color: #535bf2;
    height: 100%;
    width: 100%;

    .role-main-scroll {
      height: 100%;
      width: 100%;

      .role-main-scroll-div {
        margin-right: 30px;
      }
    }
  }
}

</style>
