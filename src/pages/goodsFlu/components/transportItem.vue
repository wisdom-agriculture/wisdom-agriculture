<template>
  <div class="transport-item">
    <u-line
      class="line"
      length="60rpx"
      direction="col"
      color="#999999"
    ></u-line>
    <div class="transport" v-if="isCurrent">
      <u--image
        :src="require('../../../static/icon/transimit.svg')"
        class="trans"
        width="20"
        shape="circle"
        mode="widthFix"
      ></u--image>
      <div class="currentState">
        <span>运输中</span>
        <span>早上 05:12</span>
        <div class="normal">快件离开广州已发往天津中转部</div>
      </div>
    </div>

    <div class="transport" v-else>
      <u--image
        :src="require('../../../static/icon/transport-grey.svg')"
        class="trans-grey"
        width="20"
        shape="circle"
        mode="widthFix"
      ></u--image>
      <div class="cur-grey">
        <span>已揽件</span>
        <span>昨天 20:26</span>
        <div>
          荔枝湾和平路的张晶晶[<span class="currentState">13834204795</span
          >]已揽收
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["isCurrent"],
};
</script>

<style scoped lang="scss">
.trans,
.trans-grey {
  margin-top: -20rpx;
  background-color: #f5791f;
  height: 50rpx;
  width: 50rpx;
  border-radius: 50%;
  padding: 10rpx 0 0 15rpx;
  div {
    padding: 0 10rpx;
  }
}
.trans-grey {
  background-color: #fff;
  border: 1px solid #c6c6c6;
}
.line {
  width: 20rpx;
  margin: -8rpx 30rpx 0 50rpx !important;
}
.transport {
  display: flex;
  align-items: center;
  padding: 5rpx 20rpx 0;
  font-size: 28rpx;
}
.currentState {
  font-weight: 700;
  color: #f5791f;
  span {
    margin-left: 10rpx;
  }
}
.normal {
  color: #000;
  font-weight: 500;
  margin-left: 10rpx;
}
.cur-grey {
  color: #9b9b9b;
  margin-left: 15rpx;
}
</style>