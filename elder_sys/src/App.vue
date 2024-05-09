<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <el-config-provider>
    <router-view />
  </el-config-provider>
</template>

<style lang="scss">
html,
body,
#app {
  width: 100%;
  height: 100%;
  background-color: white;
  margin: 0;
  padding: 0;
  * {
    outline: none;
  }
}
</style>
