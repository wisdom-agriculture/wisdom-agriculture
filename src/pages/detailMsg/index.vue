<template>
  <div class="detail-msg" ref="chat">
    <div class="message-height">
      <div v-for="(item, i) in messageData" :key="i">
        <left-message
          v-if="item.fromId == userId"
          class="item"
          :data="item.content"
          :imgUrl="imgUrl"
          :userId="userId"
        ></left-message>
        <right-message
          v-else
          class="item"
          :data="item.content"
          :imgUrl="item.imgUrl"
        ></right-message>
      </div>
    </div>
    <div class="footer">
      <sendMessage @updatecon="getAllData" :userId="userId"></sendMessage>
    </div>
  </div>
</template>

<script>
import leftMessage from "./components/left-message.vue";
import rightMessage from "./components/right-message.vue";
import sendMessage from "./components/send-message.vue";
export default {
  components: { leftMessage, rightMessage, sendMessage },
  data() {
    return {
      // messageData: messageData.messagelist,
      messageData: [],
      pageHeight: 0,
      windowHeight: 0,
      imgUrl: "",
      userId: 0,
    };
  },
  onReady() {
    this.toBottem();
  },
  async onLoad(options) {
    this.title = options.title;
    this.imgUrl = options.imgUrl;
    this.userId = options.userId;
    uni.setNavigationBarTitle({ title: this.title });
    await this.getAllData();
    let _this = this;
    uni.getSystemInfo({
      success: function (res) {
        _this.windowHeight = res.windowHeight;
      },
    });
    uni.onWindowResize((res) => {
      uni.pageScrollTo({
        scrollTop: _this.pageHeight - res.size.windowHeight,
        duration: 300,
      });
    });
    this.toBottem();
  },
  methods: {
    // addright(msgobj) {
    //   console.log(msgobj);
    //   if (msgobj.type == 0) {
    //     this.messageData.push({ type: 1, message: msgobj.value });
    //   } else {
    //     this.messageData.push({
    //       type: 1,
    //       message: `<img
    //       src=${msgobj.url}
    //       class="img"
    //     ></img>`,
    //     });
    //   }

    // this.toBottem();
    // },
    toBottem() {
      setTimeout(() => {
        let _this = this;
        this.$refs.chat.scrollTop = this.$refs.chat.scrollHeight;
      }, 0);
    },
    async getAllData() {
      let {
        data: {
          data: { conversationResultList },
        },
      } = await uni.$NoLoadhttp.get("/api/conversation?id=" + this.userId);
      this.messageData = conversationResultList;
      this.toBottem();
    },
  },
};
</script>

<style lang="scss" scoped>
.detail-msg {
  width: 100%;
  height: 100%;
  background: #fafbfe;
  overflow: scroll;
  padding-bottom: 100upx;
}
.messagelist {
  .message-height {
    padding: 30upx 0 130upx 0;
  }
}
.item {
  margin: 25rpx 0;
}
.footer {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 100upx;
}
</style>