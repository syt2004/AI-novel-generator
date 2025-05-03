import request from "../util/request.js"



export function exchange(code) {
    const data = {
        'code': code
    }
    return request({
        url: '/code/exchange',
        headers: {
            isToken: true
        },
        method: 'post',
        data: data
    })
}
