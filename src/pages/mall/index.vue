<template>
  <div class="mall">
    <div class="mallNav">
      <div class="nav">
        <div class="icon" @click="toTabPage('home')">
          <u-icon name="arrow-left" color="#fff" size="20"></u-icon>
        </div>
        <div class="title">商城</div>
      </div>
      <div class="search">
        <div class="searchInput">
          <u-search
            x
            placeholder="搜索"
            v-model="keyword"
            :showAction="false"
          ></u-search>
        </div>
        <div class="shoppingCart" @click="toOtherPage('mall/cart')">
          <u-icon name="shopping-cart" color="#fff" size="24"></u-icon>
          <span>购物车</span>
        </div>
        <div class="collect" @click="toOtherPage('mall/favorites')">
          <u-icon name="star" color="#fff" size="24"></u-icon>
          <span>收藏</span>
        </div>
      </div>
    </div>
    <div class="tabs">
      <div
        :class="['tab', isActive === 1 ? 'active' : '']"
        @click="changeTab(1)"
      >
        推荐
      </div>
      <div
        :class="['tab', isActive === 2 ? 'active' : '']"
        @click="changeTab(2)"
      >
        农产品
      </div>
      <div
        :class="['tab', isActive === 3 ? 'active' : '']"
        @click="changeTab(3)"
      >
        租地预定
      </div>
    </div>
    <div class="g">
      <!-- 第一列 -->
      <div class="goodsList">
        <goods-card
          v-for="(item, index) in firstHalf"
          :key="index"
          :isLeasehold="item.isLeasehold"
          @click.native="goodsDetail(item)"
          :img="item.producesImage"
          :goodsName="item.producesName"
          :goodsPrice="item.producesPrice"
          :transactionPrice="item.transactionPrice"
        ></goods-card>
      </div>
      <!-- 第二列 -->
      <div class="goodsList">
        <goods-card
          v-for="(item, index) in secondHalf"
          :key="index"
          :isLeasehold="item.isLeasehold"
          @click.native="goodsDetail"
          :img="item.producesImage"
          :goodsName="item.producesName"
          :goodsPrice="item.producesPrice"
          :transactionPrice="item.transactionPrice"
        ></goods-card>
      </div>
    </div>
  </div>
</template>

<script>
import goodsCard from "../../components/goodsCard.vue";
import { toTabPage, toOtherPage } from "../../utils/pageJump";
export default {
  components: {
    goodsCard,
  },
  data: () => ({
    isActive: 1,
    keyword: "",
    firstHalf: [],
    secondHalf: [],
  }),
  computed: {},
  methods: {
    toTabPage,
    toOtherPage,
    changeTab(tabId) {
      if (tabId === this.isActive) {
        return;
      }
      if (tabId > 0 && tabId < 4) {
        this.isActive = tabId;
      }
      let producesCategory = 1;
      if (tabId === 1) {
        this.getMallData();
        return;
      }
      if (tabId === 3) {
        producesCategory = 2;
      }
      this.getOtherData(producesCategory);
    },
    goodsDetail(item) {
      console.log(item);
      uni.navigateTo({
        url: `/pages/mall/goodsDetails/index?id=${item.id}`,
      });
    },
    async getMallData() {
      const { data } = await uni.$http.get("/api/produces");
      if (data.code !== "00000") {
        return uni.$showMsg(data.message);
      }
      let oneList = data.data.producesListResult;
      oneList.forEach((item) => {
        if (item.producesCategory === 1) {
          item.isLeasehold = false;
        } else {
          item.isLeasehold = true;
        }
      });
      const [firstHalf, secondHalf] = this.arrayAplit(oneList);
      this.firstHalf = firstHalf;
      this.secondHalf = secondHalf;
      uni.$showMsg(data.message);
    },
    async getOtherData(producesCategory) {
      const { data } = await uni.$http.get("/api/produces/category", {
        producesCategory: producesCategory,
      });
      if (data.code !== "00000") {
        return uni.$showMsg(data.message);
      }
      let oneList = data.data.producesListResult;
      oneList.forEach((item) => {
        if (item.producesCategory === 1) {
          item.isLeasehold = false;
        } else {
          item.isLeasehold = true;
        }
      });
      const [firstHalf, secondHalf] = this.arrayAplit(oneList);
      this.firstHalf = firstHalf;
      this.secondHalf = secondHalf;
      uni.$showMsg(data.message);
    },
    arrayAplit(array) {
      let half = Math.ceil(array.length / 2);
      let firstHalf = array.splice(0, half);
      let secondHalf = array.splice(-half);
      return [firstHalf, secondHalf];
    },
  },
  watch: {},

  // 页面周期函数--监听页面加载
  onLoad() {
    this.getMallData();
  },
  // 页面周期函数--监听页面初次渲染完成
  onReady() {},
  // 页面周期函数--监听页面显示(not-nvue)
  onShow() {},
  // 页面周期函数--监听页面隐藏
  onHide() {},
  // 页面周期函数--监听页面卸载
  onUnload() {},
};
</script>

<style scoped lang="scss">
.mall {
  background-color: #efeff4;
  height: 100%;
  overflow: scroll;
}
::v-deep .u-navbar__content__title {
  color: #fff;
  font-weight: 600;
}
::v-deep .u-icon__icon {
  font-weight: 600;
}
.mallNav {
  .nav {
    background-color: #2faf48;
    display: flex;
    align-items: center;
    height: 7vh;
    .icon {
      margin-left: 2vw;
    }
    .title {
      font-size: 16px;
      font-weight: 600;
      color: #fff;
      margin: 0 auto;
      transform: translateX(-3vw);
    }
  }
}
.search {
  background-color: #2faf48;
  height: 9vh;
  display: flex;
  align-items: center;
  justify-content: space-around;
  .shoppingCart,
  .collect {
    display: flex;
    flex-direction: column;
    align-items: center;
    span {
      color: #fff;
      font-size: 14px;
    }
  }
}
.tabs {
  height: 6vh;
  display: flex;
  justify-content: space-around;
  background-color: #fff;
  align-items: flex-end;
  .tab {
    font-size: 14px;
    font-weight: 600;
    border-bottom: 1px solid transparent;
    padding: 0px 5px 5px;
  }
  .active {
    border-bottom: 2px solid #2faf48;
    color: #2faf48;
  }
}
.goodsList {
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  margin: 0 auto;
  margin-top: 3vh;
  .goodsCard {
    margin-bottom: 1.6vh;
  }
}
.g {
  display: flex;
  justify-content: space-around;
}
</style>
