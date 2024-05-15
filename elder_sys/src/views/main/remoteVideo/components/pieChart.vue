<template>
  <div ref="dialog" style="width: 100%; height: 300px;"></div>
</template>

<script setup>
import { onMounted, ref, onUnmounted } from 'vue';
import echarts from '@/utils/echarts.js';
import {GetEmotion} from "@/api/getInfo/index.js";

const dialog = ref(null);
const option = ref({
  title: {
    text: '今日心情',
    subtext: '',
    left: 'center'
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b}:({d}%)'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      name: '最近表情',
      type: 'pie',
      radius: '50%',
      data: []
    }
  ]
});

let intervalId;
const dict = ref({
  happy: '愉快',
  sad:'难过',
  normal: '平静',
  sleepy: '犯困',
  angry: '其他',
})

onMounted(() => {
  const chart = echarts.init(dialog.value);
  chart.setOption(option.value);
  updateChartData()
  // 每分钟更新数据
  intervalId = setInterval(updateChartData, 60000);
});

onUnmounted(() => {
  // 清除定时器
  clearInterval(intervalId);
});

async function updateChartData() {
  try {
    // 请求后端表情百分比数据
    const res = await GetEmotion.Info()
    const data = res.data
    console.log(data)

    // 计算总数
    const total = data.reduce((acc, item) => acc + item.count, 0);

    // 将英文名称转换成中文名称
    const formattedData = data.map(item => ({
      value: item.count,
      name: dict.value[item.emotion] || item.emotion,
      percentage: Math.round((item.count / total) * 100)
    }));

    // 更新饼图数据
    option.value.series[0].data = formattedData;

    const chart = echarts.init(dialog.value);
    chart.setOption(option.value);
  } catch (error) {
    console.error('Error fetching emotion data:', error);
  }
}
</script>

<style scoped>
</style>
