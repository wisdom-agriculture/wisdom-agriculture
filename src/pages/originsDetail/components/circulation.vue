<template>
  <div>
    <!-- <div class="item red">
      <span>经过地:</span>
      <span>{{ circulation.passaddr }}</span>
    </div> -->
    <div class="item">
      <span>物流ID:</span>
      <span>{{ circulation.Record.transport_id }}</span>
    </div>
    <div class="item">
      <span>司机:</span>
      <span>{{ circulation.Record.driver_name }}</span>
    </div>
    <div class="item">
      <span>电话:</span>
      <span>{{ circulation.Record.driver_tel }}</span>
    </div>
    <div class="item">
      <span>备注:</span>
      <span>{{ circulation.Record.remarks }}</span>
    </div>
    <div class="item">
      <span>时间:</span>
      <span> {{ circulation.Record.transport_to_chain_time }}</span>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    circulation: { default: Object },
  },
};
</script>

<style lang="scss" scoped>
.red {
  color: #bd3124;
}
.item {
  span:last-child {
    // display: block;
    padding-left: 10rpx;
  }
}
</style>