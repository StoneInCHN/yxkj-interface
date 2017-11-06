<template>
  <div class="main">
    <group v-for="item in items">
      <div solt="title" class="group">
         <span class="substantial">{{item.key}}货柜</span>
      </div>
      <item v-for="goods in item.goods" :item="goods" ></item>
   </group>
   <group v-if="isLack">
      <div solt="title" class="group">
         <span class="shortage">缺货</span>
      </div>
      <item v-for="item in lackItems" :item="item"></item>
   </group>
  </div>
</template>

<script>
import { Group } from 'vux'
import Item from '@/components/ResultItem'
import {numAdd, numMul} from '../utils/utils'
export default {
  name: 'main',
  components: {
    Group,
    Item
  },
  data () {
    return {
      items: [],
      lackItems: [],
      lackAlert: true,
      orderSn: '',
      token: sessionStorage.token,
      interval: ''
    }
  },
  mounted () {
    document.title = '商品出柜'
    let hash = document.location.hash.replace(/^\?/, '')
    let arr = hash.split('?')
    let token = sessionStorage.token
    this.$store.dispatch('setToken', {token: token})
    if (arr[1]) {
      let params = this.$querystring.parse(arr[1])
      if (params.orderSn) {
        this.orderSn = params.orderSn
        this.getStatus()
        this.interval = window.setInterval(() => {
          this.getStatus()
        }, 2000)
      }
    }
  },
  computed: {
    userName () {
      let userInfo = JSON.parse(sessionStorage.userInfo)
      return userInfo.userId
    },
    isLack () {
      return this.lackItems.length > 0
    },
    lackprice () {
      let total = 0
      if (this.lackItems.length > 0) {
        this.lackItems.forEach((value, index) => {
          let price = numMul(value.price, value.count)
          total = numAdd(total, price)
        })
      }
      return total
    }
  },
  methods: {
    lackAlertMsg () {
      let _this = this
      let totalPrice = this.lackprice
      if (this.lackAlert) {
        this.$vux.alert.show({
          title: '商品缺货',
          content: '<p style="margin:5px auto">由于商品缺货，您支付的费用￥' + totalPrice + '已经退回到支付账户，请及时确认！</p>',
          buttonText: '查看退款详情',
          onHide () {
            window.clearInterval(_this.interval)
            _this.$router.push({path: 'refund', query: { orderSn: _this.orderSn }})
          }
        })
      }
    },
    getStatus () {
      let params = {
        orderSn: this.orderSn
      }
      this.$api.getOrderItemOutStatus(params).then(res => {
        if (res && res.code === '0000') {
          let temps = []
          this.items = []
          this.lackItems = []
          let goodsItems = res.msg
          if (goodsItems && goodsItems.length > 0) {
            goodsItems.forEach((value, index, array) => {
              if (value.status === 'NOT_SHIPMENT' && value.pickUpStatus !== 'LACK') {
                this.lackAlert = false
              }
              if (value.status === 'SHIPMENT_INPROCESS' && value.pickUpStatus !== 'LACK') {
                this.lackAlert = false
              }
              // 是否缺货
              if (value.pickUpStatus === 'LACK') {
                this.lackItems.push(value)
              } else {
                let type = value.cntrSn
                let obj = temps[type]
                if (obj) {
                  obj.push(value)
                  temps[type] = obj
                } else {
                  obj = []
                  obj.push(value)
                  temps[type] = obj
                }
              }
            })
          }
          let indeArray = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J']
          indeArray.forEach((value, index, array) => {
            if (temps[value]) {
              let obj = {
                key: value,
                goods: temps[value]
              }
              this.items.push(obj)
            }
          })
          if (this.lackItems.length > 0) {
            this.lackAlertMsg()
          }
        }
      }).catch(error => {
        console.log(error)
      })
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
.group {
  padding: 10px;
}
.substantial{
  background-color: #8CB046;
  color: #ffffff;
  padding: 3px 10px;
  border-radius: 5px;
}
.shortage{
  background-color: red;
  color: #ffffff;
  padding: 3px 10px;
  border-radius: 5px;
}
</style>
