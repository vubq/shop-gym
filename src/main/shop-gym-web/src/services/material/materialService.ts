import request from '@/utils/request'

export const getAllMaterial = () =>
  request({
    url: '/material',
    method: 'get',
  })