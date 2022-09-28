<template>
  <div class="top">
    <div class="btn">
      <button @click="recommend">推荐</button>
      <button @click="attention">关注</button>
    </div>
    <div class="serach">
      <u-search
        :show-action="false"
        placeholder="搜索"
        v-model="keyword"
        height="27"
        shape="square"
        bg-color="#ffffff"
        input-align="center"
      ></u-search>
    </div>
    <div>
      <div class="icon">
        <u-icon
          size="36"
          name="plus-circle-fill"
          color="#ffffff"
          @click="toRelease"
        ></u-icon>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data: () => ({
    checked: true,
    keyword: "",
  }),
  methods: {
    getCurPage() {
      let routes = getCurrentPages(); // 获取当前打开过的页面路由数组
      return routes[routes.length - 1].route;
    },
    async recommend() {
      let curp = this.getCurPage();
      if (curp.substring(curp.length - 5) == "index") {
        // 刷新
        await this.$emit("flushReco");
      } else {
        // 跳转
        uni.switchTab({
          url: "./index",
        });
      }
    },
    async attention() {
      let curp = this.getCurPage();
      if (this.getCurPage().substring(curp.length - 5) == "ntion") {
        // 刷新
        await this.$emit("flushReco");
      } else {
        // 跳转
        uni.navigateTo({
          url: "./attention",
        });
      }
    },
    toRelease() {
      uni.navigateTo({
        url: "./release",
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.top {
  width: 100%;
  height: 150rpx;
  background-color: rgba(47, 175, 72, 100);
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  .icon {
    margin-top: 50rpx;
  }
}
.serach {
  margin-top: 50rpx;
  width: 190rpx;
}
.btn {
  margin-top: 50rpx;
  display: flex;
  width: 250rpx;
  height: 100rpx;
  align-items: center;
  button {
    padding: 3rpx 40rpx;
    font-size: 22rpx;
    height: 60rpx;
  }
  button:nth-child(2) {
    color: #fff;
    background-color: rgb(26, 199, 59);
    margin-left: -7rpx;
  }
}
</style>