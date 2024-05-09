<template>
  <div id="main" style="width: 400px; height: 350px;"></div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import * as echarts from 'echarts'

const chartDom = ref(null)
const myChart = ref(null)
let option

onMounted(() => {
  chartDom.value = document.getElementById('main')
  myChart.value = echarts.init(chartDom.value)

  option = {
    xAxis: {
      type: 'category',
      data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
    },
    yAxis: {
      type: 'value',
    },
    series: [
      {
        data: [150, 230, 224, 218, 135, 147, 260],
        type: 'line',
      },
    ],
  }

  option && myChart.value.setOption(option)
})
</script>
