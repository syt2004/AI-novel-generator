import axios from "axios"
import {ElMessage, ElMessageBox} from 'element-plus'
import {getToken} from "./auth.js"
import { saveAs } from 'file-saver'

// 创建一个 axios 实例
const service = axios.create({
    baseURL: '/pstr', // 所有的请求地址前缀部分
    timeout: 120000, // 请求超时时间毫秒
    withCredentials: true, // 异步请求携带cookie
    headers: {
        // 设置后端需要的传参类型
        'Content-Type': 'application/json;charset=utf-8',
    },
})

// 添加请求拦截器
service.interceptors.request.use(
    function (config) {
        // 是否需要设置 token
        const isToken = (config.headers || {}).isToken === false
        const token = getToken()
        if (token && !isToken) {
            config.headers['workbench-token'] = 'Bearer ' + getToken()
        }
        // 在发送请求之前做些什么
        return config
    },
    function (error) {
        // 对请求错误做些什么
        console.log(error)
        return Promise.reject(error)
    }
)

// 添加响应拦截器
service.interceptors.response.use(
    function (response) {
        // console.log(response)
        // 2xx 范围内的状态码都会触发该函数。
        // 对响应数据做点什么
        // dataAxios 是 axios 返回数据中的 data
        const dataAxios = response.data
        if (dataAxios.code !== 200) {

            if (dataAxios.code === 401) {

                ElMessageBox.confirm(
                    '用户登录过期,是否跳转至登陆界面',
                    '提示',
                    {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning',
                    }
                ).then(() => {
                    window.location.href = '/login'
                }).catch(() => {

                })

            } else {
                ElMessage.error(dataAxios.msg)
                return Promise.reject('error')
            }


        }
        return dataAxios
    },
    function (error) {
        // 超出 2xx 范围的状态码都会触发该函数。
        // 对响应错误做点什么
        ElMessage.error('系统错误')
        return Promise.reject(error)
    }
)


export const downloadLoading = (url, params, filename, config) => {
    return service.post(url, params, {
        transformRequest: [(params) => { return tansParams(params) }],
        headers: { 'Content-Type': 'application/json' },
        responseType: 'blob',
        ...config
    }).then(async (data) => {
        const isBlob = blobValidate(data);
        if (isBlob) {
            const blob = new Blob([data])
            saveAs(blob, filename)
            return filename
        } else {
            const resText = await data.text();
            const rspObj = JSON.parse(resText);
            const errMsg = rspObj.msg
            ElMessage.error(errMsg)
            return Promise.reject(new Error(errMsg))
        }
    }).catch((r) => {
        console.error(r)
        ElMessage.error('下载文件出现错误!')
        Promise.reject(r)
    })
}


/**
 * 参数处理
 * @param {*} params  参数
 */
const tansParams = (params) => {
    let result = ''
    for (const propName of Object.keys(params)) {
        const value = params[propName];
        var part = encodeURIComponent(propName) + "=";
        if (value !== null && value !== "" && typeof (value) !== "undefined") {
            if (typeof value === 'object') {
                for (const key of Object.keys(value)) {
                    if (value[key] !== null && value[key] !== "" && typeof (value[key]) !== 'undefined') {
                        let params = propName + '[' + key + ']';
                        var subPart = encodeURIComponent(params) + "=";
                        result += subPart + encodeURIComponent(value[key]) + "&";
                    }
                }
            } else {
                result += part + encodeURIComponent(value) + "&";
            }
        }
    }
    return result
}

// 验证是否为blob格式
const blobValidate = (data) => {
    return data.type !== 'application/json'
}

export default service
