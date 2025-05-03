import request from '@/utils/request'

// 查询workUser列表
export function listWorkUser(query) {
  return request({
    url: '/system/workUser/list',
    method: 'get',
    params: query
  })
}

// 查询workUser详细
export function getWorkUser(id) {
  return request({
    url: '/system/workUser/' + id,
    method: 'get'
  })
}

// 新增workUser
export function addWorkUser(data) {
  return request({
    url: '/system/workUser',
    method: 'post',
    data: data
  })
}

// 修改workUser
export function updateWorkUser(data) {
  return request({
    url: '/system/workUser',
    method: 'put',
    data: data
  })
}

// 删除workUser
export function delWorkUser(id) {
  return request({
    url: '/system/workUser/' + id,
    method: 'delete'
  })
}
