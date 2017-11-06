import Vue from 'vue'
import Router from 'vue-router'
import Main from '@/components/Main'
import Result from '@/components/Result'
import Refund from '@/components/Refund'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Main',
      component: Main
    },
    {
      path: '/result',
      name: 'Result',
      component: Result
    },
    {
      path: '/refund',
      name: 'Refund',
      component: Refund
    }
  ]
})
