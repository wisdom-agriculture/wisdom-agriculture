<template>
  <div class="myMessage">
    <div class="top">
      <u-icon
        size="18"
        name="arrow-left"
        color="#000000"
        @click="toPreviousPage(1)"
      ></u-icon>
      <div class="title">
        <span>消息</span>
        <u--image
          class="img"
          :src="require('../../static/img/clear.png')"
          shape="circle"
          width="15"
          mode="widthFix"
          @click="clearAll"
        ></u--image>
      </div>
    </div>
    <div class="middle">
      <div class="item">
        <div class="inner-wrap" @click="toOtherPage('beLoved')">
          <u--image
            :src="require('../../static/img/thumb-up.svg')"
            width="25"
            mode="widthFix"
          ></u--image>
        </div>
        <span class="num" v-if="beLovedCount">{{ beLovedCount }}</span>
        <span>点赞</span>
      </div>
      <div class="item">
        <div class="inner-wrap" @click="toOtherPage('beRemarked')">
          <u--image
            :src="require('../../static/img/message.svg')"
            width="25"
            mode="widthFix"
          ></u--image>
        </div>
        <span class="num" v-if="beCommentedCount">{{ beCommentedCount }}</span>
        <span>评论</span>
      </div>
      <div class="item">
        <div class="inner-wrap" @click="toOtherPage('beAited')">
          <u--image
            :src="require('../../static/img/notification.svg')"
            width="25"
            mode="widthFix"
          ></u--image>
        </div>
        <span class="num" v-if="beAitedCount">{{ beAitedCount }}</span>
        <span>@我的</span>
      </div>
    </div>
    <div class="content">
      <div class="user-info">
        <wkiwi-swipe-action
          :messagesList="messages"
          :options="options"
          @opened="opened"
          @cancelStick="cancelStick"
          @toStick="toStick"
          @deleteInfo="deleteInfo"
        ></wkiwi-swipe-action>
      </div>
    </div>
  </div>
</template>

<script>
import { toPreviousPage, toOtherPage } from "../../utils/pageJump";
import wkiwiSwipeAction from "../../components/wkiwi-swipe-action.vue";
let options1 = [
  {
    text: "添加置顶",
    style: {
      backgroundColor: "#e6e330",
    },
  },
  {
    text: "删除",
    style: {
      backgroundColor: "#ff3a30",
    },
  },
];
let options2 = [
  {
    text: "取消置顶",
    style: {
      backgroundColor: "#8e8e93",
    },
  },
  {
    text: "删除",
    style: {
      backgroundColor: "#ff3a30",
    },
  },
];
export default {
  components: {
    wkiwiSwipeAction,
  },
  data: () => ({
    options: options1,
    messages: [],
    beLovedCount: 0,
    beCommentedCount: 0,
    beAitedCount: 0,
  }),
  computed: {},
  methods: {
    toOtherPage,
    toPreviousPage,
    async getAllData() {
      let {
        data: {
          data: { userMessageResults },
        },
      } = await uni.$NoLoadhttp.get(`/api/user/messages`);
      userMessageResults = userMessageResults.map((item) => ({
        ...item,
        count: 0,
        stick: item.stick ? true : false,
        disabled: item.disabled ? true : false,
        options: item.stick ? options2 : this.options,
        url: [item.imgUrl],
      }));
      return userMessageResults;
    },
    clearAll() {
      // 发送情况消息请求
      console.log("clear all");
    },
    opened(index) {
      this.options = this.messages[index].stick == false ? options1 : options2;
      console.log("opended");
    },
    cancelStick(index) {
      this.messages[index].stick = false;
      this.messages.push(this.messages.splice(index, 1)[0]);
    },
    toStick(index) {
      this.messages[index].stick = true;
      this.messages.splice(1, 0, this.messages.splice(index, 1)[0]);
    },
    deleteInfo(index) {
      this.messages.splice(index, 1);
    },
  },
  watch: {},

  // 页面周期函数--监听页面加载
  onLoad() {
    // 设置消息未读数
    // uni.setStorageSync("mall", 3);
    // uni.setStorageSync("sys", 4);
    // uni.setStorageSync("Alice", 0);
    // uni.setStorageSync("my", 5);

    // 发请求获取数据

    uni.setStorageSync("love", 3);
    uni.setStorageSync("comment", 4);
    uni.setStorageSync("aite", 0);
  },
  // 页面周期函数--监听页面初次渲染完成
  onReady() {},
  // 页面周期函数--监听页面显示(not-nvue)
  async onShow() {
    this.beLovedCount = uni.getStorageSync("love");
    this.beCommentedCount = uni.getStorageSync("comment");
    this.beAitedCount = uni.getStorageSync("aite");
    // let messages = [
    //   {
    //     userId: 2,
    //     title: "商城消息",
    //     url: ["../../static/img/shop.svg"],
    //     message: "您购买的xxx发货了",
    //     stick: true,
    //     disabled: true,
    //     type: 2,
    //     time: "17:53",
    //     count: uni.getStorageSync("mall"),
    //   },
    //   {
    //     userId: 2,
    //     title: "张三",
    //     url: ["https://avatars.githubusercontent.com/u/35719531?s=48&v=4"],
    //     message: "这是一条系统消息",
    //     time: "15:15",
    //     count: uni.getStorageSync("sys"),
    //     stick: true, //是否为置顶状态
    //     disabled: false, //是否禁止滑动
    //     type: 2, //通知类型消息
    //     options: options2,
    //   },
    //   {
    //     userId: 2,
    //     title: "Alice",
    //     url: ["https://img-cdn-aliyun.dcloud.net.cn/dev/img/ext/git.png"],
    //     message: "这是一条消息任务",
    //     time: "15:15",
    //     count: uni.getStorageSync("Alice"),
    //     stick: false, //是否为置顶状态
    //     disabled: false, //是否禁止滑动
    //     type: 2, //通知类型消息
    //     options: options1,
    //   },
    //   {
    //     userId: 2,
    //     title: "马云",
    //     url: ["https://img-cdn-aliyun.dcloud.net.cn/dev/img/ext/git.png"],
    //     message: "什么鬼，我有支付宝[禁止滑动]",
    //     time: "15:15",
    //     count: uni.getStorageSync("my"),
    //     stick: false, //是否为置顶状态
    //     disabled: false, //是否禁止滑动
    //     type: 2, //普通用户类型消息
    //     options: options1,
    //   },
    // ];
    // console.log(messages);
    // this.messages = messages
    this.messages = await this.getAllData();
  },
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
.myMessage {
  height: 100%;
}
.content {
  .shop {
    margin: 20rpx 30rpx;
    display: flex;
    height: 120rpx !important;
    font-size: 28rpx;
    position: relative;
    .img {
      flex: 1;
    }
    .info {
      flex: 4;
      display: flex;
      flex-direction: column;
      margin-left: 20rpx;
      border-bottom: 5rpx solid #f9f9f9;

      span:last-child {
        margin-top: 20rpx;
        color: #999a9c;
        font-size: 24rpx;
      }
    }
    .time {
      flex: 0.5;
      color: #999a9c;
      font-size: 24rpx;
      border-bottom: 5rpx solid #f9f9f9;
    }
    .msg-num:last-child {
      background-color: red;
      width: 38rpx;
      height: 38rpx;
      line-height: 38rpx;
      text-align: center;
      font-size: 20rpx;
      color: #fff;
      border-radius: 50%;
      position: absolute;
      bottom: 25rpx;
      right: 10rpx;
    }
  }
  .user-info {
    padding: 0 15rpx;
  }
}
.middle {
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  padding: 10rpx 0 30rpx;
  border-bottom: 20rpx solid #fafbfe;
  .item {
    position: relative;
    .num {
      background-color: red;
      width: 40rpx;
      height: 40rpx;
      line-height: 40rpx;
      text-align: center;
      font-size: 26rpx;
      color: #fff;
      border-radius: 50%;
      position: absolute;
      top: 10rpx;
      right: -15rpx;
    }
    span {
      width: 100rpx;
      display: block;
      text-align: center;
      font-weight: 700;
      font-size: 24rpx;
    }
    .inner-wrap {
      margin: 20rpx 0 10rpx;
      width: 100rpx;
      height: 100rpx;
      border-radius: 8rpx;
      background-color: #84f1c5;
      box-sizing: border-box;
      padding: 25rpx;
    }
  }
  div:first-child {
    .inner-wrap {
      background-color: #34cffb;
    }
  }
  div:last-child {
    .inner-wrap {
      background-color: #ff572c;
    }
  }
}
.top {
  height: 100rpx;
  display: flex;
  padding: 30rpx 30rpx 0;
  .title {
    margin: 20rpx auto;
    display: flex;
    span {
      font-weight: 700;
      padding-right: 15rpx;
    }
    .img {
      margin-top: 7rpx;
    }
  }
}
</style>