// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import FastClick from 'fastclick'
import App from './App'
import router from './router'
import store from './store/index'
import { ConfirmPlugin, WechatPlugin, AlertPlugin, querystring } from 'vux'
import api from './fetch/api'

Vue.prototype.$api = api
Vue.prototype.$querystring = querystring
Vue.use(ConfirmPlugin)
Vue.use(WechatPlugin)
Vue.use(AlertPlugin)

FastClick.attach(document.body)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app-box')
