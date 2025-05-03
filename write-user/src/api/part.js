import request from "../util/request.js"



export function addPart(data) {
    return request({
        url: '/part/add',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}


/**
 * 获取书籍信息
 * @param data
 * @returns {*}
 */
export function infoPart(data) {
    return request({
        url: '/part/info',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}

export function sortPartAction(data) {
    return request({
        url: '/part/sort',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}


/**
 * 获取书籍信息
 * @param data
 * @returns {*}
 */
export function updatePart(data) {
    return request({
        url: '/part/update',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}

export function deletePart(data) {
    return request({
        url: '/part/delete',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}

/**
 * 获取所有书籍
 * @param data
 * @returns {*}
 */
export function listPart(data) {
    return request({
        url: '/part/list',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}
