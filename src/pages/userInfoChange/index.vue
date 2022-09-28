<template>
  <div class="userInfoChange">
    <user-info leftText="头像" cardHeight="100">
      <img
        :src="userDetails.imgUrl"
        alt="头像"
        @click="afterRead"
        class="userHeadImg"
      />
    </user-info>
    <div>
      <user-info leftText="昵称">
        <div class="userInfo" @click="showDetails.nickname = true">
          {{ userDetails.nickname }}
        </div>
      </user-info>
    </div>
    <div>
      <user-info leftText="手机号">
        <div class="userInfo" @click="showDetails.phone = true">
          {{ userDetails.phone }}
        </div>
      </user-info>
    </div>
    <div>
      <user-info leftText="农场">
        <div class="userInfo" @click="showDetails.farm = true">
          {{ userDetails.farm }}
        </div>
      </user-info>
    </div>
    <div>
      <user-info leftText="简介">
        <div
          class="userInfo overflowOmit signature"
          @click="showDetails.signature = true"
        >
          {{ userDetails.signature }}
        </div>
      </user-info>
    </div>
    <div class="preserve">
      <button @click="showModel = true">保存</button>
    </div>
    <!-- 弹出层区 -->
    <!-- 昵称弹出层 -->
    <u-popup
      :show="showDetails.nickname"
      :round="10"
      mode="bottom"
      closeable
      @close="closePopup('nickname')"
    >
      <view class="nickname_popup popup_height popup">
        <textarea
          name=""
          id=""
          cols="30"
          rows="10"
          v-model="userDetails.nickname"
        ></textarea>
      </view>
    </u-popup>
    <!-- 手机号 -->
    <u-popup
      :show="showDetails.phone"
      :round="10"
      mode="bottom"
      closeable
      @close="closePopup('phone')"
    >
      <view class="popup_height popup">
        <textarea
          name=""
          id=""
          cols="30"
          rows="10"
          v-model="userDetails.phone"
        ></textarea>
      </view>
    </u-popup>
    <!-- 农场 -->
    <u-popup
      :show="showDetails.farm"
      :round="10"
      mode="bottom"
      closeable
      @close="closePopup('farm')"
    >
      <view class="popup_height popup">
        <textarea
          name=""
          id=""
          cols="30"
          rows="10"
          v-model="userDetails.farm"
        ></textarea>
      </view>
    </u-popup>
    <!-- 个性签名弹窗 -->
    <u-popup
      :show="showDetails.signature"
      :round="10"
      mode="bottom"
      closeable
      @close="closePopup('signature')"
    >
      <view class="popup_height popup">
        <textarea
          name=""
          id=""
          cols="30"
          rows="10"
          v-model="userDetails.signature"
        ></textarea>
      </view>
    </u-popup>
    <!-- 保存确认框 -->
    <u-modal
      :show="showModel"
      :content="content"
      showCancelButton
      @confirm="changeUserDetails"
      @cancel="showModel = false"
    ></u-modal>
  </div>
</template>

<script>
import userInfo from "./userInfoCard.vue";
export default {
  components: {
    userInfo,
  },
  data: () => ({
    showModel: false,
    content: "确认修改你的个人信息吗？",
    userDetails: {
      farm: "",
      imgUrl: "",
      nickname: "",
      phone: "",
      signature: "",
    },
    showDetails: {
      farm: false,
      nickname: false,
      phone: false,
      signature: false,
    },
    tempFilePaths: null,
  }),
  computed: {},
  methods: {
    async getUserDetails() {
      const { data: res } = await uni.$http.get("/api/users/details");
      if (res.code !== "00000") {
        return uni.$u.toast(res.message);
      }
      let { nickname, signature, imgUrl, farm, phone } = res.data;
      this.userDetails.nickname = nickname ? nickname : "普通用户";
      this.userDetails.signature = signature
        ? signature
        : "填写你的简介,让大家认识你吧";
      this.userDetails.imgUrl = imgUrl
        ? imgUrl
        : "../../static/img/user_head.png";
      this.userDetails.farm = farm ? farm : "未绑定农场";
      this.userDetails.phone = phone ? phone : "13734174131";
    },
    async changeUserDetails() {
      this.showModel = false;
      let formData = new FormData();
      if (this.tempFiles) {
        formData.append("img", this.tempFiles);
      }
      formData.append("nickname", this.userDetails.nickname);
      formData.append("phone", this.userDetails.phone);
      formData.append("farm", this.userDetails.farm);
      formData.append("signature", this.userDetails.signature);
      const data = await this.fetchPost("/api/users/details", formData);
      if (data.code !== "00000") {
        return uni.$showMsg(data.message);
      }
      uni.$showMsg("修改成功");
    },
    async fetchPost(url, data) {
      const response = await fetch(url, {
        method: "POST",
        body: data,
        headers: {
          Authorization: uni.getStorageSync("token"),
        },
      });
      return response.json();
    },
    afterRead() {
      uni.chooseImage({
        count: 1,
        sizeType: "original",
        success: (chooseImageRes) => {
          this.tempFiles = chooseImageRes.tempFiles[0];
          // 获取图片路径
          let url = window.URL;
          var imgUrl = url.createObjectURL(chooseImageRes.tempFiles[0]);
          this.userDetails.imgUrl = imgUrl;
        },
        fail: (err) => {
          console.log(err);
        },
      });
    },
    closePopup(name) {
      console.log(name);
      this.showDetails[name] = false;
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
.userInfoChange {
  background-color: #f8f8f8;
  height: 100%;
}
.signature {
  width: 60vw;
}
.preserve {
  margin-top: 5vh;
  button {
    background-color: #2faf48;
    color: #fff;
    width: 70%;
  }
}
uni-textarea {
  background-color: #ccc9c9 !important;
  border-radius: 2vw;
  padding: 2vw;
  box-sizing: border-box;
}
.u-popup__content {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 30vh;
}
.popup_height {
  height: 30vh;
}
.nickname_popup {
  display: flex;
  justify-content: center;
  align-items: center;
}
.popup {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
