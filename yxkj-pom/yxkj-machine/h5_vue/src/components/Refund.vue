<template>
  <div class="main">
    <div class="title-price">
      <span>￥{{totalprice}}</span>
    </div>
    <p>您的退款已成功，请前往您的支付账户确认是否到账！</p>
    <div class="content">
      <div class="refund-info clearfix">
        <span class="left">退款商品（{{totalCount}}件）</span>
        <span class="right">总价：￥{{totalprice}}</span>
      </div>
      <item v-for="item in items" :item="item"></item>
    </div>
  </div>
</template>

<script>
import { Group } from 'vux'
import Item from '@/components/RefundItem'
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
      orderSn: ''
    }
  },
  mounted () {
    document.title = '退款详情'
    let hash = document.location.hash.replace(/^\?/, '')
    let arr = hash.split('?')
    let token = sessionStorage.token
    this.$store.dispatch('setToken', {token: token})
    if (arr[1]) {
      let params = this.$querystring.parse(arr[1])
      if (params.orderSn) {
        this.orderSn = params.orderSn
        this.getOrderItemRefundStatus()
      }
    }
  },
  computed: {
    totalprice () {
      let total = 0
      if (this.items.length > 0) {
        this.items.forEach((value, index) => {
          let price = numMul(value.price, value.count)
          total = numAdd(total, price)
        })
      }
      return total
    },
    totalCount () {
      let total = 0
      if (this.items.length > 0) {
        this.items.forEach((value, index) => {
          total = numAdd(total, value.count)
        })
      }
      return total
    }
  },
  methods: {
    getOrderItemRefundStatus () {
      let params = {
        orderSn: this.orderSn
      }
      this.$api.getOrderItemRefundStatus(params).then(res => {
        if (res.code === '0000' && res.msg) {
          this.items = res.msg
        }
      }).catch(error => {
        console.log(error)
      })
    }
  }
}
</script>


<style lang="less">
.main{
  padding:20px;
  .title-price {
    margin: 30px;
    text-align: center;
    span{
      font-size:18px;
    }
  }
  p {
    text-align: center;
  }
  .content {
    padding:10px;
    .refund-info {
      margin-top:30px;
      border-bottom: 1px solid #b1b2b3;
      .left{
        float:left
      }
      .right{
        float:right
      }
    }
    .clearfix:after {
      content: "\0020";
      display: block;
      height: 0;
      clear: both;
    }
  }
}
</style>
