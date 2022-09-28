<template>
  <view class="personal">
    <view class="bg">
      <view class="menu">
        <view class="icon" @click="Menushow = !Menushow">
          <u-icon name="list" class="menuIcon" color="#fff" size="26"></u-icon>
        </view>
        <u-transition :show="Menushow" mode="fade">
          <view class="menuList">
            <view class="menuItem" @click="toOtherPage('userInfoChange')"
              >信息修改</view
            >
            <view class="menuItem">关于我们</view>
            <view class="menuItem">版本更新</view>
          </view>
        </u-transition>
      </view>
    </view>
    <view class="content">
      <view class="user_card">
        <view class="details_top">
          <!-- 用户的文字信息 昵称及描述 -->
          <view class="user_text_details">
            <view class="user_name">
              <h3>{{ userDetails.nickname }}</h3>
            </view>
            <view class="user_motto">
              <span class="overflowOmit">{{ userDetails.signature }}</span>
            </view>
          </view>
          <view class="user_headImg">
            <img :src="userDetails.imgUrl" />
          </view>
        </view>
        <view class="details_bottom">
          <view v-for="(item, index) in oneselfOperateItems" :key="index">
            <view class="item" @click="toOtherPage(item.path)">
              <view class="figure">{{ item.data }}</view>
              <view class="content_text">{{ item.text }}</view>
            </view>
          </view>
        </view>
      </view>
      <view class="operate">
        <h5>常用操作</h5>
        <view class="operate_item">
          <view
            v-for="(item, index) in operateItems"
            :key="index"
            class="item_card"
          >
            <view class="item_text" @click="toOtherPage(item.address)">
              <view class="item_title">{{ item.title }}</view>
              <view class="item_describe">{{ item.describe }}</view>
            </view>
            <view class="item_icon">
              <image :src="item.icon"></image>
            </view>
          </view>
        </view>
      </view>

      <view class="quit">
        <button @click="toOtherPage('login')">退出登录</button>
      </view>
    </view>
  </view>
</template>

<script>
import { toOtherPage, toTabPage } from "../../utils/pageJump";
export default {
  components: {},
  data: () => ({
    // 用户信息
    userDetails: {
      farm: "",
      imgUrl: "",
      nickname: "",
      phone: "",
      signature: "",
    },
    // 操作列表
    operateItems: [
      {
        title: "点赞圈子",
        describe: "查看历史点赞记录",
        icon: "../../static/icon/heart.svg",
        address: "beLoved",
      },
      {
        title: "收货地址",
        describe: "更改收获地址",
        icon: "../../static/icon/truck.svg",
        address: "shippingAddress",
      },
      {
        title: "我的圈子",
        describe: "查看历史发圈记录",
        icon: "../../static/icon/track.svg",
        address: "myCircle",
      },
      {
        title: "我的消息",
        describe: "快速查看各种消息",
        icon: "../../static/icon/chat.svg",
        address: "myMessage",
      },
    ],
    // 粉丝，关注，点赞记录页
    oneselfOperateItems: [
      { data: 0, text: "关注", path: "attention" },
      { text: "粉丝", data: 0, path: "follow" },
      { text: "被赞", data: 0, path: "beAited" },
    ],
    Menushow: false,
  }),
  computed: {},
  methods: {
    toOtherPage,
    toTabPage,
    async getUserDetails() {
      const { data: res } = await uni.$http.get("/api/users/details");
      if (res.code !== "00000") {
        return uni.$u.toast(res.message);
      }
      let {
        nickname,
        signature,
        imgUrl,
        farm,
        phone,
        subscribeNum,
        fanNum,
        getLikeNum,
      } = res.data;
      this.userDetails.nickname = nickname ? nickname : "普通用户";
      this.userDetails.signature = signature
        ? signature
        : "采菊东篱下, 悠然见南山";
      this.userDetails.imgUrl = imgUrl
        ? imgUrl
        : "../../static/img/user_head.png";
      this.userDetails.farm = farm ? farm : "明理农场";
      this.userDetails.phone = phone ? phone : "13734174131";
      this.oneselfOperateItems[0].data = subscribeNum ? subscribeNum : 0;
      this.oneselfOperateItems[1].data = fanNum ? fanNum : 0;
      this.oneselfOperateItems[2].data = getLikeNum ? getLikeNum : 0;
      console.log(this.userDetails);
    },
  },
  watch: {},

  // 页面周期函数--监听页面加载
  onLoad() {
    this.getUserDetails();
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
.personal {
  background-color: #efeff4;
  height: calc(100%);
  .bg {
    height: 360rpx;
    background-color: #2faf48;
    width: 100%;
    border-radius: 0rpx 0rpx 50rpx 50rpx;
  }
}
.content {
  position: absolute;
  top: 240rpx;
  left: 50%;
  transform: translateX(-50%);
  width: 90%;
  height: 880rpx;
  .user_card {
    width: 100%;
    background-color: #fff;
    height: 260rpx;
    border-radius: 26rpx;
    .details_top {
      display: flex;
      justify-content: space-around;
      height: 166rpx;
      .user_text_details {
        margin-top: 30rpx;
        height: 120rpx;
        .user_name {
          display: flex;
          align-items: center;
          justify-content: space-between;
          width: 240rpx;
          .grade {
            display: flex;
            align-items: center;
            justify-content: center;
            width: 80rpx;
            height: 30rpx;
            background-color: #af52de;
            border-radius: 15rpx;
            box-shadow: 0rpx 0rpx 4rpx rgba(0, 0, 0, 0.5);
            image {
              width: 26rpx;
              height: 26rpx;
            }
            span {
              font-size: 16rpx;
              color: #fff;
              font-weight: 500;
            }
          }
        }
        .operation {
          margin-left: 16rpx;
          image {
            width: 32rpx;
            height: 32rpx;
          }
        }
        .user_motto {
          display: flex;
          margin-top: 20rpx;
          width: 300rpx;
          span {
            color: gray;
            font-size: 14rpx;
            font-weight: 600;
          }
        }
      }
    }
    .user_headImg {
      width: 160rpx;
      height: 160rpx;
      border-radius: 50%;
      transform: translate(0, -40%);
      position: relative;
      img {
        height: 100%;
        width: 100%;
        border-radius: 50%;
      }
    }
  }
}
.details_bottom {
  display: flex;
  justify-content: space-around;
  .item {
    font-size: 12rpx;
    text-align: center;
    .content_text {
      color: #8e8e93;
    }
    .figure {
      font-weight: 600;
    }
  }
}
.operate {
  margin-top: 50rpx;
  h5 {
    color: #333333;
  }
  .operate_item {
    display: flex;
    flex-wrap: wrap;
    margin-top: 10rpx;
    justify-content: space-around;
    .item_card {
      width: 310rpx;
      height: 120rpx;
      background-color: #fff;
      margin-left: 10rpx;
      margin-top: 10rpx;
      border-radius: 10rpx;
      display: flex;
      align-items: center;
      justify-content: space-around;
      margin-top: 20rpx;
      .item_text {
        font-size: 12rpx;
        .item_title {
          font-weight: 700;
        }
      }
      .item_icon {
        width: 64rpx;
        height: 64rpx;
      }
    }
  }
}
.quit {
  margin-top: 150rpx;
  button {
    width: 400rpx;
    background-color: #2faf48;
    font-size: 32rpx;
    color: #fff;
  }
}
.menu {
  position: absolute;
  right: 40rpx;
  top: 60rpx;
  z-index: 999;
  .menuIcon {
    position: absolute;
    right: 0px;
  }
  .menuList {
    background-color: #fff;
    font-size: 14px;
    transform: translate(0rpx, 60rpx);
    .menuItem {
      padding: 16rpx 10rpx;
    }
  }
}
</style>
