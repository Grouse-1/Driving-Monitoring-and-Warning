<template>
<!--  1.老人买菜购买记录-->
<!--  2.当月买菜类别占比-->
<!--    谷物类 肉蛋奶类  豆类及其制品类 蔬菜水果类  油脂坚果类-->

<!--  3.老人饮食标准显示-->
<!--    碳水化合物  蛋白质  维生素 膳食纤维 油脂-->
<!--  -->
<!--  4.给出老人食物购买推荐建议-->
<el-row :gutter = "20" >
  <el-col :span="12" >
    <el-card>
      <template #header>
        <div class="card-header">
          <span class="card-title">老人买菜购买记录</span>
        </div>
      </template>
      <div class="table-container">
        <div class="search-bar">
          <el-input v-model="search" size="large" placeholder="搜索日期或名字" />
        </div>
        <el-table
            :data="filterTableData"
            style="width: 100%; height: 475px"
            :default-sort="{ prop: 'date', order: 'descending' }"
        >
          <el-table-column label="购买时间" prop="tdate" sortable width="120" />
          <el-table-column label="名字" prop="name" width="80"/>
          <el-table-column label="质量" prop="quantity" width="80"/>
          <el-table-column label="价格(元/斤)" prop="price" width="100"/>
          <el-table-column label="商店名" prop="shopname" width="100"/>
          <el-table-column label="金额" prop="money"/>
          <!-- 其他需要添加的 el-table-column -->
        </el-table>
      </div>
    </el-card>
  </el-col>
  <el-col :span="12" >
    <el-card style="display: flex;">
      <div ref="dialog1" style="width: 600px; height: 300px"></div>
      <el-row :gutter="20">
        <el-col :span="13">
          <FoodTypeChart :tableData="tableData" @update-percentage-data="handlePercentageDataUpdate"/>
        </el-col>
        <el-col :span="11">
          <div class="divTitle" style="margin-bottom: 20px">老人买菜推荐</div>
          <el-card style="box-shadow: none; width: 98%; height: 250px">
            <el-skeleton :rows="6" animated :loading="loading"/>
<!--            {{advice}}-->
<!--              <div v-for="item in categoryData" :key="item.name">-->
<!--                {{ item.name }}: {{ item.percent.toFixed(2) }}%-->
<!--              </div>-->
<!--              <div v-if="missingCategories.length > 0">-->
<!--                缺失的类别: {{ missingCategories.join(', ') }}-->
<!--              </div>-->
            <div v-if="lowPercentageCategories.length > 0" class="advice-container">
              <div style="height: 90px">
                <div class="advice-title">营养建议:</div>
                <div class="advice-content">
                  <p>建议适当增加食物类别的摄取比例:</p>
                  <div class="category-list">{{ lowPercentageCategories.join('、') }}</div>
                </div>
              </div>
              <div style="height: 50%">
                <div class="advice-title">参考推荐:</div>
                    <div v-for="(value, key) in resFood" :key="key" style="display: flex">
                      <div>{{ key }}：</div>
                      <div style="display: flex"><div v-for="item in value" :key="item" style="color: #666;">{{ item }}、</div></div>

                    </div>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>


    </el-card>
  </el-col>
</el-row>


</template>

<script setup>
import {computed, onMounted, ref, watch} from 'vue'
import {GetAdvise, GetBuyData} from "@/api/getInfo/index.js";
import echarts from "@/utils/echarts.js";
import FoodTypeChart from "@/views/main/analysis/Eshoppinginfo/chart/FoodTypeChart.vue";

const search = ref('')
const loading = ref(true)
const advice = ref('')
//搜索框过滤
const filterTableData = computed(() => {
  if (tableData.value.length > 0) {
    return tableData.value.filter((data) =>
        data.name.toLowerCase().includes(search.value.toLowerCase()) ||
        data.tdate.includes(search.value)
    );
  } else {
    return [];
  }
});


const tableData = ref([
]);
const elderid = ref(1)
if(localStorage.getItem("role")==='family'){
  elderid.value = localStorage.getItem("elderid")
}
const resFood = ref({})
//标准饮食占比
const FoodData = [
  { name: '蛋白质', value: 15 },
  { name: '脂肪', value: 25 },
  { name: '碳水化合物', value: 55 },
  { name: '膳食纤维', value: 4 },
  { name: '维生素和矿物质', value: 1}
];
const dialog1 = ref(null);

//标准饼图配色
const getColor = (name) => {
  switch (name) {
    case '蛋白质':
      return '#d48265';
    case '脂肪':
      return '#ca8622';
    case '碳水化合物':
      return '#91c7ae';
    case '膳食纤维':
      return '#749f83';
    case '维生素和矿物质':
      return '#61a0a8';
    default:
      return '#c23531';
  }
};


//合理化推荐饮食
const allCategories = ['蔬果类', '蛋白质类', '脂肪类', '谷物类'];
const categoryThresholds = {
  '谷物类': 40,
  '蛋白质类': 10,
  '脂肪类': 5,
  '蔬果类': 10
};
const categoryData = ref([]);
//缺失食物的类别
const visibleCategories = computed(() => {
  return categoryData.value.filter(item => item.percent > 0);
});
const missingCategories = computed(() => {
  return Object.keys(categoryThresholds).filter(category => !visibleCategories.value.some(item => item.name === category));
});
//低百分比食物的类别
const lowPercentageCategories = computed(() => {
  return categoryData.value
      .filter(item => item.percent < categoryThresholds[item.name])
      .map(item => item.name);
});
//开始分析食物数据
const handleDataUpdate = (data) => {
  // 初始化 categoryData
  categoryData.value = allCategories.map(category => ({
    name: category,
    percent: data.find(item => item.name === category)?.percent || 0
  }));
};

//返回子组件(老人近期饮食)种类和占比数据
const percentageData = ref([]);
const handlePercentageDataUpdate = (updatedPercentageData) => {
  percentageData.value = updatedPercentageData;
  //console.log("percentageData:", percentageData.value[0]);
  loading.value = false
  advice.value = percentageData.value;
  handleDataUpdate(advice.value);
};
const chartData = FoodData.map(item => ({
  name: item.name,
  value: item.value,
  itemStyle: { color: getColor(item.name) }
}));



//初始化页面
const initView = () => {
  GetBuyData.Info(elderid.value).then((res) => {
    if (res.data && res.data.length > 0) {
      //console.log(res.data)
      tableData.value = res.data;
    } else {
      tableData.value = [];
    }
  });
}

//标准饮食饼图参数
const option = {
  title: {
    text: '营养均衡参考',
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
    data: FoodData.map(item => item.name)
  },
  series: [
    {
      name: '营养成分',
      type: 'pie',
      radius: '70%',
      center: ['45%', '55%'],
      data: chartData,
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

watch(
    () => lowPercentageCategories.value,
    (categories) => {
      if (categories.length > 0) {
        //console.log("categories:",categories)
        GetAdvise.Info(categories).then((res)=>{
          resFood.value = res.data
        })
      }
    }
);

onMounted(()=>{
  initView()
  const chart1 = echarts.init(dialog1.value);
  chart1.setOption(option);
})

</script>

<style lang="scss" scoped>
.table-container {
  display: flex;
  flex-direction: column;
}

.search-bar {
  margin-bottom: 16px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;

}

.card-title {
  font-size: 18px;
  font-weight: bold;
  color: #303133;
}
.divTitle {
  font-size: 18px;
  font-weight: bold;
  color: #464646;
}
.advice-container {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
  height: 100%;
}

.advice-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 5px;
}

.advice-content {
  font-size: 14px;
}

.category-list {
  margin-top: 5px;
  color: #666;
}
</style>
<!--<template #default-->
<!--              = "scope" >-->
<!--  < el - button-->
<!--  type = "primary"-->
<!--  @click-->
<!--  = "handleEdit.value(scope.$index, scope.row)" >-->
<!--  编辑-->
<!--  < /el-button>-->
<!--  <el-button type="danger" @click = "handleDelete.value(scope.$index, scope.row)" >-->
<!--    删除-->
<!--    < /el-button>-->
<!--</template>-->
<!--const handleEdit = ref((index, row) => {-->
<!--console.log(index, row);-->
<!--});-->

<!--const handleDelete = ref((index, row) => {-->
<!--console.log(index, row);-->
<!--});-->