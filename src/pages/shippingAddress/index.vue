<template>
  <div class="get-address">
    <u-radio-group v-model="radiovalue1">
      <div class="item" v-for="(item, index) in adrList" :key="index">
        <div class="select">
          <u-radio
            shape="circle"
            :name="String(index)"
            @change="radioChange(index)"
          ></u-radio>
        </div>
        <div class="middle">
          <div class="info">
            <span class="name">{{ item.userName }}，</span>
            <span class="phone">{{ item.userPhone }}</span>
          </div>
          <div class="address">{{ item.userShoppingAddress }}</div>
        </div>
        <div class="edit">
          <u-icon
            name="edit-pen-fill"
            color="#00000"
            size="28"
            @click="toOtherPage('./editAddr', item)"
          ></u-icon>
        </div>
      </div>
    </u-radio-group>
    <div class="btn">
      <u-button color="#2faf48" @click="toOtherPage('./addAddr')"
        >新增地址</u-button
      >
    </div>
  </div>
</template>

<script>
import { da } from "make-plural";

export default {
  components: {},
  data: () => ({
    radiovalue1: "1",
    adrList: [],
  }),
  computed: {},
  methods: {
    toOtherPage(addr, ...param) {
      let itemInfo = param[0] ? JSON.stringify(param[0]) : "";
      uni.navigateTo({
        url: `${addr}?addrInfo=+` + encodeURIComponent(itemInfo),
      });
      // console.log(addr, param[0]);
    },
    radioChange(index) {
      // 获取选择的地址
      console.log(this.adrList[index]);
    },
    async getAddr() {
      let {
        data: {
          data: { userShoppingAddressResults },
        },
      } = await uni.$http.get("/api/user/address");
      this.adrList = userShoppingAddressResults;
    },
  },
  watch: {},

  // 页面周期函数--监听页面加载
  onLoad() {},
  // 页面周期函数--监听页面初次渲染完成
  onReady() {},
  // 页面周期函数--监听页面显示(not-nvue)
  onShow() {
    // 发请求获取收货地址信息
    this.getAddr();
  },
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
.get-address {
  background-color: #efeff4;
  height: 100%;
  width: 100%;
  padding-left: 20rpx;
  padding-top: 20rpx;
  .u-radio-group--row[data-v-4a02ae53] {
    display: block !important;
  }
  .item {
    display: flex;
    padding: 30rpx 20rpx;
    align-items: center;
    .select {
      flex: 0.5;
    }
    .middle {
      flex: 4;
      padding-left: 30rpx;
      .address {
        margin-top: 15rpx;
        font-size: 26rpx;
        color: #5f5f5f;
      }
    }
    .edit {
      flex: 1;
    }
  }
  .btn {
    width: 70%;
    margin: 60rpx auto;
  }
}
</style>
