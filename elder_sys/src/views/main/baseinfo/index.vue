<template>
<!--  <el-card>-->
<!--    <el-row :gutter="20" class="header">-->
<!--      <el-col :span="7">-->
<!--        <el-input placeholder="请输入搜索的用户姓名" clearable v-model="queryForm.query" ></el-input>-->
<!--      </el-col>-->
<!--      <el-button type="primary" :icon="Search">搜索</el-button>-->
<!--      <el-button type="primary">添加用户</el-button>-->
<!--    </el-row>-->
<!--    <el-table :data="tableData" stripe style="width: 100%">-->
<!--      <el-table-column prop="date" label="Date" width="180" />-->
<!--      <el-table-column prop="name" label="Name" width="180" />-->
<!--      <el-table-column prop="address" label="Address" />-->
<!--    </el-table>-->
<!--  </el-card>-->
  <router-view />
</template>

<script setup>


</script>

<style lang="scss" scoped>
.header{
  padding-bottom: 16px;
  box-sizing: border-box;

}

</style>
