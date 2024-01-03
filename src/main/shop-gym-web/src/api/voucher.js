import request from '@/utils/request'

export const getVoucherByCode = (code) =>
  request({
    url: '/voucher/' + code,
    method: 'get'
  })
