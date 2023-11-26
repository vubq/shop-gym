import axios from 'axios';
import request from '@/utils/request'

export const getAllBySearchCriteria = (params: any) =>
  request({
    url: '/product',
    method: 'get',
    params
  })

