import request from '@/utils/request'

// 查询作品列表
export function listBook(query) {
  return request({
    url: '/system/book/list',
    method: 'get',
    params: query
  })
}

// 查询作品详细
export function getBook(id) {
  return request({
    url: '/system/book/' + id,
    method: 'get'
  })
}

// 新增作品
export function addBook(data) {
  return request({
    url: '/system/book',
    method: 'post',
    data: data
  })
}

// 修改作品
export function updateBook(data) {
  return request({
    url: '/system/book',
    method: 'put',
    data: data
  })
}

// 删除作品
export function delBook(id) {
  return request({
    url: '/system/book/' + id,
    method: 'delete'
  })
}
