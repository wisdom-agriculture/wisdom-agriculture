<template>
  <div class="orderForm">
    <div class="mosttop">
      <u-icon
        class="return"
        name="arrow-left"
        color="#000000"
        size="20"
        @click="toPreviousPage(1)"
      ></u-icon>
      <u-search
        borderColor="#848a95"
        class="search"
        :show-action="false"
        placeholder="搜索"
        v-model="keyword"
        height="30"
        shape="round"
        bg-color="#D9DBD9"
        input-align="center"
      ></u-search>
    </div>
    <div class="wrap">
      <div class="tab">
        <u-tabs
          lineWidth="50"
          lineColor="#4ba234"
          itemStyle="padding:10rpx 80rpx; height: 35px; font-weight:700;color:#4ba234;"
          :activeStyle="{ color: '#4ba234' }"
          ref="tabs"
          :list="list"
          :current="current"
          @change="change"
          :is-scroll="false"
        ></u-tabs>
      </div>
      <swiper
        class="swiper-box"
        :current="swiperCurrent"
        @animationfinish="animationfinish"
      >
        <swiper-item class="swiper-item">
          <scroll-view scroll-y style="height: 100%; width: 100%" v-if="1">
            <order-item
              v-for="(item, index) in allOrderList"
              :key="index"
              :index="index"
              @confirmGoods="confirmGoods"
              :itemObj="item"
            >
            </order-item>
          </scroll-view>
          <scroll-view scroll-y style="height: 100%; width: 100%" v-else>
            <view class="page-box">
              <view>
                <view class="centre">
                  <view class="explain">
                    您还没有相关的订单
                    <view class="tips">可以去看看有那些想买的</view>
                  </view>
                  <view class="btn" @click="toFirstPage">随便逛逛</view>
                </view>
              </view>
            </view>
          </scroll-view>
        </swiper-item>
        <swiper-item class="swiper-item">
          <scroll-view scroll-y style="height: 100%; width: 100%" v-if="1">
            <!--  v-if="unDeliverList.length" -->
            <view class="undeliver">
              <order-item
                v-for="(item, index) in unDeliverList"
                :key="index"
                :index="index"
                @confirmGoods="confirmGoods"
                :isundeliver="true"
                :itemObj="item"
              >
                <u-button
                  type="primary"
                  size="mini"
                  color="white"
                  class="returnMoney"
                  @click.native.stop="toReturn(index, item.innerId)"
                  >退款</u-button
                >
              </order-item>
            </view>
          </scroll-view>
          <scroll-view scroll-y style="height: 100%; width: 100%" v-else>
            <view class="page-box">
              <view>
                <view class="centre">
                  <view class="explain">
                    您还没有相关的订单
                    <view class="tips">可以去看看有那些想买的</view>
                  </view>
                  <view class="btn" @click="toFirstPage">随便逛逛</view>
                </view>
              </view>
            </view>
          </scroll-view>
        </swiper-item>
        <swiper-item class="swiper-item">
          <scroll-view scroll-y style="height: 100%; width: 100%" v-if="1">
            <!-- unReceiptList.length -->
            <view class="receive">
              <order-item
                v-for="(item, index) in unReceiptList"
                :key="index"
                :index="index"
                :itemObj="item"
                @confirmGoods="confirmGoods"
              >
              </order-item>
            </view>
          </scroll-view>
          <scroll-view scroll-y style="height: 100%; width: 100%" v-else>
            <view class="page-box">
              <view>
                <view class="centre">
                  <view class="explain">
                    您还没有相关的订单
                    <view class="tips">可以去看看有那些想买的</view>
                  </view>
                  <view class="btn" @click="toFirstPage">随便逛逛</view>
                </view>
              </view>
            </view>
          </scroll-view>
        </swiper-item>
      </swiper>
    </div>
    <u-modal
      title="申请退款"
      :show="show"
      @confirm="confirm"
      @cancel="cancel"
      ref="uModal"
      :showCancelButton="true"
    >
      <div class="returnCon">
        <u--textarea
          style="width: 500rpx"
          placeholder="请填写退款理由"
          confirmType="done"
          height="100"
          v-model="value2"
          count
          maxlength="100"
        ></u--textarea>
      </div>
    </u-modal>
  </div>
</template>

<script>
import { toPreviousPage } from "../../utils/pageJump";
import orderItem from "../../components/orderItem.vue";
export default {
  components: {
    orderItem,
  },
  data: () => ({
    keyword: "",
    allOrderList: [
      [{ innerId: 1 }, { innerId: 2 }],
      [{ innerId: 1 }, { innerId: 2 }],
    ],
    unDeliverList: [
      [{ innerId: 1 }, { innerId: 2 }],
      [{ innerId: 1 }, { innerId: 2 }],
    ],
    unReceiptList: [
      [{ innerId: 1 }, { innerId: 2 }],
      [{ innerId: 1 }, { innerId: 2 }],
    ],
    list: [
      {
        name: "全部",
      },
      {
        name: "待发货",
      },
      {
        name: "待收货",
      },
    ],
    current: 0,
    swiperCurrent: 0,
    value2: "",
    show: false,
    returnOuterId: 0,
    returnInnerId: 0,
  }),
  computed: {},
  methods: {
    confirm() {
      this.unDeliverList[this.returnOuterId].length == 1
        ? this.unDeliverList.splice(this.returnOuterId, 1)
        : this.unDeliverList[this.returnOuterId].splice(this.returnInnerId, 1);
      this.show = false;
    },
    cancel() {
      this.show = false;
    },
    toReturn(returnOuterId, returnInnerId) {
      this.returnOuterId = returnOuterId;
      this.returnInnerId = returnInnerId;
      this.show = true;
    },
    confirmGoods(index) {
      this.allOrderList.splice(index, 1);
    },
    toPreviousPage,
    // 页面数据
    async getOrderList(cid) {
      await this.getAllOrderList();
    },
    async getAllOrderList() {
      // let allOrderListDate=await uni.$http.post("/user/shoppingOrder/getAll?pageNum=1&pageSize=100")
      // this.allOrderList=allOrderListDate.data.data.orders
      console.log("获取所有订单信息");
    },

    async getUnReceiptList() {
      // let unReceiptListData=await uni.$http.get("/user/shoppingOrder/unReceipt")
      // this.unReceiptList=unReceiptListData.data.data
      console.log("获取所有待收货信息");
    },
    async getUnDeliverList() {
      // let unDeliverListData=await uni.$http.get("/user/shoppingOrder/unDelivered")
      // this.unDeliverList=unDeliverListData.data.data
      console.log("获取所有待发货信息");
    },
    async toDeliver(did) {
      // await uni.$http.post(`/user/shoppingOrder/receipt?orderId=${did}`)
      // this.getUnDeliverList()
      console.log("去催货");
    },
    async toReceipt(rid) {
      // await uni.$http.post(`/user/shoppingOrder/evaluate?orderId=${rid}`)
      // this.getUnReceiptList()
      console.log("去收货");
    },
    toFirstPage() {
      uni.switchTab({
        url: "/pages/firstPage/firstPage",
      });
    },
    // tab栏切换
    async change({ index }) {
      this.swiperCurrent = index;
      if (index == 0) {
        await this.getAllOrderList();
      } else if (index == 1) {
        await this.getUnDeliverList();
      } else {
        await this.getUnReceiptList();
      }
    },
    animationfinish({ detail: { current } }) {
      // this.$refs.tabs.setFinishCurrent(current);
      this.swiperCurrent = current;
      // this.current = current;
    },
  },
  watch: {},

  // 页面周期函数--监听页面加载
  async onLoad() {
    await this.getOrderList();
  },
};
</script>

<style scoped lang="scss">
@import "../../static/css/orderItem.scss";

.orderForm {
  .mosttop {
    display: flex;
    padding-top: 50rpx;
    justify-content: space-between;
    align-items: center;
    .search {
      padding-right: 110rpx;
      flex: 7;
    }
    .return {
      margin-left: 40rpx;
      flex: 2;
    }
  }
  .tab {
    padding: 20rpx 0 0 20rpx;
    border-bottom: 7rpx solid rgb(249, 246, 246, 0.5);
  }
  .centre {
    text-align: center;
    margin: 200rpx auto;
    font-size: 32rpx;
    image {
      width: 164rpx;
      height: 164rpx;
      border-radius: 50%;
      margin-bottom: 20rpx;
    }
    .tips {
      font-size: 24rpx;
      color: #999999;
      margin-top: 20rpx;
    }
    .btn {
      margin: 80rpx auto;
      width: 200rpx;
      border-radius: 32rpx;
      line-height: 64rpx;
      color: #ffffff;
      font-size: 26rpx;
      background: #77bc65;
    }
  }
  .wrap {
    display: flex;
    flex-direction: column;
    height: calc(100vh - var(--window-top));
    width: 100%;
    .u-tabs-box {
      width: 100%;
    }
  }
  .swiper-box {
    flex: 1;
  }
  .swiper-item {
    height: 100%;
  }
  .returnMoney {
    font-size: 25rpx !important;
    width: 80rpx !important;
    height: 45rpx;
    margin-left: 120rpx;
    padding: 10rpx !important;
    color: black !important;
    border: 2rpx solid #bcbcbf !important;
    border-radius: 20rpx;
    letter-spacing: 2rpx;
  }
}
</style>
