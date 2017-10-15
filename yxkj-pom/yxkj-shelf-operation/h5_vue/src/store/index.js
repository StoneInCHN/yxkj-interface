import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: '',
    init: true,
    goodItems: [],
    totalPrice: 0,
    isWXBrowser: false,
    isZFBBrowser: false,
    type: '',
    userInfo: {},
    compInfo: {},
    wxConfig: {
      debug: false,
      appId: 'wx3598eb401cb80f00',
      timestamp: 1506690795,
      nonceStr: 'H0pUJ8NF3yUTIuuR',
      signature: '2fcc888479538bc8065edea20d600b4a26ae2582',
      jsApiList: [
        'checkJsApi',
        'scanQRCode',
        'chooseWXPay'
      ]
    }
  },
  mutations: {
    setTotalPrice (state, {totalPrice}) {
      state.totalPrice = totalPrice
    },
    setIsWXBrowser (state, {isWXBrowser}) {
      state.isWXBrowser = isWXBrowser
    },
    setIsZFBBrowser (state, {isZFBBrowser}) {
      state.isZFBBrowser = isZFBBrowser
    },
    setGoodItems (state, {goodItems}) {
      state.goodItems = goodItems
    },
    setAccessToken (state, {accessToken}) {
      state.accessToken = accessToken
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
    getIsZFBBrowser (state) {
      return state.isZFBBrowser
    },
    getIsWXBrowser (state) {
      return state.isWXBrowser
    },
    getAccessToken (state) {
      return state.accessToken
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
    setIsWXBrowser (context, payload) {
      context.commit('setIsWXBrowser', payload)
    },
    setIsZFBBrowser (context, payload) {
      context.commit('setIsZFBBrowser', payload)
    },
    setGoodItems (context, payload) {
      context.commit('setGoodItems', payload)
    },
    setAccessToken (context, payload) {
      context.commit('setGoodItems', payload)
    },
    setUserInfo (context, payload) {
      context.commit('setUserInfo', payload)
    },
    setCompInfo (context, payload) {
      context.commit('setCompInfo', payload)
    },
    setToken (context, payload) {
      context.commit('setToken', payload)
    },
    setType (context, payload) {
      context.commit('setType', payload)
    }
  }
})
