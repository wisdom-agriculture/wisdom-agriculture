<template>
  <div class="release">
    <div class="top">
      <span @click="reBack"> 取消 </span>
      <span> 发送动态 </span>
      <span @click="release"> 发布 </span>
    </div>
    <div class="text">
      <textarea
        placeholder-style="font-size:25rpx;color:#e5e5e4;"
        v-model="content"
        type="textarea"
        placeholder="输入内容  (最多1000字)"
        maxlength="1000"
        auto-height="true"
      >
      </textarea>
    </div>
    <div class="img-wrap">
      <u-upload
        @delete="removeImg"
        @afterRead="afterRead"
        :fileList="fileList"
        :auto-upload="false"
        maxCount="9"
        width="100"
        height="100"
      ></u-upload>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
  data() {
    return {
      fileList: [],
      content: "",
    };
  },
  methods: {
    reBack() {
      uni.navigateBack({
        delta: 1,
      });
    },
    afterRead(file) {
      this.fileList.push(file.file);
    },
    removeImg(event) {
      this[`fileList${event.name}`].splice(event.index, 1);
    },
    async sendParam() {
      if (this.content.length > 0) {
        await uni.uploadFile({
          url: "/api/zones",
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
                  setTimeout(() => {
                    uni.redirectTo({
                      url: "./../myCircle/index",
                    });
                  }, 3000);
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
      } else {
        uni.showToast({
          title: "文字不能为空",
          icon: "error",
          duration: 3000,
        });
      }
    },
    async release() {
      if (this.fileList.length > 0) {
        this.sendParam();
      } else {
        uni.showToast({
          title: "图片不能为空",
          icon: "error",
          duration: 3000,
        });
      }
    },
  },
};
</script>
<style lang="scss" scoped>
.top {
  width: 100%;
  height: 140rpx;
  display: flex;
  justify-content: space-around;
  align-items: center;
  font-size: 22rpx;
  color: gray;
  span:nth-child(2) {
    font-size: 26rpx;
    font-weight: 700;
    color: black;
  }
  span:last-child {
    color: #64a859;
  }
}
.text {
  margin: 0 50rpx;
  height: 400rpx;
  textarea {
    width: 100%;
    font-size: 22rpx;
  }
}
.img-wrap {
  margin: 0 40rpx;
  display: flex;
  align-items: center;
  .text {
    padding: 0 20rpx;
  }
}
</style>
