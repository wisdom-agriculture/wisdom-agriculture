<template>
  <view id="login_pages">
    <!-- 用来显示消息提示 -->
    <u-toast ref="uToast"></u-toast>
    <view id="bg_circle">
      <view class="circle_middle"></view>
      <view class="circle_small"></view>
      <view class="circle_big"></view>
    </view>
    <view id="login_form">
      <view class="title"><h3>欢迎登录</h3></view>
      <view class="describe">
        <p>加入体验现实版开心农场</p>
        <p>享受种植、收获、购买、溯源等一站式服务！</p>
      </view>
      <view class="form">
        <view class="form_item">
          <view class="icon">
            <img
              src="https://img.icons8.com/external-tanah-basah-detailed-outline-tanah-basah/48/undefined/external-phone-user-interface-tanah-basah-detailed-outline-tanah-basah-2.png"
            />
          </view>
          <view class="input_box">
            <input
              class="form_input phoneInput"
              placeholder="请输入手机号"
              v-model="phone"
            />
          </view>
        </view>
        <view class="form_item">
          <view class="icon">
            <img src="https://img.icons8.com/windows/32/undefined/shield.png" />
          </view>
          <view class="input_box">
            <input
              class="form_input input_small"
              placeholder="请输入验证码"
              v-model="code"
            />
            <view class="code_slot" @click="getCode" @tap="getCode">
              {{ tips }}
            </view>
            <u-code
              :seconds="seconds"
              ref="uCode"
              @change="codeChange"
            ></u-code>
          </view>
        </view>
        <view class="informed_consent">
          登录代表同意<a @click="toOtherPage('informed')">用户隐私协议</a>
        </view>
        <view class="login_box">
          <button @click="toHome" :class="[isActivation ? 'isActivation' : '']">
            登录
          </button>
        </view>
      </view>
    </view>
    <view id="third_party_login">
      <view class="third_text">第三方登录</view>
      <view class="icon_list">
        <view class="weixin">
          <u-icon name="weixin-circle-fill" color="#01c465" size="38"></u-icon>
        </view>
        <view class="qq">
          <u-icon name="qq-circle-fill" color="#2979fe" size="38"></u-icon>
        </view>
        <view class="weibo">
          <u-icon name="weibo-circle-fill" color="#f96654" size="38"></u-icon>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import { toOtherPage, toTabPage } from "../../utils/pageJump";
import phoneValidate from "../../utils/phoneValidate";
export default {
  data() {
    return {
      phone: "",
      tips: "",
      seconds: 10,
      code: "",
    };
  },
  onLoad() {},
  methods: {
    toOtherPage,
    toTabPage,
    codeChange(text) {
      this.tips = text;
    },
    async getCode() {
      if (this.phone == "") {
        uni.$u.toast("请填写手机号");
        return;
      }
      if (!phoneValidate(this.phone)) {
        uni.$u.toast("手机号格式错误");
        return;
      }
      if (this.$refs.uCode.canGetCode) {
        // 模拟向后端请求验证码
        uni.showLoading({
          title: "正在获取验证码",
        });
        const { data: res } = await uni.$http.get("/api/verification/codes", {
          phone: this.phone,
        });
        if (res.code !== "00000") {
          return uni.$u.toast(res.message);
        }
        uni.hideLoading();
        // 这里此提示会被this.start()方法中的提示覆盖
        console.log(res);
        uni.$u.toast(res.message);
        // 通知验证码组件内部开始倒计时
        this.$refs.uCode.start();
      } else {
        uni.$u.toast("倒计时结束后再发送");
      }
    },
    async toHome() {
      uni.showLoading({
        title: "正在登录",
      });
      const { data: res } = await uni.$http.post("/api/app/login", {
        phone: this.phone,
        code: this.code,
      });
      if (res.code !== "00000") {
        return uni.$u.toast(res.message);
      }

      uni.hideLoading();
      console.log(res);
      let token = res.data.token;
      uni.setStorageSync("token", token);
      uni.$u.toast("登录成功,等待跳转");
      setTimeout(() => {
        this.toTabPage("home");
      }, 1000);
    },
  },
  computed: {
    isActivation() {
      if (this.phone !== "" && this.code !== "") {
        return true;
      } else {
        return false;
      }
    },
  },
};
</script>

<style lang="scss" scoped>
#bg_circle {
  height: 240rpx;
  width: 100%;
  display: flex;
  align-items: center;
  .circle_small {
    background-color: #2faf48;
    width: 20rpx;
    height: 20rpx;
    border-radius: 50%;
    margin-left: 50rpx;
    margin-top: 180rpx;
  }
  .circle_middle {
    background-color: #2ee753;
    width: 50rpx;
    height: 50rpx;
    border-radius: 50%;
    margin-left: 30rpx;
  }
  .circle_big {
    background-color: #0ab12b;
    width: 200rpx;
    height: 200rpx;
    border-radius: 50%;
    position: absolute;
    top: -40rpx;
    right: -40rpx;
  }
}
#login_form {
  margin: 20rpx 40rpx 0rpx;
  .describe {
    color: gray;
    font-weight: 500;
    font-size: 27rpx;
    width: 88%;
    margin: 40rpx auto 0;
    p {
      &:nth-child(2) {
        text-indent: 2em;
      }
    }
  }
  .form {
    .form_item {
      height: 100rpx;
      display: flex;
      align-items: center;
      .icon {
        margin: 0 20rpx;
        img {
          display: block;
          height: 50rpx;
          width: 50rpx;
        }
      }
      .input_box {
        width: 80%;
        border-bottom: 1px solid rgb(173, 166, 166);
        display: flex;
        justify-content: space-around;
        align-items: center;
        .uni-input-placeholder {
          font-size: 14rpx;
        }
        .phoneInput {
          width: 100%;
          padding-left: 18rpx;
        }
        .form_input {
          height: 60rpx;
          .uni-input-input {
            padding-left: 20rpx;
          }
        }
        .code_slot {
          font-size: 14rpx;
          color: #19b939;
          font-weight: 600;
        }
      }
    }
    .input_small {
      .uni-input-wrapper {
        width: 80%;
      }
    }
    .informed_consent {
      font-size: 14rpx;
      font-weight: 600;
      text-align: right;
      margin-right: 44rpx;
      margin-top: 16rpx;
      letter-spacing: 1px;
      a {
        color: #3575d4;
        text-decoration: none;
      }
    }
    .login_box {
      margin-top: 160rpx;
      uni-button {
        background-color: #c7c7cc;
        color: white;
        color: 16rpx;
        width: 520rpx;
      }
      .isActivation {
        background-color: #0ab12b;
      }
    }
  }
}
#third_party_login {
  margin-top: 80rpx;
  .third_text {
    width: 500rpx;
    display: flex;
    align-items: center;
    justify-content: space-around;
    text-align: center;
    margin: 0 auto;
    font-size: 12rpx;
    font-weight: 500;
    color: #8e8e93;
    &::before {
      content: "";
      width: 34%;
      height: 1rpx;
      border-bottom: 2rpx solid #8e8e93;
      display: block;
    }
    &::after {
      content: "";
      width: 34%;
      height: 1rpx;
      border-bottom: 2rpx solid #8e8e93;
      display: block;
    }
  }
  .icon_list {
    margin: 30rpx auto;
    width: 500rpx;
    display: flex;
    justify-content: space-around;
  }
}
</style>
