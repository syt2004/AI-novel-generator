import request from '@/utils/request'

// 查询用户生成列表
export function listWrite(query) {
  return request({
    url: '/system/write/list',
    method: 'get',
    params: query
  })
}

// 查询用户生成详细
export function getWrite(id) {
  return request({
    url: '/system/write/' + id,
    method: 'get'
  })
}

// 新增用户生成
export function addWrite(data) {
  return request({
    url: '/system/write',
    method: 'post',
    data: data
  })
}

// 修改用户生成
export function updateWrite(data) {
  return request({
    url: '/system/write',
    method: 'put',
    data: data
  })
}

// 删除用户生成
export function delWrite(id) {
  return request({
    url: '/system/write/' + id,
    method: 'delete'
  })
}
