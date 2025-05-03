import request from '@/utils/request'

// 查询兑换码列表
export function listWordCode(query) {
  return request({
    url: '/system/wordCode/list',
    method: 'get',
    params: query
  })
}

// 查询兑换码详细
export function getWordCode(id) {
  return request({
    url: '/system/wordCode/' + id,
    method: 'get'
  })
}

// 新增兑换码
export function addWordCode(data) {
  return request({
    url: '/system/wordCode',
    method: 'post',
    data: data
  })
}

// 修改兑换码
export function updateWordCode(data) {
  return request({
    url: '/system/wordCode',
    method: 'put',
    data: data
  })
}

// 删除兑换码
export function delWordCode(id) {
  return request({
    url: '/system/wordCode/' + id,
    method: 'delete'
  })
}
