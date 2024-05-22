<template>
  <el-row :gutter="20">
    <el-col :span="15">
      <el-card style="width: 100%; margin-bottom: 20px; text-align: center;">
        <template #header>
          <div class="card-header">
            <span style="font-weight: bold; font-size: 16px;">实时视频</span>
          </div>
        </template>
        <smp-video/>
      </el-card>
    </el-col>
    <el-col :span="9">
      <el-card style="width: 100%; margin-bottom: 5px">
        <el-card style="width: 100%; margin-bottom: 5px" shadow="hover">
          <span style="align-items: center; font-weight: bold;margin-right: 20px">当前状态：</span>
          <el-tag :type="Etype" size="large" effect="light" style="width: 200px">{{ latestData.value}}</el-tag>
        </el-card>
        <el-card style="width: 100%; height: 50%; margin-bottom: 10px"  shadow="hover">
          <pie-chart />
        </el-card>
        <el-card style="width: 100%; margin-bottom: 5px"  shadow="hover">
          <div v-if=" key === 'admin'">
            <el-input v-model="Ename" clearable style="width: 80%;" placeholder="请输入老人姓名" />
            <el-button :icon="Search" @click="initElderly" style="margin-left: 5px; background-color: gray; width: 10%" />
          </div>
          <div v-else>
            <el-input clearable style="width: 80%;" placeholder="非管理员无权搜索" />
            <el-button :icon="Search" style="margin-left: 5px; background-color: gray; width: 10%" />
          </div>
        </el-card>
        <el-card style="width: 100%; height: 125px"  shadow="hover">
        <el-descriptions
            class="margin-top"
            :column="column"
            :size="size"
            :direction="direction"
            border
            style="height: 300px"
        >
          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon :style="iconStyle">
                  <user />
                </el-icon>
                姓名
              </div>
            </template>
            {{form.name}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon :style="iconStyle">
                  <grid />
                </el-icon>
                年龄
              </div>
            </template>
            {{ form.age }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon :style="iconStyle">
                  <user-filled />
                </el-icon>
                性别
              </div>
            </template>
            {{form.sex}}
          </el-descriptions-item>x`

          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon :style="iconStyle">
                  <location />
                </el-icon>
                地点
              </div>
            </template>
            {{form.location}}
          </el-descriptions-item>
        </el-descriptions>
        </el-card>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup>
import SmpVideo from "@/views/main/remoteVideo/Video/smpVideo.vue";
import {Grid, Money, Search, UserFilled} from "@element-plus/icons-vue";
import {onMounted, onUnmounted, ref} from "vue";
import PieChart from "@/views/main/remoteVideo/components/pieChart.vue";
import {GetElderly} from "@/api/getInfo/index.js";
const size = 'default'
const direction = ref('horizontal');
const column = ref(2)
const Etype = ref('primary')
const key = ref('')
key.value = localStorage.getItem('role')
const form = ref({
  name: '暂无',
  age: '暂无',
  sex: '暂无',
  location: '暂无',
})

//const messages = ref([]);
const latestData = ref('未知');
let ws = null;
const elderlyList = ref({})
const Ename = ref("");
const LocationKV = ref({
  1: '菜市场',
  2: '公园'
})
const dict = ref({
  happy: '愉快',
  sad:'难过',
  normal: '平静',
  sleepy: '犯困',
  angry: '其他',
})

const connectWebSocket = () => {
  // 这里的地址ws://localhost:3000需要替换为你的WebSocket服务端地址
  ws = new WebSocket('ws://localhost:7799/emotion');

  ws.onopen = () => {
    console.log('WebSocket连接已打开');
  };

  //显示实时表情
  ws.onmessage = (event) => {
    console.log('接收到消息:', event.data);
    const dataArray = event.data.split(':');
    latestData.value = ref(dict.value[dataArray[0]]);
    if(dataArray[0] === "sleepy"){
      Etype.value = "danger";
    } else {
      Etype.value = "primary";
    }
    //messages.value.push({ id: messages.value.length, data: event.data });
  };

  ws.onerror = (error) => {
    console.error('WebSocket发生错误:', error);
  };

  ws.onclose = () => {
    console.log('WebSocket连接已关闭');
  };
};

const initElderly = async() => {
  const res = await GetElderly.Info(Ename)
  const updatedElderlyList = res.data.map(item => {
    return {
      ...item,
      location: LocationKV.value[item.locationId],
    }
  })
  if (res.data[0]) {
    elderlyList.value = updatedElderlyList[0]
    console.log(elderlyList.value)
    form.value.name = elderlyList.value.name;
    form.value.age = elderlyList.value.age;
    form.value.location = elderlyList.value.location;
    form.value.sex = elderlyList.value.sex;
    //console.log(elderlyList.value)
  }
}
onMounted(async () => {
  if (localStorage.getItem('role') === 'family') {

    await GetElderly.getName(localStorage.getItem('elderid')).then((res1) => {
      Ename.value = res1.data
      initElderly()
    })

  }
  connectWebSocket();
});

onUnmounted(() => {
  if (ws) {
    ws.close();
  }
});
</script>
<style scoped>
.el-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  transition: box-shadow 0.3s;
}
.el-card:hover {
  box-shadow: 0 4px 24px 0 rgba(0, 0, 0, 0.2);
}
</style>

