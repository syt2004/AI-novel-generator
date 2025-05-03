import request from '@/utils/request'

// 查询小说章节列表
export function listBookPart(query) {
  return request({
    url: '/system/bookPart/list',
    method: 'get',
    params: query
  })
}

// 查询小说章节详细
export function getBookPart(id) {
  return request({
    url: '/system/bookPart/' + id,
    method: 'get'
  })
}

// 新增小说章节
export function addBookPart(data) {
  return request({
    url: '/system/bookPart',
    method: 'post',
    data: data
  })
}

// 修改小说章节
export function updateBookPart(data) {
  return request({
    url: '/system/bookPart',
    method: 'put',
    data: data
  })
}

// 删除小说章节
export function delBookPart(id) {
  return request({
    url: '/system/bookPart/' + id,
    method: 'delete'
  })
}
