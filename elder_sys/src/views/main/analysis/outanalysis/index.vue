<template>
<!--1.老人出行记录-->
<!--2.老人犯困时间点-->
<!--3.根据老人开心活动场所给出建议-->
<!--4.根据犯困时间给出出门时间推荐-->
  <el-row :gutter = "20" >
    <el-col :span="12" >
      <el-card>
        <template #header>
          <div class="card-header">
            <span class="card-title">老人出行记录</span>
          </div>
        </template>
        <div class="table-container">
          <div class="search-bar">
            <el-input v-model="search" size="large" placeholder="Type to search" />
          </div>
          <el-table
              :data="filterTableData"
              style="width: 100%; height: 520px"
              :default-sort="{ prop: 'date', order: 'descending' }"
          >
            <el-table-column label="时间" prop="date" sortable width="240" />
            <el-table-column label="出行地点" prop="name" />
            <!-- 其他需要添加的 el-table-column -->
          </el-table>
        </div>
      </el-card>
    </el-col>
    <el-col :span="12" >
      <el-card>
        老人频繁犯困时间点
        老人开心活动地点

        出行建议之避开犯困时间点
        出行建议之多去开心活动场所
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
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;

}

.card-title {
  font-size: 18px;
  font-weight: bold;
  color: #303133;
}
.divTitle {
  font-size: 18px;
  font-weight: bold;
  color: #464646;
}
.advice-container {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
  height: 100%;
}

.advice-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 5px;
}

.advice-content {
  font-size: 14px;
}

.category-list {
  margin-top: 5px;
  color: #666;
}
</style>
