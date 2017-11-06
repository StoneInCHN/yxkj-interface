<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style lang="less">
@import '~vux/src/styles/reset.less';

body {
  background-color: #fbf9fe;
}
#app{
	position: relative;
}
.vux-number-round .vux-number-selector-sub, .vux-number-round .vux-number-selector-plus {
  border: 1px solid #ffb609 !important;
}
.vux-number-selector svg{
  fill:#ffb609 !important;
}
.vux-number-round .vux-number-selector.vux-number-disabled {
    border-color: #ececec !important;
}
.vux-number-selector.vux-number-disabled svg {
    fill: #ccc !important;
}
.weui-cells:before{
  border-top: none !important;
}
.weui-cells:after{
  border-bottom: none !important;
}
</style>
