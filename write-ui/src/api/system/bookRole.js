import request from '@/utils/request'

// 查询小说角色列表
export function listBookRole(query) {
  return request({
    url: '/system/bookRole/list',
    method: 'get',
    params: query
  })
}

// 查询小说角色详细
export function getBookRole(id) {
  return request({
    url: '/system/bookRole/' + id,
    method: 'get'
  })
}

// 新增小说角色
export function addBookRole(data) {
  return request({
    url: '/system/bookRole',
    method: 'post',
    data: data
  })
}

// 修改小说角色
export function updateBookRole(data) {
  return request({
    url: '/system/bookRole',
    method: 'put',
    data: data
  })
}

// 删除小说角色
export function delBookRole(id) {
  return request({
    url: '/system/bookRole/' + id,
    method: 'delete'
  })
}
