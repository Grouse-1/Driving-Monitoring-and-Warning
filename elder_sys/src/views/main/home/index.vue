<template>
  <div class="container">
    <weather-component></weather-component>
    <time-component></time-component> 
    <chart-component class="small-chart"></chart-component>
    <!-- 在这里添加其他组件 -->
  </div>
</template>

<script>
import WeatherComponent from '@/views/home/weather.vue'
import ChartComponent from '@/views/home/chart.vue'
import TimeComponent from '@/views/home/time.vue'

export default {
  components: {
    WeatherComponent,
    ChartComponent,
    TimeComponent,
    // 在这里注册其他组件
  },
}
</script>

<style>
.container {
  display: grid;
  grid-template-columns: 1fr 1fr; /* 将父容器分为两列，每列宽度相等 */
  gap: 20px; /* 设置列之间的间距 */
}


</style>
