<template>
  <view class="cart">
    <Nav title="购物车"></Nav>
    <view class="cartList">
      <view
        class="cartCard"
        v-for="(item, index) in carResultList"
        :key="index"
      >
        <view class="shopTitle">{{ item.shopName }}</view>
        <view class="shopsList">
          <u-swipe-action>
            <u-swipe-action-item
              :options="options1"
              v-for="item1 in item.carItemResults"
              :key="item1.id"
              @click="itemButton(item1)"
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
                        <view class="goodsWeight">
                          重量：{{ item1.producesWeight
                          }}{{ item1.producesPer }}
                        </view>
                      </view>
                      <view class="goodsPrice">
                        ￥{{ item1.producesPrice }}
                      </view>
                    </view>
                  </slot>
                </view>
              </view>
            </u-swipe-action-item>
          </u-swipe-action>
        </view>
      </view>
    </view>
    <view class="bottem">
      <div class="checkout"></div>
    </view>
  </view>
</template>

<script>
import Nav from "../../../components/nav.vue";
export default {
  name: "cart",
  components: { Nav },
  props: {},
  data() {
    return {
      options1: [
        {
          text: "删除",
        },
      ],
      carResultList: [],
    };
  },
  computed: {},
  methods: {
    async getAllCart() {
      const { data: res } = await uni.$http.get("/api/cars");
      if (res.code !== "00000") {
        return uni.$showMsg(res.message);
      }
      console.log(res.data);
      this.carResultList = res.data.carResultList;
    },
    itemButton(item) {
      console.log(typeof item.id);
      console.log(item.id);
      this.deleteCart(item.id);
    },
    async deleteCart(id) {
      const { data } = await uni.$http.delete("/api/cars", [id]);
      if (data.code !== "00000") {
        return uni.$showMsg(data.message);
      }
      uni.$showMsg("删除成功");
      this.getAllCart();
    },
  },
  watch: {},

  // 页面周期函数--监听页面加载
  onLoad() {
    this.getAllCart();
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
.cart {
  background-color: #f0f0f0;
  height: 100%;
  .cartList {
    width: 98vw;
    margin: 54px auto 0;
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
</style>
