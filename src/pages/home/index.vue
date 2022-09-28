<template>
  <div class="home">
    <div class="homeNav">
      <div class="farmTitle">{{ farm }}</div>
      <div class="farmDetails">
        <div class="title">
          <span>农场概况</span>
          <div class="farmMap" @click="toOtherPage('farmMap')">农场地图</div>
        </div>
        <div class="detailLists">
          <div
            class="list"
            v-for="(item, index) in farmDetailsLists"
            :key="index"
          >
            <div class="apellation">{{ item.apellation }}</div>
            <div class="content">{{ item.content }}</div>
          </div>
        </div>
      </div>
    </div>
    <div class="farmManagement">
      <!-- 将功能模块进行了抽离,封装为组件 -->
      <func-card
        title="农场管理"
        :list="farmManagementLists"
        class="funcCard"
        @pageTo="toOtherPage"
      ></func-card>
      <func-card
        title="农场经营"
        :list="farmManagementLists1"
        class="funcCard"
        @pageTo="toOtherPage"
      ></func-card>
      <func-card
        title="经营决策"
        :list="operatingDecisionLists"
        class="funcCard"
        @pageTo="toOtherPage"
      ></func-card>
    </div>
    <div class="homeBottom">
      <p>服务热线: xxx-xxxxx-xx</p>
      <p>天津xxxxx股份有限公司 版权所有</p>
    </div>
  </div>
</template>

<script>
import funcCard from "./funcCard.vue";
import { toOtherPage } from "../../utils/pageJump.js";
export default {
  components: {
    funcCard,
  },
  data: () => ({
    // 农场管理
    farmManagementLists: [
      {
        iconPath: require("../../static/icon/home/landParcdl.png"),
        text: "地块管理",
        pagePath: "landParcdl",
      },
      {
        iconPath: require("../../static/icon/home/agriculturalOperations.png"),
        text: "农事操作",
        pagePath: "agriculturalOperations",
      },
      {
        iconPath: require("../../static/icon/home/dailyCrops.png"),
        text: "每日作物",
        pagePath: "dailyCrops",
      },
    ],
    // 农场经营
    farmManagementLists1: [
      {
        iconPath: require("../../static/icon/home/mall.png"),
        text: "商城",
        pagePath: "mall",
      },
      {
        iconPath: require("../../static/icon/home/origins.png"),
        text: "溯源",
        pagePath: "origins",
      },
      {
        iconPath: require("../../static/icon/home/orderForm.png"),
        text: "订单",
        pagePath: "orderForm",
      },
    ],
    // 经营决策
    operatingDecisionLists: [
      {
        iconPath: require("../../static/icon/home/specialist.png"),
        text: "专家咨询",
        pagePath: "specialist",
      },
      {
        iconPath: require("../../static/icon/home/priceQuotations.png"),
        text: "价格行情",
        pagePath: "priceQuotations",
      },
      {
        iconPath: "",
        text: "",
        pagePath: "",
      },
    ],
    // 农场详情列表
    farmDetailsLists: [
      {
        apellation: "地块使用率",
        content: "95%",
      },
      {
        apellation: "地块总亩数",
        content: "64.2亩",
      },
      {
        apellation: "当前种养面积",
        content: "10亩",
      },
      {
        apellation: "种养品种",
        content: "4种",
      },
    ],
    farm: "",
  }),
  computed: {},
  methods: {
    toOtherPage,
    async getFarmName() {
      const { data } = await uni.$http.get("/api/users/details");
      if (data.code !== "00000") {
        return uni.$showMsg(data.message);
      }
      this.farm = data.data.farm;
    },
  },
  watch: {},

  // 页面周期函数--监听页面加载
  onLoad() {
    this.getFarmName();
  },
  // 页面周期函数--监听页面初次渲染完成
  onReady() {},
  // 页面周期函数--监听页面显示(not-nvue)
  onShow() {},
  // 页面周期函数--监听页面隐藏
  onHide() {},
  // 页面周期函数--监听页面卸载
  onUnload() {},
  // 页面处理函数--监听用户下拉动作
  // onPullDownRefresh() { uni.stopPullDownRefresh(); },
  // 页面处理函数--监听用户上拉触底
  // onReachBottom() {},
  // 页面处理函数--监听页面滚动(not-nvue)
  // onPageScroll(event) {},
  // 页面处理函数--用户点击右上角分享
  // onShareAppMessage(options) {},
};
</script>

<style scoped lang="scss">
.home {
  .homeNav {
    width: 100%;
    height: 300rpx;
    background: url(../../static/img/homeBg.png);
    background-repeat: no-repeat;
    margin-bottom: 20rpx;
    color: #fff;
    overflow: hidden;
    .farmTitle {
      display: flex;
      justify-content: center;
      height: 80rpx;
      font-size: 18px;
      line-height: 80rpx;
      // border-bottom: 1px solid #d2f2ca;
    }
    .farmDetails {
      width: 80%;
      margin: 10rpx auto 0;
      height: calc(100% - 100rpx);
      .title {
        font-size: 16px;
        font-weight: 600;
        height: 22%;
        display: flex;
        justify-content: space-between;
        .farmMap {
          background-color: #d2f2ca;
          color: #4ba234;
          padding: 14rpx 20rpx;
          display: flex;
          justify-content: center;
          align-items: center;
          transform: translateX(76rpx);
          border-radius: 20rpx 0rpx 0rpx 20rpx;
        }
      }
      .detailLists {
        display: flex;
        justify-content: space-between;
        font-size: 14px;
        align-items: center;
        height: 80%;
        .list {
          .content {
            font-size: 20px;
            margin-top: 12rpx;
          }
        }
      }
    }
  }
  .farmManagement {
    margin-top: 3vh;
  }
  .funcCard {
    margin-bottom: 50rpx;
  }
  .homeBottom {
    p {
      text-align: center;
      color: gray;
      font-weight: 500;
      letter-spacing: 1px;
    }
    padding-bottom: 140rpx;
  }
}
</style>
