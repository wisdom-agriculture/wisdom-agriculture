<template>
  <div class="com-item">
    <div class="left">
      <u-image
        shape="circle"
        :src="comItem.imgUrl"
        width="38"
        height="38"
      ></u-image>
    </div>
    <div class="right">
      <div class="content-top">
        <span class="name">
          {{ comItem.nickname ? comItem.nickname : "离谱" }}
        </span>
        <span class="time">{{ getMinSec(comItem.time) }}</span>
      </div>
      <div class="content">
        <div class="text">
          {{ comItem.content }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
function change(t) {
  if (t < 10) {
    return "0" + t;
  } else {
    return t;
  }
}
export default {
  props: {
    comItem: {},
  },
  computed: {
    getMinSec(timetemp) {
      return (timestamp) => {
        let date = new Date(timestamp);
        let h = change(date.getHours()) + ":";
        let m = change(date.getMinutes());
        return h + m;
      };
    },
  },
};
</script>

<style lang="scss" scoped>
.com-item {
  margin: 20rpx 0;
  display: flex;
  border-bottom: 7rpx solid #fafbfe;
}
.left {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0 15rpx 20rpx 0;
}
.right {
  flex: 6;
  .time {
    font-size: 24rpx;
    color: #6a6a6b;
    margin: 3rpx 0;
    float: right;
  }
  .name {
    font-size: 28rpx;
    font-weight: 700;
    width: 150rpx;
  }
  .content {
    font-size: 28rpx;
    color: #727272;
    font-weight: 700;
  }
}
</style>