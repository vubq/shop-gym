import request from '@/utils/request'

export const getAllSize = () =>
  request({
    url: '/size',
    method: 'get',
  })