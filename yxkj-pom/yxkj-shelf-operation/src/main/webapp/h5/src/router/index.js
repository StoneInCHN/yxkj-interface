import Vue from 'vue'
import Router from 'vue-router'
import Main from '@/components/Main'
import Pay from '@/components/Pay'
import Pay4wx from '@/components/PaySuccess4wx'
import PayResult from '@/components/PayResult'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Main',
      component: Main
    },
    {
      path: '/pay',
      name: 'Pay',
      component: Pay
    },
    {
      path: '/pay4wx',
      name: 'Pay4wx',
      component: Pay4wx
    },
    {
      path: '/payResult',
      name: 'PayResult',
      component: PayResult
    }
  ]
})
