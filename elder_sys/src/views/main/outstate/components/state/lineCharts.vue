<template>
  <div ref="lineChart" style="width: 100%; height: 300px;"></div>
</template>

<script setup>
import {onMounted, onUnmounted, ref} from 'vue';
import echarts from '@/utils/echarts.js';
import {GetNum} from "@/api/getInfo/index.js";
import moment from "moment";

const lineChart = ref(null);
const chart = ref(null);
const locationName = ref("菜市场");
let xAxisData = [];
const lineData = ref([0,0,0,0,0,0,0,0,0,0,0]);
let ws = null;
const latestData = ref('normal');//'happy', 'normal', 'angry', 'sad', 'sleepy'
const dict = ref({
  'happy': 1,
  'normal': 2,
  'angry': 3,
  'sad': 3,
  'sleepy': 4
});
const option = {
  title: {
    subtext: '危险等级'
  },
  grid: {
    left: '8%',
    right: '16%',
    bottom: '10%'
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'cross'
    }
  },
  toolbox: {
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    // prettier-ignore
    //data: ['00:00', '01:15', '02:30', '03:45', '05:00', '06:15', '07:30', '08:45', '10:00', '11:15', '12:30', '13:45', '15:00', '16:15', '17:30', '18:45', '20:00', '21:15', '22:30', '23:45']
    data: []
  },
  yAxis: {
    type: 'value',
    interval: 1,
    min: 0,
    max: 4,
     // 固定的 y 轴数据
  },
  visualMap: {
    show: true,
    left: 'right',
    top:'10%',
    type: 'piecewise',
    dimension: 1,
    pieces: [
      {
        lte: 1,
        color: 'green',
        label: '等级1'
      },
      {
        gt: 1,
        lte: 2,
        color: 'blue',
        label: '等级2'
      },
      {
        gt: 2,
        lte: 3,
        color: '#FFA07A',
        label: '等级3'
      },
      {
        gt: 6,
        lte: 8,
        color: 'red',
        label: '等级4'
      },
    ]
  },
  series: [
    {
      name: 'state',
      type: 'line',
      smooth: true,
      // prettier-ignore
      data: lineData.value,
      markArea: {
        itemStyle: {
          color: 'rgba(255, 173, 177, 0.4)'
        },
      }
    }
  ]
};

const connectWebSocket = () => {
  return new Promise((resolve, reject) => {
    ws = new WebSocket('ws://localhost:7799/emotion');

    ws.onopen = () => {
      console.log('WebSocket连接已打开');
      resolve(); // WebSocket 连接成功后 resolve Promise
    };

    ws.onmessage = (event) => {
      console.log('接收到消息:', event.data);
      const dataArray = event.data.split(':');
      latestData.value = dict.value[dataArray[0]];
      //messages.value.push({ id: messages.value.length, data: event.data });
    };

    ws.onerror = (error) => {
      console.error('WebSocket发生错误:', error);
      reject(error);
    };

    ws.onclose = () => {
      console.log('WebSocket连接已关闭');
    };
  });
};

function generateTimeLabels() {
  let currentTime = moment().minutes(Math.floor(moment().minutes())).seconds(0); // 获取当前时间并将分钟和秒数调整为5的倍数
  xAxisData = [currentTime.format('HH:mm')]; // 将当前时间加入数组
  for (let i = 1; i <= 10; i++) {
    let previousTime = currentTime.clone().subtract(i, 'minutes'); // 依次获取前面的五个时间点
    xAxisData.unshift(previousTime.format('HH:mm')); // 将时间点加入数组的开头
  }
}

async function getState(arg) {
  if (arg === 0) {
    console.log("第一次访问")
    return 0;
  }
  if (latestData.value === '') {
    console.log("ws未获得数据")
    return 0;
  }
  console.log("正常获取数据")
    return latestData.value
}

async function updateChartXAxisAndRefresh(count) {
  generateTimeLabels(); // 生成时间标签
  option.xAxis.data = xAxisData; // 更新图表横坐标数据
  console.log(locationName.value)
  try {
    const newData =await getState(count); // 获取新的折线数据
    console.log(newData)
    const newDataArray = option.series[0].data; // 获取当前的折线数据数组
    newDataArray.push(parseInt(newData)); // 将新数据添加到数组末尾
    if (newDataArray.length > 11) {
      newDataArray.shift(); // 如果数组长度超过六个，移除最旧的数据
    }
    option.series[0].data = newDataArray; // 更新折线数据
    const myChart = echarts.init(lineChart.value);
    myChart.setOption(option); // 更新图表配置
  } catch (error) {
    console.error("Failed to update chart data:", error);
  }
}


onMounted(() => {


  // 每五分钟更新一次图表横坐标
  connectWebSocket();
  updateChartXAxisAndRefresh(0); // 首次加载时生成横坐标并绘制图表
  setInterval(() => {
    updateChartXAxisAndRefresh();
  },  30 * 1000); // 半分钟刷新一次
});

onUnmounted(() => {
  if (ws) {
    ws.close();
  }
});
</script>

<style scoped>
</style>
