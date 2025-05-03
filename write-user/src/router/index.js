import { createWebHashHistory, createRouter } from 'vue-router'

import Login from '@/page/login.vue'
import Register from '@/page/register.vue'
import Index from '@/page/index.vue'
import Content from '@/page/content.vue'
import AIBreak from '@/page/aibreak.vue'
import History from '@/page/history.vue'
import {getToken} from "../util/auth.js"

const routes = [
    {
        name: 'Index',
        path: '/',
        component: Index
    },
    {
        name: 'Login',
        path: '/login',
        component: Login
    },
    {
        name: 'Register',
        path: '/register',
        component: Register
    },
    {
        name: 'Content',
        path: '/content',
        component: Content
    },
    {
        name: 'AIBreak',
        path: '/aibreak',
        component: AIBreak
    },
    {
        name: 'History',
        path: '/history',
        component: History
    },
]

export const router = createRouter({
    history: createWebHashHistory(),
    routes,
})


router.beforeEach((to, from, next) => {
    const token = getToken()
    if (to.name !== 'Login' && to.name !== 'Register' && !token) {
        next({
            path: '/login'
        })
    } else {
        next()
    }
})
