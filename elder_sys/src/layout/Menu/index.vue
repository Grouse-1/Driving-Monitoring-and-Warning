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
<!--        <el-sub-menu index="1">-->
<!--          <template #title>-->
<!--            <el-icon><location /></el-icon>-->
<!--            <span>Navigator One</span>-->
<!--          </template>-->
<!--          <el-menu-item-group title="Group One">-->
<!--            <el-menu-item index="1-1">item one</el-menu-item>-->
<!--            <el-menu-item index="1-2">item two</el-menu-item>-->
<!--          </el-menu-item-group>-->
<!--          <el-menu-item-group title="Group Two">-->
<!--            <el-menu-item index="1-3">item three</el-menu-item>-->
<!--          </el-menu-item-group>-->
<!--          <el-sub-menu index="1-4">-->
<!--            <template #title>item four</template>-->
<!--            <el-menu-item index="1-4-1">item one</el-menu-item>-->
<!--          </el-sub-menu>-->
<!--        </el-sub-menu>-->
<!--        <el-sub-menu :index="'/'+item.path" v-for="item in menuList" :key="item.id">-->
<!--&lt;!&ndash;          <el-text style="display: flex; align-items: center;">&ndash;&gt;-->
<!--&lt;!&ndash;            <el-icon><Folder /></el-icon>&ndash;&gt;-->
<!--&lt;!&ndash;            <span>{{ item.authName }}</span>&ndash;&gt;-->
<!--&lt;!&ndash;          </el-text>&ndash;&gt;-->
<!--            <template #title>-->
<!--              <el-icon>-->
<!--                <component :is=item.icon></component>-->
<!--              </el-icon>-->
<!--              <span>{{ item.authName }}</span>-->
<!--            </template>-->
<!--        </el-sub-menu>-->
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