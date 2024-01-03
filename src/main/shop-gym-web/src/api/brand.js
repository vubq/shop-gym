import request from '@/utils/request'

const ENV = '/brand'

export const getListOfBrandsByCriteria = (params) =>
  request({
    url: ENV + '/get-list-of-brands-by-criteria',
    method: 'get',
    params
  })

export const getBrandById = (id) =>
  request({
    url: ENV + '/' + id,
    method: 'get'
  })

export const createBrand = (data) =>
  request({
    url: ENV,
    method: 'post',
    data
  })

export const updateBrand = (data) =>
  request({
    url: ENV,
    method: 'put',
    data
  })
