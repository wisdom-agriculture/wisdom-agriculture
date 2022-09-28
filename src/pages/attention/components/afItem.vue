<template>
  <div class="gf-item">
    <div class="img">
      <u-image
        :src="afitem.imgUrl"
        shape="circle"
        width="50"
        height="50"
        @click="toHisCircle(afitem.id)"
      ></u-image>
    </div>
    <div class="info">
      <div class="twrap">
        <span class="name">{{
          afitem.nickname ? afitem.nickname : "emmm"
        }}</span>
        <span class="name">V0</span>
      </div>
      <div class="dwrap">
        {{ afitem.signature ? afitem.signature : "走过路过,点个关注不迷路" }}
      </div>
    </div>
    <div class="btn">
      <slot v-if="!afitem.isMutual"></slot>
      <u-button
        color="#2faf48"
        class="btn"
        size="small"
        @click="cancelAtten(afitem.id)"
        v-else
        >互相关注</u-button
      >
    </div>
  </div>
</template>

<script>
export default {
  props: {
    afitem: {},
  },
  methods: {
    toHisCircle(userId) {
      uni.navigateTo({
        url: `/pages/hisCircle/index?userId=` + userId,
      });
    },
    async cancelAtten(userId) {
      this.$emit("cancelAtten", userId);
    },
  },
};
</script>

<style scoped lang="scss">
.gf-item {
  background-color: #fff;
  margin: 20rpx 30rpx;
  display: flex;
  align-items: center;
  padding: 35rpx 30rpx;
  border-radius: 14rpx;
  .img {
    flex: 1;
  }
  .info {
    flex: 3;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    .twrap {
      display: flex;
      align-items: center;
      span:first-child {
        font-weight: 700;
        margin-right: 25rpx;
      }
      span:last-child {
        background-color: #af53de;
        color: #fff;
        padding: 5rpx 20rpx;
        font-size: 12rpx;
        border-radius: 20rpx;
      }
    }
    .dwrap {
      margin-top: 25rpx;
      color: #737475;
      font-size: 24rpx;
    }
  }
  .btn {
    flex: 1;
    font-size: 16rpx;
  }
}
</style>