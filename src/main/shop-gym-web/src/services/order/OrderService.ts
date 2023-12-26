import request from '@/utils/request'

export const createInvoice = (data: any) =>
  request({
    url: '/order',
    method: 'post',
    data
  })