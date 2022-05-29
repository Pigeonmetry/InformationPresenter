import {createRouter, createWebHistory} from "vue-router";

// 路由信息
const routes = [
    {
        path: "/",
        name: "Index",
        component: undefined,
    },
    {
        path: "/test",
        name: "test",
        component: undefined,
    },
];

// 导出路由
const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
