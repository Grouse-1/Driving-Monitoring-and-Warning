<template>
  <el-card style="width: 100%; height: 500px;margin-bottom: 10px">
<!--    <goods @handleId="handleCustomEvent" :initView="initView"/>-->
    <goods :initView="initView"/>
  </el-card>

  <el-row :gutter="20">
    <el-col :span="6">
      <el-card shadow="hover" style="height: 175px">
        <template #header>
          <div class="card-header">
            <div class="card-panel">
              <div class="card-panel-description">
                <div class="card-panel-text">交易笔数</div>
              </div>
              <el-tag type="success" size="large" effect="light" style="margin-left: auto;">日</el-tag>
            </div>
          </div>
        </template>
        <div class="card-panel" style="font-size: 20px; display: flex;">
          <div style="margin-top: auto; margin-bottom: auto">{{ transCount }}</div>
          <i class="fa fa-file-text-o fa-2x" aria-hidden="true" style="margin-left:auto; color: yellowgreen;"></i>
        </div>
      </el-card>
    </el-col>
    <el-col :span="6" style="margin-left: 2%;">
      <el-card shadow="hover" style="height: 175px">
        <template #header>
          <div class="card-header">
            <div class="card-panel">
              <div class="card-panel-description">
                <div class="card-panel-text">销售额</div>
              </div>
              <el-tag type="success" size="large" effect="light" style="margin-left: auto;">日</el-tag>
            </div>
          </div>
        </template>
        <div class="card-panel" style="font-size: 20px; display: flex;">
          <div style="margin-top: auto; margin-bottom: auto">{{ transMoney }}</div>
          <i class="fa fa-cny fa-2x" aria-hidden="true" style="margin-left:auto; color: gold;"></i>
        </div>
      </el-card>
    </el-col>
    <el-col :span="10" style="margin-left: 6%">
    <el-card>
      <div ref="chart" style="width: 100%; height: 135px;"></div>
    </el-card>

    </el-col>
  </el-row>



</template>

<script setup>
import Goods from "@/views/main/trade/components/goods.vue";
import {GetSeven, GetTrans} from "@/api/getInfo/index.js";
import {ref, onMounted, watch, onBeforeMount} from 'vue';
import echarts from '@/utils/echarts.js';

const chart = ref(null);
const revenueData = ref([]);
const income = [0, 0, 0, 0, 0, 0, 0];
let myChart = null;


const transCount = ref('')
const transMoney = ref('')
const shopID = ref(3)
const shopid = ref("3")


// const handleCustomEvent = (data) =>{
//   shopID.value = data.value
//   console.log(shopID.value)
// }
const getRecentDates = () => {
  const dates = [];
  const today = new Date();
  for (let i = 6; i >= 0; i--) {
    const date = new Date(today.getTime() - i * 24 * 60 * 60 * 1000);
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    dates.push(`${month}.${day}`);
  }
  return dates;
};


const fetchRevenueData = async () => {
  // 模拟异步获取数据
  const seven =await GetSeven.Info(shopID.value)
  console.log(seven)
  income.value = seven.data;
  // 初始化 ECharts 实例并设置图表数据
  myChart = echarts.init(chart.value);
  myChart.setOption({
    title: {
      subtext: '最近七日收入(元)'
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    grid: {
      left: '28%',
      right: '0%',
      bottom: '13%',
      top: '4%',
    },
    xAxis: {
      type: 'category',
      data: getRecentDates()
    },
    yAxis: {
      type: 'value',
      // interval: 100,
      // min: 0,
      // max: 500,
    },
    series: [
      {
        data: income.value,
        type: 'bar',
        showBackground: true,
        backgroundStyle: {
          color: 'rgba(180, 180, 180, 0.2)'
        }
      }
    ]
  });
};
const initView = () =>{
  GetTrans.Info(shopID.value).then((data)=>{
    //console.log(data)
    transCount.value = data.data[0]
    transMoney.value = data.data[1]
  })
  fetchRevenueData()
}

onMounted(()=>{
  initView()
})

</script>

<style lang="scss" scoped>
.card-panel {
  display: flex;
  cursor: pointer;
  font-size: 12px;
  position: relative;
  overflow: hidden;
  color: #666;
  background: #fff;
  box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.05);
  border-color: rgba(0, 0, 0, 0.05);

  &:hover {
    .card-panel-icon-wrapper {
      color: #fff;
    }
  }

  .card-panel-icon-wrapper {
    float: left;
    transition: all 0.38s ease-out;
    border-radius: 6px;
  }

  .card-panel-description {
    float: left;
    font-weight: bold;

    .card-panel-text {

      color: rgba(0, 0, 0, 0.45);
      font-size: 16px;

    }

    .card-panel-num {
      font-size: 20px;
      text-align: left;
    }
  }
}
.el-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  transition: box-shadow 0.3s;
}
.el-card:hover {
  box-shadow: 0 4px 24px 0 rgba(0, 0, 0, 0.2);
}
</style>
