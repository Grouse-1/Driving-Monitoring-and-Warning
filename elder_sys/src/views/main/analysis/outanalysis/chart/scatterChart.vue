<template>
  <div id="myChart" style="width: 100%; height: 250px;"></div>
</template>

<script setup>
import echarts from "@/utils/echarts.js";
import {onMounted, ref, watch} from 'vue';
import {GetHappyList, GetSleepyData} from "@/api/getInfo/index.js";

const emit = defineEmits(['outAdvice']);
const recommendedTimes = ref([]);
const elderid = ref();
const props = defineProps({
  elderid: {
    type: String,
    required: true
  }
});

// 监听 elderid 的变化
watch(() => props.elderid, async (newDate) => {
  elderid.value = props.elderid;
  await initializeChart();
}, {immediate: true, deep: true});

// 提取图表初始化和数据加载逻辑到一个独立的函数中
const initializeChart = async () => {
  const myChart = echarts.init(document.getElementById('myChart'));
  if (localStorage.getItem("role") === 'family') {
    elderid.value = localStorage.getItem("elderid");
  }

  try {
    const res = await GetSleepyData.Info(elderid.value);
    console.log(res.data);
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
        interval: 3,
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
  } catch (error) {
    console.error(error);
    // 处理错误逻辑
  }
};

// 调用 initializeChart 函数
onMounted(initializeChart);

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
