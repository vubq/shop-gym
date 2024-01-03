import request from '@/utils/request'

export const getAllProductDetailInOfStock = () =>
  request({
    url: '/product-detail/get-all-product-detail-in-of-stock',
    method: 'get'
  })
