<template>
  <el-row class="tac">
    <el-col>
      <div class="header">
        <h1 class="title">银发科技</h1>
        <h5 class="subtitle">驾驶出行-生活辅助</h5>
      </div>
      <el-menu
          default-active="2"
          class="el-menu--vertical"
          background-color="#F5F7FA"
          text-color="#303133"
          active-text-color="#409EFF"
          router
          unique-opened
      >
        <template v-for="item in menuList">
          <el-sub-menu v-if="item.children && item.children.length" :index="item.id">
            <template #title>
              <el-icon>
                <component :is="item.icon"></component>
              </el-icon>
              <span>{{ item.authName }}</span>
            </template>
            <el-menu-item v-for="child in item.children" :key="child.id" :index="`/${item.path}/${child.path}`">
              <template #title>
                <el-icon>
                  <component :is="child.icon"></component>
                </el-icon>
                <span>{{ child.authName }}</span>
              </template>
            </el-menu-item>
          </el-sub-menu>
          <el-menu-item v-else :index="`/${item.path}`">
            <template #title>
              <el-icon>
                <component :is="item.icon"></component>
              </el-icon>
              <span>{{ item.authName }}</span>
            </template>
          </el-menu-item>
        </template>
      </el-menu>
    </el-col>
  </el-row>
</template>

<script setup>
import GetMenu from '@/api/getMenu/index'
import { onMounted, ref } from 'vue'
import { Folder } from '@element-plus/icons-vue'

const menuList = ref([])
const currentRole = ref('')
currentRole.value = localStorage.getItem("role")
const idsToRemove = ref([]);

if(currentRole.value==='family'){
  idsToRemove.value = [1,5,8,9,10,11,13];
}
if(currentRole.value==='seller'){
  idsToRemove.value = [1,2,3,6,7,8,9,10,11,13,14];
}

const initMenuList = async () => {
  const res = await GetMenu.menu()
  console.log(res.data)
  menuList.value = res.data
  if( currentRole.value !== 'admin'){
    removeMenuItems(idsToRemove.value)
  }
}
initMenuList()



function removeMenuItems(ids) {
  menuList.value = menuList.value.filter(item => !ids.includes(item.id));
}

</script>

<style lang="scss" scoped>
.title {
  font-family: 'Montserrat', sans-serif;
  font-weight: 700;
  font-size: 24px;
  margin-bottom: 8px;
  text-align: center;
  padding-top: 20px;
  color: #303133;
}

.subtitle {
  font-family: 'Open Sans', sans-serif;
  text-align: center;
  padding-top: 10px;
  padding-bottom: 20px;
  color: #303133;
  font-size: 16px;
}




::v-deep .el-menu {
  background-color: #F5F7FA;
  border-right: none;

  .el-menu-item {
    height: 50px;
    line-height: 50px;
    font-size: 16px;
    color: #303133;

    &:hover {
      background-color: #E4E7ED;
    }

    &.is-active {
      background-color: #E6F1FF; // 更浅的蓝色
      color: #409EFF; // 与背景色形成对比
    }
  }

  .el-sub-menu__title {
    height: 50px;
    line-height: 50px;
    font-size: 16px;
    color: #303133;
  }
}
::v-deep .el-menu {
  background-color: #F5F7FA;
  border-right: none;

  .el-menu-item {
    height: 75px; // 增加菜单项高度
    line-height: 60px;
    font-size: 16px;
    color: #303133;
    padding: 0 20px; // 增加左右 padding

    &:hover {
      background-color: #E4E7ED;
    }

    &.is-active {
      background-color: #E6F1FF;
      color: #409EFF;
    }
  }

  .el-sub-menu__title {
    height: 60px; // 增加子菜单标题高度
    line-height: 60px;
    font-size: 16px;
    color: #303133;
    padding: 0 20px; // 增加左右 padding
  }
}

</style>
