const goodsItems = []

const goodItem = {
  'code': '0000',
  'desc': null,
  'token': null,
  'msg': {
    gSpec: '1000ml',
    gName: '统一冰火茶1000ml',
    gId: '100001',
    gImg: 'http://osv8eirwz.bkt.clouddn.com/3bc1c63e-ebd1-400b-83fe-8550b7a613da.png',
    gPrice: 0.3,
    gCount: 1,
    fullName: '1111'
  }
}

const authUserInfo = {
  'code': '0000',
  'desc': null,
  'token': 'eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJvZC1QTzFaNkNGTmlSVVZHZFBEeWZwYS0xME9rIiwiaWF0IjoxNTA2NTc2NDY3LCJzdWIiOiIiLCJleHAiOjE1MDY1NzgyNjd9.bcn19kKwcOoAmi7OYkD9MdvkUJkLPDkeKtIUnsUoCGY',
  'msg': {
    userInfo: {
      nickname: 'andrea',
      userId: 'od-PO1Z6CFNiRUVGdPDyfpa-10Ok'
    },
    gInfo: [{
      'gSpec': '1000ml',
      'gName': '统一冰火茶',
      'gImg': 'http://osv8eirwz.bkt.clouddn.com/3bc1c63e-ebd1-400b-83fe-8550b7a613da.png',
      'price': 20,
      'count': 2,
      'cSn': 'A21',
      'cId': 1
    },
    {
      'gSpec': '1000ml',
      'gName': '统一冰火茶',
      'gImg': 'http://osv8eirwz.bkt.clouddn.com/3bc1c63e-ebd1-400b-83fe-8550b7a613da.png',
      'price': 16,
      'count': 1,
      'cSn': 'A23',
      'cId': 2
    }]
  }
}
const jsapiConfig = {
  'code': '0000',
  'desc': null,
  'token': null,
  'msg': {
    'jsapi_ticket': 'HoagFKDcsGMVCIY2vOjf9rjPzPK2e0iS8xnzfwrN32K6yoY6eTLYQ1ortIxir9oylnkJuIuqH_-Wrsv0KIC0dQ',
    'signature': 'c645651095476acdb4dea8718e6d6542053abd26',
    'url': 'http://test.ybjcq.com/shelfH5/index.html?1s11d2',
    'nonceStr': '40d8d9b6-64cf-4fdf-af91-74810646dd68',
    'timestamp': '1506756190'
  }
}

const paywx = {
  'code': '0000',
  'desc': null,
  'token': null,
  'msg': {
    'timeStamp': '1506599870',
    'out_trade_no': '2017092811817',
    'package': 'prepay_id=wx20170928195750df4b35a6a30133945258',
    'paySign': 'EB8698E9CFCCB6C70A51B1EEB468A8CF',
    'appId': 'wx3598eb401cb80f00',
    'signType': 'MD5',
    'nonceStr': 'CF3A196E85604CD5999A6C2F6623E491'
  }
}
const verifyStock = {
  'code': '0000',
  'desc': '由于商品库存变化，您选购的商品数量发生变化，请确认！',
  'token': null,
  'msg': [{
    count: 2,
    cId: 1
  }, {
    count: 0,
    cId: 2
  }]
}
const getOrderItemOutStatus = {
  'code': '0000',
  'desc': '由于商品库存变化，您选购的商品数量发生变化，请确认！',
  'token': null,
  'msg': [{
    Id: 1,
    cntrSn: 'B',
    count: 1,
    goodName: '统一冰火茶',
    pic: 'http://shelf.ybjstore.com/upload/goods/src_4ff9da04-b9e4-40c0-b5a2-147c4b6f62ef.jpg',
    pickUpStatus: '',
    price: 0.01,
    refundStatus: 'REFUNDED',
    status: 'SHIPMENT_SUCCESS'
  }, {
    Id: 2,
    cntrSn: 'A',
    count: 1,
    goodName: '统一冰火茶',
    pic: 'http://shelf.ybjstore.com/upload/goods/src_4ff9da04-b9e4-40c0-b5a2-147c4b6f62ef.jpg',
    pickUpStatus: '',
    price: 0.01,
    refundStatus: 'REFUNDED',
    status: 'SHIPMENT_SUCCESS'
  }, {
    Id: 3,
    cntrSn: 'B',
    count: 2,
    goodName: '统一冰火茶',
    pic: 'http://shelf.ybjstore.com/upload/goods/src_4ff9da04-b9e4-40c0-b5a2-147c4b6f62ef.jpg',
    pickUpStatus: '',
    price: 0.01,
    refundStatus: 'REFUNDED',
    status: 'SHIPMENT_SUCCESS'
  }, {
    Id: 4,
    cntrSn: 'A',
    count: 1,
    goodName: '统一冰火茶',
    pic: 'http://shelf.ybjstore.com/upload/goods/src_4ff9da04-b9e4-40c0-b5a2-147c4b6f62ef.jpg',
    pickUpStatus: '',
    price: 0.01,
    refundStatus: 'REFUNDED',
    status: 'SHIPMENT_SUCCESS'
  }, {
    Id: 5,
    cntrSn: 'C',
    count: 2,
    goodName: '统一冰火茶',
    pic: 'http://shelf.ybjstore.com/upload/goods/src_4ff9da04-b9e4-40c0-b5a2-147c4b6f62ef.jpg',
    pickUpStatus: 'LACK',
    price: 0.01,
    refundStatus: 'REFUNDED',
    status: 'SHIPMENT_SUCCESS'
  }]
}

const getOrderItemRefundStatus = {
  'code': '0000',
  'desc': '',
  'token': null,
  'msg': [{
    cntrSn: 'B',
    goodName: '矿泉水',
    price: '12',
    count: 1,
    cId: 1,
    pic: 'http://osv8eirwz.bkt.clouddn.com/3bc1c63e-ebd1-400b-83fe-8550b7a613da.png'
  }, {
    cntrSn: 'A',
    goodName: '矿泉水',
    price: '12',
    count: 2,
    cId: 1,
    pic: 'http://osv8eirwz.bkt.clouddn.com/3bc1c63e-ebd1-400b-83fe-8550b7a613da.png'
  }, {
    cntrSn: 'B',
    goodName: '矿泉水',
    price: '12',
    count: 1,
    cId: 1,
    pic: 'http://osv8eirwz.bkt.clouddn.com/3bc1c63e-ebd1-400b-83fe-8550b7a613da.png'
  }, {
    cntrSn: 'C',
    goodName: '矿泉水',
    price: '12',
    count: 2,
    cId: 1,
    pic: 'http://osv8eirwz.bkt.clouddn.com/3bc1c63e-ebd1-400b-83fe-8550b7a613da.png'
  }]
}

export default {
  getGoodsItems: config => {
    return goodsItems
  },
  getGoodsBySn: config => {
    return goodItem
  },
  authUserInfo: config => {
    return authUserInfo
  },
  jsapiConfig: config => {
    return jsapiConfig
  },
  pay: config => {
    return paywx
  },
  verifyStock: config => {
    return verifyStock
  },
  getOrderItemOutStatus: config => {
    return getOrderItemOutStatus
  },
  getOrderItemRefundStatus: config => {
    return getOrderItemRefundStatus
  }
}
