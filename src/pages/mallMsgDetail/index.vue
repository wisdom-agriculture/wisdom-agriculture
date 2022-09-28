<template>
  <div class="mallMsg-detail">
    <div class="item" v-for="(item, index) in msgList" :key="index">
      <div class="top">
        <div>{{ item.mallName }}</div>
        <div>{{ item.info }}</div>
      </div>
      <div class="bottom">
        <u-image
          shape="square"
          :src="item.textImg"
          width="78"
          height="78"
        ></u-image>
        <div class="detail">{{ item.textContent }}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data: () => ({
    msgList: [
      {
        mallName: "xxxxx店",
        info: "包裹已到站,取件码123456",
        textImg:
          "https://img2.baidu.com/it/u=2153119163,1100522879&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=480",
        textContent: "您购买的大西瓜已到达天津市西青区天津理工大学",
      },
      {
        mallName: "xxxxx店",
        info: "您的订单已发货",
        textImg:
          "https://img2.baidu.com/it/u=2153119163,1100522879&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=480",
        textContent: "您购买的大西瓜已发货",
      },
      {
        mallName: "xxx店",
        info: "交易退款成功",
        textImg:
          "https://img2.baidu.com/it/u=2153119163,1100522879&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=480",
        textContent: "您购买的大西瓜退款成功",
      },
    ],
  }),
  methods: {},
  onShow() {
    uni.setStorageSync("mall", 0);
  },
};
</script>

<style scoped lang="scss">
.mallMsg-detail {
  .item {
    padding: 20rpx 0rpx 30rpx 30rpx;
    border-bottom: 20rpx solid #f5f5f9;
    font-size: 30rpx;
  }
  .top {
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    div {
      margin-bottom: 20rpx;
    }
    div:first-child {
      font-size: 28rpx;
    }
  }
  .bottom {
    background-color: #f5f5f9;
    padding: 10rpx 30rpx 10rpx 0;
    display: flex;
    align-items: center;
    font-size: 26rpx;
    color: #666666;
    .detail {
      margin-left: 25rpx;
    }
  }
}
</style>