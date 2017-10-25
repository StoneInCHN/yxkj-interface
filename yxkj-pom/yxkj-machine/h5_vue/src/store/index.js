import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: '',
    goodItems: [],
    totalPrice: 0,
    type: '',
    userInfo: {}
  },
  mutations: {
    setTotalPrice (state, {totalPrice}) {
      state.totalPrice = totalPrice
    },
    setGoodItems (state, {goodItems}) {
      state.goodItems = goodItems
    },
    setUserInfo (state, {userInfo}) {
      state.userInfo = userInfo
    },
    setToken (state, {token}) {
      state.token = token
    },
    setType (state, {type}) {
      state.type = type
    }
  },
  getters: {
    getTotalPrice (state) {
      return state.totalPrice
    },
    getGoodItems (state) {
      return state.goodItems
    },
    getWxConfig (state) {
      return state.wxConfig
    },
    getUserInfo (state) {
      return state.userInfo
    },
    getToken (state) {
      return state.token
    },
    getType (state) {
      return state.type
    }
  },
  actions: {
    setTotalPrice (context, payload) {
      context.commit('setTotalPrice', payload)
    },
    setGoodItems (context, payload) {
      context.commit('setGoodItems', payload)
    },
    setUserInfo (context, payload) {
      context.commit('setUserInfo', payload)
    },
    setToken (context, {token}) {
      context.commit('setToken', {token})
    },
    setType (context, payload) {
      context.commit('setType', payload)
    }
  }
})
