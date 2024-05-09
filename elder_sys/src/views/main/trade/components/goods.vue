<script setup>
import {Search} from "@element-plus/icons-vue";
import {ref} from "vue";

const tableData = [
  {
    date: '2016-05-03',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
];
const item = ref('')
const dialogVisible = ref(false)

const getFood =()=>{
  dialogVisible.value = true

}

const gridData = [
  {
    date: '2016-05-02',
    name: 'John Smith',
    address: 'No.1518,  Jinshajiang Road, Putuo District',
  },
  {
    date: '2016-05-04',
    name: 'John Smith',
    address: 'No.1518,  Jinshajiang Road, Putuo District',
  },
  {
    date: '2016-05-01',
    name: 'John Smith',
    address: 'No.1518,  Jinshajiang Road, Putuo District',
  },
  {
    date: '2016-05-03',
    name: 'John Smith',
    address: 'No.1518,  Jinshajiang Road, Putuo District',
  },
]

const handleConfirm = () => {
  dialogVisible.value = false
}

const handleClose = () => {
  dialogVisible.value = false
}
</script>

<template>
  <el-row :gutter="20">
    <el-col :span="17">
      <el-card style="margin-bottom: 5px">
        <el-input
            v-model="item"
            style="max-width: 240px"
            size="large"
            placeholder="搜索菜品或食物"
            class="input-with-select"
        ><template #suffix>
            <el-button type="text" :icon="Search" @click="getFood" />
          </template>
        </el-input>
      </el-card>
      <el-card>
        <el-table :data="tableData" height="350" style="width: 100%">
          <el-table-column prop="name" label="名字" width="180" />
          <el-table-column prop="date" label="数量(kg)" width="180" />
          <el-table-column prop="address" label="单价/元" />
        </el-table>
      </el-card>
      <el-dialog v-model="dialogVisible"width="800">
        <template #header>
          <div class="dialog-header">
            菜品或食物
          </div>
        </template>
        <el-card>
        <el-table :data="gridData">
          <el-table-column prop="name" label="名字" width="180" />
          <el-table-column prop="address" label="单价/元" />
        </el-table>
        </el-card>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="handleClose">取消</el-button>
            <el-button type="primary" @click="handleConfirm">
              确认
            </el-button>
          </div>
        </template>
      </el-dialog>
    </el-col>
    <el-col :span="7">
      <el-card>
        <template #header>
          <div class="card-header">
            <span style="font-weight: bold; font-size: 16px;">功能区</span>
          </div>
        </template>
        <el-button>删除商品</el-button>
        <el-button>清空商品</el-button>
        <el-button>结算</el-button>

      </el-card>
    </el-col>

  </el-row>

</template>

<style scoped lang="scss">

</style>