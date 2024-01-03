import request from '@/utils/request'

const ENV = '/size'

export const getListOfSizesByCriteria = (params) =>
  request({
    url: ENV + '/get-list-of-sizes-by-criteria',
    method: 'get',
    params
  })

export const getSizeById = (id) =>
  request({
    url: ENV + '/' + id,
    method: 'get'
  })

export const createSize = (data) =>
  request({
    url: ENV,
    method: 'post',
    data
  })

export const updateSize = (data) =>
  request({
    url: ENV,
    method: 'put',
    data
  })
