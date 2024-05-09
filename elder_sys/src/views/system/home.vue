<script setup>
import store from "../../store/index.js";

const token = localStorage.getItem('token')
</script>

<template>
芝士首页
  <p/>
  <router-link to="/login">----登录----</router-link>
  <router-link to="/canshu/张三">----张三参数----</router-link>
  <p>counter {{ store.state.counter}}</p>
  <p>counter {{ token }}</p>
</template>

<style scoped lang="scss">

</style>