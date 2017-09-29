// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import FastClick from 'fastclick'
import App from './App'
import router from './router'
import store from './store/index'
import { ConfirmPlugin, WechatPlugin } from 'vux'
import api from './fetch/api'
import './mock/index.js'  // 该项目所有请求使用mockjs模拟

Vue.use(ConfirmPlugin)
Vue.use(WechatPlugin)
Vue.prototype.$api = api
FastClick.attach(document.body)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app-box')
