<template>
  <view class="message-list">
    <block v-for="(it, i) of messagesList" :key="i">
      <view class="uni-swipe-action">
        <view
          class="uni-swipe-action__container"
          @touchstart="touchStart"
          @touchmove="touchMove"
          @touchend="touchEnd"
          @touchcancel="touchEnd"
          :style="{
            transform: messageIndex == i ? transformX : 'translateX(0px)',
            '-webkit-transform':
              messageIndex == i ? transformX : 'translateX(0px)',
          }"
          :data-index="i"
          :data-disabled="it.disabled"
        >
          <view
            class="uni-swipe-action__content"
            @click="toMessageDetail(i, it.type)"
          >
            <!-- 等lcq把接口写好在打开 -->
            <view class="item" :class="it.stick && i != 0 ? 'stick' : ''">
              <block v-if="it.type == 1">
                <view class="item-left">
                  <view class="avator"
                    ><view class="iconfont ic_system-news"></view
                  ></view>
                </view>
                <view class="item-middle">
                  <text class="title">{{ it.nickname }}</text>
                  <text class="message">{{ it.content }}</text>
                </view>
                <view class="item-right">
                  <view class="time">{{ formatDate2(it.time) }}</view>
                  <view class="mark" v-if="it.count > 0">{{ it.count }}</view>
                </view>
              </block>
              <block v-if="it.type == 2 || it.type == 3">
                <view class="item-left">
                  <avator-group :type="it.type" :avator="it.url"></avator-group>
                </view>
                <view class="item-middle">
                  <text class="title">{{ it.nickname }}</text>
                  <text class="message">{{ it.content }}</text>
                </view>
                <view class="item-right">
                  <view class="time">{{ formatDate2(it.time) }}</view>
                  <view class="mark" v-if="it.count > 0">{{ it.count }}</view>
                </view>
              </block>
            </view>
          </view>
          <view class="uni-swipe-action__btn-group" :id="elId">
            <div
              v-for="(item, index) in options"
              :key="index"
              class="uni-swipe-action--btn"
              :style="{
                backgroundColor:
                  item.style && item.style.backgroundColor
                    ? item.style.backgroundColor
                    : '#C7C6CD',
                color:
                  item.style && item.style.color ? item.style.color : '#FFFFFF',
                fontSize:
                  item.style && item.style.fontSize
                    ? item.style.fontSize
                    : '28upx',
              }"
              @click="bindClickBtn(item, i)"
            >
              {{ item.text }}
            </div>
          </view>
        </view>
      </view>
    </block>
  </view>
</template>

<script>
import avatorGroup from "./avator-group.vue";
export default {
  name: "wkiwi-swipe-action",
  props: {
    options: Array,
    messagesList: Array,
  },
  components: { avatorGroup },
  data() {
    const elId = `Uni_${Math.ceil(Math.random() * 10e5).toString(36)}`;
    return {
      elId: elId,
      transformX: "translateX(0px)",
      messageIndex: -1,
    };
  },
  created() {
    this.direction = "";
    this.startX = 0;
    this.startY = 0;
    this.btnGroupWidth = 0;
    this.isMoving = false;
  },
  // #ifdef H5
  mounted() {
    this.getSize();
  },
  // #endif
  // #ifndef H5
  onReady() {
    this.getSize();
  },
  // #endif
  methods: {
    toMessageDetail(i, type) {
      if (type == 2) {
        console.log("进入到聊天界面" + i);
        if (i == 0) {
          uni.navigateTo({
            url: "../../pages/mallMsgDetail/index",
          });
        } else {
          uni.navigateTo({
            url:
              "../../pages/detailMsg/index?title=" +
              `${this.messagesList[i].nickname}` +
              "&&imgUrl=" +
              `${this.messagesList[i].imgUrl}` +
              "&&userId=" +
              `${this.messagesList[i].id}`,
          });
        }
      } else if (type == 1) {
        console.log("进入系统消息界面" + i);
      }
    },
    getSize() {
      setTimeout(() => {
        uni
          .createSelectorQuery()
          .in(this)
          .select(`#${this.elId}`)
          .boundingClientRect()
          .exec((ret) => {
            this.btnGroupWidth = ret[0].width;
          });
      }, 1000);
    },
    async bindClickBtn(item, index) {
      this.messageIndex = -1;
      console.log(item.text + "message第" + index + "项");
      if (item.text == "取消置顶") {
        // 发取消置顶请求,成功后取消置顶
        await this.stick(0, "cancelStick", index);
      } else if (item.text == "删除") {
        this.$emit("deleteInfo", index);
        // 发删除请求删除该项
      } else {
        // 发送设置置顶请求,成功后设置置顶
        await this.stick(1, "toStick", index);
      }
    },
    async stick(val, event, index) {
      let {
        data: { code },
      } = await uni.$NoLoadhttp.post("/api/stick", {
        id: this.messagesList[index].id,
        stick: val,
      });
      console.log(code);
      if (code == "00000") {
        val == 1 ? this.$emit(event, index) : this.$emit(event, index);
      }
    },
    touchStart(event) {
      if (event.currentTarget.dataset.disabled === true) {
        return;
      }
      this.startX = event.touches[0].pageX;
      this.startY = event.touches[0].pageY;
    },
    touchMove(event) {
      if (
        this.direction === "Y" ||
        event.currentTarget.dataset.disabled === true
      ) {
        this.direction = "";
        return;
      }
      var moveY = event.touches[0].pageY - this.startY,
        moveX = event.touches[0].pageX - this.startX;
      if (
        (!this.isMoving && Math.abs(moveY) > Math.abs(moveX)) ||
        Math.abs(moveY) > 100 ||
        Math.abs(moveX) < 50
      ) {
        //纵向滑动//参数100与50可调节侧滑灵敏度
        this.direction = "Y";
        return;
      }
      this.direction = moveX > 0 ? "right" : "left";
      this.messageIndex = moveX < 0 ? event.currentTarget.dataset.index : -1;
      this.isMoving = true;
    },
    touchEnd(event) {
      this.isMoving = false;
      if (this.direction !== "right" && this.direction !== "left") {
        this.direction = "";
        return;
      }
      if (this.direction == "right") {
        this.messageIndex = -1;
      }
      this.endMove(event);
    },
    endMove(event) {
      if (
        this.direction === "Y" ||
        event.currentTarget.dataset.disabled === true
      ) {
        this.direction = "";
        return;
      }
      if (this.messageIndex !== -1) {
        this.transformX = `translateX(${-this.btnGroupWidth}px)`;
        this.$emit("opened", this.messageIndex);
      } else {
        this.transformX = "translateX(0px)";
        this.$emit("closed");
      }
      this.direction = "";
    },
  },
  computed: {
    formatDate2(timestamp) {
      return (timestamp) => {
        let dt = new Date(timestamp);
        let hh = (dt.getHours() + "").padStart(2, "0");
        let mm = (dt.getMinutes() + "").padStart(2, "0");
        return hh + ":" + mm;
      };
    },
  },
};
</script>

<style lang="scss">
.uni-swipe-action {
  width: 100%;
  overflow: hidden;
  &__container {
    background-color: #ffffff;
    width: 200%;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    transition: transform 350ms cubic-bezier(0.165, 0.84, 0.44, 1);
  }
  &__content {
    width: 50%;
  }
  &__btn-group {
    display: flex;
    flex-direction: row;
  }
  &--btn {
    padding: 0 32upx;
    color: #ffffff;
    background-color: #c7c6cd;
    font-size: 28upx;
    display: inline-flex;
    text-align: center;
    flex-direction: row;
    align-items: center;
  }
}
.item {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  padding: 10upx 16upx;
  background-color: #fff;
  border-bottom: 1px solid rgb(243, 243, 243);
  &.stick {
    border-left: 8rpx solid #2faf48;
  }
  .item-left {
    text-align: center;
    .image {
      width: 100upx;
      height: 100upx;
      border-radius: 50upx;
      background-color: #eee;
    }
    .avator {
      width: 100upx;
      height: 100upx;
      text-align: center;
      line-height: 100upx;
      background: #4191ea;
      border-radius: 50%;
      overflow: hidden;
      .iconfont {
        width: 100upx;
        height: 100upx;
        text-align: center;
        line-height: 100upx;
        color: #fff;
        font-size: 40upx;
      }
    }
  }
  .item-middle {
    display: flex;
    flex: 1;
    flex-wrap: wrap;
    flex-direction: row;
    justify-content: flex-start;
    margin-left: 15upx;
    overflow: hidden;
    .title {
      width: 100%;
      color: #000;
      font-family: "微软雅黑";
      font-weight: 400;
      font-size: 30upx;
      height: 50upx;
      line-height: 60upx;
      overflow: hidden; /*自动隐藏文字*/
      text-overflow: ellipsis; /*文字隐藏后添加省略号*/
      white-space: nowrap; /**强制不换行*/
    }
    .message {
      width: 100%;
      font-family: "微软雅黑";
      color: #808080;
      margin-top: 8rpx;
      height: 50upx;
      line-height: 40upx;
      font-size: 24upx;
      overflow: hidden; /**自动隐藏文字*/
      text-overflow: ellipsis; /**文字隐藏后添加省略号*/
      white-space: nowrap; /**强制不换行*/
    }
  }
  .item-right {
    overflow: hidden;
    display: flex;
    flex-direction: column;
    align-items: center;
    .time {
      color: #808080;
      font-size: 18upx;
      height: 60upx;
      line-height: 60upx;
    }
    .mark {
      background-color: #f74c31;
      line-height: 35upx;
      text-align: center;
      font-size: 18upx;
      min-width: 35upx;
      min-height: 35upx;
      border-radius: 18upx;
      color: #fff;
    }
  }
}
</style>
Footer
© 2022 GitHub, Inc.
Footer navigation
Terms
Privacy
Security
Status
Docs
Contact GitHub
Pricing
API
Training
Blog
About
 Octotree
hj196264796 (free user)
