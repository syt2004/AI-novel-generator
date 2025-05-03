<template>
  <el-tabs
      v-model="type"
      type="card"
      style="width: 100%"
  >
    <el-tab-pane label="内置提示词" :name="1">
      <desc-select v-show="type === 1" v-model:id="id" :descType="descType" v-model:desc="desc" />
<!--      <el-select-->
<!--          v-model="id"-->
<!--          placeholder=""-->
<!--          -->
<!--          :fit-input-width="true">-->
<!--        <el-option-->
<!--            v-for="item in descData.value"-->
<!--            :key="item.id"-->
<!--            :label="item.name"-->
<!--            :value="item.id"-->
<!--            :title="item.des"-->
<!--        >-->
<!--        </el-option>-->
<!--      </el-select>-->
    </el-tab-pane>
    <el-tab-pane label="自定义输入" :name="2">
      <el-input v-show="type === 2" v-model="desc" type="textarea" clearable
                placeholder="请输入生成提示词" :maxlength="limit"
                show-word-limit size="large" :autosize="{minRows: minRows, maxRows: maxRows}"/>
    </el-tab-pane>
  </el-tabs>
</template>

<script setup>

import DescSelect from "./DescSelect.vue"

let type = defineModel('type', {
  type: Number,
  default: 1,
  required: true,
  validator(value, props) {
    return [1, 2].includes(value)
  }
});

let id = defineModel('id', {
  type: Number,
  default: 0
})

let desc = defineModel('desc', {
  type: String,
  default: ''
})

const descType = defineModel('descType', {
  type: [Number, String],
  required: true,
})

let limit = defineModel('limit', {
  type: Number,
  default: 100
})

let minRows = defineModel('minRows', {
  type: Number,
  default: 1
})

let maxRows = defineModel('maxRows', {
  type: Number,
  default: 1
})

const descData = reactive([]);


onMounted(() => {
  // myListDesc({type: descType.value}).then(res => {
  //   descData.value = res.data
  //   if (id.value === 0) {
  //     id.value = res.data[0].id
  //     desc.value = res.data[0].des
  //   }
  // }).catch(err => {
  //
  // })
})




</script>

<style scoped lang="scss">

</style>
