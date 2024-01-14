import request from '@/utils/request'

const ENV = '/order'

export const createInvoice = (data) =>
  request({
    url: ENV,
    method: 'post',
    data
  })

export const createOrder = (data) =>
  request({
    url: ENV + '/create-order',
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

export const getAllOrderWaitForConfirmationByUserId = (userId) =>
  request({
    url: ENV + '/get-all-order-wait-for-confirmation-by-user-id/' + userId,
    method: 'get'
  })

export const getAllOrderSuccessByUserId = (userId) =>
  request({
    url: ENV + '/get-all-order-success-by-user-id/' + userId,
    method: 'get'
  })

export const getAllOrderCancelledByUserId = (userId) =>
  request({
    url: ENV + '/get-all-order-cancelled-by-user-id/' + userId,
    method: 'get'
  })
