import request from "../util/request.js"







export function infoTemplate(data) {
    return request({
        url: '/template/info',
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
export function updateTemplate(data) {
    return request({
        url: '/template/update',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}






