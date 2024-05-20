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
            <el-button
                size="default"
                @click="adminSearch"
                style="margin-left: auto"
                type="success"
                v-if="key === 'admin'"
            >
              搜索用户
            </el-button>
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
      <el-dialog v-model="adminS" title="搜索用户" width="500">
        <el-input v-model="inputName"></el-input>
        <template #footer>
          <div class="dialog-footer">
            <el-button type="primary" @click="giveElderID">
              确认
            </el-button>
            <el-button @click="adminS = false; inputName = ''">取消</el-button>

          </div>
        </template>
      </el-dialog>
    </el-col>
    <el-col :span="12">
      <el-card>
        <div class="card-header">
          <span class="card-title">老人犯困信息</span>
        </div>
        <scatter-chart @outAdvice="OutAdvise" :elderid="elderid"/>
        <!-- 添加图表或其他元素来展示老人的犯困时间点 -->
        <div class="card-header" style="margin-bottom: 5px">
          <span class="card-title">老人较开心活动地点</span>
        </div>
        <bar-chart :elderid="elderid"/>
        <!-- 添加图表或其他元素来展示老人的开心活动地点 -->
      </el-card>
    </el-col>
  </el-row>
  <el-row>
    <el-card class="travel-advice-card">
      <template #header>
        <div class="card-header">
          <span class="card-title">出行建议</span>
        </div>
      </template>
      <div class="advice-container">
        <el-row gutter="20">
          <el-col :span="16">
            <div class="advice-section" style="margin-left: 45%; margin-right: 300px">
              <h3 class="section-title">出行时间推荐</h3>
              <div class="section-content" style="width: 100%;">推荐老人在这几个时间点外出活动：{{ outTime.join(', ') }}</div>
            </div>
          </el-col>
          <el-col :span="4">
            <div class="advice-section" style="height: 100%">
              <h3 class="section-title">活动场所推荐</h3>
              <el-tag type="info" size="large" effect="light" class="location-tag" style="width: 100%; text-align: center">{{ loveLocation }}</el-tag>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-card>
  </el-row>
</template>


<script setup>
import {computed, onMounted, ref, watch} from 'vue'
import {GetElderly, GetHappyLocation, GetTravel} from "@/api/getInfo/index.js";
import ScatterChart from "@/views/main/analysis/outanalysis/chart/scatterChart.vue";
import BarChart from "@/views/main/analysis/outanalysis/chart/barChart.vue";
import {ElMessage} from "element-plus";




const search = ref('')
const loveLocation = ref("")
const outTime = ref([])
const elderid = ref()
if(localStorage.getItem("role")==='family'){
  elderid.value = localStorage.getItem("elderid")
}
const adminS = ref(false)
const key = ref('')
const inputName = ref('')
const content = ref(true)
key.value = localStorage.getItem("role")
const adminSearch = () =>{
  adminS.value = true
}
const giveElderID = async () => {
  try {
    const res1 = await GetElderly.Info(inputName);
    const data1 = res1.data.shift();

    if (data1 !== null) {
      elderid.value = data1.id;
      initView();
    }
  } catch (error) {
    ElMessage.error({
      message: '没有该用户',
      type: 'error',
      duration: 1000
    });
    return; // 直接退出函数，不执行后续代码
  }

  adminS.value = false
  inputName.value = ''
}


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
    GetTravel.Info(elderid.value).then(res => {
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

watch(() => elderid, async (newDate) => {
  getHappyLocation()
}, {immediate: true, deep: true});

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
  padding: 10px;
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


.table-container {
  height: 515px;
  overflow-y: auto;
}

.search-bar {
  margin-bottom: 16px;
}


.advice-section {
  flex: 1;
  border-radius: 4px;
  margin-bottom: 4px;
  padding: 10px;
  background-color: #f9fafc;
  width: 300px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
}

.advice-section h3 {
    font-size: 16px;
    font-weight: bold;
}

.travel-advice-card {
  margin-top: 10px;
  width: 100%;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}




//.section-title {
//  font-size: 16px;
//  font-weight: 500;
//  margin-bottom: 10px;
//}

.section-content {
  font-size: 14px;
  color: #666;
}

.location-tag {
  display: block;
  width: fit-content;
  margin-top: 10px;
  font-size: 14px;
  padding: 10px 15px;
  background-color: #e0f7fa;
  color: #00796b;
  border-radius: 4px;
}
</style>
