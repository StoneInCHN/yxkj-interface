import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: '',
    init: true,
    goodItems: [],
    totalPrice: 0,
    type: '',
    userInfo: {},
    compInfo: {}
  },
  mutations: {
    setTotalPrice (state, {totalPrice}) {
      state.totalPrice = totalPrice
    },
    setGoodItems (state, {goodItems}) {
      console.log('--------------')
      if (goodItems && goodItems.length > 1) {
        let items = JSON.stringify(goodItems)
        sessionStorage.items = items
      }
      state.goodItems = goodItems
    },
    setUserInfo (state, {userInfo}) {
      state.userInfo = userInfo
    },
    setCompInfo (state, {compInfo}) {
      state.compInfo = compInfo
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
    getCompInfo (state) {
      return state.compInfo
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
    setCompInfo (context, payload) {
      context.commit('setCompInfo', payload)
    },
    setToken (context, {token}) {
      context.commit('setToken', {token})
    },
    setType (context, payload) {
      context.commit('setType', payload)
    }
  }
})
