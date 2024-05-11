<script setup>
import {Search} from "@element-plus/icons-vue";
import {ref, watch} from "vue";
import {GetFoods} from "@/api/getInfo/index.js";
import {ElMessageBox} from "element-plus";
import {addPay} from "@/api/Pay/index.js";
import { defineEmits } from 'vue';

const tableData = ref([]);
const item = ref('')
const dialogVisible = ref(false)
const SumVisible = ref(false)
const gridData = ref([])
const currentData = ref({})
const Fweight = ref()
const allMoney = ref(0)
const elder = ref({
  name: null
})
const shopId = ref(3)
const elderId = ref(1)
const emit = defineEmits(['handleId']);
const props = defineProps({
  initView: Function
})

const getFood =()=>{
  console.log(item.value)
  GetFoods.Info(item).then((res)=>{
    console.log(res.data)
    gridData.value = res.data
  })

  dialogVisible.value = true
}

const handleCurrentChange = (selection) => {
  currentData.value = selection
  //查询商品的id，并存储----------
}

const handleConfirm = () => {
  //console.log(currentData.value)
  ElMessageBox.prompt('请输入食物质量', '添加质量', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    // inputPattern:
    //     /[^0-9.]/,
    inputErrorMessage: '未知质量',
  }).then(value => {
    Fweight.value = value.value
    const newFoodItem = {
      goodsname: currentData.value.name,
      quantity: Fweight.value,
      price: currentData.value.price,
      money: currentData.value.price * Fweight.value,
      shopid: shopId.value,
      elderid: elderId.value,
      //goodsid: goodId.value
    };
    tableData.value.push(newFoodItem)
    currentData.value = null
  })
  //tableData.value.push(currentData.value)

  dialogVisible.value = false

}

const handleClose = () => {
  dialogVisible.value = false
}

const deletFood = () =>{
  tableData.value.pop(currentData.value)
  currentData.value = null
}

const clearFood = () => {
  tableData.value = []
}

const summary = () => {
  SumVisible.value = true
}

const getElder = () =>{
//返回姓名和id,姓名展示，id存储-------------
}
const payConfirm = () => {
  //对tableDate进行判空
  addPay.Info(tableData.value).then((res)=>{})
  SumVisible.value = false
  tableData.value = []
  emit('handleId', shopId);
  props.initView()
}

const payClose = () =>{
  SumVisible.value = false
}

watch(tableData, (newVal, oldVal) => {
  let sum = 0;
  for (const item of newVal) {
  sum += item.money;
  console.log("money", item.money);
  }
  allMoney.value = sum;

}, { deep: true });

</script>

<template>
  <el-row :gutter="20">
    <el-col :span="17">
      <el-card style="margin-bottom: 5px;">
        <div class="input-container">
          <el-input
              v-model="item"
              style="max-width: 240px;"
              size="large"
              placeholder="搜索菜品或食物"
              class="input-with-select"
          >
            <template #suffix>
              <el-button type="text" :icon="Search" @click="getFood" />
            </template>
          </el-input>
          <div class="settlement">总价：{{allMoney}}</div>
        </div>
      </el-card>
      <el-card>
        <el-table :data="tableData" height="325" style="width: 100%" highlight-current-row @current-change="handleCurrentChange">
          <el-table-column prop="goodsname" label="名字" width="180" />
          <el-table-column prop="quantity" label="数量(kg)" width="180" />
          <el-table-column prop="price" label="单价/元" width="180" />
          <el-table-column prop="money" label="价格" />
        </el-table>
      </el-card>
      <el-dialog v-model="dialogVisible"width="800">
        <template #header>
          <div class="dialog-header">
            菜品或食物
          </div>
        </template>
        <el-card>
        <el-table :data="gridData" height="325" highlight-current-row style="width: 100%"  @current-change="handleCurrentChange">
          <el-table-column prop="name" label="名字" width="180" />
          <el-table-column prop="price" label="单价/元" />
        </el-table>
        </el-card>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="handleClose">取消</el-button>
            <el-button type="primary" @click="handleConfirm">
              确认
            </el-button>
          </div>
        </template>
      </el-dialog>
    </el-col>
    <el-col :span="7">
      <el-card style="height: 325px; margin-left: 15%">
        <template #header>
          <div class="card-header">
            <span style="font-weight: bold; font-size: 16px;">功能区</span>
          </div>
        </template>
        <div class="button-group">
        <el-button  class="custom-button" @click="deletFood" type="danger">删除商品</el-button>
        <el-button  class="custom-button" @click="clearFood" type="warning">清空商品</el-button>
        <el-button  class="custom-button" @click="summary" type="success">结算</el-button>
        </div>
      </el-card>
      <el-dialog v-model="SumVisible" title="结算" width="500">
        <el-form :model="elder">

          <el-form-item>
            <el-col :span="24">
              <el-descriptions
                  class="margin-top"
                  size="default"
                  column="1"
                  border
              >
                <el-descriptions-item label="顾客姓名" width="150px">
                  {{elder.name}}<el-button type="success" style="margin-left: 75%" @click="getElder">扫描</el-button>
                </el-descriptions-item>1

                <el-descriptions-item label="应付金额" width="150px">
                  {{allMoney}}
                </el-descriptions-item>
              </el-descriptions>
              <div style="margin-top: 40px; text-align: center">
                <el-button type="primary" @click="payConfirm">
                  确认支付
                </el-button>
                <el-button @click="payClose">取消支付</el-button>
              </div>
            </el-col>
          </el-form-item>
        </el-form>
        <template #footer>
          <div class="dialog-footer">
<!--            <el-button @click="dialogFormVisible = false">Cancel</el-button>-->
<!--            <el-button type="primary" @click="dialogFormVisible = false">-->
<!--              Confirm-->
<!--            </el-button>-->
          </div>
        </template>
      </el-dialog>
    </el-col>

  </el-row>

</template>

<style scoped lang="scss">
.el-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  transition: box-shadow 0.3s;
}
.el-card:hover {
  box-shadow: 0 4px 24px 0 rgba(0, 0, 0, 0.2);
}
.input-container {
  display: flex;
  align-items: center;
  justify-content: space-between; /* 调整元素之间的间距 */
}
.settlement {
  margin-left: auto; /* 调整间距 */
}
.cell-item {
  display: flex;
  align-items: center;
}
.card-header {
  padding: 10px 16px;
  background-color: #f5f7fa;
  border-bottom: 1px solid #ebeef5;
  border-radius: 4px 4px 0 0;
  font-weight: bold;
  font-size: 16px;
  color: #333;
}
.custom-button {
  margin-bottom: 10px;
  width: 120px;
}
.button-group {
  display: flex;
  flex-direction: column; /* 将按钮垂直排列 */
  justify-content: center; /* 垂直居中对齐 */
  align-items: center; /* 水平居中对齐 */
  padding: 20px;
}
.custom-button:first-child {
  margin-left: 12px; /* 将第一个按钮的右边距设为0 */
}

</style>