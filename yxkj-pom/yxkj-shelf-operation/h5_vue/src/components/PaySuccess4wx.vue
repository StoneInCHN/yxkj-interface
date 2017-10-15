<template>
  <div class="pay">
    <div class="pay-top">
       <img src="../assets/paySuccess4wx.svg" alt="支付成功">
       <p class="tips">支付成功</p>
    </div>
    <div class="pay-center">
      <p>Mini优比家</p>
      <p class="price">￥<strong>18.9</strong></p>
    </div>
    <div class="pay-footer">
       <x-button plain type="primary" link="payResult">完成</x-button>
       <check-icon :value.sync="isAttention" type="plain">关注Mini优比家</check-icon>
    </div>
  </div>
</template>

<script>
import { XButton, CheckIcon } from 'vux'

export default {
  components: {
    XButton,
    CheckIcon
  },
  data () {
    return {
      isAttention: true
    }
  }
}
</script>

<style scoped>
.pay{
  position: relative;
}
.pay-top{
  text-align: center;
  color: #19AD17;
  padding: 50px;
}
.pay-top img{
  width: 50px;
}
.pay-center{
  text-align: center;
  color: #b1b2b3;
}
.price strong{
  font-size: 30px;
  color:black;
}
.pay-footer {
  padding: 200px 80px 0 80px;
}
.pay-footer p {
  padding: 0 30px;
}
.pay-footer .weui-cell__bd{
  font-size: 14px!important;
}
</style>
