<template>
  <el-card style="width: 100%; height: 500px;margin-bottom: 10px">
    <goods/>
  </el-card>

  <el-row :gutter="20">
    <el-col :span="6">
      <el-card shadow="hover">
        <template #header>
          <div class="card-header">
            <div class="card-panel">
              <div class="card-panel-description">
                <div class="card-panel-text">交易笔数</div>
              </div>
              <el-tag type="success" size="large" effect="light" style="margin-left: auto;">日</el-tag>
            </div>
          </div>
        </template>
        <div class="card-panel" style="font-size: 20px; display: flex;">
          <div style="margin-top: auto; margin-bottom: auto">1000</div>
          <i class="fa fa-file-text-o fa-2x" aria-hidden="true" style="margin-left:auto; color: yellowgreen;"></i>
        </div>
      </el-card>
    </el-col>
    <el-col :span="6" style="margin-left: 1%">
      <el-card shadow="hover">
        <template #header>
          <div class="card-header">
            <div class="card-panel">
              <div class="card-panel-description">
                <div class="card-panel-text">销售额</div>
              </div>
              <el-tag type="success" size="large" effect="light" style="margin-left: auto;">日</el-tag>
            </div>
          </div>
        </template>
        <div class="card-panel" style="font-size: 20px; display: flex;">
          <div style="margin-top: auto; margin-bottom: auto">1000</div>
          <i class="fa fa-cny fa-2x" aria-hidden="true" style="margin-left:auto; color: gold;"></i>
        </div>
      </el-card>
    </el-col>
    <el-col :span="11" style="margin-left: 3%">

      <el-card >
        <div class="common-layout">
          <el-container>
            <el-aside width="15%" style="height: 25%">
              <el-card style="writing-mode: vertical-lr; font-size: medium;width:90%;display: flex;justify-content: center;align-items: center; color: #666;margin: auto">收入分析</el-card>
            </el-aside>
            <el-main>
              <el-card>

              </el-card>

            </el-main>
          </el-container>
        </div>

      </el-card>
    </el-col>
  </el-row>



</template>

<script setup>
import Goods from "@/views/main/trade/components/goods.vue";
</script>

<style lang="scss" scoped>
.card-panel {
  display: flex;
  cursor: pointer;
  font-size: 12px;
  position: relative;
  overflow: hidden;
  color: #666;
  background: #fff;
  box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.05);
  border-color: rgba(0, 0, 0, 0.05);

  &:hover {
    .card-panel-icon-wrapper {
      color: #fff;
    }
  }

  .card-panel-icon-wrapper {
    float: left;
    transition: all 0.38s ease-out;
    border-radius: 6px;
  }

  .card-panel-description {
    float: left;
    font-weight: bold;

    .card-panel-text {

      color: rgba(0, 0, 0, 0.45);
      font-size: 16px;

    }

    .card-panel-num {
      font-size: 20px;
      text-align: left;
    }
  }
}
.el-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  transition: box-shadow 0.3s;
}
.el-card:hover {
  box-shadow: 0 4px 24px 0 rgba(0, 0, 0, 0.2);
}
</style>
