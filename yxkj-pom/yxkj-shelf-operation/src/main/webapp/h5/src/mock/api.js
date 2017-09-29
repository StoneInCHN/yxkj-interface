const goodsItems = [{
  id: 1,
  name: '统一冰红茶300ml',
  price: 3.5,
  count: 2,
  img: 'http://osv8eirwz.bkt.clouddn.com/3bc1c63e-ebd1-400b-83fe-8550b7a613da.png'
}, {
  id: 2,
  name: '油切麦仔茶300ml',
  price: 2.5,
  count: 1,
  img: 'http://osv8eirwz.bkt.clouddn.com/3bc1c63e-ebd1-400b-83fe-8550b7a613da.png'
}, {
  id: 3,
  name: '水溶C100-300ml',
  price: 5,
  count: 1,
  img: 'http://osv8eirwz.bkt.clouddn.com/3bc1c63e-ebd1-400b-83fe-8550b7a613da.png'
}]

const goodItem = {
  code: '0000',
  desc: null,
  token: null,
  msg: {
    gSpec: '1000ml',
    gName: '统一冰火茶1000ml',
    gId: '100001',
    gImg: 'http://osv8eirwz.bkt.clouddn.com/3bc1c63e-ebd1-400b-83fe-8550b7a613da.png',
    gPrice: 120.5,
    gCount: 1
  }
}

export default {
  getGoodsItems: config => {
    return goodsItems
  },
  getGoodsBySn: config => {
    return goodItem
  }
}
