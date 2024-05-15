<!--active-text-color="#ffd04b" //当前标签颜色-->

<template>
  <el-row class="tac">
    <el-col>
      <h1 style="text-align: center; padding-top: 10px; color: white">银发科技</h1>
      <h5 style="text-align: center; padding-top: 5px; padding-bottom: 5px; color: white">驾驶出行-生活辅助</h5>
      <el-menu
          default-active="2"
          class="el-menu--vertical"
          background-color="#304156"
          text-color="#fff"
          active-text-color="#ffd04b"
          router
          unique-opened
      >
          <template v-for="item in menuList">
            <el-sub-menu v-if="item.children && item.children.length" :index="item.id">
              <template #title>
                <el-icon>
                  <component :is=item.icon></component>
                </el-icon>
                <span>{{ item.authName }}</span>
              </template>
              <el-menu-item v-for="child in item.children" :key="child.id" :index="`/${item.path}/${child.path}`">
                <template #title>
                  <el-icon>
                    <component :is=child.icon></component>
                  </el-icon>
                  <span>{{ child.authName }}</span>
                </template>
              </el-menu-item>
            </el-sub-menu>
            <el-menu-item v-else :index="`/${item.path}`">
              <template #title>
                <el-icon>
                  <component :is=item.icon></component>
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
import {onMounted, ref} from "vue";
import {Folder} from "@element-plus/icons-vue";
import * as variables from '@/styles/variables.scss'
const menuList = ref([])

const initMenuList = async() => {
  const res = await GetMenu.menu()
  // console.log(res.data)
  menuList.value = res.data
}
initMenuList()


</script>

<style lang="scss" scoped>

</style>