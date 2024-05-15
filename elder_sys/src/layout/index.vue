<script setup>
import Menu from './Menu/index.vue'
import store from "@/store/index.js";
import Header from './headers/index.vue'


</script>

<template>
  <div class="app-wrapper">
    <el-container>
      <el-aside width="210px" class="sidebar-container"><Menu/></el-aside>
      <el-container class="container">
        <el-header><Header/></el-header>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>


<style lang="scss" scoped>
@import "@/styles/variables.scss";
@import "@/styles/mixin.scss";
.app-container {
  position: relative;
  width: 100%;
  height: 100%;
}
.container {
  width: calc(100% - $sideBarWidth);
  height: 100%;


  position: fixed;
  top: 0;
  right: 0;
  z-index: 9;
  transition: all 0.28s;
  &.hidderContainer {
    width: calc(100% - $hideSideBarWidth);
  }
}
::v-deep .el-header {
  padding: 0;
}
</style>