import request from '@/utils/request'

export const getVoucherByCode = (code: string) =>
  request({
    url: '/voucher/' + code,
    method: 'get',
  })