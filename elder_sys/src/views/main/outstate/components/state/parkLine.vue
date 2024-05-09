<template>
  <div ref="chart" style="width: 100%; height: 212px;"></div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import echarts from '@/utils/echarts.js';
import moment from 'moment';
import {GetNum} from "@/api/getInfo/index.js";

const chart = ref(null);
let xAxisData = [];
const locationName = ref("公园")

const option = {
  title: {
    subtext: '人数'
  },
  grid: {
    left: '10%',
    right: '5%',
    bottom: '10%',
    top: '18%',
  },
  tooltip: {
    trigger: 'axis',
    formatter: '{b}: {c} 人'
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: xAxisData
  },
  yAxis: {
    type: 'value',
    interval: 5,
    min: 0,
    max: 40,
  },
  series: [{
    data: [0,0,0,0,0,0],
    type: 'line',
    smooth: true,
  }]
};

// 生成时间标签
function generateTimeLabels() {
  let currentTime = moment().minutes(Math.floor(moment().minutes())).seconds(0); // 获取当前时间并将分钟和秒数调整为5的倍数
  xAxisData = [currentTime.format('HH:mm')]; // 将当前时间加入数组
  for (let i = 1; i <= 5; i++) {
    let previousTime = currentTime.clone().subtract(i, 'minutes'); // 依次获取前面的五个时间点
    xAxisData.unshift(previousTime.format('HH:mm')); // 将时间点加入数组的开头
  }
}

// 更新图表横坐标以及触发图表更新
async function updateChartXAxisAndRefresh() {
  generateTimeLabels(); // 生成时间标签
  option.xAxis.data = xAxisData; // 更新图表横坐标数据
  console.log(locationName.value)
  try {
    const newData = await GetNum.Info(locationName.value); // 获取新的折线数据
    console.log(newData.data)
    const newDataArray = option.series[0].data; // 获取当前的折线数据数组
    newDataArray.push(newData.data); // 将新数据添加到数组末尾
    if (newDataArray.length > 6) {
      newDataArray.shift(); // 如果数组长度超过六个，移除最旧的数据
    }
    option.series[0].data = newDataArray; // 更新折线数据
    const myChart = echarts.init(chart.value);
    myChart.setOption(option); // 更新图表配置
  } catch (error) {
    console.error("Failed to update chart data:", error);
  }
}

onMounted(() => {
  const myChart = echarts.init(chart.value);
  myChart.setOption(option);
  updateChartXAxisAndRefresh(); // 首次加载时生成横坐标并绘制图表

  // 每五分钟更新一次图表横坐标
  setInterval(() => {
    updateChartXAxisAndRefresh();
  },  30 * 1000); // 5分钟刷新一次
});
</script>

<style scoped lang="scss">
/* 这里可以添加组件样式 */
</style>
