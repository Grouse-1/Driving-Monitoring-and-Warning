<template>
  <div :class="['current-time-widget', isDaytime ? 'daytime' : 'nighttime']">
    <el-card class="time-card">
      <div class="time">{{ currentTime }}</div>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentTime: '',
      isDaytime: true,
    }
  },
  mounted() {
    this.updateTime()
    this.updateDaytime()
    setInterval(() => {
      this.updateTime()
      this.updateDaytime()
    }, 1000) // 每秒更新一次
  },
  methods: {
    updateTime() {
      const now = new Date()
      this.currentTime = now.toLocaleTimeString()
    },
    updateDaytime() {
      const now = new Date()
      const hour = now.getHours()
      this.isDaytime = hour >= 6 && hour < 18 // 假设白天是从早上6点到晚上6点
    },
  },
}
</script>

<style scoped>
.current-time-widget {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.time-card {
  width: 200px;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.widget-title {
  font-size: 18px;
  font-weight: bold;
}

.time {
  font-size: 24px;
  text-align: center;
  margin-top: 20px;
}

 

.nighttime {
  background-color: rgb(54, 43, 215);
  color: white;
}
</style>
