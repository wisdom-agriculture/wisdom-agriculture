<template>
  <div class="nav">
    <!-- 配置autoBack 的话, 就可以点击左面的进行返回 -->
    <u-navbar :title="title" :autoBack="true"> </u-navbar>
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

<style>
</style>