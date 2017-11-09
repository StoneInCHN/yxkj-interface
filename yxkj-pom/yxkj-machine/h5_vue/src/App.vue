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
  background-color: #FFFFFF;
  font-size:14px;
}
.weui-cells{
  margin-top: 0 !important;
  margin-bottom: 2px;
}
.weui-dialog{
  border-radius: 20px !important;
}
.weui-dialog__btn_primary {
    color: #aac97b !important;
}
</style>
