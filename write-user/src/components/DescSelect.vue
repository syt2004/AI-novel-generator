<template>
  <el-select
      v-model="id"
      placeholder="请选择提示词"
      :fit-input-width="true">
    <el-option
        v-for="item in descData.data"
        :key="item.id"
        :label="item.name"
        :value="item.id"
        :title="item.des"
    />
<!--    <template #footer>-->
<!--      <el-button type="primary">-->
<!--        提示词管理-->
<!--      </el-button>-->
<!--    </template>-->
  </el-select>
</template>

<script setup>

const descType = defineModel('descType', {
  type: [Number, String],
  required: true,
})

let id = defineModel('id', {
  required: true,
  type: Number
});

let desc = defineModel('desc', {
  type: String,
  default: ''
})

let descData = reactive({
  data: []
})

import {myListDesc} from "@/api/desc.js"

onMounted(() => {
  list()
})

let timer

const list = () => {
  console.log("list")
  if (timer) clearTimeout(timer)
  if (descType.value === null || descType.value === undefined) {
    timer = setTimeout(() => {
      list()
    }, 1000)
    return
  }
  console.log(descType.value)
  myListDesc({type: descType.value}).then(res => {
    descData.data = res.data
    console.log(id.value)
    console.log(id.value)
    console.log(desc.value)
    console.log(desc.value)
    if (id.value === 0) {
      id.value = res.data[0].id
      desc.value = res.data[0].des
    }
  }).catch(err => {

  })
}






</script>

<style scoped lang="scss">

</style>
