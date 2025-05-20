<template>
  <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
    <el-tab-pane label="轮播图管理" name="first">
      <HomeSlider />
    </el-tab-pane>
    <el-tab-pane label="主页文章管理" name="second">
      主页文章管理
    </el-tab-pane>
  </el-tabs>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const activeName = ref('first')

const handleClick = (tab: { paneName: string }, event: Event) => {
  console.log(tab.paneName);
  console.log(event);
}
</script>

<style scoped></style>
