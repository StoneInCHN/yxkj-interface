<template>
<flexbox-item :span="1/2">
	<div class="goods-item">
    <div class="goods-header">
      <h5>{{fullName}}</h5>
      <icon type="cancel" class="goods-header-icon" :key="dataItem.gId" @click.native="removeGoods"></icon>
    </div>
    <div class="goods-content">
      <img :src=dataItem.gImg :alt=dataItem.gName />
      <group>
        <p><strong>{{price}}</strong></p>
        <p>
          <span>数量:</span><inline-x-number v-model="dataItem.gCount" :min="1" button-style="round"></inline-x-number>
        </p>
      </group>
    </div>
  </div>
</flexbox-item>
</template>
<script>
import { Group, Cell, Icon, InlineXNumber, FlexboxItem } from 'vux'
export default {
  components: {
    Group,
    Cell,
    Icon,
    InlineXNumber,
    FlexboxItem
  },
  props: ['dataItem'],
  data () {
    return {}
  },
  computed: {
    fullName () {
      return this.dataItem.gName + this.dataItem.gSpec
    },
    price () {
      return '￥' + this.dataItem.gPrice
    }
  },
  watch: {
    'dataItem.gCount' (newVal, oldVal) {
      this.changeCount(this.dataItem.gId, newVal)
    }
  },
  methods: {
    removeGoods () {
      this.$emit('remove', this.dataItem.gId)
    },
    changeCount (gId, newVal) {
      if (gId && newVal) {
        this.$emit('changeCount', gId, newVal)
      }
    }
  }
}
</script>
<style>
  .goods-item{
  margin:10px;
  border: 1px solid #EAEAEA;
  border-radius: 10px;
  box-shadow: 0 0 5px #EAEAEA;
}
.goods-header{
  text-align: center;
  position: relative;
  height: 50px;
  border-bottom:1px solid #EAEAEA;
}
.goods-header h5{
  height: 20px;
  line-height: 20px;
  padding: 10px;
}
.goods-header-icon{
  position:absolute;
  top: -7px;
  right: -12px;
  color: #b1b2b3 !important;
  font-size: 30px !important;
}
.goods-content{
   padding: 10px;
   text-align: center;
}
.goods-content img{
  width: 60%;
}
.only-one-goods .goods-content p{
  margin:10px;
}
.only-one-goods .goods-content span{
  vertical-align: top;
  display: inline-block;
  width: 60px;
}
.goods-item{
  background-color: #ffffff;
}
.goods-item .goods-content span{
  vertical-align: top;
  font-size: 15px;
  margin-right: 5px;
}
.goods-item .vux-number-input{
  width: 25px !important;
  height: 15px !important;
  font-size: 15px !important;
}
.goods-item svg{
  width: 15px!important;
  height: 15px!important;
}
.goods-content .vux-number-selector{
  color:#999999 !important;
}
.goods-item  .vux-number-selector{
    font-size: 15px!important;
    line-height: 15px!important;
    color: #EAEAEA!important;
    padding: 0 1px!important;
    border: 1px solid #EAEAEA!important;
    height: 16px !important;
    width: 15px !important;
}
</style>
