import axios from 'axios'
import { Message } from 'element-ui'
import { getToken } from '@/common/auth'

const service = axios.create({
  baseURL: 'http://localhost:7070/webapi/'
})

service.interceptors.request.use(
  (config) => {
    if (getToken()) {
      config.headers['Authorization'] = 'Bearer ' + getToken()
    }
    return config
  }
)

service.interceptors.response.use(
  (response) => {
    return response
  }
)

export default service
