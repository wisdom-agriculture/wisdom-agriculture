<template>
  <view class="card">
    <div class="left">
      <div class="title">{{ title }}</div>
      <div class="tags">
        <u-tag
          v-for="(item, index) in tagList"
          :key="index"
          :text="item"
          shape="circle"
          bgColor="#D2F2CA"
          color="#4BA234"
          borderColor="#fff"
        ></u-tag>
      </div>
    </div>
    <div class="right"><u-icon name="arrow-right" size="20"></u-icon></div>
  </view>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      required: true,
    },
    tagList: {
      type: Array,
      required: true,
    },
  },
};
</script>

<style scoped lang="scss">
.card {
  display: flex;
  box-shadow: 0 0 4px rgba(0, 0, 0, 0.2);
  height: 160rpx;
  width: 90%;
  margin: 0 auto;
  align-items: center;
  padding: 10rpx 60rpx;
  border-radius: 20rpx;
  margin-bottom: 28rpx;
  box-sizing: border-box;
  justify-content: space-between;
  .tags {
    display: flex;
    // justify-content: space-around;
  }
  .title {
    font-size: 18px;
    margin-bottom: 20rpx;
    font-weight: 600;
  }
  .card_right {
    flex: 1;
    margin-left: 56rpx;
  }
}
</style>
