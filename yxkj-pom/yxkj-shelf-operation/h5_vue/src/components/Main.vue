<template>
  <div class="main">
    <div class="only-one-goods" v-if="isOnly">
        <div class="goods-header">
         <h5>{{onlyItem.fullName}}</h5>
         <icon type="cancel" class="goods-header-icon"  @click.native="canaelOnlyItem"></icon>
        </div>
        <div class="goods-content">
          <img :src="onlyItem.gImg" :alt="onlyItem.gName" />
          <group>
            <p><strong> ￥ {{onlyItem.gPrice}}</strong></p>
            <p>
              <span>数量:</span><inline-x-number v-model="onlyItem.gCount" :min="1" button-style="round"></inline-x-number>
            </p>   
          </group>
        </div>
    </div>
    <div class="goods-list" v-else>
      <flexbox :gutter="0" wrap="wrap">
        <flexbox-item :span="1/2" v-for="dataItem in datas">
          <div class="goods-item">
            <div class="goods-header">
             <h5>{{dataItem.fullName}}</h5>
             <icon type="cancel" class="goods-header-icon" :data-id="dataItem.gId" @click.native="removeGoods"></icon>
            </div>
            <div class="goods-content">
              <img :src=dataItem.gImg :alt=dataItem.gName />
              <group>
                <p><strong> ￥ {{dataItem.gPrice}}</strong></p>
                <p>
                  <span>数量:</span><inline-x-number v-model="dataItem.gCount" :min="1" button-style="round"></inline-x-number>
                </p>
              </group>
            </div>
          </div>
        </flexbox-item>
      </flexbox>
    </div>
    <p class="total-price">总共需要支付：￥ {{totalPrice}}</p>
    <div class="goods-btns">
      <flexbox>
        <flexbox-item>
          <x-button type="default" @click.native="scanQR">继续扫</x-button>
        </flexbox-item>
        <flexbox-item>
          <x-button type="default" link="pay">结算</x-button>
        </flexbox-item>
      </flexbox>
    </div>
  </div>
</template>

<script>
import { Group, Cell, Icon, InlineXNumber, Flexbox, FlexboxItem, XButton } from 'vux'
import {numAdd, numMul} from '../utils/utils'
export default {
  components: {
    Group,
    Cell,
    Icon,
    InlineXNumber,
    Flexbox,
    FlexboxItem,
    XButton
  },
  data () {
    return {
      datas: [],
      userName: '',
      type: '',
      config: {},
      token: '',
      urlPre: 'http://test.ybjcq.com/h5/shelf',
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
        this.$store.dispatch('setType', {type: 'wx'})
      } else if (params.type === 'alipay') {
        this.$store.dispatch('setType', {type: 'alipay'})
      }
      this.$api.authUserInfo(params).then(res => {
        console.log(res)
        if (res && res.code === '0000') {
          this.token = res.token
          this.$store.dispatch('setToken', {token: this.token})
          if (res.msg.userInfo) {
            this.userName = res.msg.userInfo.userId
            this.$store.dispatch('setUserInfo', {userInfo: res.msg.userInfo})
            this.getConfig()
          }
          if (res.msg.compInfo) {
            document.title = res.msg.compInfo.displayName
            this.$store.dispatch('setCompInfo', {compInfo: res.msg.compInfo})
          }
          if (res.msg.gInfo) {
            let item = res.msg.gInfo
            item.gCount = 1
            item.fullName = item.gName + item.gSpec
            this.datas.push(item)
            this.$store.dispatch('setGoodItems', {goodItems: this.datas})
          }
        }
      }).catch(error => {
        console.log(error)
      })
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
      return total
    }
  },
  methods: {
    canaelOnlyItem () {
      this.scanQR()
      this.datas = []
      this.$store.dispatch('setGoodItems', {goodItems: this.datas})
    },
    removeGoods (e) {
      let $el = e.target
      let gId = $el.getAttribute('data-id')
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
          })
        })
      }
    },
    getGoodsInfo (params) {
      this.$api.getGoodsBySn(params).then(res => {
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
            this.datas.push(item)
          }
          this.$store.dispatch('setGoodItems', {goodItems: this.datas})
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
    alipayJSReady (callback) {
      if (window.AlipayJSBridge) {
        callback && callback()
      } else {
        document.addEventListener('AlipayJSBridgeReady', callback, false)
      }
    }
  }
}
</script>

<style scoped>
.main{
  padding-bottom: 60px;
}
.only-one-goods{
  margin:20px;
  border: 1px solid #EAEAEA;
  border-radius: 10px;
  background-color: #ffffff;
}
.goods-item{
  margin:10px;
  border: 1px solid #EAEAEA;
  border-radius: 10px;
  box-shadow: 0 0 5px #EAEAEA;
}
.goods-header{
  text-align: center;
  position: relative;
  height: 50px;
  border-bottom:1px solid #EAEAEA;
}
.goods-header h5{
  height: 20px;
  line-height: 20px;
  padding: 10px;
}
.goods-header-icon{
  position:absolute;
  top: -7px;
  right: -12px;
  color: #b1b2b3 !important;
  font-size: 30px !important;
}
.goods-content{
   padding: 10px;
   text-align: center;
}
.goods-content img{
  width: 60%;
}
.only-one-goods .goods-content p{
  margin:10px;
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
    color: #EAEAEA!important;
    padding: 0 1px!important;
    border: 1px solid #EAEAEA!important;
    height: 16px !important;
    width: 15px !important;
}
.total-price{
  margin:20px;
}
.goods-btns{
  position: fixed;
  bottom: 0;
  width: 100%;
}
.goods-btns .vux-flexbox{
  padding: 9px 20px;
  width: initial !important;
}
</style>
