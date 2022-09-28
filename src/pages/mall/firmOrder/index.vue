<template>
  <div class="firmOrder">
    <Nav title="确认订单"></Nav>
    <div class="content">
      <div class="orderAddress">
        <div class="address">
          <div class="have" v-if="isHaveAddress">
            <div class="left">
              <div class="ads">{{ userAddress[0].userShoppingAddress }}</div>
              <div class="userInfo">
                <div class="userName">{{ userAddress[0].userName }}</div>
                <div class="phone">
                  {{ userAddress[0].userPhone }}
                </div>
              </div>
            </div>
            <div class="right" @click="toOtherPage('shippingAddress')">
              <u-icon name="arrow-right" size="21"></u-icon>
            </div>
          </div>
          <div class="notHave" v-else>
            <div class="text">当前暂无地址</div>
            <u-button
              type="primary"
              text="添加地址"
              @click="toOtherPage('shippingAddress')"
            ></u-button>
          </div>
        </div>
      </div>
      <div class="shop">
        <div class="shopName">
          <img src="../../../static/icon/shop.png" alt="" />
          <div class="name">驻马店王丰收粮店</div>
        </div>
        <div class="goodsList">
          <div class="goodsCard">
            <div class="goodsImg">
              <img src="../../../static/img/vegetable.png" alt="" />
            </div>
            <div class="goodsInfo">
              <div class="title">一号青菜</div>
              <div class="goodsPrice">
                <span>￥20.0</span>
                <div class="price">
                  <div>-</div>
                  <span class="goodsNumber">{{ number }}</span>
                  <div>+</div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="serve">
          <div>配送服务</div>
          <div>快递 包邮</div>
        </div>
        <div class="remarks">
          <div>留言</div>
          <input type="text" placeholder="建议留言前先与商家沟通确认" />
        </div>
      </div>
      <div class="modeOfPayment">
        <div>支付方式</div>
        <div>
          <span>在线支付</span>
          <u-icon name="arrow-right"></u-icon>
        </div>
      </div>
    </div>
    <div class="putInOrder">
      <div class="price">合计: <span>￥20.00 </span></div>
      <div class="btn" @click="submitOrder">提交订单</div>
    </div>
  </div>
</template>

<script>
import Nav from "../../../components/nav.vue";
import { toOtherPage } from "../../../utils/pageJump";
export default {
  name: "firmOrder",
  components: {
    Nav,
  },
  data() {
    return {
      userAddress: [],
      number: 1,
    };
  },
  methods: {
    toOtherPage,
    async getUserAddress() {
      const { data } = await uni.$http.get("/api/user/address");
      if (data.code !== "00000") {
        return uni.$showMsg(data.message, 1500);
      }
      uni.$showMsg(data.message);
      console.log(data);
      this.userAddress = data.data.userShoppingAddressResults;
    },
    submitOrder() {
      uni.$showMsg("订单提交成功");
    },
  },
  watch: {},
  computed: {
    isHaveAddress() {
      if (this.userAddress.length === 0) {
        return false;
      } else {
        return true;
      }
    },
  },

  // 页面周期函数--监听页面加载
  onLoad() {
    this.getUserAddress();
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
.firmOrder {
  height: 100%;
  background-color: #f0f0f0;
  .content {
    width: 96%;
    margin: 54px auto 0;
    .orderAddress {
      background-color: #fff;
      height: 10vh;
      border-radius: 1vw;
      .address {
        width: 100%;
        height: 100%;
        .have {
          display: flex;
          width: 100%;
          height: 100%;
          .left {
            width: 80%;
            margin-left: 6vw;
            display: flex;
            height: 100%;
            flex-direction: column;
            justify-content: center;
            .userInfo {
              display: flex;
              font-size: 12px;
              color: #a29f9f;
              margin-top: 2vw;
              .phone {
                margin-left: 8vw;
              }
            }
          }
          .right {
            width: 20%;
            display: flex;
            justify-content: center;
            align-items: center;
          }
        }
        .notHave {
          display: flex;
          align-items: center;
          height: 100%;
          width: 100%;
          justify-content: space-around;
          .u-button {
            width: 80px;
            height: 36px;
            margin: 0px;
          }
        }
      }
    }
    .shop {
      width: 100%;
      margin-top: 2vh;
      background-color: #fff;
      padding: 2vh;
      border-radius: 1vw;
      box-sizing: border-box;
      .shopName {
        display: flex;
        align-items: center;
        img {
          height: 20px;
          width: 20px;
        }
        .name {
          margin-left: 3vw;
        }
      }
      .goodsList {
        .goodsCard {
          display: flex;
          margin-top: 2vh;
          width: 100%;
          border-radius: 1vw;
          .goodsImg {
            img {
              height: 60px;
              width: 60px;
              border-radius: 2vw;
            }
          }
          .goodsInfo {
            margin-left: 3vw;
            flex: 1;
            display: flex;
            justify-content: space-around;
            flex-direction: column;
            .title {
              color: #4ba234;
              font-size: 14px;
              font-weight: 600;
            }
            .goodsPrice {
              font-size: 14px;
              display: flex;
              margin-top: 1vh;
              justify-content: space-between;
              align-items: center;
              span {
                color: #d73a2a;
                font-weight: 600;
              }
              .price {
                display: flex;
                align-items: center;
                font-size: 21px;
                .goodsNumber {
                  color: #333;
                  background-color: #f6f6f6;
                  padding: 1px 10px;
                  font-size: 12px;
                  margin: 0 8px;
                }
                div {
                  line-height: 27px;
                }
              }
            }
          }
        }
      }
      .serve {
        display: flex;
        font-size: 13px;
        justify-content: space-between;
        margin-top: 2vh;
      }
      .remarks {
        display: flex;
        font-size: 13px;
        justify-content: space-between;
        margin-top: 2vh;

        input {
          font-size: 13px;
        }
      }
    }
    .modeOfPayment {
      display: flex;
      background-color: #fff;
      margin-top: 2vh;
      border-radius: 1vw;
      height: 9vh;
      align-items: center;
      font-size: 13px;
      justify-content: space-between;
      padding: 0 6vw;

      div {
        display: flex;
      }
    }
  }
  .putInOrder {
    display: flex;
    font-size: 14px;
    align-items: center;
    background-color: #fff;
    height: 8vh;
    position: fixed;
    width: 100%;
    bottom: 0px;
    justify-content: flex-end;
    .price {
      margin-right: 5vw;
      span {
        color: #f73339;
      }
    }
    .btn {
      background-color: #2faf48;
      color: #fff;
      padding: 5px 10px;
      border-radius: 18px;
      margin-right: 5vw;
    }
  }
}
</style>
