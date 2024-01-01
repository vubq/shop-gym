import request from '@/utils/request'

export const login = (data) =>
  request({
    url: '/auth/login',
    method: 'post',
    data
  })
