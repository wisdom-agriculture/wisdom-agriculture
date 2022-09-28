<template>
  <div class="contentCard">
    <div class="title">
      <b>{{ title }}</b>
    </div>
    <div class="funcLists">
      <div class="list" v-for="(item, index) in list" :key="index">
        <div class="icon" @click="cardPageTo(item.pagePath)">
          <image :src="item.iconPath" alt="icon"></image>
        </div>
        <div class="text">{{ item.text }}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      required: true,
    },
    list: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {};
  },
  methods: {
    cardPageTo(path) {
      console.log(path);
      this.$emit("pageTo", path);
    },
  },
};
</script>

<style scoped lang="scss">
.contentCard {
  width: 90%;
  margin: 0 auto;
  height: 320rpx;
  background-color: #fff;
  box-shadow: 0 0 9px rgb(0 0 0 / 20%);
  border-radius: 16px;
  .title {
    height: 30%;
    display: flex;
    align-items: center;
    padding-left: 30rpx;
    border-bottom: 1rpx solid rgb(184, 172, 172);
  }
  .funcLists {
    display: flex;
    align-items: center;
    justify-content: space-around;
    height: 70%;
    .list {
      display: flex;
      flex-direction: column;
      align-items: center;
      .icon {
        width: 80rpx;
        height: 80rpx;
      }
      .text {
        margin-top: 10rpx;
        font-weight: 600;
      }
    }
  }
}
</style>
