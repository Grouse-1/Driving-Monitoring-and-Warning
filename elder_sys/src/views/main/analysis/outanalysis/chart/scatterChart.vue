<template>
  <div id="myChart" style="width: 100%; height: 250px;"></div>
</template>
<script setup>
import echarts from "@/utils/echarts.js";
import { onMounted } from 'vue';
import {GetSleepyData} from "@/api/getInfo/index.js";

onMounted(() => {
  const myChart = echarts.init(document.getElementById('myChart'));

  // 获取后端数据
  GetSleepyData.Info().then((res) => {
    console.log(res.data)
    const data = res.data;
    const formattedData = Object.entries(res.data).map(([time, count]) => {
      return [formatTimeData(time), count];
    });

    const option = {
      title: {
        subtext: '次数'
      },
      grid: {
        top: '5%',
        right: '5%',
        bottom: '5%',
        left: '5%',
        containLabel: true
      },
      xAxis: {
        type: 'value',
        min: 0,
        max: 24,
        // 设置 x 轴主刻度间隔为 3 小时
        interval: 3,
        // 自定义 x 轴刻度标签的格式化函数
        axisLabel: {
          formatter: (value) => {
            const hours = Math.floor(value);
            return `${hours}:00`;
          }
        }
      },
      yAxis: {
        type: 'value',
        minInterval: 1
      },
      series: [
        {
          symbolSize: 10,
          data: formattedData,
          type: 'scatter'
        }
      ]
    };

    myChart.setOption(option);
  });
});
function fetchData() {
  return new Promise((resolve) => {
    // 模拟后端数据
    const data = [
      ['9:32', 3],
      ['12:00', 2],
      ['18:00', 5],
      // 添加更多数据点
    ];
    resolve(data);
  });
}

function formatTimeData(timeStr) {
  const [hours, minutes] = timeStr.split(':').map(Number);
  return hours + minutes / 60;
}

</script>


<style scoped lang="scss">
</style>
