<template>
  <div class="orderItem">
    <div class="top">
      <div class="shopName">一水商店</div>
      <div class="time">下单时间:&nbsp;&nbsp;2021.02.03</div>
    </div>
    <div class="middle">
      <div
        class="item"
        @click="toOtherPage('shopDetail')"
        v-for="(item, index) in itemObj"
        :key="index"
      >
        <u--image
          class="img"
          src="https://t13.baidu.com/it/u=1349244691,2459609399&fm=224&app=112&size=h200&n=0&f=JPEG&fmt=auto?sec=1662397200&t=5873a7f4aea43d3b79cafba35abf69d1"
          width="87"
          shape="square"
          mode="widthFix"
        ></u--image>
        <div class="con">
          <span class="name">一号青菜</span>
          <div class="dbcon">
            <span class="kilo">重量:&nbsp;1公斤</span>
            <slot></slot>
          </div>
          <div class="addr">一号种植场</div>
        </div>
      </div>
    </div>
    <div class="bottom" v-if="!isundeliver">
      <u-button
        type="primary"
        size="mini"
        color="white"
        class="custom-style"
        @click="toOtherPage('goodsFlu')"
        >查看物流</u-button
      >
      <u-button
        type="primary"
        size="mini"
        color="#ff913a"
        class="custom-style"
        @click="showModal"
        >确认收货</u-button
      >
    </div>
    <u-modal
      title="确认收货"
      content="是否确认已收货?"
      :show="show"
      style="text-align: center"
      @confirm="confirm"
      @cancel="cancel"
      ref="uModal"
      :showCancelButton="true"
    ></u-modal>
  </div>
</template>

<script>
import { toOtherPage } from "../utils/pageJump";
export default {
  props: ["index", "isundeliver", "itemObj"],
  data: () => ({
    show: false,
  }),
  methods: {
    toOtherPage,
    showModal() {
      this.show = true;
    },
    cancel() {
      this.show = false;
    },
    confirm() {
      this.$emit("confirmGoods", this.index);
      this.show = false;
    },
  },
};
</script>

<style lang="scss" scoped>
@import "../static/css/orderItem.scss";
</style>