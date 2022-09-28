<template>
  <div class="card">
    <div class="title">{{ title }}</div>
    <div class="description">{{ description }}</div>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      required: true,
    },
    description: {
      type: String,
      required: true,
    },
  },
  data() {
    return {};
  },
};
</script>

<style scoped lang="scss">
.card {
  width: 90%;
  margin: 0 auto;
  border-radius: 30rpx;
  box-shadow: 0 0 9px rgb(0 0 0 / 20%);
  .title {
    height: 80rpx;
    line-height: 80rpx;
    padding-left: 24rpx;
    font-size: 18px;
    font-weight: 600;
    border-bottom: 1px solid #a29f9f;
  }
  .description {
    color: #a29f9f;
    font-size: 13px;
    line-height: 1.5em;
    padding: 20rpx 40rpx;
  }
}
</style>
