import axios from 'axios'


const service = axios.create({
  baseURL: 'http://localhost:7070/webapi/',
  timeout: 50000,
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
    Promise.reject(error)
  }
)

export default service
