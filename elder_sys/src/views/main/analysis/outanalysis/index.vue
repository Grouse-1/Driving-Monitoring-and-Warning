<template>
  <!--  1.老人买菜购买记录-->
  <!--  2.当月买菜类别占比-->
  <!--    谷物类 肉蛋奶类  豆类及其制品类 蔬菜水果类  油脂坚果类-->

  <!--  3.老人饮食标准显示-->
  <!--    碳水化合物  蛋白质  维生素 膳食纤维 油脂-->
  <!--  -->
  <!--  4.给出老人食物购买推荐建议-->
  <el-row :gutter = "20" >
    <el-col :span="12" >
      <el-card>
        老人买菜购买记录
        <div class="table-container">
          <div class="search-bar">
            <el-input v-model="search" size="large" placeholder="Type to search" />
          </div>
          <el-table
              :data="filterTableData"
              style="width: 100%; height: 520px"
              :default-sort="{ prop: 'date', order: 'descending' }"
          >
            <el-table-column label="Date" prop="date" sortable width="120" />
            <el-table-column label="Name" prop="name" />
            <!-- 其他需要添加的 el-table-column -->
          </el-table>
        </div>
      </el-card>
    </el-col>
    <el-col :span="12" >
      <el-card>
        图表占比处
      </el-card>
    </el-col>
  </el-row>


</template>

<script setup>
import {computed, ref} from 'vue'

const search = ref('')
// const filterTableData = computed(() =>
//     tableData.filter(
//         (data) =>
//             !search.value ||
//             data.name.toLowerCase().includes(search.value.toLowerCase())
//     )
// )
const filterTableData = computed(() =>
    tableData.filter(
        (data) =>
            (data.name.toLowerCase().includes(search.value.toLowerCase()) ||
                data.date.includes(search.value))
    )
);

const handleEdit = (index, row) => {
  console.log(index, row)
}

const handleDelete = (index, row) => {
  console.log(index, row)
}

const tableData = [
  {
    date: '2016-05-03',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-02',
    name: 'John',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-04',
    name: 'Morgan',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-01',
    name: 'Jessy',
    address: 'No. 189, Grove St, Los Angeles',
  },
]
</script>

<style lang="scss" scoped>
.table-container {
  display: flex;
  flex-direction: column;
}

.search-bar {
  margin-bottom: 16px;
}
</style>
