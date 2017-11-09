// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import FastClick from 'fastclick'
import App from './App'
import api from './fetch/api'
import router from './router'
import store from './store/index'
import { WechatPlugin, AlertPlugin, querystring } from 'vux'

// require('./mock/index.js')

FastClick.attach(document.body)
Vue.prototype.$api = api
Vue.prototype.$querystring = querystring
Vue.use(WechatPlugin)
Vue.use(AlertPlugin)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app-box')
