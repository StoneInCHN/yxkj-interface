<template>
  <div class="main">
    <div class="only-one-goods" v-if="isOnly">
        <div class="goods-header">
         <h5>{{onlyItem.fullName}}</h5>
         <icon type="clear" class="goods-header-icon"  @click.native="canaelOnlyItem"></icon>
        </div>
        <div class="goods-content">
          <img :src="onlyItem.gImg" :alt="onlyItem.gName" />
          <group>
            <p>
              <strong> ￥ {{onlyItem.gPrice}}</strong><inline-x-number v-model="onlyItem.gCount" :min="1" button-style="round"></inline-x-number>
            </p>   
          </group>
        </div>
    </div>
    <div class="goods-list" v-else>
      <flexbox :gutter="0" wrap="wrap" >
        <goods v-for="dataItem in datas" :dataItem="dataItem" @remove="removeGoods" @changeCount="changeCount"></goods>
      </flexbox>
    </div>
    <div class="goods-btns">
      <p class="total-price">总共需要支付：￥ <span>{{totalPrice}}</span></p>
      <flexbox>
        <flexbox-item>
          <x-button type="default" @click.native="scanQR"><span class="btn-text">继续扫</span><img src="../assets/qrcode.svg" alt="继续扫" class="btn-img"></x-button>
        </flexbox-item>
        <flexbox-item>
          <x-button type="default" @click.native="goPay"><span class="btn-text">结</span><span class="btn-text">算</span></x-button>
        </flexbox-item>
      </flexbox>
    </div>
  </div>
</template>

<script>
import { Group, Cell, Icon, InlineXNumber, Flexbox, FlexboxItem, XButton } from 'vux'
import {numAdd, numMul} from '../utils/utils'
import Goods from '@/components/Goods'
export default {
  components: {
    Group,
    Cell,
    Icon,
    InlineXNumber,
    Flexbox,
    FlexboxItem,
    XButton,
    Goods
  },
  data () {
    return {
      datas: [],
      userName: '',
      type: '',
      config: {},
      token: sessionStorage.token,
      urlPre: 'http://www.ybjstore.com/h5/shelf',
      hasItems: true
    }
  },
  mounted () {
    let parseParams = this.$querystring.parse()
    let params = {
      authCode: parseParams.authCode,
      compId: parseParams.compId,
      gId: parseParams.goodsId,
      type: parseParams.type
    }
    this.type = params.type
    if (params) {
      if (params.type === 'wx') {
        sessionStorage.type = 'wx'
        this.$store.dispatch('setType', {type: 'wx'})
      } else if (params.type === 'alipay') {
        sessionStorage.type = 'alipay'
        this.$store.dispatch('setType', {type: 'alipay'})
      }
      if (sessionStorage && sessionStorage.items) {
        let goodItems = JSON.parse(sessionStorage.items)
        sessionStorage.items
        if (sessionStorage && goodItems && goodItems.length > 0) { // 是否第一次加载 没有值说明是第一次
          this.datas = goodItems
          this.$store.dispatch('setGoodItems', {goodItems: this.datas})
          let userInfo = JSON.parse(sessionStorage.userInfo)
          this.userName = userInfo.userId
          this.token = sessionStorage.token
          this.$store.dispatch('setToken', {token: this.token})
          this.getConfig()
        } else {
          this.getAuthUserInfo(params)
        }
      } else {
        this.getAuthUserInfo(params)
      }
    }
  },
  computed: {
    onlyItem () {
      let item = {}
      if (this.datas && this.datas.length === 1) {
        item = this.datas[0]
      }
      return item
    },
    isOnly () {
      if (this.datas && this.datas.length === 1) {
        return true
      } else {
        return false
      }
    },
    totalPrice () {
      let total = 0
      if (this.datas) {
        for (let item in this.datas) {
          let data = this.datas[item]
          if (data) {
            let price = numMul(data.gPrice, data.gCount)
            total = numAdd(total, price)
          }
        }
      }
      this.$store.dispatch('setTotalPrice', {totalPrice: total})
      sessionStorage.total = total
      return total
    }
  },
  methods: {
    canaelOnlyItem () {
      this.scanQR()
      this.datas = []
      this.$store.dispatch('setGoodItems', {goodItems: this.datas})
      this.setGoodsStorage(this.datas)
    },
    removeGoods (gId) {
      if (gId) {
        let length = this.datas.length
        let index
        for (let i = 0; i < length; i++) {
          let item = this.datas[i]
          if (item && item.gId === gId) {
            index = i
            break
          }
        }
        if (index || index === 0) {
          this.datas.splice(index, 1)
          this.$store.dispatch('setGoodItems', {goodItems: this.datas})
          this.setGoodsStorage(this.datas)
        }
      }
    },
    changeCount (gId, newVal) { // 重新计算gCount
      if (this.datas && this.datas.length > 0) {
        let length = this.datas.length
        let items = []
        for (let i = 0; i < length; i++) {
          let item = this.datas[i]
          if (item && item.gId === gId) {
            item.gCount = newVal
          }
          items.push(item)
        }
        this.datas = items
        this.$store.dispatch('setGoodItems', {goodItems: this.datas})
        this.setGoodsStorage(this.datas)
      }
    },
    scanQR () {
      if (this.type === 'wx') {
        this.$wechat.scanQRCode({
          needResult: 1,
          desc: 'scanQRCode desc',
          success: (res) => {
            let url = res.resultStr
            if (url && url.indexOf(this.urlPre) !== -1) {
              let gId = this.parseUrl(url)
              let params = {
                userName: this.userName,
                gId: gId
              }
              this.getGoodsInfo(params)
            } else {
              this.$vux.alert.show({
                content: '请扫描正确的二维码'
              })
              setTimeout(() => {
                this.$vux.alert.hide()
              }, 3000)
            }
          },
          cancel: (res) => {
            if (!this.datas || this.datas.length === 0) {
              this.$wechat.closeWindow()
            }
          }
        })
      } else if (this.type === 'alipay') {
        this.alipayJSReady(() => {
          window.AlipayJSBridge.call('scan', {
            type: 'qr'
          }, (result) => {
            if (result.errorCode && result.errorCode === '10') {
              if (!this.datas || this.datas.length === 0) {
                window.AlipayJSBridge.call('closeWebview')
              }
            } else if (result.errorCode && result.errorCode === '11') {
              console.log('scan fail')
            } else {
              let url = result.qrCode
              if (url && url.indexOf(this.urlPre) !== -1) {
                let gId = this.parseUrl(url)
                let params = {
                  userName: this.userName,
                  gId: gId
                }
                this.getGoodsInfo(params)
              } else {
                this.$vux.alert.show({
                  content: '请扫描正确的二维码'
                })
                setTimeout(() => {
                  this.$vux.alert.hide()
                }, 3000)
              }
            }
          })
        })
      }
    },
    getGoodsInfo (params) {
      this.$api.getGoodsBySn(params).then(res => {
        if (res && res.code === '0000') {
          let item = res.msg
          if (item) {
            let flag = false
            if (this.datas.length > 0) {
              let datasTemp = []
              for (let i = 0; i < this.datas.length; i++) {
                let data = this.datas[i]
                if (data.gId === item.gId) {
                  flag = true
                  data.gCount += 1
                }
                datasTemp.push(data)
              }
              this.datas = datasTemp
            }
            if (!flag) {
              item.gCount = 1
              item.fullName = item.gName + item.gSpec
              this.datas.unshift(item)
            }
            this.$store.dispatch('setGoodItems', {goodItems: this.datas})
            this.setGoodsStorage(this.datas)
          }
        } else {
          let desc = res.desc
          this.$vux.alert.show({
            content: desc + ',请重新扫码!'
          })
          setTimeout(() => {
            this.$vux.alert.hide()
          }, 3000)
        }
      }).catch(error => {
        console.log(error)
      })
    },
    parseUrl (url) {
      let gId
      if (url) {
        let params = url.split('/')
        if (params) {
          gId = params[params.length - 1]
        }
      }
      return gId
    },
    getConfig () {
      let params = {
        userName: this.userName,
        curUrl: location.href
      }
      this.$api.jsApiConfig(params).then(res => {
        if (res && res.code === '0000' && res.msg) {
          this.config.jsapi_ticket = res.msg.jsapi_ticket
          this.config.signature = res.msg.signature
          this.config.nonceStr = res.msg.nonceStr
          this.config.timestamp = res.msg.timestamp
          this.config.url = res.msg.url
          this.config.appId = res.msg.appId
        }
        if (this.config) {
          this.$wechat.config({
            debug: false,
            appId: this.config.appId,
            timestamp: this.config.timestamp,
            nonceStr: this.config.nonceStr,
            signature: this.config.signature,
            jsApiList: [
              'scanQRCode',
              'chooseWXPay',
              'hideAllNonBaseMenuItem',
              'closeWindow'
            ]
          })
          this.$wechat.ready(() => {
            this.$wechat.hideAllNonBaseMenuItem()
            console.log('wx loading success')
          })
          this.$wechat.error((res) => {
            console.log('wx loading error')
          })
        }
      }).catch(error => {
        console.log(error)
      })
    },
    getAuthUserInfo (params) {
      this.$api.authUserInfo(params).then(res => {
        if (res && res.code === '0000') {
          this.token = res.token
          sessionStorage.setItem('token', this.token)
          this.$store.dispatch('setToken', {token: this.token})
          if (res.msg.userInfo) {
            this.userName = res.msg.userInfo.userId
            this.setStorage('userInfo', res.msg.userInfo)
            this.$store.dispatch('setUserInfo', {userInfo: res.msg.userInfo})
            this.getConfig()
          }
          if (res.msg.compInfo) {
            document.title = res.msg.compInfo.displayName
            this.setStorage('compInfo', res.msg.compInfo)
            this.$store.dispatch('setCompInfo', {compInfo: res.msg.compInfo})
          }
          if (res.msg.gInfo) {
            let item = res.msg.gInfo
            item.gCount = 1
            item.fullName = item.gName + item.gSpec
            this.datas.push(item)
            this.$store.dispatch('setGoodItems', {goodItems: this.datas})
          }
        } else {
          let desc = res.desc
          this.$vux.alert.show({
            content: desc + ',请退出重新扫码！'
          })
          setTimeout(() => {
            this.$vux.alert.hide()
          }, 3000)
        }
      }).catch(error => {
        console.log(error)
      })
    },
    alipayJSReady (callback) {
      if (window.AlipayJSBridge) {
        callback && callback()
      } else {
        document.addEventListener('AlipayJSBridgeReady', callback, false)
      }
    },
    goPay () {
      let goodItems = this.$store.getters.getGoodItems
      this.setGoodsStorage(goodItems)
      this.$router.push('pay')
    },
    setGoodsStorage (value) {
      let items = JSON.stringify(value)
      sessionStorage.items = items
    },
    setStorage (key, value) {
      let items = JSON.stringify(value)
      sessionStorage.setItem(key, items)
    }
  },
  watch: {
    'onlyItem.gCount' (curVal, oldVal) {
      if (this.datas && this.datas.length === 1) {
        this.changeCount(this.onlyItem.gId, curVal)
      }
    }
  }
}
</script>

<style scoped>
.main{
  padding-bottom: 85px;
}
.only-one-goods{
  margin:20px;
  border-radius: 10px;
  background-color: #ffffff;
  border: 1px solid #EAEAEA;
  border-radius: 10px;
  box-shadow: 0 0 5px #EAEAEA;
}
.goods-item{
  margin:10px;
  border: 1px solid #E2E1DF;
  border-radius: 10px;
  box-shadow: 0 0 5px #E2E1DF;
}
.goods-header{
  text-align: center;
  position: relative;
  height: 40px;
  border-bottom: 1px solid #E2E1DF;
}
.goods-header h5{
  height: 20px;
  line-height: 20px;
  padding: 10px;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
}
.goods-header-icon{
  position:absolute;
  top: 0;
  right: -5px;
  color: #b1b2b3 !important;
  font-size: 30px !important;
}
.goods-content{
   padding: 10px;
   text-align: center;
}
.goods-content img{
  width: 100%;
}
.only-one-goods .goods-content p{
  margin:10px;
}
.goods-content p{
  text-align: left;
  position: relative;
}
.goods-content p div{
  text-align: right;
  position: absolute;
  top: 0;
  right: 0;
}
.only-one-goods .goods-content span{
  vertical-align: top;
  display: inline-block;
  width: 60px;
}
.goods-item{
  background-color: #ffffff;
}
.goods-item .goods-content span{
  vertical-align: top;
  font-size: 15px;
  margin-right: 5px;
}
.goods-item .vux-number-input{
  width: 25px !important;
  height: 15px !important;
  font-size: 15px !important;
}
.goods-item svg{
  width: 15px!important;
  height: 15px!important;
}
.goods-content .vux-number-selector{
  color:#999999 !important;
}
.goods-item  .vux-number-selector{
    font-size: 15px!important;
    line-height: 15px!important;
    color: #E2E1DF!important;
    padding: 0 1px!important;
    border: 1px solid #E2E1DF!important;
    height: 16px !important;
    width: 15px !important;
}
.goods-btns{
  position: fixed;
  bottom: 0;
  width: 100%;
  background-color: #FBFAF9;
}
.total-price{
  margin: 0 20px;
  color:#384053;
}
.total-price span {
  color: #CD3132;
}
.goods-btns .vux-flexbox{
  padding: 9px 20px;
  width: initial !important;
}
.weui-btn_default {
  background-color: #ffb609;
  color:#ffffff;
}
.btn-img{
  width: 20px;
  height: 20px;
  position: absolute;
  top: 10px;
  position: absolute;
}
.btn-text {
  margin-right: 5px;
  margin-left: 5px;
}
</style>
