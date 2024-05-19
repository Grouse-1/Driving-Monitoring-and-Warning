<template>
  <div id="myChart" style="width: 100%; height: 250px;"></div>
</template>
<script setup>
import echarts from "@/utils/echarts.js";
import {onMounted, ref} from 'vue';
import {GetSleepyData} from "@/api/getInfo/index.js";

const emit = defineEmits(['outAdvice']);
const recommendedTimes = ref([]);

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
    recommendTimes(data);
  });

  // const data = '这是子组件的数据';
  // emit('outAdvice', data);
});
// function recommendTimes(data) {
//   const timeDataPairs = Object.entries(data).map(([time, count]) => ({
//     time,
//     count
//   }));
//
//   // 按犯困次数排序，找出最少的三个点
//   timeDataPairs.sort((a, b) => a.count - b.count);
//   const minThreePoints = timeDataPairs.slice(0, 3);
//
//   // 过滤掉犯困次数超过3次的点
//   recommendedTimes.value = minThreePoints
//       .filter(point => point.count <= 3)
//       .map(point => point.time);
// }
// function fetchData() {
//   return new Promise((resolve) => {
//     // 模拟后端数据
//     const data = [
//       ['9:32', 3],
//       ['12:00', 2],
//       ['18:00', 5],
//       // 添加更多数据点
//     ];
//     resolve(data);
//   });
// }


// function formatTimeData(timeStr) {
//   const [hours, minutes] = timeStr.split(':').map(Number);
//   return hours + minutes / 60;
// }


function formatTimeData(timeStr) {
  const [hours, minutes] = timeStr.split(':').map(Number);
  return hours + minutes / 60;
}

function recommendTimes(data) {
  const timeDataPairs = Object.entries(data).map(([time, count]) => ({
    time,
    count,
    timeValue: formatTimeData(time) // 将时间字符串转换为数值形式
  }));

  // 过滤出 6:00 到 21:00 之间的时间点
  const filteredTimeDataPairs = timeDataPairs.filter(point =>
      point.timeValue >= 6 && point.timeValue <= 18
  );

  // 按犯困次数排序，找出最少的三个点
  filteredTimeDataPairs.sort((a, b) => a.count - b.count);
  const minThreePoints = filteredTimeDataPairs.slice(0, 3);

  // 过滤掉犯困次数超过3次的点
  recommendedTimes.value = minThreePoints
      .filter(point => point.count <= 3)
      .map(point => point.time);
  emit('outAdvice', recommendedTimes.value);
}

</script>


<style scoped lang="scss">
</style>