import Mock from 'mockjs'
import api from './api'

Mock.mock(/getGoodsItems.jhtml/, 'get', api.getGoodsItems)
Mock.mock(/getGoodsBySn.jhtml/, 'post', api.getGoodsBySn)
Mock.mock(/authUserInfo.jhtml/, 'post', api.authUserInfo)
Mock.mock(/jsapiConfig.jhtml/, 'post', api.jsApiConfig)
Mock.mock(/pay.jhtml/, 'post', api.pay)

export default Mock
