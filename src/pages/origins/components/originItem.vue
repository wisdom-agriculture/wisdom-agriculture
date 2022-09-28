<template>
  <div class="originitem">
    <div class="main">
      <u--image
        class="img"
        src="https://img0.baidu.com/it/u=3814642046,670391970&fm=253&app=138&size=w931&n=0&f=JPEG&fmt=auto?sec=1661446800&t=281915c3b1813f51a4bd61a3852ac327"
        width="180rpx"
        height="180rpx"
        shape="square"
        radius="10rpx"
      ></u--image>
      <div class="text">
        <div class="item">
          <span>农产品名称:</span>
          <span>一号青菜</span>
        </div>
        <div class="item">
          <span>生产公司:</span>
          <span>xxxxxxx有限公司</span>
        </div>
        <div class="item">
          <span>开具日期:</span>
          <span>2021.06.09</span>
        </div>
        <div class="item">
          <span>溯源编码:</span>
          <span>NMR8001001-800010100</span>
        </div>
      </div>
    </div>
    <div class="icon">
      <u-icon
        space="10rpx"
        class="iconItem"
        name="share"
        color="#22c85e"
        size="28"
        @click="popShare"
        label="分享"
      ></u-icon>
      <u-icon
        class="iconItem"
        name="trash"
        space="10rpx"
        color="#4cb0e3"
        size="28"
        label="删除"
        @click="deleteOrigin(index)"
      ></u-icon>
    </div>
    <div class="icon-bottom">
      <u-icon
        space="10rpx"
        class="iconItem"
        :name="require('../../../static/img/print.png')"
        color="#22c85e"
        size="28"
        @click="printOrigin"
        label="打印溯源结果"
      ></u-icon>
      <u-icon
        space="10rpx"
        class="iconItem"
        :name="require('../../../static/img/result.png')"
        color="#2979ff"
        size="28"
        label="查看溯源结果"
        @click="toOtherPWithPram('originsDetail', 'id', '6961251753807319040')"
      ></u-icon>
    </div>
    <u-popup :show="show" @close="close" height="800" mode="bottom">
      <div class="share-wrap">
        <div class="title">
          <u-icon class="icon" size="24" name="share"></u-icon>
          <text>分享</text>
        </div>
        <div class="share-list">
          <div>
            <u-image
              :src="require('../../../static/icon/gplant.png')"
              width="40"
              height="40"
            ></u-image>
            <span>我的圈子</span>
          </div>
          <div>
            <u-image
              :src="require('../../../static/icon/wx.svg')"
              width="40"
              height="40"
            ></u-image>
            <span>微信</span>
          </div>
          <div>
            <u-image
              :src="require('../../../static/icon/QQ.svg')"
              width="40"
              height="40"
            ></u-image>
            <span>QQ</span>
          </div>
          <div>
            <u-image
              :src="require('../../../static/icon/wb.svg')"
              width="40"
              height="40"
            ></u-image>
            <span>微博</span>
          </div>
        </div>
      </div>
    </u-popup>
  </div>
</template>

<script>
import { toOtherPWithPram } from "../../../utils/pageJump";
export default {
  props: {
    index: Number,
  },
  data: () => ({
    show: false,
  }),
  methods: {
    toOtherPWithPram,
    printOrigin() {
      console.log("打印溯源结果");
      window.print();
    },
    close() {
      this.show = false;
    },
    popShare() {
      this.show = true;
    },
    deleteOrigin(index) {
      this.$emit("deleteOrigin", index);
    },
  },
};
</script>

<style lang="scss" scoped>
.originitem {
  box-shadow: 0 0 10rpx 0 #eae8e8;
  margin: 30rpx 30rpx;
  border-radius: 30rpx;
  padding: 30rpx 0rpx;

  .icon-bottom {
    display: flex;
    padding-top: 10rpx;
    justify-content: space-around;
  }
  .icon {
    display: flex;
    justify-content: space-around;
    padding-top: 20rpx;
    .iconItem {
      padding-right: 120rpx;
    }
  }
  .main {
    display: flex;
    justify-content: center;
    .img {
      padding-right: 10rpx;
    }
    .text {
      margin: 5rpx 0 0 0;
      padding-left: 30rpx;
      font-size: 22rpx;
      color: #6f6962;

      .item:first-child {
        color: #000;
        font-size: 28rpx;
      }
      .item {
        padding: 5rpx 0;
        span:last-child {
          padding-left: 10rpx;
        }
      }
    }
  }
}
.share-wrap {
  height: 250rpx;
  width: 100%;
  display: flex;
  flex-direction: column;
  .title {
    display: flex;
    align-items: center;
    font-size: 32rpx;
    padding: 10rpx 40rpx 30rpx;
    text {
      display: block;
      padding: 20rpx 0 0 10rpx;
    }
  }
  .share-list {
    display: flex;
    justify-content: space-evenly;
    div {
      margin: 0 20rpx;
      display: flex;
      flex-direction: column;
      justify-content: space-evenly;
      align-items: center;
    }
  }
}
</style>