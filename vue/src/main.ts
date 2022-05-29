import {createApp} from 'vue'
import App from './App.vue'

import router from './router'
import store from "./store/index";

import ElementPlus from 'element-plus'
import zhCn from "element-plus/es/locale/lang/zh-cn";

const app = createApp(App)
    // .use(router)
    .use(store)
    .use(ElementPlus, {
        locale: zhCn
    })

app.mount('#app')
