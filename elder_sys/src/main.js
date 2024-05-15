import './assets/main.css'

import { createApp } from 'vue'

import pinia from './pinia'
import App from './App.vue'
import router from './router'
import echarts from './utils/echarts.js'

import 'element-plus/dist/index.css'

import zhCn from 'element-plus/es/locale/lang/zh-cn'

const app = createApp(App)



import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'font-awesome/css/font-awesome.min.css'
import * as ICONS from '@element-plus/icons-vue'
Object.entries(ICONS).forEach(([key, component]) => {
    // app.component(key === 'PieChart' ? 'PieChartIcon' : key, component)
    app.component(key, component)
})

import '@/styles/index.scss'



import i18n from "@/i18n";

import axios from 'axios'


import store from './store'

//axios挂载全全局 $axios
axios.defaults.baseURL='http://localhost:7799'
app.config.globalProperties.$axios= axios

app.config.globalProperties.$echarts = echarts;
// import * as ICONS from '@element-plus/icons-vue'
// Object.entries(ICONS).forEach(([key, component]) => {
//     // app.component(key === 'PieChart' ? 'PieChartIcon' : key, component)
//     app.component(key, component)
// })


// document.title = '银发科技';
// document.querySelector('link[rel="icon"]').setAttribute('href', './src/styles/img/111111.webp');
const setPageIcon = () => {
    document.title = '银发科技'
}

app .use(ElementPlus, { locale: zhCn })
    .use(router)
    .use(pinia)
    .use(store)
    .use(i18n)
    .use(setPageIcon)
    .mount('#app')
