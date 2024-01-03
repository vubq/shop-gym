import request from '@/utils/request'

const ENV = '/color'

export const getListOfColorsByCriteria = (params) =>
  request({
    url: ENV + '/get-list-of-colors-by-criteria',
    method: 'get',
    params
  })

export const getColorById = (id) =>
  request({
    url: ENV + '/' + id,
    method: 'get'
  })

export const createColor = (data) =>
  request({
    url: ENV,
    method: 'post',
    data
  })

export const updateColor = (data) =>
  request({
    url: ENV,
    method: 'put',
    data
  })

export const getAllColor = () =>
  request({
    url: '/color',
    method: 'get'
  })
