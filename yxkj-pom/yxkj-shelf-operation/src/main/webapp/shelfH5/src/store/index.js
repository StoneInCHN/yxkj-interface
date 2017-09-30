import Api from '../fetch/api'
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    accessToken: '',
    init: true,
    goodItems: [],
    totalPrice: 100,
    isWXBrowser: true,
    isZFBBrowser: false,
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
    getGoodsBySn (context, payload) {
      console.log('*******')
      console.log(payload)
      Api.getGoodsBySn().then(response => {
        const data = {
          goodItems: response.msg
        }
        context.commit('setGoodItems', data)
      }).catch(error => {
        console.log(error)
      })
    },
    setAccessToken (context, payload) {
      Api.getAccessToken().then(response => {
        const data = {
          goodItems: response.msg
        }
        context.commit('setGoodItems', data)
      }).catch(error => {
        console.log(error)
      })
    }
  }
})
