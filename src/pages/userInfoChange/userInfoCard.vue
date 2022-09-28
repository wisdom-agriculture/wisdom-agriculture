<template>
  <div class="userHead infoItem" :style="'height:' + cardHeight + 'px'">
    <span class="left">{{ leftText }}</span>
    <div class="right">
      <slot></slot>
      <u-icon name="arrow-right" class="icon"></u-icon>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    leftText: {
      type: String,
      required: true,
    },
    cardHeight: {
      type: String,
      required: false,
      default: "50",
    },
  },
};
</script>

<style scoped lang="scss">
.infoItem {
  width: 100vw;
  margin-bottom: 5px;
}
.userHead {
  height: 100px;
  background-color: #fff;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  .userHeadImg {
    width: 80px;
    height: 80px;
    border-radius: 50%;
    box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.3);
  }
}
.left {
  width: 20vw;
  font-size: 18px;
  margin-left: 20px;
}
.right {
  display: flex;
  align-items: center;
  margin-right: 20px;
  .icon {
    margin-left: 10px;
    color: gainsboro;
  }
}
</style>
