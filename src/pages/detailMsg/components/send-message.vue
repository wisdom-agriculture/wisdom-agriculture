<template>
  <div class="bottom">
    <u--input
      border="surround"
      v-model="value"
      :customStyle="cstyle"
    ></u--input>
    <!-- <u-icon
      @click="sendPicture"
      class="icon"
      size="28"
      name="photo-fill"
      color="#444444"
    ></u-icon> -->
    <span @click="sendComment">发送</span>
  </div>
</template>

<script>
export default {
  name: "send-message",
  props: ["userId"],
  data: () => ({
    cstyle: {
      fontSize: "26rpx",
      backgroundColor: "#fff",
      height: "35rpx",
    },
    fileList: [],
    value: "",
  }),
  methods: {
    uploadAndSend() {
      // 发送图片
      uni.uploadFile({
        url: "http://localhost:8080/api/zones",
        files: this.fileList,
        header: {
          Authorization: uni.getStorageSync("token"),
        },
        formData: { content: this.content },
        success: ({ data }) => {
          if (JSON.parse(data).code == "00000") {
            uni.showToast({
              title: "上传成功",
              icon: "success",
              duration: 3000,
              success: () => {
                console.log("success");
              },
            });
          } else {
            uni.showToast({
              title: "上传失败,请重新上传",
              icon: "error",
              duration: 3000,
            });
          }
        },
        fail: (err) => {
          console.log(err);
        },
      });
    },
    sendPicture() {
      console.log("发送图片");
      uni.chooseImage({
        count: 1, //先单张上传，多张的话，后面流程代码需自己优化
        sizeType: ["original", "compressed"],
        sourceType: ["album"],
        success: (res) => {
          console.log("选择的图片", res);
          this.fileList.push(res.tempFiles[0]);
          //   上传图片
          // this.uploadAndSend();
          // 上传图片成功后呈现图片样式,无接口否则url不应该写死
          this.$emit("addright", {
            type: 1,
            url: "https://fc1tn.baidu.com/it/u=3366718740,3845909803&fm=202&src=780&ernie_sim_online&mola=new&crop=v1",
          });
        },
        fail: () => {
          console.log("取消");
        },
      });
    },
    async sendComment() {
      //   调用接口发送成功后
      let {
        data: { code },
      } = await uni.$NoLoadhttp.post("/api/messages", {
        toId: this.userId,
        content: this.value,
      });
      if (code == "00000") {
        this.$emit("updatecon");
        this.value = "";
      }
    },
  },
};
</script>

<style lang="scss" scoped="true">
.bottom {
  border-top: 7rpx solid #fafbfe;
  position: fixed;
  background-color: #ececec;
  bottom: 0;
  left: 0;
  display: flex;
  align-items: center;
  margin-top: 20rpx;
  padding: 20rpx 40rpx;
  width: 85%;
  // .icon {
  //   padding-left: 20rpx;
  // }
  span {
    font-size: 30rpx;
    padding-left: 30rpx;
  }
}
</style>