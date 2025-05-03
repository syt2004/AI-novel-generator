import { createApp } from 'vue'
import './style.css'
import App from './App.vue'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// 暗黑模式
import 'element-plus/theme-chalk/dark/css-vars.css'
import 'virtual:svg-icons-register'



const app = createApp(App)

app.use(ElementPlus)

import ArcoVue from '@arco-design/web-vue'
import '@arco-design/web-vue/dist/arco.css'
app.use(ArcoVue)

import SvgIcon from './components/SvgIcon.vue'
app.component('SvgIcon', SvgIcon)


import {createPinia} from 'pinia'
import piniaPluginPersistedstate  from 'pinia-plugin-persistedstate'
const pinia = createPinia()
// pinia 持久化
pinia.use(piniaPluginPersistedstate)
app.use(pinia)

import * as ElementPlusIconsVue from '@element-plus/icons-vue'
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}


import {router} from './router'
app.use(router)


const elBlur = (el) => {
    return () => el?.blur()
}

app.directive('blur', {
    created (el, binding, vnode) {
        if (vnode?.type === 'button') {
            el.addEventListener('click', elBlur(el))
        } else {
            el.addEventListener('click', elBlur(el))
        }
    },
    unmounted (el) {
        el.removeEventListener('click', elBlur(el))
    }
})




import './style/dark/css-vars.css'

app.mount('#app')
