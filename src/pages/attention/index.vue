<template>
  <div class="atten">
    <af-item
      v-for="(item, index) in lists"
      :key="index"
      :afitem="item"
      @cancelAtten="cancelAtten"
    >
      <u-button size="small" color="#c7c7cc" @click="cancelAtten(item.id)"
        >已关注</u-button
      >
    </af-item>
    <div class="final">无更多关注</div>
  </div>
</template>

<script>
import afItem from "./components/afItem.vue";
export default {
  components: {
    afItem,
    id: 0,
  },
  data: () => ({
    lists: [],
  }),
  methods: {
    async getAttenUser(prop) {
      let {
        data: { data },
      } = await uni[prop].get("/api/users/subscribes?id=" + this.id);
      this.lists = data.fanList;
    },
    async cancelAtten(userId) {
      let {
        data: { code },
      } = await uni.$NoLoadhttp.delete("/api/users/subscribes?id=" + userId);
      if (code == "00000") {
        await this.getAttenUser("$NoLoadhttp");
      }
    },
  },
  async onShow() {
    await this.getAttenUser("$http");
  },
  onLoad(options) {
    this.id = options.userId;
    console.log(options);
  },
};
</script>

<style scoped lang="scss">
.atten {
  background-color: #efeff4;
  height: 100%;
  overflow: hidden;
  padding-top: 20rpx;
}
.final {
  text-align: center;
  margin: 40rpx auto;
  color: rgb(98, 98, 98);
  font-size: 24rpx;
}
</style>