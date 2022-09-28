<template>
  <div class="card">
    <div class="title">{{ title }}</div>
    <div class="content">
      <slot></slot>
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
  },
};
</script>

<style lang="scss" scoped>
.card {
  width: 90%;
  margin: 0 auto;
  box-shadow: 0px 0px 4px rgba(0, 0, 0, 0.4);
  border-radius: 3vw;
  .title {
    height: 5vh;
    box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.2);
    padding-left: 6vw;
    border-radius: 3vw 3vw 0 0;
    margin-bottom: 1vh;
    font-size: 18px;
    font-weight: 600;
    line-height: 5vh;
  }
  .content {
    border-radius: 0 0 3vw 3vw;
    padding: 2vw;
  }
}
</style>
