import request from "../util/request.js"



export function aiWriterAction(data) {
    return request({
        url: '/ai/write',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}


export function aibreakAction(data) {
    return request({
        url: '/ai/break',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}
