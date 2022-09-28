<template>
  <div>
    <div class="left">
      <div class="item">
        <span>生长状况:</span>
        <span>{{
          process.Record.grow_status ? process.Record.grow_status : "无"
        }}</span>
      </div>
      <div class="item">
        <span>光照情况:</span>
        <span>{{
          process.Record.illumination_status
            ? process.Record.illumination_status
            : "无"
        }}</span>
      </div>
      <div class="item">
        <span>水分:</span>
        <span>{{
          process.Record.water_content ? process.Record.water_content : "无"
        }}</span>
      </div>
      <div class="item">
        <span>温度:</span>
        <span>{{
          process.Record.temperature ? process.Record.temperature : "无"
        }}</span>
      </div>
      <div class="item">
        <span>记录时间:</span>
        <span>{{
          process.Record.record_time
            ? process.Record.record_time.substring(0, 10)
            : "无"
        }}</span>
      </div>
      <div class="item">
        <span>备注:</span>
        <span>{{
          process.Record.remarks ? process.Record.remarks : "无"
        }}</span>
      </div>
    </div>
    <div class="right">
      <u--image
        class="img"
        :src="process.Record.crops_grow_photo_url"
        width="140"
        height="150"
        mode="aspectFit"
        shape="square"
      ></u--image>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    process: Object,
  },
};
</script>

<style scoped lang="scss">
.left {
  border: 0;
  padding-left: 10rpx;
  width: 60%;
}
.img {
  margin: 10px 30rpx;
}
.item {
  span:last-child {
    // display: block;
    padding-left: 10rpx;
  }
}
</style>