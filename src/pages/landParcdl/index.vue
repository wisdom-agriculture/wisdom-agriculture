<template>
  <div class="landParcdl">
    <div class="nav">
      <span>体验现实版开心农场</span>
      <span @click="toOtherPage('farmMap')">进入地图模式 ></span>
    </div>
    <div
      v-for="(item, index) in fields"
      :key="index"
      @click="
        toOtherPage(`landParcelDetails`, {
          path: 1,
          id: item.id,
        })
      "
    >
      <card-vue :title="item.name" :tagList="tagList"></card-vue>
    </div>
  </div>
</template>

<script>
import cardVue from "./card.vue";
import { toOtherPage } from "../../utils/pageJump";
export default {
  components: {
    cardVue,
  },
  data: () => ({
    tagList: ["娃娃菜", "大黄瓜", "大番茄"],
    fields: [],
  }),
  computed: {},
  methods: {
    toOtherPage,
    async getFarmData() {
      const { data } = await uni.$http.get("/api/fields");
      console.log(data);
      if (data.code !== "00000") {
        return uni.$showMsg(data.message);
      }
      console.log(data.data);
      this.fields = data.data.fieldList;
    },
  },
  watch: {},

  // 页面周期函数--监听页面加载
  onLoad() {
    this.getFarmData();
  },
  // 页面周期函数--监听页面初次渲染完成
  onReady() {},
  // 页面周期函数--监听页面显示(not-nvue)
  onShow() {},
  // 页面周期函数--监听页面隐藏
  onHide() {},
  // 页面周期函数--监听页面卸载
  onUnload() {},
};
</script>

<style scoped lang="scss">
.nav {
  background-color: #d2f2ca;
  height: 70rpx;
  display: flex;
  justify-content: space-around;
  align-items: center;
  font-size: 14px;
  font-family: PingFangSC-regular;
  font-weight: 600;
  margin-bottom: 50rpx;
  span {
    &:nth-child(2) {
      color: rgba(75, 162, 52, 100);
    }
  }
}
.landParcdl {
  padding-bottom: 20px;
}
</style>
