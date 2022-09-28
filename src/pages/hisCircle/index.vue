<template>
  <div class="circle">
    <div class="bg">
      <div class="top">
        <u-icon
          name="arrow-left"
          color="#fff"
          size="20"
          @click="toPreviousPage(1)"
        ></u-icon>
        <u-icon name="chat" color="#fff" size="30" @click="goChat()"></u-icon>
      </div>
      <div class="middle">
        <u-image :src="info.imgUrl" shape="circle" width="80" height="80">
        </u-image>
        <div class="right">
          <div class="twrap">
            <span>{{ info.nickname ? info.nickname : "我就是我" }}</span>
            <span>V6</span>
          </div>
          <div class="dwrap">
            {{ info.signature ? info.signature : "走过路过,点个关注不迷路" }}
          </div>
        </div>
      </div>
      <div class="myinfo">
        <div class="item">
          <span>{{ info.subscribeNum }}</span>
          <span @click="toOtherPWithPram('attention', 'userId', info.id)"
            >关注</span
          >
        </div>
        <div class="item">
          <span>{{ info.fanNum }}</span>
          <span @click="toOtherPage('follow')">粉丝</span>
        </div>
        <div class="item">
          <span>{{ info.getLikeNum }}</span>
          <span>获赞</span>
        </div>
      </div>
    </div>
    <div class="content">
      <reco-item
        id="item"
        @updateShow="updateShow"
        @flushReco="flushReco"
        v-for="(item, index) in recoList"
        :key="index"
        :recoitem="item"
      ></reco-item>
    </div>
  </div>
</template>

<script>
import {
  toPreviousPage,
  toOtherPWithPram,
  toOtherPage,
} from "../../utils/pageJump";
import recoItem from "../planting/components/recoItem.vue";
export default {
  components: { recoItem },
  data: () => ({
    recoList: [],
    info: {},
    id: 0,
  }),
  async onLoad(options) {
    this.id = options.userId;
    uni.setStorageSync("circleId", options.userId);
    await this.getRecoList("$http");
    await this.getInfo("$http");
    //  options里有userId可以通过接口/users/details获取信息
  },
  async onShow() {
    this.id = await uni.getStorageSync("circleId");
    console.log(this.id);
    await this.getRecoList("$http");
    await this.getInfo("$http");
  },
  methods: {
    toOtherPage,
    toPreviousPage,
    toOtherPWithPram,
    goChat() {
      uni.navigateTo({
        url:
          "../../pages/detailMsg/index?title=" +
          `${this.info.nickname}` +
          "&&imgUrl=" +
          `${this.info.imgUrl}` +
          "&&userId=" +
          `${this.info.id}`,
      });
      console.log(this.info);
    },
    // 获取他人信息接口的列表出来换接口
    async getRecoList(prop) {
      let {
        data: {
          data: { zoneList },
        },
      } = await uni[prop].get("/api/zones?userId=" + this.id);
      this.recoList = zoneList;
    },
    async flushReco(isNoLoad) {
      isNoLoad = isNoLoad == true ? "$NoLoadhttp" : "$http";
      await this.getRecoList(isNoLoad);
      await this.getInfo(isNoLoad);
    },
    async getInfo(prop) {
      let {
        data: { data },
      } = await uni[prop].get("/api/users/details?id=" + this.id);
      this.info = data;
    },
  },
};
</script>

<style scoped lang="scss">
.bg {
  height: 360rpx;
  background-color: #2faf48;
  border-radius: 0rpx 0rpx 50rpx 50rpx;
  padding: 20rpx;
  .top {
    display: flex;
    justify-content: space-between;
  }
  .middle {
    display: flex;
    margin: 30rpx;
    .right {
      margin-left: 30rpx;
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      color: #fff;
      .twrap {
        display: flex;
        align-items: center;
        span:first-child {
          font-size: 50rpx;
          font-weight: 700;
          margin-right: 25rpx;
        }
        span:last-child {
          background-color: #af53de;
          padding: 5rpx 25rpx;
          font-size: 16rpx;
          border-radius: 20rpx;
        }
      }
    }
  }
  .myinfo {
    display: flex;
    justify-content: space-evenly;
    align-items: center;
    color: #fff;
    font-size: 24rpx;
    .item {
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      align-items: center;
    }
  }
}
.recon-list {
  border-bottom: 10rpx solid #f3f5f4;
}
</style>