<template>
   <cell class="item" :title="item.goodName" >
        <div slot="icon" style="margin:auto 20px auto 0">
          <img width="50" :alt="item.goodName"  :src="item.pic">
        </div>
        <span slot class="item-info">
          <span class="count">x{{item.count}}</span>
          <label class="item-label" :class="statusClass">{{statusLabel}}</label>
          <i v-if="imporcess" class="weui-loading"></i>
        </span>                
      </cell>
</template>

<script>
import { Cell } from 'vux'
export default {
  name: 'item',
  components: {
    Cell
  },
  props: ['item'],
  data () {
    return {
      msg: 'main',
      statusClass: {}
    }
  },
  computed: {
    imporcess () {
      return this.item.status === 'SHIPMENT_INPROCESS'
    },
    statusLabel () {
      let label = '未出货'
      if (this.item.pickUpStatus === 'LACK') {
        label = '未出货'
        this.statusClass = {
          'label-primary': true
        }
      } else if (this.item.status === 'NOT_SHIPMENT') {
        label = '未出货'
        this.statusClass = {
          'label-primary': true
        }
      } else if (this.item.status === 'SHIPMENT_INPROCESS') {
        label = '出货中'
        this.statusClass = {
          'label-inprocess': true
        }
      } else if (this.item.status === 'SHIPMENT_SUCCESS') {
        label = '已出货'
        this.statusClass = {
          'label-info': true
        }
      } else {
        // SHIPMENT_FAIL
        label = '出货失败'
        this.statusClass = {
          'label-fail': true
        }
      }
      return label
    }
  }
}
</script>
<style scoped>
.item{
  background-color: #ffffff;
}
.price {
  color: red;
}
.count{
  margin-right: 20px;
}
.item-label {
  background-color: #8CB046;
  color: #ffffff;
  padding: 2px 5px;
  border-radius: 5px;
  margin-right: 5px;
}
.label-primary{
  background-color: #b1b2b3;
}
.label-info{
  background-color: #8CB046;
}
.label-fail{
  background-color: red;
}
.label-inprocess{
  background-color: green;
}
.item-info {
  position: relative;
}
.item-info .weui-loading {
  position: absolute;
  top: 0;
  right: -18px;
}
</style>
