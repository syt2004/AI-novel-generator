import request from "../util/request.js"



export function addBook(title, content) {
    const data = {
        'title': title,
        'content': content,
    }
    return request({
        url: '/book/add',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}

/**
 * 获取书籍信息
 * @param id
 * @returns {*}
 */
export function infoBook(id) {
    const data = {
        'id': id
    }
    return request({
        url: '/book/info',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}


/**
 * 获取书籍信息
 * @param id
 * @returns {*}
 */
export function updateBook(id, title, content) {
    const data = {
        'id': id,
        'title': title,
        'content': content,
    }
    return request({
        url: '/book/update',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}

export function deleteBook(id) {
    const data = {
        'id': id
    }
    return request({
        url: '/book/delete',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}

/**
 * 获取所有书籍
 * @param title
 * @param content
 * @returns {*}
 */
export function listBook(title, content) {
    const data = {}
    return request({
        url: '/book/list',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}

export function exportBook(data) {
    return request({
        url: '/book/export',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}
