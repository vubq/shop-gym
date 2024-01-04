import request from '@/utils/request'

const ENV = '/voucher'

export const getVoucherByCode = (code) =>
  request({
    url: ENV + '/code/' + code,
    method: 'get'
  })

export const getListOfVouchersByCriteria = (params) =>
  request({
    url: ENV + '/get-list-of-vouchers-by-criteria',
    method: 'get',
    params
  })

export const getVoucherById = (id) =>
  request({
    url: ENV + '/' + id,
    method: 'get'
  })

export const createVoucher = (data) =>
  request({
    url: ENV,
    method: 'post',
    data
  })

export const updateVoucher = (data) =>
  request({
    url: ENV,
    method: 'put',
    data
  })
