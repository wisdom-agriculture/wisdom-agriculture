<template>
  <div class="goodsDetail">
    <Nav title="商品详情"></Nav>
    <div class="goodsInfo">
      <div class="goodsImg">
        <u-swiper
          :list="imgList"
          height="200"
          indicator
          indicatorMode="line"
          circular
        ></u-swiper>
      </div>
      <div class="goodsInfoDetail">
        <div class="goodsPrice">
          <span>￥</span>{{ goodsDetail.lowProducesPrice }}~{{
            goodsDetail.higProducesPrice
          }}
        </div>
        <div class="goodsTag">
          <span>一件代发</span>
        </div>
        <div class="goodsName">
          {{ goodsDetail.producesName }}
        </div>
        <div class="goodsMore">{{ goodsDetail.producesDescription }}</div>
      </div>
    </div>
    <div class="userAddress">
      <div class="left">发货</div>
      <div class="right">
        <div class="address">{{ goodsDetail.shoppingAddress }}</div>
        <div class="noShipAddress">
          {{ goodsDetail.shoppingAddressRemark }}
        </div>
      </div>
    </div>
    <div class="standard">
      <div class="standardTitle">规格/价格</div>
      <div class="standardList">
        <div
          class="standardDetail"
          v-for="(item, index) in goodsDetail.produceSkuInfoList"
          :key="index"
        >
          <div class="title">{{ item.producesSpecification }}</div>
          <div class="price">
            {{ item.producesPrice }}元/{{ item.producesPer }}
          </div>
        </div>
      </div>
    </div>
    <div style="height: 70px"></div>
    <div class="shopFunc">
      <div class="follow" @click="addFavorites">
        <img
          src="../../../static/icon/home/shopFollow.svg"
          alt=""
          v-if="!goodsDetail.isFavorites"
        />
        <img src="../../../static/icon/favorited.png" alt="" v-else />
        <span>收藏</span>
      </div>
      <div class="addGoodsToCart">
        <u-button
          type="primary"
          text="加入购物车"
          color="#0fd284"
          class="addToCart"
          @click="addCart"
        ></u-button>
      </div>
      <div class="shopping">
        <u-button
          type="primary"
          text="立即购买"
          color="#2faf48"
          class="addToCart"
          @click="addOrderForm"
        ></u-button>
      </div>
    </div>
  </div>
</template>

<script>
import { toPreviousPage } from "../../../utils/pageJump";
import Nav from "../../../components/nav.vue";
export default {
  data() {
    return {
      goodsid: 0,
      goodsDetail: {},
      imgList: [],
    };
  },
  methods: {
    toPreviousPage,
    addOrderForm() {
      uni.navigateTo({
        url: `/pages/mall/firmOrder/index`,
      });
    },
    async getGoodsDetails(id) {
      const { data } = await uni.$http.get("/api/produce", {
        producesId: id,
      });
      if (data.code !== "00000") {
        return uni.$showMsg(data.message);
      }
      this.goodsDetail = data.data;
      let imgArray = data.data.imagesList;
      imgArray.forEach((item) => {
        this.imgList.push(item.imageAddress);
      });
    },
    async addCart() {
      const { data } = await uni.$http.post("/api/cars", {
        producesId: this.goodsid,
        producesSkuId: 1,
      });
      if (data.code !== "00000") {
        return uni.$showMsg(data.message);
      }
      uni.$showMsg("加入购物车成功");
    },
    async addFavorites() {
      console.log(this.goodsDetail.isFavorites);
      if (this.goodsDetail.isFavorites) {
        const { data } = await uni.$http.delete("/api/favorites", {
          ids: [this.goodsDetail.id],
        });
        this.getGoodsDetails(this.goodsid);
        return;
      }
      const { data } = await uni.$http.post(`/api/favorites`, {
        produceId: this.goodsDetail.id,
      });
      if (data.code !== "00000") {
        return uni.$showMsg(data.message);
      }
      uni.$showMsg("收藏成功");
    },
  },
  components: { Nav },
  onLoad(option) {
    this.goodsid = option.id;
    this.getGoodsDetails(option.id);
  },
};
</script>

<style scoped lang="scss">
.goodsDetail {
  // height: 100%;
  background-color: #f0f0f0;
  // overflow: hidden;
}
.circle {
  height: 8vw;
  width: 8vw;
  background-color: rgba(1, 1, 1, 0.4);
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  img {
    height: 16px;
    width: 16px;
  }
}
.nav {
  display: flex;
  justify-content: space-between;
  margin: 4vw 0vw;
  position: fixed;
  top: 0px;
  width: 100%;
  z-index: 999;
  .leftQuit {
    margin-left: 4vw;
  }
  .rightShare {
    margin-right: 4vw;
  }
}
.goodsInfo {
  .goodsImg {
    margin-top: 60px;
    uni-swiper-wrapper {
      height: 12vh !important;
    }
    img {
      width: 100%;
    }
  }
}

.goodsInfoDetail {
  width: 94%;
  margin: 0 auto;
  background-color: #fff;
  box-sizing: border-box;
  padding: 10px;
  margin-top: 2vh;
  border-radius: 2vw;
  .goodsPrice {
    color: red;
    font-size: 21px;
    span {
      font-size: 12px;
    }
  }
  .goodsTag {
    font-size: 12px;
    margin-top: 6px;
    span {
      background-color: #f0f0f0;
      padding: 4px;
      border-radius: 3px;
    }
  }
  .goodsName {
    font-weight: 600;
    margin-top: 10px;
  }
  .goodsMore {
    margin: 1vh 0;
    font-size: 13px;
    font-weight: 600;
  }
}
.userAddress {
  background-color: #fff;
  width: 94%;
  margin: 0 auto;
  padding: 10px;
  box-sizing: border-box;
  margin-top: 2vh;
  border-radius: 2vw;
  display: flex;
  .noShipAddress {
    margin-top: 1vh;
    font-size: 13px;
    color: #b5b5b5;
  }
  .left {
    width: 20vw;
    color: #b5b5b5;
  }
}
.standard {
  width: 94%;
  background-color: #fff;
  border-radius: 2vw;
  margin: 2vh auto 0;
  padding: 10px;
  box-sizing: border-box;
  .standardTitle {
  }
  .standardList {
    .standardDetail {
      padding: 1vh;
      box-sizing: border-box;
      height: 60px;
      border-bottom: 1px solid #f0f0f0;
      display: flex;
      align-items: center;
      justify-content: space-between;
      &:last-child {
        border: none;
      }
      .title {
        display: flex;
        line-height: calc(60px - 2vh);
      }
      .price {
        margin-top: 1vh;
        color: red;
      }
    }
  }
}

.shopFunc {
  position: fixed;
  bottom: 0px;
  background-color: #fff;
  width: 100%;
  height: 56px;
  display: flex;
  justify-content: space-around;
  align-items: center;
  .follow {
    width: 40px;
    display: flex;
    flex-direction: column;
    align-items: center;
    span {
      font-size: 12px;
      font-weight: 600;
      color: gray !important;
    }
    span {
      color: #2faf48;
    }
    img {
      height: 24px;
      width: 24px;
    }
  }
  .addToCart {
    border-radius: 18px;
  }
}
</style>
