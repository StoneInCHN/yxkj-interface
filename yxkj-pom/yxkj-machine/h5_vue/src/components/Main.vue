<template>
  <div class="main">
    <group>
      <item v-for="item in datas" :item="item"></item>
   </group>
    <div class="footer">
      <div class="price">
        <span>总计：￥{{ totalPrice }}</span>
      </div>
      <div class="pay">
        <span @click="verifyStock">立即支付</span>
      </div>
    </div>
  </div>
</template>

<script>
import { Group } from 'vux'
import Item from '@/components/Item'
import {numAdd, numMul} from '../utils/utils'
export default {
  name: 'main',
  components: {
    Group,
    Item
  },
  data () {
    return {
      datas: [],
      userName: '',
      type: '',
      imei: '',
      config: {},
      token: sessionStorage.token,
      urlPre: 'http://www.ybjstore.com/h5/shelf'
    }
  },
  mounted () {
    let parseParams = this.$querystring.parse()
    let params = {
      authCode: parseParams.authCode,
      gl: parseParams.gl,
      imei: parseParams.imei,
      type: parseParams.type
    }
    this.type = params.type
    this.imei = parseParams.imei
    this.getAuthUserInfo(params)
  },
  computed: {
    ip () {
      return ''
    },
    totalPrice () {
      let total = 0
      if (this.datas) {
        for (let item in this.datas) {
          let data = this.datas[item]
          if (data) {
            let price = numMul(data.price, data.count)
            total = numAdd(total, price)
          }
        }
      }
      return total
    }
  },
  methods: {
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
        console.log(res)
        if (res && res.code === '0000') {
          this.token = res.token
          this.$store.dispatch('setToken', {token: this.token})
          if (res.msg.userInfo) {
            this.userName = res.msg.userInfo.userId
            this.getConfig()
          }
          if (res.msg.gInfo) {
            this.datas = res.msg.gInfo
          }
        } else {
          let desc = res.desc
          this.$vux.alert.show({
            content: desc
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
    getGInfo () {
      let gInfos = []
      let items = this.datas
      for (let i = 0; i < items.length; i++) {
        let str = items[i].cId + '_' + items[i].count
        gInfos.push(str)
      }
      return gInfos
    },
    verifyStock () {
      let params = {
        gInfo: this.getGInfo()
      }
      this.$api.verifyStock(params).then(res => {
        console.log(res)
        if (res && res.code === '0000') {
          this.pay()
        } else if (res && res.code === '1000') {
          let desc = res.desc
          this.$vux.alert.show({
            content: desc
          })
          setTimeout(() => {
            this.$vux.alert.hide()
          }, 3000)
        } else {
          console.log('111')
        }
      }).catch(error => {
        console.log(error)
      })
    },
    pay () {
      let params = {
        gInfo: this.getGInfo(),
        type: this.type,
        userName: this.userName,
        imei: this.imei,
        ip: this.ip
      }
      this.$api.pay(params).then(res => {
        if (res && res.code === '0000' && res.msg) {
          this.resInfo = res
          this.goPay(res)
        } else {
          this.$vux.alert.show({
            content: '操作失败'
          })
          setTimeout(() => {
            this.$vux.alert.hide()
          }, 3000)
        }
      }).catch(error => {
        console.log(error)
      })
    },
    goPay (res) {
      if (this.type === 'wx') {
        this.$wechat.chooseWXPay({
          'timestamp': res.msg.timeStamp,
          'nonceStr': res.msg.nonceStr,
          'package': res.msg.package,
          'signType': res.msg.signType,
          'paySign': res.msg.paySign,
          success: (res) => {
            this.$router.push('result')
          },
          cancel: (res) => {
            this.$vux.toast.text('取消了支付')
          }
        })
      } else if (this.type === 'alipay') {
        const div = document.createElement('div')
        div.innerHTML = res.msg.a_page
        document.body.appendChild(div)
        document.forms[0].submit()
      }
    }
  }
}
</script>


<style scoped>
.main {
  position: relative;
  font-size:14px;
}
.footer{
  position: fixed;
  bottom: 0;
  left: 0;
  height: 50px;
  width: 100%;
  background-color: #b1b2b3;
}
.price{
  display: inline-block;
  width: 67%;
  background-color: #434448;
  height: 50px;
}
.pay{
  display: inline-block;
  width: 33%;
  background-color: #89b13e;
  height: 50px;
  float: right;
}
.footer span{
  color: #ffffff;
  line-height: 50px;
  height: 50px;
  display: block;
  text-align: center;
}
</style>
