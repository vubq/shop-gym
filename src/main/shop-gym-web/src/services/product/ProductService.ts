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

export const getProductDetailByProductId = (id: string) =>
  request({
    url: '/product/product-detail/' + id,
    method: 'get',
  })

export const getAllProductInOfStock = () =>
  request({
    url: '/product/product-in-of-stock',
    method: 'get',
  })

export const getProductById = (id: string) =>
  request({
    url: '/product/' + id,
    method: 'get',
  })



