<template>
<flexbox-item :span="1/2">
	<div class="goods-item">
    <div class="goods-content">
      <icon type="clear" class="goods-header-icon" :key="dataItem.gId" @click.native="removeGoods"></icon>
      <img :src="dataItem.gImg" :alt="dataItem.gName" />
      <p class="goods-title">{{fullName}}</p>
      <p class="goods-price">
      <span>{{price}}</span><inline-x-number v-model="dataItem.gCount" :min="1" button-style="round"></inline-x-number>
      </p>
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
  margin:5px;
  border: 1px solid #EAEAEA;
  border-radius: 10px;
  box-shadow: 0 0 5px #EAEAEA;
}
.goods-header{
  text-align: center;
  position: relative;
  height: 50px;
}
.goods-header h5{
  height: 20px;
  line-height: 20px;
  padding: 10px;
}
.goods-header-icon{
  position:absolute;
  top: 0;
  right: -5px;
  color: #b1b2b3 !important;
  font-size: 30px !important;
}
.goods-content{
  position: relative;
  padding: 10px;
  text-align: center;
}
.goods-content img{
  width: 100%;
}
.goods-title{
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
  margin:5px;
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
    border: 1px solid #ffb609!important;
    height: 16px !important;
    width: 15px !important;
}
.goods-item .goods-price {
  position: relative;
  text-align: left;
}
.goods-item .goods-price .vux-inline-x-number{
  position: absolute;
  top: 2px;
  right: 0;
}
</style>
