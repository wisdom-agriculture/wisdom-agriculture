<template>
  <div class="attention">
    <div class="top">
      <top-tab @flushReco="flushReco"></top-tab>
    </div>
    <div v-if="haveAtten" class="recoList">
      <recon-item
        @updateShow="updateShow"
        @flushReco="flushReco"
        v-for="(item, index) in recoList"
        :key="index"
        :recoitem="item"
      ></recon-item>
    </div>
    <div v-else class="tip">请先关注好友哦~</div>
  </div>
</template>
<script>
import topTab from "./components/topTab.vue";
import reconItem from "./components/recoItem.vue";

export default {
  data: () => ({
    recoList: [],
    show: false,
    haveAtten: true,
  }),
  methods: {
    async getRecoList(prop) {
      let {
        data: {
          data: { zoneList },
        },
      } = await uni[prop].get("/api/zones");
      this.recoList = zoneList;
      if (this.recoList.length == 0) {
        this.haveAtten = false;
      }
    },
    async flushReco(isNoLoad) {
      isNoLoad = isNoLoad == true ? "$NoLoadhttp" : "$http";
      await this.getRecoList(isNoLoad);
    },
  },
  async onShow() {
    await this.getRecoList("$http");
  },
  components: {
    topTab,
    reconItem,
  },
  async onPullDownRefresh() {
    await this.getRecoList("$http");
    setTimeout(function () {
      uni.stopPullDownRefresh();
    }, 1000);
  },
};
</script>

<style scoped lang="scss">
.recoList {
  background-color: #fafbfe;
  padding-top: 30rpx;
}
.tip {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
</style>