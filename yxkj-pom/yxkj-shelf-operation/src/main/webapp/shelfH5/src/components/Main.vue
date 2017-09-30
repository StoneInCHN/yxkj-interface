<template>
  <div>
    <div class="only-one-goods" v-if="isOnly">
        <div class="goods-header">
         <h5>{{onlyItem.gName}}</h5>
         <icon type="cancel" class="goods-header-icon" @click.native="canaelOnlyItem"></icon>
        </div>
        <div class="goods-content">
          <img :src="onlyItem.gImg" :alt="onlyItem.gName" />
          <group>
            <p><strong> ￥ {{onlyItem.gPrice}}</strong></p>
            <p>
              <span>数量:</span><inline-x-number v-model="onlyItem.gCount" :min="0" button-style="round"></inline-x-number>
            </p>   
          </group>
        </div>
    </div>
    <div class="goods-list" v-else>
      <flexbox :gutter="0" wrap="wrap">
        <flexbox-item :span="1/2" v-for="dataItem in datas">
          <div class="goods-item">
            <div class="goods-header">
             <h5>{{dataItem.gName}}</h5>
             <icon type="cancel" class="goods-header-icon" @click.native="removeItem"></icon>
            </div>
            <div class="goods-content">
              <img :src=dataItem.gImg :alt=dataItem.gName />
              <group>
                <p><strong> ￥ {{dataItem.gPrice}}</strong></p>
                <p>
                  <span>数量:</span><inline-x-number v-model="dataItem.gCount" :min="0" button-style="round"></inline-x-number>
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
          <x-button type="default" @click.native="scan">继续扫</x-button>
        </flexbox-item>
        <flexbox-item>
          <x-button type="default" link="pay">结算</x-button>
        </flexbox-item>
      </flexbox>
    </div>
  </div>
</template>

<script>
import { Group, Cell, Icon, InlineXNumber, Flexbox, FlexboxItem, XButton } from 'vux'
import { mapActions } from 'vuex'

export default {
  components: {
    Group,
    Cell,
    Icon,
    InlineXNumber,
    Flexbox,
    FlexboxItem,
    XButton
  },
  data () {
    return {
      datas: []
    }
  },
  mounted () {
    console.log(this.$store.getters.getWxConfig)
    this.$wechat.config({
      debug: false,
      appId: 'wx3598eb401cb80f00',
      timestamp: 1506690795,
      nonceStr: 'H0pUJ8NF3yUTIuuR',
      signature: '2fcc888479538bc8065edea20d600b4a26ae2582',
      jsApiList: [
        'checkJsApi',
        'scanQRCode',
        'chooseWXPay'
      ]
    })
    this.$wechat.ready(function () {
      console.log('wx loading success')
    })
    this.$wechat.error(function (res) {
      // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
      console.log('wx loading error')
      console.log(res)
    })
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
          total += data.gPrice * data.gCount
        }
      }
      return total
    }
  },
  methods: {
    canaelOnlyItem () {
      this.$vux.confirm.show({
        title: '移除商品',
        content: '确定要从购物栏中移除该商品吗？',
        onCancel () {
          console.log('plugin cancel')
        },
        onConfirm () {
          console.log('plugin confirm')
        }
      })
    },
    removeItem () {
      this.$vux.confirm.show({
        title: '移除商品',
        content: '确定要从购物栏中移除该商品吗？',
        onCancel () {
          console.log('plugin cancel')
        },
        onConfirm () {
          console.log('plugin confirm')
        }
      })
    },
    scan () {
      console.log(this.$wechat)
      this.$wechat.scanQRCode({
        needResult: 1,
        desc: 'scanQRCode desc',
        success: function (res) {
          console.log(JSON.stringify(res))
        }
      })
    },
    ...mapActions({
      getGoodItems: 'setGoodItems',
      getGoodsBySn: 'getGoodsBySn'
    })
  }
}
</script>

<style scoped>
.only-one-goods{
  margin:20px;
  border: 1px solid #999999;
  border-radius: 10px;
}
.goods-item{
  margin:10px;
  border: 1px solid #999999;
  border-radius: 10px;
  box-shadow: 0 0 5px #b1b2b3;
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
