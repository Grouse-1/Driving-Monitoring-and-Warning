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
                meta: { roles: ['admin'] },
          },
            {
              path: 'elderlyInfo',
              name: 'elderlyInfo',
              component: () => import('@/views/main/baseinfo/info/elderlyInfo/index.vue'),
              meta: { roles: ['admin'] },
            },{
              path: 'familyInfo',
              name: 'familyInfo',
              component: () => import('@/views/main/baseinfo/info/familyInfo/index.vue'),
              meta: { roles: ['admin'] },
            },{
              path: 'sellerInfo',
              name: 'sellerInfo',
              component: () => import('@/views/main/baseinfo/info/sellerInfo/index.vue'),
              meta: { roles: ['admin'] },
            },{
              path: 'plant',
              name: 'plantInfo',
              component: () => import('@/views/main/baseinfo/info/plantInfo/index.vue'),
              meta: { roles: ['admin'] },
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
          component: () => import('@/views/main/outstate/index.vue'),
          meta: { roles: ['admin', 'family'] }
        },{
          path: 'dangerpre',
          name: 'dangerpre',
          component: () => import('@/views/main/dangerpre/index.vue')
        },
        {
          path: 'remoteVideo',
          name: 'remoteVideo',
          component: () => import('@/views/main/remoteVideo/index.vue'),
          meta: { roles: ['admin', 'family'] }
        },

        {
          path: 'trade',
          name: 'trade',
          component: () => import('@/views/main/trade/index.vue'),
          meta: { roles: ['admin', 'seller'] }
        },
        {
          path: 'analysis',
          name: 'analysis',
          component: () => import('@/views/main/analysis/index.vue'),
          children: [
            {
              path: 'Eshoppinginfo',
              name: 'Eshoppinginfo',
              component: () => import('@/views/main/analysis/Eshoppinginfo/index.vue'),
              meta: { roles: ['admin', 'family'] }
            },
            {
              path: 'outanalysis',
              name: 'outanalysis',
              component: () => import('@/views/main/analysis/outanalysis/index.vue'),
              meta: { roles: ['admin', 'family'] }
            }
          ]

        },
        {
          //传参示例
          path: 'error',
          name:'error',
          component: ()=>import('../views/system/401.vue')
        }
      ]
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('../views/system/login.vue')
    },
  ]
})


router.beforeEach((to, from, next) => {
  console.log("当前路径")
  console.log(to.path)
  console.log(localStorage.getItem('token'))
  const isLogin = !!localStorage.getItem('token')
  const role = localStorage.getItem('role');
  console.log("role", role);
  if (to.path === '/login') {
    next();
  } else {
    if (!isLogin) {
      next('/login');
    } else {
      if (to.meta.roles && to.meta.roles.length > 0) {
        if (to.meta.roles.includes(role)) {
          next(); // 有权限访问
        } else {
          next('/error'); // 无权限，跳转到错误页面
        }
      } else {
        next(); // 路由没有定义权限，默认放行
      }
    }
  }
  //console.log(isLogin)
  // if (to.path === '/login') {
  //   // 如果是访问登录页面，则直接放行
  //   if(role){
  //     next('/login')
  //   }
  //   if (to.meta.roles && to.meta.roles.length > 0) {
  //     if (to.meta.roles.includes(role)) {
  //       next(); // 有权限访问
  //     } else {
  //       next({ path: '/error' }); // 无权限，跳转到错误页面
  //     }
  //   } else {
  //     //next(); // 路由没有定义权限，默认放行
  //   }
  // } else {
  //   // 如果未登录，则跳转到登录页面
  //   isLogin ? next() : next('/login')
  // }


})
export default router
