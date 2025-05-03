import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import {resolve} from 'path'
import AutoImport from "unplugin-auto-import/vite"
import {createSvgIconsPlugin} from 'vite-plugin-svg-icons'
// import virtual from "vite-plugin-virtual"

export default defineConfig({
    plugins: [
        vue(),
        AutoImport({
            imports: ['vue', 'vue-router', 'pinia']
        }),
        // virtual(),
        createSvgIconsPlugin({
            // 图标文件夹为src/assets/icons
            iconDirs: [resolve(process.cwd(), 'src/assets/icons')],
            // 指定symbolId格式
            symbolId: '[name]'
        })
    ],
    resolve: {
        alias: {
            // 别名
            '@': resolve(__dirname, './src')
        },
        extensions: ['.vue', '.js', '.json', '.ts']
    },
    css: {
        preprocessorOptions: {
            scss: {
                additionalDate: ''
            }
        }
    },
    server: {
        host: '0.0.0.0',
        compress: false,
        proxy: {
            '/pstr': {
                target: 'http://127.0.0.1:8080/api',
                changeOrigin: true,  // 允许跨域
                rewrite: path => path.replace(/^\/pstr/, '')
            }
        }
    }
})
