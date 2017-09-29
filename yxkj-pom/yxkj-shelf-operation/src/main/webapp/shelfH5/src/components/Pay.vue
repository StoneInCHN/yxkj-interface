<template>
  <div>
    <group title="商品信息">
      <cell title="纳宝帝威化饼香草奶油58g" inlineDesc="数量：2" value="￥3.50" >
         <img slot="icon" width="50" style="display:block;margin-right:5px;" src="http://osv8eirwz.bkt.clouddn.com/3bc1c63e-ebd1-400b-83fe-8550b7a613da.png">
      </cell>
      <cell title="纳宝帝威化饼香草奶油58g" inlineDesc="数量：1" value="￥3.50" >
        <img slot="icon" width="50" style="display:block;margin-right:5px;" src="http://osv8eirwz.bkt.clouddn.com/3bc1c63e-ebd1-400b-83fe-8550b7a613da.png">
      </cell>
       <cell title="纳宝帝威化饼香草奶油58g" inlineDesc="数量：1" value="￥3.50" >
        <img slot="icon" width="50" style="display:block;margin-right:5px;" src="http://osv8eirwz.bkt.clouddn.com/3bc1c63e-ebd1-400b-83fe-8550b7a613da.png">
      </cell>
       <cell>
          <template solt="value">
            共 <span class="color-red">4</span> 件商品，合计： <span class="color-red">￥14.00</span>
          </template>
      </cell>
    </group>
    <group title="订单信息">
      <cell title="订单金额" value="￥14.00" ></cell>
      <cell title="实付金额" value="￥14.00" >
        <template solt="value">
            <span class="color-red">￥14.00</span>
        </template>
      </cell>
    </group>
    <div class="btns">
      <x-button type="primary" link="pay4wx">微信支付</x-button>
    </div>
  </div>
</template>

<script>
import { Group, Cell, XButton, Flexbox, FlexboxItem } from 'vux'

export default {
  components: {
    Group,
    Cell,
    XButton,
    Flexbox,
    FlexboxItem
  },
  data () {
    return {
      msg: 'Hello World!'
    }
  }
}
</script>

<style scoped>
.color-red{
  color:red;
}
.btns{
  padding:30px 10px;
}
</style>
