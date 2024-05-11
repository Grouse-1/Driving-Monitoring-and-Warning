import { createRouter, createWebHistory } from 'vue-router'
import store from "@/store";
import {useStore} from "vuex";
//import HomePage from "@/router/modules/Home";
//import Layout from "@/layout/index.vue";

//const Layout = () => import('@/layout/index.vue')
const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/home',
      name: 'home',
      component: () => import('../views/system/home.vue')
    },
    {
      path: '/',
      name: '/',
      component: () => import('@/layout/index.vue'),
      children: [
        {
          path: 'baseinfo',
          name: 'baseinfo',
          component: () => import('@/views/main/baseinfo/index.vue'),
          children: [
              {
            path: 'adminInfo',
            name: 'adminInfo',
            component: () => import('@/views/main/baseinfo/info/adminInfo/index.vue'),
          },
            {
              path: 'elderlyInfo',
              name: 'elderlyInfo',
              component: () => import('@/views/main/baseinfo/info/elderlyInfo/index.vue'),
            },{
              path: 'familyInfo',
              name: 'familyInfo',
              component: () => import('@/views/main/baseinfo/info/familyInfo/index.vue'),
            },{
              path: 'sellerInfo',
              name: 'sellerInfo',
              component: () => import('@/views/main/baseinfo/info/sellerInfo/index.vue'),
            },{
              path: 'plant',
              name: 'plantInfo',
              component: () => import('@/views/main/baseinfo/info/plantInfo/index.vue'),
            },


          ]
        },
        {
          path: 'schedule',
          name: 'schedule',
          component: () => import('@/views/main/schedule/index.vue')
        },

        {
          path: 'outstate',
          name: 'outstate',
          component: () => import('@/views/main/outstate/index.vue')
        },{
          path: 'dangerpre',
          name: 'dangerpre',
          component: () => import('@/views/main/dangerpre/index.vue')
        },
        {
          path: 'remoteVideo',
          name: 'remoteVideo',
          component: () => import('@/views/main/remoteVideo/index.vue')
        },

        {
          path: 'trade',
          name: 'trade',
          component: () => import('@/views/main/trade/index.vue')
        },
        {
          path: 'analysis',
          name: 'analysis',
          component: () => import('@/views/main/analysis/index.vue'),
          children: [
            {
              path: 'Eshoppinginfo',
              name: 'Eshoppinginfo',
              component: () => import('@/views/main/analysis/Eshoppinginfo/index.vue')
            },
            {
              path: 'outanalysis',
              name: 'outanalysis',
              component: () => import('@/views/main/analysis/outanalysis/index.vue')
            }
          ]

        },
      ]
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('../views/system/login.vue')
    },
    {
      //传参示例
      path: '/canshu/:name',
      name:'test',
      component: ()=>import('../views/system/test.vue')
    },
    // {
    //   path: '/',
    //   component: Layout,
    //   redirect: '/index',
    //   children: [
    //     {
    //       path: '/index',
    //       component:  import('@/views/main/home/index.vue'),
    //     }
    //   ]
    // }
  ]
})


router.beforeEach((to, from, next) => {
  console.log("当前路径")
  console.log(to.path)
  console.log(localStorage.getItem('token'))
  const isLogin = !!localStorage.getItem('token')
  console.log(isLogin)
  if (to.path === '/login') {
    // 如果是访问登录页面，则直接放行
    next()
  } else {
    // 如果未登录，则跳转到登录页面
    isLogin ? next() : next('/login')
  }
})
export default router
