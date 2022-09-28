<template>
  <div class="addAddr">
    <div class="info">
      <span>收货人</span>
      <u--input
        class="input"
        v-model="addrInfo.name"
        placeholder="名字"
        border="surround"
        :customStyle="npStyle"
        color="#6c6c6c"
      ></u--input>
    </div>
    <div class="info">
      <span>手机号码</span>
      <u--input
        class="input"
        placeholder="手机号"
        v-model="addrInfo.phone"
        border="surround"
        :customStyle="npStyle"
        color="#6c6c6c"
      ></u--input>
    </div>
    <div class="info">
      <span>收货地址</span>
      <u--textarea
        placeholder="省、市、区、街道、小区楼栋/乡村名称"
        class="input"
        border="surround"
        v-model="addrInfo.address"
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
  data: () => ({
    addrInfo: {
      name: "",
      phone: "",
      address: "",
    },
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
    async save() {
      // 发送新增请求
      if (
        this.addrInfo.name == "" ||
        this.addrInfo.phone == "" ||
        this.addrInfo.address == ""
      ) {
        uni.showToast({
          title: "请完善信息",
          duration: 1500,
          icon: "error",
        });
      } else {
        let {
          data: { code },
        } = await uni.$http.post("/api/user/address", {
          userName: this.addrInfo.name,
          userPhone: this.addrInfo.phone,
          address: this.addrInfo.address,
        });
        // console.log(data);

        // 成功跳转回上一页
        if (code == "00000") {
          this.toPreviousPage();
        }
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.addAddr {
  padding-top: 90rpx !important;
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
    margin: 130rpx auto;
  }
  .uni-input-placeholder,
  .uni-textarea-placeholder {
    font-size: 26rpx !important;
    color: #6c6c6c !important;
  }
}
</style>