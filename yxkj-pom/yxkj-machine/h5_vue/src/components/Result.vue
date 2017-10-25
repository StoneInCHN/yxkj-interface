<template>
  <div class="main">
    <group >
      <div solt="title" class="group">
         <span class="substantial">A货柜</span>
      </div>
      <item v-for="item in items" :item="item" ></item>
   </group>
   <group >
      <div solt="title" class="group">
         <span class="shortage">缺货</span>
      </div>
      <item v-for="item in items" :item="item"></item>
   </group>
  </div>
</template>

<script>
import { Group } from 'vux'
import Item from '@/components/ResultItem'
export default {
  name: 'main',
  components: {
    Group,
    Item
  },
  data () {
    return {
      items: []
    }
  },
  mounted () {
    this.getStatus()
    window.setInterval(() => {
      this.getStatus()
    }, 2000)
  },
  methods: {
    getStatus () {
      let params = {
        orderId: '6'
      }
      this.$api.getOrderItemOutStatus(params).then(res => {
        if (res && res.code === '0000') {
          this.items = res.msg
        } else {
          console.log('111')
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
