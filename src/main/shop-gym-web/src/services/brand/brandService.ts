import request from '@/utils/request'

export const getAllBrand = () =>
  request({
    url: '/brand',
    method: 'get',
  })