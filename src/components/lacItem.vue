<template>
  <div class="lac-item">
    <div class="top">
      <u-image
        @click="toHisCircle(lacitem.userId)"
        shape="circle"
        :src="lacitem.imgUrl"
        width="45"
        height="45"
      ></u-image>
      <div class="baseInfo">
        <span>{{ lacitem.nickname }}</span>
        <span>{{ lacitem.time }}</span>
      </div>
    </div>
    <slot></slot>
    <div class="bottom" @click="toContentDetail(lacitem)">
      <u-image
        shape="square"
        :src="lacitem.textImg"
        width="78"
        height="78"
      ></u-image>
      <div class="text">
        <div class="aite">{{ lacitem.textName }}</div>
        <div class="detail">{{ lacitem.content }}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    lacitem: Object,
  },
  methods: {
    toHisCircle(userId) {
      uni.navigateTo({
        url: `/pages/hisCircle/index?userId=` + userId,
      });
    },
    toContentDetail(item) {
      let itemstr = JSON.stringify(item);
      uni.navigateTo({
        url:
          `/pages/contentDetail/index?recoItem=` + encodeURIComponent(itemstr),
      });
    },
  },
};
</script>

<style scoped lang="scss">
.lac-item {
  padding: 20rpx 0rpx 30rpx 30rpx;
  border-bottom: 20rpx solid #f5f5f9;
  .top {
    display: flex;
    padding-bottom: 20rpx;
  }
  .baseInfo,
  .text {
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    margin-left: 20rpx;
    font-size: 28rpx;
    span:last-child {
      color: rgb(119, 120, 120);
      font-size: 24rpx;
    }
  }
  .bottom {
    background-color: #f5f5f9;
    padding: 10rpx 30rpx 10rpx 0;
    margin-top: 10rpx;
    display: flex;
  }
  .aite {
    font-weight: 700;
    font-size: 30rpx;
  }
  .detail {
    color: rgb(94, 94, 94);
    font-size: 26rpx;
    overflow: hidden;
    overflow-wrap: break-word;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
  }
}
</style>