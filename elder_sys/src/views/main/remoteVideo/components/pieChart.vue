<template>
  <div ref="dialog" style="width: 100%; height: 300px;"></div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import echarts from '@/utils/echarts.js';

const dialog = ref(null);
const option = {
  title: {
    text: '今日心情',
    subtext: '',
    left: 'center'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      name: 'Access From',
      type: 'pie',
      radius: '50%',
      data: [
        { value: 1048, name: '其他' },
        { value: 735, name: '愉快' },
        { value: 580, name: '难过' },
        { value: 484, name: '犯困' },
        { value: 300, name: '平静' }
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
};

onMounted(() => {
  const chart = echarts.init(dialog.value);
  chart.setOption(option);
});
</script>

<style scoped>
</style>
