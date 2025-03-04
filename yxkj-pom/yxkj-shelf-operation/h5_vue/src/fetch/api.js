import Vue from 'vue'
import axios from 'axios'
import store from '../store/index'
import { LoadingPlugin, ToastPlugin } from 'vux'

Vue.use(LoadingPlugin)
Vue.use(ToastPlugin)
// 超时时间
axios.defaults.timeout = 5000
//
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8'

// http请求拦截器
axios.interceptors.request.use(config => {
  if (store.state.token) {
    config.headers.post['X-Auth-Token'] = `${store.state.token}`
  }
  return config
}, error => {
  return Promise.reject(error)
})
// http响应拦截器
axios.interceptors.response.use(data => {
  return data
}, error => {
  return Promise.reject(error)
})

export function postFetch (url, params) {
  return new Promise((resolve, reject) => {
    axios.post(url, params)
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
    return postFetch('/login', params)
  },
  getGoodsItems (params) {
    return getFetch('getGoodsItems', params)
  },
  /**
  * 1.7.3扫一扫添加商品
  * userName gId
  */
  getGoodsBySn (params) {
    return postFetch('/yxkj-shelf/h5/shelf/getGoodsBySn', params)
  },
  /**
  * 获取授权用户信息
  * authCode compId gId type
  */
  authUserInfo (params) {
    return postFetch('/yxkj-shelf/h5/shelf/authUserInfo', params)
  },
  jsApiConfig (params) {
    return postFetch('/yxkj-shelf/h5/shelf/jsapiConfig', params)
  },
  pay (params) {
    return postFetch('/yxkj-shelf/h5/shelf/pay', params)
  }
}
