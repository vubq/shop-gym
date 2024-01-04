import request from '@/utils/request'

const ENV = '/order'

export const createInvoice = (data) =>
  request({
    url: ENV,
    method: 'post',
    data
  })

export const getListOfOrdersByCriteriaSellAtTheShop = (params) =>
  request({
    url: ENV + '/get-list-of-orders-by-criteria-sell-at-the-shop',
    method: 'get',
    params
  })

export const getOrderDetailsByOrderId = (orderId) =>
  request({
    url: ENV + '/get-order-details-by-order-id/' + orderId,
    method: 'get'
  })
