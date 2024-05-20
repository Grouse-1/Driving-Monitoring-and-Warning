<template>
  <el-row :gutter="20">
    <el-col :span="16">
      <el-card style="width: 100%; margin-bottom: 20px">
        <div class="clearfix" style="height: 32px" >尊敬的用户，今天是 {{ currentDate }}</div>
      </el-card>
    </el-col>

    <el-col :span="8">
      <el-card style="width: 100%; margin-bottom: 20px">
        <div v-if=" key === 'admin'">
        <el-input v-model="Ename" clearable style="width: 240px;" placeholder="请输入老人姓名" />
        <el-button :icon="Search" @click="initElderly" style="margin-left: 2px; background-color: gray" />
        </div>
        <div v-else>
          <el-input clearable style="width: 240px;" placeholder="非管理员无权搜索" />
          <el-button :icon="Search" style="margin-left: 2px; background-color: gray" />
        </div>
      </el-card>
<!--      <el-tag type="primary">Tag 1</el-tag>-->
<!--      <el-tag type="success">Tag 2</el-tag>-->
<!--      <el-tag type="info">Tag 3</el-tag>-->
<!--      <el-tag type="warning">Tag 4</el-tag>-->
<!--      <el-tag type="danger">Tag 5</el-tag>-->
    </el-col>
  </el-row>

  <el-row :gutter="20">
    <el-col :span="16">
      <el-row >
        <el-card style="text-align: center;width: 75%; margin-right: 5px">
        <template #header>老人实时状态</template>
        <line-chart/>
        </el-card>
        <el-card style="text-align: center;width: 24%"  >
          <template #header>危险条目</template>
          <el-table :data="tableData"  style="width: 100%" :cell-style="cellStyle" >
            <el-table-column prop="date" label="等级" width="70" />
            <el-table-column prop="name" label="状态" width="80" />
          </el-table>
        </el-card>
      </el-row>

    </el-col>
    <el-col :span="8">
      <el-card style="text-align: center">
        <template #header>
          <div class="card-header">
            <span style="font-weight: bold; font-size: 16px;">老人基本信息</span>
          </div>
        </template>
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
          </el-descriptions-item>

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

          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon :style="iconStyle">
                  <money />
                </el-icon>
                余额
              </div>
            </template>
            {{form.balance}}
          </el-descriptions-item>
        </el-descriptions>
      </el-card>
    </el-col>
  </el-row>


  <el-row :gutter="20" style="margin-top: 20px">
    <el-col :span="8" >
      <el-card style="text-align: center">
        <template #header>菜市场人口密集度</template>
        <market-line />
      </el-card>
    </el-col>
    <el-col :span="8">
      <el-card style="text-align: center">
        <template #header>公园人口密集度</template>
        <park-line/>
      </el-card>
    </el-col>
    <el-col :span="8">
      <el-card
        shadow="hover"
        style="height: 315px; padding: 0px; margin: 0px;text-align: center"
        :body-style="{ padding: '0px' }"

    >
        <template #header>
          <div class="card-header">
            <span style="font-weight: bold; font-size: 16px;">今日天气</span>
          </div>
        </template>
        <iframe scrolling="no" src="https://widget.tianqiapi.com/?style=yf&skin=pitaya" frameborder="0" width="100%" height="320" allowtransparency="true"></iframe>
      </el-card>
    </el-col>
  </el-row>





</template>

<script setup>
import {Grid, Money, Search, UserFilled} from '@element-plus/icons-vue'
import {computed, onBeforeMount, onMounted, ref} from 'vue'
import LineChart from "@/views/main/outstate/components/state/lineCharts.vue";
import MarketLine from "@/views/main/outstate/components/state/marketLine.vue";
import ParkLine from "@/views/main/outstate/components/state/parkLine.vue";
import {GetElderly} from "@/api/getInfo/index.js";

const currentDate = ref('');
const size = 'default'
const direction = ref('horizontal');
const column = ref(1)
const key = ref('')
key.value = localStorage.getItem('role')
const tableData = [
  {
    date: '1',
    name: '积极',
    arg:'[0,200]'
  },
  {
    date: '2',
    name: '正常',
    arg:'[200,400]'
  },
  {
    date: '3',
    name: '消极',
    arg:'[400,600]'
  },
  {
    date: '4',
    name: '犯困',
    arg:'[600,800]'
  }]
const LocationKV = ref({
  1: '菜市场',
  2: '公园'
})
const elderlyList = ref({})
const Ename = ref("");
const cellStyle = ({ rowIndex, columnIndex }) => {
  // 状态列字体颜色
  // columnIndex 列下标
  // rowIndex 行下标
  // row 行
  // column 列
  if (columnIndex ===1 && rowIndex === 0) {
    return { color: "#1CD66C" };
  }else if (columnIndex ===1 && rowIndex === 1) {
    return { color: "#189EFF" };
  } else if (columnIndex ===1 && rowIndex === 2) {
    return { color: "#FF7F50" };
  } else if (columnIndex ===1 && rowIndex === 3) {
    return { color: "red" };
  }
}
const form = ref({
  name: '暂无',
  age: '暂无',
  sex: '暂无',
  location: '暂无',
  balance:'暂无',
})




const updateCurrentDate=()=>{
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, '0');
  const day = String(now.getDate()).padStart(2, '0');
  const hours = String(now.getHours()).padStart(2, '0');
  const minutes = String(now.getMinutes()).padStart(2, '0');
  const seconds = String(now.getSeconds()).padStart(2, '0');
  currentDate.value = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

const iconStyle = computed(() => {
  const marginMap = {
    large: '8px',
    default: '6px',
    small: '4px',
  };
  return {
    marginRight: marginMap[size.valueOf()] || marginMap.default,
  };
});



const initElderly = async() => {
  // if(Ename === ''){
  //   form.value = null
  //   return null
  // }
  console.log("Ename",Ename.value)
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
    form.value.balance = elderlyList.value.balance;
    //console.log(elderlyList.value)
  }
}

onBeforeMount(()=>{

})

onMounted(async () => {
  if (localStorage.getItem('role') === 'family') {

    await GetElderly.getName(localStorage.getItem('elderid')).then((res1) => {
      Ename.value = res1.data
      initElderly()
    })

  }
  updateCurrentDate();
  setInterval(updateCurrentDate, 1000); // 每秒更新一次时间
});



</script>

<style lang="scss" scoped>
</style>
