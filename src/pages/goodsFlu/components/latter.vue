<template>
  <div class="latter">
    <u-line
      class="line"
      length="60rpx"
      direction="col"
      color="#999999"
    ></u-line>
    <view class="dot">
      <u--image
        :src="require('../../../static/icon/dot.svg')"
        class="dotimg"
        width="30"
        shape="circle"
        mode="widthFix"
      ></u--image>
      <div class="latter">
        <span>早上 05:12</span>
        <div class="normal">快件已到达广州中心</div>
      </div>
    </view>
  </div>
</template>

<script>
export default {};
</script>

<style scoped lang="scss">
.line {
  width: 20rpx;
  margin: -15rpx 30rpx 0 50rpx !important;
}
.dot {
  margin-top: -20rpx;
  margin-left: 5rpx;
  display: flex;
  font-size: 28rpx;
  .dotimg {
    margin-left: 22rpx;
    height: 50rpx;
    width: 50rpx;
  }
  span,
  div {
    color: #9b9b9b;
    margin-left: 15rpx;
  }
}
</style>