<template>
   <cell class="item">
        <div slot="icon" style="margin:auto 20px auto 0">
          <img width="50" :alt="item.goodName"  :src="item.pic">
        </div>
        <div slot="title">
          <p>{{item.goodName}}</p>
        </div> 
        <div slot="inline-desc">
          <p>{{item.cSn}}</p>
          <p class="price">￥<span>{{item.price}}</span></p>
        </div>    
        <span slot>
          <span class="count">x{{item.count}}</span>
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
      msg: 'main'
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
  margin-right: 5px;
}
</style>
