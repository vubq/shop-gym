import request from '@/utils/request'

export const getAllColor = () =>
  request({
    url: '/color',
    method: 'get',
  })