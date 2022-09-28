<template>
  <div :class="['goodsCard', isLeasehold ? 'isLeasehold' : 'noLeasehold']">
    <div class="goodsImg">
      <img :src="img" alt="goods" />
    </div>
    <div class="leasehold" v-if="isLeasehold">
      <button class="leaseholdBtn">租地预订</button>
    </div>
    <div class="goodsInfo">
      <div class="goodsName">{{ goodsName }}</div>
      <div class="goodsPrice">
        <span class="price">￥{{ goodsPrice }}/件</span>
        <span class="number">成交{{ transactionPrice }}万元</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    isLeasehold: {
      type: Boolean,
      required: true,
    },
    img: {
      type: String,
      defaultd: require("../static/img/goods.png"),
    },
    goodsName: {
      type: String,
      required: true,
    },
    goodsPrice: {
      type: Number,
      required: true,
    },
    transactionPrice: {
      type: Number,
      required: true,
    },
  },
  computed: {},
};
</script>

<style lang="scss" scoped>
.isLeasehold {
  height: 41vh;
}
.noLeasehold {
  height: 35vh;
}
.goodsCard {
  box-shadow: 1px 1px 5px rgba(0, 0, 0, 0.3);
  width: 46vw;
  border-radius: 2vw;
  .goodsImg {
    width: 100%;
    img {
      width: 100%;
    }
  }
  .leasehold {
    display: flex;
    justify-content: flex-start;
    margin: 0.5vh 3vw;
    width: 50%;
    .leaseholdBtn {
      border: none;
      color: #fff;
      background-color: rgba(47, 175, 72, 0.72);
      border-radius: 3vh;
      font-size: 14px;
    }
  }
  .goodsInfo {
    margin-top: 1vh;
    .goodsName {
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
      width: 65%;
      height: 1;
      font-size: 14px;
      font-weight: 600;
      margin-left: 4vw;
    }
    .goodsPrice {
      display: flex;
      justify-content: space-around;
      margin-top: 1vh;
      align-items: center;
      margin-bottom: 1vh;
    }
    .price {
      color: #f70606;
    }
    .number {
      color: #a29f9f;
      font-size: 12px;
    }
  }
}
</style>
