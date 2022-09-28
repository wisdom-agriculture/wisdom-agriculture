<template>
  <div class="editAddr">
    <div class="del" @click="deleteAddr">删除</div>
    <div class="info">
      <span>收货人</span>
      <u--input
        class="input"
        v-model="addrInfo.userName"
        border="surround"
        :customStyle="npStyle"
        color="#7d7f80"
      ></u--input>
    </div>
    <div class="info">
      <span>手机号码</span>
      <u--input
        class="input"
        v-model="addrInfo.userPhone"
        border="surround"
        :customStyle="npStyle"
        color="#7d7f80"
      ></u--input>
    </div>
    <div class="info">
      <span>收货地址</span>
      <u--textarea
        class="input"
        color="#7d7f80"
        border="surround"
        v-model="addrInfo.userShoppingAddress"
        :customStyle="addrStyle"
        confirmType="done"
      ></u--textarea>
    </div>
    <div class="btn">
      <u-button color="#2faf48" @click="save">保存</u-button>
    </div>
  </div>
</template>

<script>
import { toPreviousPage } from "../../utils/pageJump";
export default {
  onLoad(options) {
    let addrInfo = JSON.parse(decodeURIComponent(options.addrInfo));
    this.addrInfo = addrInfo;
  },
  data: () => ({
    addrInfo: {},
    npStyle: {
      backgroundColor: "#fff",
      height: "40rpx",
      borderColor: "#c0c0c0 !important",
      borderRadius: "0",
    },
    addrStyle: {
      backgroundColor: "#fff",
      height: "120rpx",
      borderColor: "#c0c0c0 !important",
      borderRadius: "0",
    },
  }),
  methods: {
    toPreviousPage,
    async deleteAddr() {
      uni.showModal({
        title: "提示",
        content: "是否确认删除此地址",
        success: async (res) => {
          if (res.confirm) {
            // 发请求删除地址,跳转到收货地址页
            let {
              data: { code },
            } = await uni.$http.delete(
              `/api/user/address?id=${this.addrInfo.id}`
            );
            // 成功跳转回上一页
            if (code == "00000") {
              this.toPreviousPage();
            }
          } else if (res.cancel) {
            // 用户取消
          }
        },
      });
    },
    async save() {
      // 发送修改请求
      console.log(this.addrInfo);
      let {
        data: { code },
      } = await uni.$http.put("/api/user/address", {
        id: this.addrInfo.id,
        userName: this.addrInfo.userName,
        userPhone: this.addrInfo.userPhone,
        address: this.addrInfo.userShoppingAddress,
      });
      console.log(code);
      // 成功跳转回上一页
      if (code == "00000") {
        this.toPreviousPage();
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.editAddr {
  background-color: #efeff4;
  height: 100%;
  padding: 30rpx 30rpx 0 20rpx;
  .del {
    color: red;
    font-weight: 700;
    text-align: right;
    margin-bottom: 30rpx;
  }
  .info {
    display: flex;
    align-items: center;
    margin-bottom: 50rpx;
    span {
      flex: 2;
      padding-right: 20rpx;
      text-align: right;
    }
    .input {
      flex: 6;
    }
  }
  .btn {
    width: 70%;
    // margin: 130rpx 0 130rpx 110rpx;
    margin: 130rpx auto;
  }
}
</style>