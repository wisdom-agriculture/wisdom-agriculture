<template>
  <div class="origins">
    <!-- 扫描二维码 -->
    <div class="top" @click="getScanInfo">
      <u--image
        class="img"
        :src="require('../../static/img/scan.png')"
        width="25"
        mode="widthFix"
      ></u--image>
      <span>新建溯源</span>
    </div>
    <div class="content">
      <origin-item
        v-for="(item, index) in falseData"
        :index="index"
        :key="index"
        @deleteOrigin="deleteOrigin"
      ></origin-item>
    </div>
  </div>
</template>

<script>
import originItem from "./components/originItem.vue";
export default {
  components: {
    originItem,
  },
  data: () => ({
    falseData: [1, 2],
  }),
  computed: {},
  methods: {},
  watch: {},
  methods: {
    async getAllData() {
      //  获得用户的农作物列表
    },
    getScanInfo() {
      uni.scanCode({
        success: (res) => {
          console.log(res);
        },
      });
    },
    deleteOrigin(index) {
      this.falseData.splice(index, 1);
    },
  },

  // 页面周期函数--监听页面加载
  onLoad() {},
  // 页面周期函数--监听页面初次渲染完成
  onReady() {},
  // 页面周期函数--监听页面显示(not-nvue)
  async onShow() {
    await this.getAllData();
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
.origins {
  height: 100%;
  .top {
    margin: 20rpx 30rpx 10rpx;
    border-radius: 30rpx;
    display: flex;
    color: #4ba234;
    justify-content: center;
    box-shadow: 0 0 10rpx 0 #eae8e8;
    height: 86rpx;
    line-height: 86rpx;
    .img {
      margin-top: 18rpx;
    }
    span {
      padding: 5rpx 0 0 10rpx;
    }
  }
}
</style>