<template>
  <div class="pay">
    <div class="pay-top">
       <p >Mini优比家</p>
       <img src="../assets/paySuccess.svg" alt="支付成功">
       <p class="tips">购买成功，快享用美食吧~</p>
    </div>
    <div class="pay-footer">
       <p >Mini优比家，美味尽在手边</p>
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
  padding: 30px;
}
.pay-top p{
  margin:20px auto;
}
.pay-top img{
  width: 100px;
}
.tips{
  margin-top: 50px;
  color: green;
}
.pay-footer {
  padding: 200px 0 0 0;
}
.pay-footer p{
  font-size: 14px!important;
  text-align: center;
}
</style>
