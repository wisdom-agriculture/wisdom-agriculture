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
      </div>
      <div class="middle">
        <u-image :src="info.imgUrl" shape="circle" width="80" height="80">
        </u-image>
        <div class="right">
          <div class="twrap">
            <span class="name">{{
              info.nickname ? info.nickname : "我就是我"
            }}</span>
            <span class="name">V6</span>
          </div>
          <div class="dwrap">
            {{ info.signature ? info.signature : "走过路过,点个关注不迷路" }}
          </div>
        </div>
      </div>
      <div class="myinfo">
        <div class="item">
          <span>{{ info.subscribeNum }}</span>
          <span @click="toOtherPage('attention', info.id)">关注</span>
        </div>
        <div class="item">
          <span>{{ info.fanNum }}</span>
          <span @click="toOtherPage('follow', info.id)">粉丝</span>
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
      >
        <div class="del">
          <u-icon
            name="trash-fill"
            color="#2faf48"
            size="20"
            @click="delMyCon(item.id)"
          ></u-icon>
        </div>
      </reco-item>
    </div>
  </div>
</template>

<script>
import { toPreviousPage } from "../../utils/pageJump";
import recoItem from "../planting/components/recoItem.vue";
export default {
  components: { recoItem },
  data: () => ({
    recoList: [],
    info: {},
    id: 0,
  }),
  async onShow() {
    let {
      data: {
        data: { id },
      },
    } = await uni.$http.get("/api/users/details");
    this.id = id;
    await this.getRecoList("$http");
    await this.getInfo("$http");
  },
  methods: {
    toPreviousPage,
    toOtherPage(page, id) {
      uni.navigateTo({
        url: `/pages/${page}/index?userId=` + id,
      });
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
      } = await uni[prop].get("/api/users/details");
      this.info = data;
    },
    async delMyCon(id) {
      uni.showModal({
        title: "提示",
        content: "是否确认删除该条内容",
        success: async (res) => {
          if (res.confirm) {
            // 删除自己发的内容
            let {
              data: { code },
            } = await uni.$NoLoadhttp.delete("/api/zones?id=" + id);
            if (code == "00000") {
              this.flushReco();
            }
          } else if (res.cancel) {
            // 用户取消
          }
        },
      });
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
.del {
  float: right;
  margin-top: -10rpx;
  margin-right: 20rpx;
}
</style>