import request from '@/utils/request'

export const getAllBySearchCriteria = (params: any) =>
  request({
    url: '/product',
    method: 'get',
    params
  })

export const createProduct = (data: any) =>
  request({
    url: '/product',
    method: 'post',
    data
  })


