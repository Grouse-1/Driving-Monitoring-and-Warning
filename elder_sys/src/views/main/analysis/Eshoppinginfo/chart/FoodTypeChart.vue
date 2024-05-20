<template>
  <div id="pie-chart" style="width: 300px; height: 300px" ></div>
</template>

<script setup>
import {onMounted, ref, watch} from 'vue';
import echarts from "@/utils/echarts.js";
const key = ref(false)

const props = defineProps({
  tableData: {
    type: Array,
    required: true
  }
});

const emit = defineEmits(['update-percentage-data']);

watch(
    () => props.tableData,
    (newData) => {
      if (newData.length > 0) {
        console.log(props.tableData)
        initPieChart();
      }else if(newData.length === 0){
        const chartDom = document.getElementById('pie-chart');
        try {
          echarts.dispose(chartDom);
          const data1 = ref([])
          emit('update-percentage-data', data1);
          key.value = true
        }catch (error){
        }
      }
    },
    { immediate: true , deep: true }
);

function initPieChart() {
  const chartDom = document.getElementById('pie-chart');
  const myChart = echarts.init(chartDom);
  const option = {
    title: {
      text: '老人近期买菜类型占比',
      left: 'left'
    },
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: {d}%'
    },
    legend: {
      orient: 'vertical',
      right: 0,
      top: 10,
    },
    series: [
      {
        name: '食品类型',
        type: 'pie',
        radius: '70%',
        center: ['40%', '55%'],
        data: calculateTypePercentage(props.tableData),
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        },
        label: {
          show: true,
          position: 'outer',
          formatter: '{b}: {d}%'
        }
      }
    ]
  };

  myChart.setOption(option);
  // myChart.on('updated', () => {
  //   const seriesData = option.series[0].data;
  //   const updatedPercentageData = seriesData.map((item) => ({
  //     name: item.name,
  //     percent: item.value,
  //   }));
  //   emit('update-percentage-data', updatedPercentageData);
  // });
  myChart.on('updated', () => {
    const seriesData = option.series[0].data;
    const totalValue = seriesData.reduce((acc, item) => acc + item.value, 0);
    const updatedPercentageData = seriesData.map((item) => ({
      name: item.name,
      percent: (item.value / totalValue) * 100,
    }));

    if ((!isEqual(lastPercentageData.value, updatedPercentageData)) || key.value === true) {
      key.value = false
      lastPercentageData.value = updatedPercentageData;
      //console.log(1111111111)
      emit('update-percentage-data', updatedPercentageData);
    }
  });
}
const lastPercentageData = ref([]);

const isEqual = (arr1, arr2) => {
  if (arr1.length !== arr2.length) return false;
  for (let i = 0; i < arr1.length; i++) {
    if (arr1[i].name !== arr2[i].name || arr1[i].percent !== arr2[i].percent) {
      return false;
    }
  }
  return true;
};
function calculateTypePercentage(data) {
  const typeCount = {};
  data.forEach((item) => {
    typeCount[item.gtype] = (typeCount[item.gtype] || 0) + 1;
  });
  return Object.entries(typeCount).map(([name, value]) => ({
    name,
    value
  }));
}
</script>
