<script setup>
import {ref} from "vue";
import{ Search } from '@element-plus/icons-vue';
import { GetElderly } from '@/api/getInfo/index';
import {Delete, Edit} from "@element-plus/icons";
import Dialog from './components/dialog.vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import {delElderly} from "@/api/CRUD/elderly.js";
const queryForm = ref({
  query: '',
  page: 1,
  pageSize: 5,
})
const total = ref(0)

const elderlyList = ref([])

const dialogVisible = ref(false)
const dialogTitle = ref("")
const dialogTableValue = ref({})

const LocationKV = ref({
  1: '菜市场',
  2: '公园'
})

const initMenuList = async() => {
  const res = await GetElderly.elderlyInfo(queryForm.value)

  const updatedElderlyList = res.data.data.map(item => {
    return {
      ...item,
      location: LocationKV.value[item.locationId],
    }
  })
  console.log(updatedElderlyList)
  elderlyList.value = updatedElderlyList
  total.value = res.data.total
}
initMenuList()

const handleSizeChange = (pageSize) => {
  queryForm.value.page = 1
  queryForm.pageSize = pageSize
  initMenuList()
}

const handleCurrentChange = (page) => {
  queryForm.value.page = page
  initMenuList()
}

const handleDialog=(row)=>{
  if(!row){
    dialogTitle.value = '添加老人信息'
    dialogTableValue.value = null
  }
  else {
    dialogTitle.value = '编辑老人信息'
    dialogTableValue.value = JSON.parse(JSON.stringify(row))
  }

  dialogVisible.value = true
}

const deladmin=(date)=>{
  ElMessageBox.confirm(
      '确认要删除用户?',
      'Warning',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(async () => {
        await delElderly.del(date.id)
        ElMessage({
          type: 'success',
          message: '成功删除',
        })
        await initMenuList()
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '取消删除',
        })
      })
}


</script>

<template>
  <el-card>
    <el-row :gutter="20" class="header">
      <el-col :span="7">
        <el-input placeholder="请输入搜索的用户姓名" clearable v-model="queryForm.query" ></el-input>
      </el-col>
      <el-button type="primary" :icon="Search" @click="initMenuList">搜索</el-button>
      <el-button type="primary" @click="handleDialog()">添加用户</el-button>
    </el-row>
    <el-table :data="elderlyList" stripe  style="width: 100%">
      <el-table-column prop="name" label="姓名" width="100" />
      <el-table-column prop="password" label="密码" width="150" />
      <el-table-column prop="sex" label="性别" width="100" />
      <el-table-column prop="age" label="年龄" width="100" />
      <el-table-column prop="balance" label="余额" width="100" />
      <el-table-column prop="rfidtag" label="序列号" width="150" />
      <el-table-column prop="location" label="地点" width="150" />
      <el-table-column label="操作" width="200">
        <template #default="{row}">
          <el-button type="primary" size="default" :icon="Edit"  @click="handleDialog(row)">编辑</el-button>
          <el-button type="danger" size="default" :icon="Delete"  @click="deladmin(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="demo-pagination-block">
      <el-pagination
          v-model:current-page="queryForm.page"
          v-model:page-size="queryForm.pageSize"
          :page-sizes="[1, 5, 10, 15]"
          :small="small"
          :disabled="disabled"
          :background="background"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />
    </div>
  </el-card>
  <Dialog v-model="dialogVisible" :dialogTitle="dialogTitle" :dialogTableValue="dialogTableValue" :initMenuList="initMenuList"/>
</template>

<style scoped lang="scss">
.el-pagination{
  padding-top: 16px;
  box-sizing: border-box;
  justify-content: flex-end;
}
</style>