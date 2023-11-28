import request from '@/utils/request'

export const getAllCategory = () =>
  request({
    url: '/category',
    method: 'get',
  })