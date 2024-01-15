import { saveToLocal, loadFromLocal } from '@/common/local-storage'

const ADD_PRODUCT_TO_CART = 'ADD_PRODUCT_TO_CART'
const REMOVE_PRODUCT_TO_CART = 'REMOVE_PRODUCT_TO_CART'
const CLEAR_CART = 'CLEAR_CART'
const SET_PAY = 'SET_PAY'

const cart = {
  state: {
    cart: loadFromLocal('cart', '') || [],
    pay: loadFromLocal('pay', '') || {
      city: '',
      district: '',
      ward: '',
      paymentType: '',
      fullNameCustomer: '',
      phoneNumber: '',
      address: '',
      voucherId: '',
      noteByCustomer: '',
      totalAmount: '',
      email: '',
      createdBy: '',
      orderDetails: null
    }
  },
  mutations: {
    [ADD_PRODUCT_TO_CART](state, productDetail) {
      const pd = state.cart.find(e => e.productDetailId === productDetail.productDetailId)
      if(pd) {
        state.cart = state.cart.map(e => {
          return {...e, quantity: productDetail.quantity}
        })
      } else {
        state.cart.push(productDetail)
      }
      saveToLocal('cart', state.cart)
    },
    [REMOVE_PRODUCT_TO_CART](state, productDetailId) {
      state.cart = state.cart.filter(e => e.productDetailId !== productDetailId)
      saveToLocal('cart', state.cart)
    },
    [CLEAR_CART](state) {
      state.cart = []
      saveToLocal('cart', state.cart)
      state.pay = {
        city: '',
        district: '',
        ward: '',
        paymentType: '',
        fullNameCustomer: '',
        phoneNumber: '',
        address: '',
        voucherId: '',
        noteByCustomer: '',
        totalAmount: '',
        email: '',
        createdBy: '',
        orderDetails: null
      }
      saveToLocal('pay', state.pay)
    },
    [SET_PAY](state, pay) {
      state.pay = pay
      saveToLocal('pay', state.pay)
    }
  },
  actions: {
    addProductToCart({ commit }, productDetail) {
      return new Promise((resolve, reject) => {
        commit(ADD_PRODUCT_TO_CART, productDetail)
        return resolve()
      })
    },
    removeProductToCart({ commit }, productDetailId) {
      return new Promise((resolve, reject) => {
        commit(REMOVE_PRODUCT_TO_CART, productDetailId)
        return resolve()
      })
    },
    clearCart({ commit }) {
      return new Promise((resolve, reject) => {
        commit(CLEAR_CART)
        return resolve()
      })
    },
    setPay({ commit }, pay) {
      return new Promise((resolve, reject) => {
        commit(SET_PAY, pay)
        return resolve()
      })
    }
  },
  getters: {
    cart: state => state.cart,
    pay: state => state.pay
  }
}

export default cart
