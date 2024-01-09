import request from '@/utils/request'

const ENV = '/product'

export const getListOfProductsByCriteria = (params) =>
  request({
    url: ENV + '/get-list-of-products-by-criteria',
    method: 'get',
    params
  })

export const createProduct = (data) =>
  request({
    url: '/product',
    method: 'post',
    data
  })

export const getProductDetailByProductId = (id) =>
  request({
    url: '/product/product-detail/' + id,
    method: 'get'
  })

export const getAllProductInOfStock = () =>
  request({
    url: '/product/product-in-of-stock',
    method: 'get'
  })

export const getProductById = (id) =>
  request({
    url: '/product/' + id,
    method: 'get'
  })

export const getListOfProductsByCriteriaWebShop = (params, data) =>
  request({
    url: ENV + '/get-list-of-products-by-criteria-web-shop',
    method: 'post',
    params,
    data
  })

export const getProductByIdWebShop = (id) =>
  request({
    url: ENV + '/get-product-by-id-web-shop/' + id,
    method: 'get'
  })
