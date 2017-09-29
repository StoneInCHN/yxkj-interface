import Vue from 'vue'
import axios from 'axios'
import { LoadingPlugin, ToastPlugin } from 'vux'

Vue.use(LoadingPlugin)
Vue.use(ToastPlugin)
// 超时时间
axios.defaults.timeout = 5000
//
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8'
// http请求拦截器
axios.interceptors.request.use(data => {
  return data
}, error => {
  return Promise.reject(error)
})

export function postFetch (url, params) {
  return new Promise((resolve, reject) => {
    axios.post(url, params)
      .then(response => {
        console.log(response)
        resolve(response.data)
      }, err => {
        reject(err)
      })
      .catch((error) => {
        reject(error)
      })
  })
}
export function getFetch (url, params) {
  return new Promise((resolve, reject) => {
    axios.get(url, params)
      .then(response => {
        resolve(response.data)
      }, err => {
        reject(err)
      })
      .catch((error) => {
        reject(error)
      })
  })
}
export default{

  login (params) {
    return postFetch('/login.jhtml', params)
  },
  getGoodsItems (params) {
    return getFetch('getGoodsItems.jhtml', params)
  },
  getGoodsBySn (params) {
    return postFetch('getGoodsBySn.jhtml', params)
  }
}
