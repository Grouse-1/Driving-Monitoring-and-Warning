<template>
  <div ref="chart" style="width: 100%; height: 270px;"></div>
</template>

<script setup>
import {ref, onMounted, watch} from 'vue';
import echarts from '@/utils/echarts.js';
import {GetHappyList} from "@/api/getInfo/index.js";

const props = defineProps({
  elderid: {
    type: String,
    required: true
  }
})

watch(()=>props.elderid, async (newDate) => {
  const data = await GetHappyList.Info(props.elderid);
  initChart(data.data);
},{ immediate: true, deep: true})
// 模拟从后端获取数据
const fetchData = async () => {
  // 这里你可以替换成实际的 API 调用

  return new Promise((resolve) => {
    setTimeout(() => {
      resolve({
        park: 10,
        market: 5,
      });
    }, 1000);
  });
};

const chart = ref(null);

const initChart = (data) => {
  const chartInstance = echarts.init(chart.value);
  const option = {
    title: {
      subtext: '次数'
    },
    grid: {
      top: '10%',
      right: '5%',
      bottom: '2%',
      left: '6%',
      containLabel: true
    },
    tooltip: {},
    xAxis: {
      type: 'category',
      data: ['公园', '菜市场'],
    },
    yAxis: {
      type: 'value',
      minInterval: 1
    },
    series: [
      {
        name: '次数',
        type: 'bar',
        data: [data.park, data.market],
      },
    ],
  };
  chartInstance.setOption(option);
};

onMounted(async () => {
  const data = await GetHappyList.Info(props.elderid);
  console.log(data.data)
  initChart(data.data);
});
</script>
