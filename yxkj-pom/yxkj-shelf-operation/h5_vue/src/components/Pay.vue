<template>
  <div>
    <group title="商品信息">
      <cell v-for="goods in goodsItems" :title="goods.gName" :inlineDesc="goods.gDesc" :value="goods.totalPrice" >
         <img slot="icon" width="50" style="display:block;margin-right:5px;" :src="goods.gImg">
      </cell>
       <cell>
          <template solt="value">
            共 <span class="color-red">{{goodsCounts}}</span> 件商品，合计： <span class="color-red">{{totalPrice}}</span>
          </template>
      </cell>
    </group>
    <group title="订单信息">
      <cell title="订单金额" :value="totalPrice" ></cell>
      <cell title="实付金额" :value="totalPrice" >
        <template solt="value">
            <span class="color-red">{{totalPrice}}</span>
        </template>
      </cell>
    </group>
    <div class="btns">
      <x-button type="primary" @click.native="pay" :style="alipayStyle">{{payBtnInfo}}</x-button>
    </div>
  </div>
</template>

<script>
import { Group, Cell, XButton, Flexbox, FlexboxItem } from 'vux'

export default {
  components: {
    Group,
    Cell,
    XButton,
    Flexbox,
    FlexboxItem
  },
  data () {
    return {
      payConfig: {},
      showScrollBox: false,
      pageInfo: '',
      resInfo: {},
      config: {}
    }
  },
  mounted () {
    window.addEventListener('popstate', (e) => {
      if (this.type === 'wx') {
        this.$wechat.closeWindow()
      } else if (this.type === 'alipay') {
        window.AlipayJSBridge.call('closeWebview')
      }
    }, false)
    if (sessionStorage && sessionStorage.token) {
      let token = sessionStorage.token
      this.$store.dispatch('setToken', {token: token})
      this.type = sessionStorage.type
      this.$store.dispatch('setType', {type: this.type})
      let userInfo = JSON.parse(sessionStorage.userInfo)
      this.$store.dispatch('setUserInfo', {userInfo: userInfo})
      let compInfo = JSON.parse(sessionStorage.compInfo)
      this.$store.dispatch('setCompInfo', {compInfo: compInfo})
      this.getConfig()
    }
  },
  computed: {
    type () {
      let type = sessionStorage.type
      if (!type) {
        type = this.$store.getters.getType
      }
      return type
    },
    goodsItems () {
      let items = this.$store.getters.getGoodItems
      if (!items || items.length < 1) {
        items = JSON.parse(sessionStorage.items)
      }
      let datas = []
      for (let i = 0; i < items.length; i++) {
        let item = items[i]
        item.totalPrice = '￥' + item.gPrice * item.gCount
        item.gDesc = '数量:' + item.gCount
        datas.push(item)
      }
      return datas
    },
    totalPrice () {
      let total = sessionStorage.total
      if (total) {
        return '￥' + total
      } else {
        return '￥' + this.$store.getters.getTotalPrice
      }
    },
    goodsCounts () {
      let count = 0
      let items = this.goodsItems
      for (let i = 0; i < items.length; i++) {
        let item = items[i]
        count += item.gCount
      }
      return count
    },
    payBtnInfo () {
      if (this.type === 'wx') {
        return '微信支付'
      } else if (this.type === 'alipay') {
        return '支付宝支付'
      }
    },
    alipayStyle () {
      if (this.type === 'alipay') {
        return {backgroundColor: '#0F8CE6'}
      } else {
        return {}
      }
    }
  },
  methods: {
    pay () {
      if (this.resInfo) {
        let gInfos = []
        let items = this.goodsItems
        for (let i = 0; i < items.length; i++) {
          let str = items[i].gId + '_' + items[i].gCount
          gInfos.push(str)
        }
        let params = {
          type: this.$store.getters.getType,
          userName: this.$store.getters.getUserInfo.userId,
          compId: this.$store.getters.getCompInfo.compId,
          gInfo: gInfos
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
      } else {
        this.goPay(this.resInfo)
      }
    },
    getConfig () {
      let params = {
        userName: this.$store.getters.getUserInfo.userId,
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
    goPay (res) {
      if (this.type === 'wx') {
        this.$wechat.chooseWXPay({
          'timestamp': res.msg.timeStamp,
          'nonceStr': res.msg.nonceStr,
          'package': res.msg.package,
          'signType': res.msg.signType,
          'paySign': res.msg.paySign,
          success: (res) => {
            this.$router.push('payResult')
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
.color-red{
  color:red;
}
.btns{
  padding:30px 10px;
}
</style>
