import request from '@/utils/request'

// 查询用户兑换码列表
export function listCode(query) {
  return request({
    url: '/system/code/list',
    method: 'get',
    params: query
  })
}

// 查询用户兑换码详细
export function getCode(id) {
  return request({
    url: '/system/code/' + id,
    method: 'get'
  })
}

// 新增用户兑换码
export function addCode(data) {
  return request({
    url: '/system/code',
    method: 'post',
    data: data
  })
}

// 修改用户兑换码
export function updateCode(data) {
  return request({
    url: '/system/code',
    method: 'put',
    data: data
  })
}

// 删除用户兑换码
export function delCode(id) {
  return request({
    url: '/system/code/' + id,
    method: 'delete'
  })
}
