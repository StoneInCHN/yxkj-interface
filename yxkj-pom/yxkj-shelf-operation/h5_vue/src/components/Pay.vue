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
      <x-button type="primary" @click.native="pay">{{payBtnInfo}}</x-button>
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
      type: this.$store.getters.getType,
      showScrollBox: false,
      pageInfo: ''
    }
  },
  computed: {
    goodsItems () {
      let items = this.$store.getters.getGoodItems
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
      return '￥' + this.$store.getters.getTotalPrice
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
    }
  },
  methods: {
    pay () {
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
          if (this.type === 'wx') {
            this.$wechat.chooseWXPay({
              'timestamp': res.msg.timeStamp,
              'nonceStr': res.msg.nonceStr,
              'package': res.msg.package,
              'signType': res.msg.signType, // 注意：新版支付接口使用 MD5 加密
              'paySign': res.msg.paySign,
              success: (res) => {
                this.$router.push('payResult')
              }
            })
          } else if (this.type === 'alipay') {
            const div = document.createElement('div')
            div.innerHTML = res.msg.a_page
            document.body.appendChild(div)
            document.forms[0].submit()
          }
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
