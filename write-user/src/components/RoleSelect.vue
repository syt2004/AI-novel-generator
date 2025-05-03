<template>
  <el-select
      v-model="roless"
      multiple
      placeholder="请选择本章出场人物">
    <el-option
        v-for="item in roleData.data"
        :key="item.id"
        :label="item.name"
        :value="item.id.toString()"
    />
  </el-select>
</template>

<script setup>

let bookId = defineModel('bookId', {
  required: true,
  type: Number
});

let roless = defineModel('roless', {
  required: true,
  type: Array
});

let roleData = reactive({
  data: []
})

import {listRoleByAI} from "@/api/role.js"

onMounted(() => {
  listRolex()
})

let timer

const listRolex = () => {
  if (timer) clearTimeout(timer)
  if (bookId.value === null) {
    timer = setTimeout(() => {
      listRolex()
    }, 1000)
    return
  }
  listRoleByAI(bookId.value).then(res => {
    roleData.data = res.data
  }).catch(err => {

  })
}






</script>

<style scoped lang="scss">

</style>
