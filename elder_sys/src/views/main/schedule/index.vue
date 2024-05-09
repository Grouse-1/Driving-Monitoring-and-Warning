<!--<template>-->
<!--  <div>danger</div>-->
<!--</template>-->

<!--<script setup></script>-->

<!--<style lang="scss" scoped></style>-->

<template>
  <div>
    <h1>实时数据显示</h1>
    <ul>
      <li v-for="message in messages" :key="message.id">{{ message.data }}</li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';

const messages = ref([]);
let ws = null;

const connectWebSocket = () => {
  // 这里的地址ws://localhost:3000需要替换为你的WebSocket服务端地址
  ws = new WebSocket('ws://localhost:7799/emotion');

  ws.onopen = () => {
    console.log('WebSocket连接已打开');
  };

  ws.onmessage = (event) => {
    console.log('接收到消息:', event.data);
    //latestData.value = event.data;
    messages.value.push({ id: messages.value.length, data: event.data });
  };

  ws.onerror = (error) => {
    console.error('WebSocket发生错误:', error);
  };

  ws.onclose = () => {
    console.log('WebSocket连接已关闭');
  };
};

onMounted(() => {
  connectWebSocket();
});

onUnmounted(() => {
  if (ws) {
    ws.close();
  }
});
</script>

