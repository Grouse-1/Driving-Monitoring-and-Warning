<!--1.老人出行记录-->
<!--2.老人犯困时间点-->
<!--3.根据老人开心活动场所给出建议-->
<!--4.根据犯困时间给出出门时间推荐-->
<template>
  <el-row :gutter="20">
    <el-col :span="12">
      <el-card>
        <template #header>
          <div class="card-header">
            <span class="card-title">老人出行记录</span>
          </div>
        </template>
        <div class="table-container">
          <div class="search-bar">
            <el-input v-model="search" size="large" placeholder="搜索日期或名字" />
          </div>
          <el-table
              :data="filterTableData"
              style="width: 100%; height: 475px"
              :default-sort="{ prop: 'tdate', order: 'descending' }"
          >
            <el-table-column label="时间" prop="tdate" sortable width="240" />
            <el-table-column label="出行地点" prop="location" />
            <!-- 其他需要添加的 el-table-column -->
          </el-table>
        </div>
      </el-card>
    </el-col>
    <el-col :span="12">
      <el-card>
        <div class="card-header">
          <span class="card-title">老人犯困信息</span>
        </div>
        <scatter-chart @outAdvice="OutAdvise"/>
        <!-- 添加图表或其他元素来展示老人的犯困时间点 -->
        <div class="card-header" style="margin-bottom: 5px">
          <span class="card-title">老人较开心活动地点</span>
        </div>
        <el-tag type="info" size="large" effect="light" style="width: 150px;">{{ loveLocation}}</el-tag>

        <!-- 添加图表或其他元素来展示老人的开心活动地点 -->
      </el-card>
      <el-card style="margin-top: 10px">
        <template #header>
          <div class="card-header">
            <span class="card-title">出行建议</span>
          </div>
        </template>
        <div class="advice-container">
          <div class="advice-section">
            <h3>出行时间推荐</h3>
            <div>推荐老人在这几个时间点外出活动：{{outTime.join(', ')}}</div>
<!--            <div>起飞想出发就出发</div>-->
            <!-- 添加出行建议内容 -->
          </div>
          <div class="advice-section">
            <h3>活动场所推荐</h3>
<!--            <div>{{ outLocation }}</div>-->
            <div>起飞想去哪去哪</div>
            <!-- 添加出行建议内容 -->
          </div>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>


<script setup>
import { computed, onMounted, ref } from 'vue'
import {GetHappyLocation, GetTravel} from "@/api/getInfo/index.js";
import ScatterChart from "@/views/main/analysis/outanalysis/chart/scatterChart.vue";

const search = ref('')
const loveLocation = ref("")
const outTime = ref([])
const elderid = ref(1)

const filterTableData = computed(() =>
    tableData.value.filter(
        (data) =>
            data.location.toLowerCase().includes(search.value.toLowerCase()) ||
            data.tdate.includes(search.value)
    )
);

const tableData = ref([])

const OutAdvise = (data) => {
  outTime.value = data
}

const initView = () => {
  try {
    // 调用 API 获取数据
    GetTravel.Info().then(res => {
      const data = res.data
      console.log(data)
      // 将服务器返回的数据映射到 tableData 数组中
      tableData.value = data.map(item => ({
        tdate: item.tdate,
        location: item.location
      }));

    })
  } catch (error) {
    console.error('Error fetching record data:', error);
  }
}

const getHappyLocation = () => {
  GetHappyLocation.Info(elderid.value).then(res => {
    console.log(res.data)
    loveLocation.value = res.data
  })
}

onMounted(() => {
  getHappyLocation()
  initView()
})
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

.card-title {
  font-size: 18px;
  font-weight: bold;
}

.table-container {
  height: 515px;
  overflow-y: auto;
}

.search-bar {
  margin-bottom: 16px;
}

.advice-container {
  display: flex;
  justify-content: space-between;
}

.advice-section {
  flex: 1;
  border-radius: 4px;
  margin-bottom: 4px;
}

.advice-section h3 {
  font-size: 16px;
  font-weight: bold;
}
</style>
