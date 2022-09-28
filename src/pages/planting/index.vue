<template>
  <div class="planting">
    <div class="top">
      <top-tab @flushReco="flushReco"></top-tab>
    </div>
    <div class="recoList">
      <recon-item
        @updateShow="updateShow"
        @flushReco="flushReco"
        v-for="(item, index) in recoList"
        :key="index"
        :recoitem="item"
      ></recon-item>
    </div>
  </div>
</template>

<script>
import topTab from "./components/topTab.vue";
import reconItem from "./components/recoItem.vue";

export default {
  components: {
    topTab,
    reconItem,
  },
  data: () => ({
    recoList: [],
    show: false,
  }),
  methods: {
    async getRecoList(prop) {
      let {
        data: {
          data: { zoneList },
        },
      } = await uni[prop].get("/api/zones");
      this.recoList = zoneList;
    },
    async flushReco(isNoLoad) {
      isNoLoad = isNoLoad == true ? "$NoLoadhttp" : "$http";
      await this.getRecoList(isNoLoad);
    },
  },
  watch: {},
  async onPullDownRefresh() {
    await this.getRecoList("$http");
    setTimeout(function () {
      uni.stopPullDownRefresh();
    }, 1000);
  },
  // 页面周期函数--监听页面加载
  onLoad() {},
  // 页面周期函数--监听页面初次渲染完成
  onReady() {},
  // 页面周期函数--监听页面显示(not-nvue)
  async onShow() {
    await this.getRecoList("$http");
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
.recoList {
  background-color: #fafbfe;
  padding-top: 30rpx;
}
</style>
