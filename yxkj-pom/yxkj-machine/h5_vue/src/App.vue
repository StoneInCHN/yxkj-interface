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
  background-color: #F1F1F1;
  font-size:14px;
}
.weui-cells{
  margin-top: 0 !important;
  margin-bottom: 2px;
}
</style>
