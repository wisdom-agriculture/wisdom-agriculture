<template>
  <div class="recon-list">
    <div class="left">
      <u-image
        @click="toHisCircle(recoitem.userId)"
        shape="circle"
        :src="recoitem.imgUrl"
        width="39"
        height="39"
      ></u-image>
      <u-icon
        v-if="!recoitem.isSubscribed"
        @click="sendAttention(recoitem.userId)"
        class="icon"
        size="17"
        name="plus-circle-fill"
        color="#444444"
      ></u-icon>
    </div>
    <div class="right">
      <div class="content-top">
        <div class="name" @click="toHisCircle(recoitem.userId)">
          {{ recoitem.nickname }}
        </div>
        <slot></slot>
        <div class="time">{{ getMinSec(recoitem.time) }}</div>
      </div>
      <div class="content" @click="toContentDetail(recoitem)">
        <div class="text">
          {{ recoitem.content }}
        </div>
        <div class="img-wrap">
          <u-image
            class="img"
            v-for="(item, index) in recoitem.imgs"
            :key="index"
            :src="item"
            width="80"
            height="80"
          ></u-image>
        </div>
      </div>
      <div class="icon-wrap">
        <div class="divide">
          <div class="like">
            <u-icon
              v-if="recoitem.isLike"
              class="icon"
              size="30"
              name="thumb-up-fill"
              color="#d90101"
              @click="cancellike(recoitem.id)"
            ></u-icon>
            <u-icon
              v-else
              class="icon"
              size="30"
              name="thumb-up-fill"
              @click="tolike(recoitem.id)"
            ></u-icon>
            <span>{{ recoitem.likeNum }}</span>
          </div>
          <div class="comment">
            <u-icon
              @click="toContentDetail(recoitem)"
              class="icon"
              size="30"
              name="chat"
            ></u-icon>
            <span>{{ recoitem.commentNum }}</span>
          </div>
          <div class="share">
            <u-icon
              class="icon"
              size="30"
              name="share"
              @click="popShare"
            ></u-icon>
            <!-- <span>{{ recoitemData.shareCount }}</span> 分享没有分享数量-->
          </div>
        </div>
      </div>
    </div>
    <u-popup :show="show" @close="close" height="800" mode="bottom">
      <div class="share-wrap">
        <div class="title">
          <u-icon class="icon" size="20" name="share"></u-icon>
          <text style="padding-left: 10rpx">分享</text>
        </div>
        <div class="share-list">
          <div>
            <u-image
              :src="require('../../../static/icon/gplant.png')"
              width="40"
              height="40"
            ></u-image>
            <span>我的圈子</span>
          </div>
          <div>
            <u-image
              :src="require('../../../static/icon/wx.svg')"
              width="40"
              height="40"
            ></u-image>
            <span>微信</span>
          </div>
          <div>
            <u-image
              :src="require('../../../static/icon/QQ.svg')"
              width="40"
              height="40"
            ></u-image>
            <span>QQ</span>
          </div>
          <div>
            <u-image
              :src="require('../../../static/icon/wb.svg')"
              width="40"
              height="40"
            ></u-image>
            <span>微博</span>
          </div>
        </div>
      </div>
    </u-popup>
  </div>
</template>

<script>
function change(t) {
  if (t < 10) {
    return "0" + t;
  } else {
    return t;
  }
}
export default {
  props: {
    recoitem: {},
  },
  data() {
    return {
      show: false,
      // 模拟数据
      /*       recoitemData: {
        content: "好可爱的小猫咪!",
        id: 2,
        imgUrl: "https://image.yaohy.cn/intelligent-farming/zone/12.jpeg",
        userName: "haley",
        isMutal: true,
        imgs: [
          "https://image.yaohy.cn/intelligent-farming/zone/12.jpeg",
          "https://image.yaohy.cn/intelligent-farming/zone/13.jpeg",
          "https://image.yaohy.cn/intelligent-farming/zone/13.jpeg",
        ],
        isLike: false,
        time: 1654617083894,
        userId: 2,
        likeCount: 10,
        shareCount: 12,
        commentCount: 22,
      }, */
    };
  },
  methods: {
    close() {
      this.show = false;
    },
    toHisCircle(userId) {
      uni.navigateTo({
        url: `/pages/hisCircle/index?userId=` + userId,
      });
    },
    popShare() {
      this.show = true;
    },

    showError() {
      uni.showToast({
        title: "操作失败",
        icon: "error",
        duration: 3000,
      });
    },
    async sendAttention(id) {
      let {
        data: { code },
      } = await uni.$NoLoadhttp.put(`/api/users/subscribes?id=${id}`);
      if (code == "00000") {
        setTimeout(() => {
          this.$emit("flushReco", true);
        });
      } else {
        this.showError();
      }
    },
    async tolike(contentId) {
      let {
        data: { code },
      } = await uni.$NoLoadhttp.get(`/api/zones/likes?id=${contentId}`);
      if (code == "00000") {
        // 图标变红,数字+1
        // this.recoitem.isLike = true;
        // this.recoitem.likeNum++;
        this.$emit("flushReco", true);
      } else {
        this.showError();
      }
    },
    async cancellike(contentId) {
      let {
        data: { code },
      } = await uni.$NoLoadhttp.delete(`/api/zones/likes?id=${contentId}`);
      if (code == "00000") {
        // 图标变灰,数字-1
        // this.recoitem.isLike = false;
        // this.recoitem.likeNum--;
        this.$emit("flushReco", true);
      } else {
        this.showError();
      }
    },
    toContentDetail(recoitem) {
      let recoitemstr = JSON.stringify(recoitem);
      uni.navigateTo({
        url:
          `/pages/contentDetail/index?recoItem=` +
          encodeURIComponent(recoitemstr),
      });
    },
  },
  computed: {
    getMinSec(timetemp) {
      return (timestamp) => {
        let date = new Date(timestamp);
        let h = change(date.getHours()) + ":";
        let m = change(date.getMinutes());
        return h + m;
      };
    },
  },
};
</script>

<style lang="scss" scoped>
@import "./../static/reco-item.scss";
</style>