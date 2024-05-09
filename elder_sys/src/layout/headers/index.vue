<template>
<!--  -->
<div class="navbar">
<breadcrumb/>
<div class="navbar-right">
<avatar />
</div>
</div>
</template>
<script setup>

import Breadcrumb from "@/layout/headers/components/breadcrumb.vue";
import Avatar from "@/layout/headers/components/avatar.vue";
</script>
<style scoped lang="scss">
.navbar {
  width: 100%;
  height: 60px;
  overflow: hidden;
  background-color: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  padding: 0 16px;
  display: flex;
  align-items: center;
  box-sizing: border-box;
  position: relative;
  .navbar-right {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: flex-end;
    ::v-deep .navbar-item {
      display: inline-block;
      margin-left: 18px;
      font-size: 22px;
      color: #5a5e66;
      box-sizing: border-box;
      cursor: pointer;
    }
  }
}
</style>