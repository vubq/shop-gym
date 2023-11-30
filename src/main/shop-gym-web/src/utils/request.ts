import axios from 'axios'
import { Message } from 'element-ui'

const service = axios.create({
  baseURL: 'http://localhost:7070/webapi/',
})

// Request interceptors
service.interceptors.request.use(
  (config) => {
    const user = JSON.parse(localStorage.getItem('user') as any);
    if (user.token) {
      config.headers['Authorization'] = 'Bearer ' + user.token
    }
    return config
  },
  (error) => {
    Message({
      message: 'Lỗi hệ thống! Vui lòng thử lại sau!',
      type: 'error',
      duration: 1000
    })
  }
)

service.interceptors.response.use(
  (response) => {
    return response
  },
  (error) => {
    Message({
      message: 'Lỗi hệ thống! Vui lòng thử lại sau!',
      type: 'error',
      duration: 1000 * 5
    })
  }
)

export default service
