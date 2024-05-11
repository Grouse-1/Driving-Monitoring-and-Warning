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
    <el-card>
      图表占比处
    </el-card>
  </el-col>
</el-row>


</template>

<script setup>
import {computed, onMounted, ref} from 'vue'
import {GetBuyData} from "@/api/getInfo/index.js";

const search = ref('')
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
const initView = () => {
  GetBuyData.Info(elderid.value).then((res) => {
    if (res.data && res.data.length > 0) {
      tableData.value = res.data;
    } else {
      tableData.value = [];
    }
  });
}



onMounted(()=>{
  initView()
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