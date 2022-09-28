<template>
  <view class="left-message">
    <view class="avator-wrapper">
      <view class="avator" @click="toHisCircle(userId)">
        <u-image shape="circle" :src="imgUrl" width="45" height="45"></u-image>
      </view>
    </view>
    <view class="bubble-wrapper">
      <div class="bubble">
        <rich-text :nodes="data"></rich-text>
      </div>
    </view>
  </view>
</template>

<script>
export default {
  name: "left-message",
  props: {
    data: String, //聊天内容
    imgUrl: String,
    userId: String,
  },
  methods: {
    toHisCircle(userId) {
      uni.navigateTo({
        url: `/pages/hisCircle/index?userId=` + userId,
      });
    },
  },
};
</script>

<style lang="scss" scoped="true">
.left-message {
  display: flex;
  padding-left: 20upx;
}
.avator-wrapper {
  flex: 0 0 auto;
}
.bubble-wrapper {
  padding-left: 30upx;
  flex: 1;
  padding-right: 135upx;
  .bubble {
    padding: 20upx;
    background: #8dfa69;
    border-radius: 15upx;
    position: relative;
    &::before {
      position: absolute;
      left: -20upx;
      top: 10upx;
      content: "";
      width: 0;
      height: 0;
      border-top: 20px solid transparent;
      border-right: 20px solid #8dfa69;
      border-bottom: 20px solid transparent;
    }
  }
}
.avator {
  width: 100upx;
  height: 100upx;
  border-radius: 50%;
  overflow: hiideen;
  background: #f1f1f1;
  .img {
    width: 100%;
    height: 100%;
    border-radius: 50%;
  }
}
</style>