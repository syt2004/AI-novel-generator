

<template>
  <div class="history-table">
    <el-table :data="data.list" title=""  border stripe  style="width: 100%" v-loading="loading" workUserCode="暂无数据">
      <el-table-column type="expand" label="提示词" width="70">
        <template #default="props">
          <div style="padding-left: 70px; padding-right: 70px;">
            <p>提示词:{{ props.row.des }}</p>
            <p v-if="props.row.type === 1">剧情:{{ props.row.plot }}</p>
            <p v-if="props.row.type === 1">要求:{{ props.row.style }}</p>
          </div>

        </template>
      </el-table-column>
      <el-table-column label="内容" show-overflow-tooltip prop="content">
<!--        <template #default="props">-->
<!--          <el-popover-->
<!--              trigger="hover"-->
<!--              :width="500"-->
<!--              :content="props.row.content"-->
<!--          >-->
<!--            <template #reference>-->
<!--              <el-text :line-clamp="2">{{ props.row.content }}</el-text>-->
<!--            </template>-->
<!--          </el-popover>-->


<!--        </template>-->
      </el-table-column>

      <el-table-column prop="words" label="消耗(字)" width="80"/>
      <el-table-column prop="type" label="类型"  width="120">
        <template #default="props">
          <el-tag v-if="props.row.type === 1" :key="props.row.type" type="primary">
            生成
          </el-tag>
          <el-tag v-if="props.row.type === 2" :key="props.row.type" type="success">
            扩写优化
          </el-tag>
          <el-tag v-if="props.row.type === 3" :key="props.row.type" type="warning">
            AI续写
          </el-tag>
          <el-tag v-if="props.row.type === 4" :key="props.row.type" type="danger">
            拆解
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="时间" width="170"/>
    </el-table>
  </div>



  <div class="history-page">
    <el-pagination
        v-model:current-page="data.currentPage"
        background
        layout="prev, pager, next"
        :page-count="data.pages"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
  </div>




</template>

<script setup>

import {usercode} from '@/api/login.js'

const loading = ref(false)

const data = reactive({
  list: [],
  currentPage: 1,
  pageSize: 10,
  total: 0,
  pages: 0
})

const handleSizeChange = () => {

}

const handleCurrentChange = (num) => {
  console.log('num')
  console.log(num)
  data.currentPage = num
  getList()
}


onMounted(() => {
  getList()
})

const getList = () => {
  loading.value = true
  usercode({pageSize: data.pageSize, page: data.currentPage}).then(res => {
    console.log(res.data)
    data.list = res.data.records
    data.currentPage = res.data.current
    data.total = res.data.total
    data.pages = res.data.pages
    loading.value = false
  }).catch(error => {
    loading.value = false
  })
}

</script>

<style scoped lang="scss">

.history-table {
  left: 20px;
  right: 20px;
}

.history-page {
  width: 100%;
  display: flex;
  height: 60px;
  align-items: center;
  //padding-top: 20px;
  //padding-right: 20px;
  justify-content: flex-end;
}

</style>
