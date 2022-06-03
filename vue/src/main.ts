import {Component, createApp} from 'vue'
import App from './App.vue'

import router from './router'
import store from "./store/index";

import ElementPlus from 'element-plus'
import zhCn from "element-plus/es/locale/lang/zh-cn";
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import 'element-plus/es/components/message/style/css'

import "./assets/css/global.css";

const app = createApp(App)
    .use(router)
    .use(store)
    .use(ElementPlus, {
        locale: zhCn
    })

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component as Component)
}

app.mount('#app')
