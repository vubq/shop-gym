import request from '@/utils/request'

const ENV = '/material'

export const getListOfMaterialsByCriteria = (params) =>
  request({
    url: ENV + '/get-list-of-materials-by-criteria',
    method: 'get',
    params
  })

export const getMaterialById = (id) =>
  request({
    url: ENV + '/' + id,
    method: 'get'
  })

export const createMaterial = (data) =>
  request({
    url: ENV,
    method: 'post',
    data
  })

export const updateMaterial = (data) =>
  request({
    url: ENV,
    method: 'put',
    data
  })

export const getAllMaterial = () =>
  request({
    url: '/material',
    method: 'get'
  })
