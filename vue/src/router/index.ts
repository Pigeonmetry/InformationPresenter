import {createRouter, createWebHistory} from "vue-router";
import LoginView from "../views/LoginView.vue";

// 路由信息
const routes = [
    {
        path: "/",
        name: "Home",
        component: LoginView,
    },
    {
        path: "/login",
        name: "Login",
        component: LoginView,
    },
    {
        path: "/info",
        name: "Info",
        component: () => import("../views/InfoView.vue"),
    }
];

// 导出路由
const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
