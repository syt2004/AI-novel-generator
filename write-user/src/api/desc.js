import request from "../util/request.js"
export function myListDesc(data) {
    return request({
        url: '/desc/my/list',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}
