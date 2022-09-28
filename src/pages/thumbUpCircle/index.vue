<template>
  <div class="like-circle">
    <recon-item
      @updateShow="updateShow"
      @flushReco="flushReco"
      v-for="(item, index) in recoList"
      :key="index"
      :recoitem="item"
    ></recon-item>
  </div>
</template>

<script>
import reconItem from "../planting/components/recoItem.vue";
export default {
  components: {
    reconItem,
  },
  data: () => ({
    recoList: [],
  }),
  computed: {},
  methods: {
    async getLikeData(prop) {
      let {
        data: {
          data: { zoneList },
        },
      } = await uni[prop].get("/api/zones/filter/likes");
      this.recoList = zoneList;
    },
    async flushReco(isNoLoad) {
      isNoLoad = isNoLoad == true ? "$NoLoadhttp" : "$http";
      await this.getLikeData(isNoLoad);
    },
  },
  watch: {},

  // 页面周期函数--监听页面加载
  onLoad() {},
  // 页面周期函数--监听页面初次渲染完成
  onReady() {},
  // 页面周期函数--监听页面显示(not-nvue)
  async onShow() {
    await this.getLikeData("$http");
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

<style scoped >
.like-circle {
  background-color: #fafbfe;
  padding-top: 30rpx;
}
</style>