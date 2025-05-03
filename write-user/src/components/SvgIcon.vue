<template>
  <svg :class="svgClass" :style="{
        width: size + 'px',
        height: size + 'px'
    }"
  >
<!--    <use :xlink:href="iconName" :fill="color" />-->
    <use :xlink:href="iconName" />
  </svg>
</template>

<script setup>
const props = defineProps({
  name: {
    type: String,
    required: true
  },
  size: {
    type:[Number,String],
    default: 30
  }
})

const iconName = computed(() => `#${props.name}`)
const svgClass = computed(() => {
  if (props.name) {
    return `svg-icon ${props.name}`
  }
  return "svg-icon"
})



</script>

<style scoped>
.svg-icon {
  fill: var(--el-text-color-primary);
  vertical-align: middle;
}





</style>
