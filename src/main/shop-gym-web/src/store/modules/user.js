// import { login, userInfo, logout } from '@/api/login'
import { getToken, setToken, removeToken } from '@/common/auth'
import { login } from '@/api/auth'
import { saveToLocal, loadFromLocal } from '@/common/local-storage'

const SET_ID = 'SET_ID'
const SET_USERNAME = 'SET_USERNAME'
const SET_FULLNAME = 'SET_FULLNAME'
const SET_EMAIL = 'SET_EMAIL'
const SET_AVATAR = 'SET_AVATAR'
const SET_TOKEN = 'SET_TOKEN'
const SET_ROLES = 'SET_ROLES'
const SET_ALL = 'SET_ALL'

const user = {
  state: {
    id: loadFromLocal('id', ''),
    userName: loadFromLocal('userName', ''),
    fullName: loadFromLocal('fullName', ''),
    email: loadFromLocal('email', ''),
    avatar: loadFromLocal('avatar', ''),
    token: loadFromLocal('token', ''),
    roles: loadFromLocal('roles', '') || []
  },
  mutations: {
    [SET_ID](state, id) {
      state.id = id
    },
    [SET_USERNAME](state, userName) {
      state.userName = userName
    },
    [SET_FULLNAME](state, fullName) {
      state.fullName = fullName
    },
    [SET_EMAIL](state, email) {
      state.email = email
    },
    [SET_AVATAR](state, avatar) {
      state.id = avatar
    },
    [SET_TOKEN](state, token) {
      state.token = token
    },
    [SET_ROLES](state, roles) {
      state.roles = roles
    },
    [SET_ALL](state, userInfo) {
      state = Object.assign(state, userInfo)
    }
  },
  actions: {
    login({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        login(userInfo).then(res => {
          let data = res.data
          setToken(data.token)
          commit(SET_ALL, data)
          saveToLocal('id', data.id)
          saveToLocal('fullName', data.fullName)
          saveToLocal('userName', data.userName)
          saveToLocal('email', data.email)
          saveToLocal('token', data.token)
          saveToLocal('avatar', data.avatar)
          saveToLocal('roles', data.roles)
          return resolve()
        }).catch(err => {
          return reject(err)
        })
      })
    },
    // // 拉取用户信息
    // pullUserInfo({ commit }) {
    //   return new Promise((resolve, reject) => {
    //     userInfo().then(resp => {
    //       let data = resp.data
    //       commit(SET_ACCOUNT, data.account)
    //       commit(SET_NAME, data.name)
    //       commit(SET_AGE, data.age)
    //       commit(SET_SEX, data.sex)
    //       commit(SET_AVATAR, data.avatar)
    //       commit(SET_PERMISSIONS, data.permissions)
    //       commit(SET_TYPE, data.type)
    //       commit(SET_DESC, data.desc)
    //       return resolve(data)
    //     }).catch(err => {
    //       return reject(err)
    //     })
    //   })
    // },
    // 用户退出登录
    logout({ commit }) {
      return new Promise((resolve, reject) => {
        removeToken()
        window.localStorage.clear()
        commit(SET_TOKEN, '')
        return resolve()
      })
    }
    // // 头像更新
    // doUpdateAvatar({ commit }, imgFile) {
    //   return new Promise(resolve => {
    //     setTimeout(() => {
    //       commit(SET_AVATAR, imgFile)
    //       resolve()
    //     }, 1000)
    //   })
    // },
    // /**
    //  * 更新用户信息
    //  * userInfo: 用户信息表对象
    //  */
    // doUpdateUser({ commit }, userInfo) {
    //   return new Promise(resolve => {
    //     commit(SET_ALL, userInfo)
    //     setTimeout(() => {
    //       resolve()
    //     }, 1000)
    //   })
    // }
  },
  getters: {
    allInfor: state => state
  }
}

export default user
