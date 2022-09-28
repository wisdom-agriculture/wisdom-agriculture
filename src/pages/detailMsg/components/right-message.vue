<template>
  <view class="right-message">
    <view class="bubble-wrapper">
      <div class="bubble">
        <rich-text :nodes="data"></rich-text>
      </div>
    </view>
    <view class="avator-wrapper">
      <view class="avator" @click="toMyCircle">
        <u-image shape="circle" :src="imgUrl" width="45" height="45"></u-image>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  name: "right-message",
  props: {
    data: String, //聊天内容
    imgUrl: "",
  },
  methods: {
    toMyCircle() {
      uni.navigateTo({
        url: `/pages/myCircle/index`,
      });
    },
  },
};
</script>

<style lang="scss" scoped="true">
.img {
  width: 200rpx;
  height: 150rpx;
  margin: 0 auto;
}
.right-message {
  display: flex;
  padding-right: 20upx;
}
.avator-wrapper {
  flex: 0 0 auto;
}
.bubble-wrapper {
  padding-left: 135upx;
  flex: 1;
  padding-right: 30upx;
  .bubble {
    padding: 20upx;
    background: #8dfa69;
    border-radius: 15upx;
    position: relative;
    &::before {
      position: absolute;
      right: -20upx;
      top: 10upx;
      content: "";
      width: 0;
      height: 0;
      border-top: 20px solid transparent;
      border-left: 20px solid #8dfa69;
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
}
</style>