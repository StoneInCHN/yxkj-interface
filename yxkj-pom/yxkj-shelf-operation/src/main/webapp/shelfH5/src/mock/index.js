import Mock from 'mockjs'
import api from './api'

Mock.mock(/getGoodsItems.jhtml/, 'get', api.getGoodsItems)
Mock.mock(/getGoodsBySn.jhtml/, 'post', api.getGoodsBySn)

export default Mock
