<template>
  <div>
    <group title="商品信息">
      <cell title="纳宝帝威化饼香草奶油58g" inlineDesc="数量：2" value="￥3.50" >
         <img slot="icon" width="50" style="display:block;margin-right:5px;" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC4AAAAuCAMAAABgZ9sFAAAAVFBMVEXx8fHMzMzr6+vn5+fv7+/t7e3d3d2+vr7W1tbHx8eysrKdnZ3p6enk5OTR0dG7u7u3t7ejo6PY2Njh4eHf39/T09PExMSvr6+goKCqqqqnp6e4uLgcLY/OAAAAnklEQVRIx+3RSRLDIAxE0QYhAbGZPNu5/z0zrXHiqiz5W72FqhqtVuuXAl3iOV7iPV/iSsAqZa9BS7YOmMXnNNX4TWGxRMn3R6SxRNgy0bzXOW8EBO8SAClsPdB3psqlvG+Lw7ONXg/pTld52BjgSSkA3PV2OOemjIDcZQWgVvONw60q7sIpR38EnHPSMDQ4MjDjLPozhAkGrVbr/z0ANjAF4AcbXmYAAAAASUVORK5CYII=">
      </cell>
      <cell title="纳宝帝威化饼香草奶油58g" inlineDesc="数量：1" value="￥3.50" >
        <img slot="icon" width="50" style="display:block;margin-right:5px;" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC4AAAAuCAMAAABgZ9sFAAAAVFBMVEXx8fHMzMzr6+vn5+fv7+/t7e3d3d2+vr7W1tbHx8eysrKdnZ3p6enk5OTR0dG7u7u3t7ejo6PY2Njh4eHf39/T09PExMSvr6+goKCqqqqnp6e4uLgcLY/OAAAAnklEQVRIx+3RSRLDIAxE0QYhAbGZPNu5/z0zrXHiqiz5W72FqhqtVuuXAl3iOV7iPV/iSsAqZa9BS7YOmMXnNNX4TWGxRMn3R6SxRNgy0bzXOW8EBO8SAClsPdB3psqlvG+Lw7ONXg/pTld52BjgSSkA3PV2OOemjIDcZQWgVvONw60q7sIpR38EnHPSMDQ4MjDjLPozhAkGrVbr/z0ANjAF4AcbXmYAAAAASUVORK5CYII=">
      </cell>
       <cell title="纳宝帝威化饼香草奶油58g" inlineDesc="数量：1" value="￥3.50" >
        <img slot="icon" width="50" style="display:block;margin-right:5px;" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC4AAAAuCAMAAABgZ9sFAAAAVFBMVEXx8fHMzMzr6+vn5+fv7+/t7e3d3d2+vr7W1tbHx8eysrKdnZ3p6enk5OTR0dG7u7u3t7ejo6PY2Njh4eHf39/T09PExMSvr6+goKCqqqqnp6e4uLgcLY/OAAAAnklEQVRIx+3RSRLDIAxE0QYhAbGZPNu5/z0zrXHiqiz5W72FqhqtVuuXAl3iOV7iPV/iSsAqZa9BS7YOmMXnNNX4TWGxRMn3R6SxRNgy0bzXOW8EBO8SAClsPdB3psqlvG+Lw7ONXg/pTld52BjgSSkA3PV2OOemjIDcZQWgVvONw60q7sIpR38EnHPSMDQ4MjDjLPozhAkGrVbr/z0ANjAF4AcbXmYAAAAASUVORK5CYII=">
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
      <x-button type="primary" link="pay">微信支付</x-button>
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

<style>
.color-red{
  color:red;
}
.btns{
  padding:30px 10px;
}
</style>
