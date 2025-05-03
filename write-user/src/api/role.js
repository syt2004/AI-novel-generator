import request from "../util/request.js"


/**
 * 添加作品人物
 * @param data
 * @returns {*}
 */
export function addRole(data) {
    return request({
        url: '/role/add',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}

export function aiAddRole(data) {
    return request({
        url: '/role/aiAdd',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}
//
// /**
//  * 获取书籍信息
//  * @param id
//  * @returns {*}
//  */
// export function infoBook(id) {
//     const data = {
//         'id': id
//     }
//     return request({
//         url: '/book/info',
//         headers: {
//             isToken: true
//         },
//         method: 'post',
//         data: data
//     })
// }
//
//

/**
 * 更新人物信息
 * @param data
 * @returns {*}
 */
export function updateRole(data) {
    return request({
        url: '/role/update',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}


export function deleteRole(data) {
    return request({
        url: '/role/delete',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}

/**
 * 获取人物信息
 * @param id
 * @returns {*}
 */
export function listRole(id) {
    const data = {
        id: id
    }
    return request({
        url: '/role/list',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}


export function listRoleByAI(id) {
    const data = {
        id: id
    }
    return request({
        url: '/role/listByAi',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}
