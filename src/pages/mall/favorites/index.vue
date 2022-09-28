<template>
  <div class="favorites">
    <Nav title="我的收藏"></Nav>
    <u-empty
      mode="data"
      icon="http://cdn.uviewui.com/uview/empty/data.png"
      text="暂无收藏"
      v-if="!isHaveFavorites"
    >
    </u-empty>
    <div class="favoriteList" v-else>
      <view
        class="cartCard"
        v-for="(item, index) in userFavoritesListResults"
        :key="index"
      >
        <view class="shopTitle">{{ item.shopName }}</view>
        <view class="shopsList">
          <u-swipe-action>
            <u-swipe-action-item
              :options="options1"
              v-for="item1 in item.userFavoritesItemResults"
              :key="item1.id"
            >
              <view class="swipe-action u-border-top u-border-bottom">
                <view class="swipe-action__content">
                  <slot>
                    <view class="shopCard">
                      <view class="goodsImg">
                        <img :src="item1.producesImage" alt="" />
                      </view>
                      <view class="goodsInfo">
                        <view class="goodsName overflowOmit">
                          {{ item1.producesName }}
                        </view>
                        <view class="goodsPrice">
                          ￥{{ item1.producesPrice }}/{{ item1.producesPer }}
                        </view>
                      </view>
                    </view>
                  </slot>
                </view>
              </view>
            </u-swipe-action-item>
          </u-swipe-action>
        </view>
      </view>
    </div>
  </div>
</template>

<script>
import Nav from "../../../components/nav.vue";
export default {
  name: "favorites",
  components: { Nav },
  props: {},
  data() {
    return {
      userFavoritesListResults: [],
      options1: [
        {
          text: "删除",
        },
      ],
    };
  },
  computed: {
    isHaveFavorites() {
      if (this.userFavoritesListResults.length === 0) {
        return false;
      } else {
        return true;
      }
    },
  },
  methods: {
    async getAllFavorites() {
      const { data: res } = await uni.$http.get("/api/favorites");
      if (res.code !== "00000") {
        return;
      }
      this.userFavoritesListResults = res.data.userFavoritesListResults;
      console.log(this.userFavoritesListResults);
    },
  },
  watch: {},

  // 页面周期函数--监听页面加载
  onLoad() {
    this.getAllFavorites();
  },
  // 页面周期函数--监听页面初次渲染完成
  onReady() {},
  // 页面周期函数--监听页面显示(not-nvue)
  onShow() {},
  // 页面周期函数--监听页面隐藏
  onHide() {},
  // 页面周期函数--监听页面卸载
  onUnload() {},
  // 页面处理函数--监听用户下拉动作
  // onPullDownRefresh() { uni.stopPullDownRefresh(); },
  // 页面处理函数--监听用户上拉触底
  // onReachBottom() {},
  // 页面处理函数--监听页面滚动(not-nvue)
  // onPageScroll(event) {},
  // 页面处理函数--用户点击右上角分享
  // onShareAppMessage(options) {},
};
</script>

<style scoped lang="scss">
.favorites {
  background-color: #f0f0f0;
  height: 100%;
}
.favoriteList {
  width: 98vw;
  margin: 54px auto 0;
}
.cartCard {
  border-radius: 3vw;
  background-color: #fff;
  overflow: hidden;
  padding-bottom: 2vh;
  margin-top: 1.5vh;
  .shopTitle {
    height: 7vh;
    width: 90%;
    margin: 0 auto;
    line-height: 7vh;
    font-weight: 600;
    border-bottom: 1px solid #d8d7d7;
  }
  .shopCard {
    display: flex;
    width: 90%;
    margin: 0 auto;
    .goodsImg {
      height: 15vw;
      width: 15vw;
      img {
        height: 100%;
        border-radius: 1vw;
      }
    }
    .goodsInfo {
      width: 60vw;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      margin-left: 3vw;
    }
    .goodsName {
      color: #4ba234;
      font-size: 16px;
    }
    .goodsPrice {
      color: #f70606;
      display: flex;
      align-items: center;
    }
  }
}
.u-page {
  padding: 0;
}

.u-demo-block__title {
  padding: 10px 0 2px 15px;
}

.swipe-action {
  &__content {
    padding: 25rpx 0;

    &__text {
      font-size: 15px;
      color: $u-main-color;
      padding-left: 30rpx;
    }
  }
}
.u-border-top {
  border: none;
}
.u-border-bottom {
  border: none;
}
.u-empty {
  margin-top: 20vh !important;
}
</style>
