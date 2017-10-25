<template>
   <cell class="item" :title="item.goodName">
        <div slot="icon" style="margin:auto 20px auto 0">
          <img width="50" :alt="item.goodName"  :src="item.gImg">
        </div>
        <span slot>
          <span class="count">x{{item.count}}</span>
          <label class="item-label" :class="statusClass">{{statusLabel}}</label>
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
    statusLabel () {
      let label = '未出柜'
      if (this.item.status === 'NOT_SHIPMENT') {
        label = '未出柜'
        this.statusClass = {
          'label-info': true
        }
      } else if (this.item.status === 'SHIPMENT_INPROCESS') {
        label = '出货中'
        this.statusClass = {
          'label-inprocess': true
        }
      } else if (this.item.status === 'SHIPMENT_SUCCESS') {
        label = '出货中'
        this.statusClass = {
          'label-success': true
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
}
.label-info{
  background-color: #8CB046;
}
.label-success{
  background-color: blue;
}
.label-fail{
  background-color: red;
}
.label-inprocess{
  background-color: green;
}
</style>
