import Mock from 'mockjs'
import api from './api'

Mock.mock(/getGoodsItems/, 'get', api.getGoodsItems)
Mock.mock(/getGoodsBySn/, 'post', api.getGoodsBySn)
Mock.mock(/authUserInfo/, 'post', api.authUserInfo)
Mock.mock(/jsapiConfig/, 'post', api.jsApiConfig)
Mock.mock(/pay/, 'post', api.pay)

export default Mock
