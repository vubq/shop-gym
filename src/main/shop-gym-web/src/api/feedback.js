import request from '@/utils/request'

const ENV = '/feedback'

export const getListOfFeedbacksByCriteria = (params) =>
  request({
    url: ENV + '/get-list-of-feedbacks-by-criteria',
    method: 'get',
    params
  })
