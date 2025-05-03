import request from '@/utils/request'

// 查询提示词列表
export function listDesc(query) {
  return request({
    url: '/system/desc/list',
    method: 'get',
    params: query
  })
}

// 查询提示词详细
export function getDesc(id) {
  return request({
    url: '/system/desc/' + id,
    method: 'get'
  })
}

// 新增提示词
export function addDesc(data) {
  return request({
    url: '/system/desc',
    method: 'post',
    data: data
  })
}

// 修改提示词
export function updateDesc(data) {
  return request({
    url: '/system/desc',
    method: 'put',
    data: data
  })
}

// 删除提示词
export function delDesc(id) {
  return request({
    url: '/system/desc/' + id,
    method: 'delete'
  })
}
