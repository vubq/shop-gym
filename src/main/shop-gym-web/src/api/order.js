import request from '@/utils/request'

export const createInvoice = (data) =>
  request({
    url: '/order',
    method: 'post',
    data
  })
