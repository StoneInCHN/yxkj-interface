<template>
  <div>
    <div class="only-one-goods" v-if="isOnly">
        <div class="goods-header">
         <h5>{{onlyItem.name}}</h5>
         <icon type="cancel" class="goods-header-icon" @click.native="canaelOnlyItem"></icon>
        </div>
        <div class="goods-content">
          <img :src=onlyItem.img :alt=onlyItem.name />
          <group>
            <p><strong> ￥ {{onlyItem.price}}</strong></p>
            <p>
              <span>数量:</span><inline-x-number v-model="onlyItem.count" :min="0" button-style="round"></inline-x-number>
            </p>   
          </group>
        </div>
    </div>
    <div class="goods-list" v-else>
      <flexbox :gutter="0" wrap="wrap">
        <flexbox-item :span="1/2" v-for="dataItem in datas">
          <div class="goods-item">
            <div class="goods-header">
             <h5>{{dataItem.name}}</h5>
             <icon type="cancel" class="goods-header-icon" @click.native="removeItem"></icon>
            </div>
            <div class="goods-content">
              <img :src=dataItem.img :alt=dataItem.name />
              <group>
                <p><strong> ￥ {{dataItem.price}}</strong></p>
                <p>
                  <span>数量:</span><inline-x-number v-model="dataItem.count" :min="0" button-style="round"></inline-x-number>
                </p>
              </group>
            </div>
          </div>
        </flexbox-item>
      </flexbox>
    </div>
    <p class="total-price">总共需要支付：￥ {{totalPrice}}</p>
    <div class="goods-btns">
      <flexbox>
        <flexbox-item>
          <x-button type="default">继续扫</x-button>
        </flexbox-item>
        <flexbox-item>
          <x-button type="default" link="pay">结算</x-button>
        </flexbox-item>
      </flexbox>
    </div>
  </div>
</template>

<script>
import { Group, Cell, Icon, InlineXNumber, Flexbox, FlexboxItem, XButton, Alert } from 'vux'

export default {
  components: {
    Group,
    Cell,
    Icon,
    InlineXNumber,
    Flexbox,
    FlexboxItem,
    XButton,
    Alert
  },
  data () {
    return {
      datas: [{
        id: 1,
        name: '统一冰红茶300ml',
        price: 3.5,
        count: 2,
        img: 'http://osv8eirwz.bkt.clouddn.com/3bc1c63e-ebd1-400b-83fe-8550b7a613da.png'
      },
      {
        id: 2,
        name: '油切麦仔茶300ml',
        price: 2.5,
        count: 1,
        img: 'http://osv8eirwz.bkt.clouddn.com/3bc1c63e-ebd1-400b-83fe-8550b7a613da.png'
      },
      {
        id: 3,
        name: '水溶C100-300ml',
        price: 5,
        count: 1,
        img: 'http://osv8eirwz.bkt.clouddn.com/3bc1c63e-ebd1-400b-83fe-8550b7a613da.png'
      }]
    }
  },
  computed: {
    onlyOneItem () {
      return true
    },
    onlyItem () {
      let item = {}
      if (this.datas && this.datas.length === 1) {
        item = this.datas[0]
      }
      return item
    },
    isOnly () {
      if (this.datas && this.datas.length === 1) {
        return true
      } else {
        return false
      }
    },
    totalPrice () {
      let total = 0
      if (this.datas) {
        for (let item in this.datas) {
          let data = this.datas[item]
          total += data.price * data.count
        }
      }
      return total
    }
  },
  methods: {
    canaelOnlyItem () {
      console.log(new Date())
      this.$vux.alert.show({
        title: 'Vux is Cool',
        content: 'Do you agree?',
        onShow () {
          console.log('Plugin: I\'m showing')
        },
        onHide () {
          console.log('Plugin: I\'m hiding')
        }
      })
    },
    removeItem () {
      console.log('remove')
    }
  }
}
</script>

<style>
.only-one-goods{
  margin:20px;
  border: 1px solid #999999;
  border-radius: 10px;
}
.goods-item{
  margin:10px;
  border: 1px solid #999999;
  border-radius: 10px;
}
.goods-header{
  text-align: center;
  position: relative;
  height: 40px;
  border-bottom:1px solid #999999;
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
  color: black !important;
  font-size: 30px !important;
}
.goods-content{
   padding: 10px;
}
.goods-content img{
  width: 100%;
}
.only-one-goods .goods-content p{
  margin:10px;
}
.only-one-goods .goods-content span{
  vertical-align: top;
  display: inline-block;
  width: 60px;
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
    color: #999999!important;
    padding: 0 1px!important;
    border: 1px solid #999999!important;
    height: 16px !important;
    width: 15px !important;
}
.total-price{
  margin:20px;
}
.goods-btns{
  padding:0 20px 20px 20px;
}
</style>
