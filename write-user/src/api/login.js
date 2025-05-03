import request from "../util/request.js"


/**
 * 注册
 * @param username 用户名
 * @param password 密码
 * @param inviteCode 邀请码
 * @returns {*}
 */
export function register(username, password, inviteCode) {
    const data = {
        'username': username,
        'password': password,
        'inviteCode': inviteCode
    }
    return request({
        url: '/register',
        headers: {
            isToken: false
        },
        method: 'post',
        data: data
    })
}


/**
 * 登录
 * @param username 用户名
 * @param password 密码
 * @returns {*}
 */
export function login(username, password) {
    const data = {
        'username': username,
        'password': password
    }
    return request({
        url: '/login',
        headers: {
            isToken: false
        },
        method: 'post',
        data: data
    })
}

/**
 * 用户信息
 * @returns {*}
 */
export function userinfo() {
    return request({
        url: '/userinfo',
        headers: {
            isToken: true
        },
        method: 'post',
        data: {}
    })
}

export function updateUserinfo(data) {
    return request({
        url: '/userinfo/update',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}

export function usercode(data) {
    return request({
        url: '/usercode',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}
