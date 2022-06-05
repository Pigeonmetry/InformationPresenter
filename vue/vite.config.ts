import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import {ElementPlusResolver} from "unplugin-vue-components/resolvers";

// https://vitejs.dev/config/
export default defineConfig({
    // server: {
    //     proxy:{
    //         '/api': {
    //             target: 'http://172.21.118.52:8081',
    //             changeOrigin: true,
    //             // rewrite: path => path.replace(/^\/api/, '')
    //         }
    //     }
    // },
    plugins: [
        vue(),
        AutoImport({
            resolvers: [ElementPlusResolver()],
        }),
        Components({
            resolvers: [ElementPlusResolver()],
        }),
    ]
})
