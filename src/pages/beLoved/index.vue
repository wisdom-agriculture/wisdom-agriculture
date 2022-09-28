<template>
  <div class="be-loved">
    <lac-item :lacitem="item" v-for="(item, index) in contentList" :key="index">
      <div class="replace">赞了这条圈子</div>
    </lac-item>
  </div>
</template>

<script>
import lacItem from "../../components/lacItem.vue";
export default {
  data: () => ({
    contentList: [],
  }),
  onShow() {
    uni.setStorageSync("love", 0);
    this.contentList = [
      {
        id: 2,
        userId: 2,
        imgUrl: "https://image.yaohy.cn/intelligent-farming/user/img/2.jpg",
        nickname: "老王",
        time: "17:53",
        textName: "@鸽子开",
        content:
          "云种植的西瓜收货啦! 能吃到自己种植的东西是一种奇妙的体验,能吃到自己种植的东西是一种奇妙的体验...",
        textImg:
          "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS3ysdfv8PhvND0mf0tHC0AYoHn2DPB-eWKMg&usqp=CAU",
      },
      {
        id: 2,
        userId: 2,
        imgUrl: "https://image.yaohy.cn/intelligent-farming/user/img/2.jpg",
        nickname: "老王",
        time: "17:53",
        textName: "@鸽子开",
        content:
          "云种植的西瓜收货啦! 能吃到自己种植的东西是一种奇妙的体验,能吃到自己种植的东西是一种奇妙的体验...",
        textImg:
          "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS3ysdfv8PhvND0mf0tHC0AYoHn2DPB-eWKMg&usqp=CAU",
      },
      {
        id: 2,
        userId: 2,
        imgUrl: "https://image.yaohy.cn/intelligent-farming/user/img/2.jpg",
        nickname: "老王",
        time: "17:53",
        textName: "@鸽子开",
        content:
          "云种植的西瓜收货啦! 能吃到自己种植的东西是一种奇妙的体验,能吃到自己种植的东西是一种奇妙的体验...",
        textImg:
          "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS3ysdfv8PhvND0mf0tHC0AYoHn2DPB-eWKMg&usqp=CAU",
      },
      {
        userId: 2,
        imgUrl: "https://image.yaohy.cn/intelligent-farming/user/img/2.jpg",
        nickname: "老王",
        time: "17:53",
        textName: "@鸽子开",
        content:
          "云种植的西瓜收货啦! 能吃到自己种植的东西是一种奇妙的体验,能吃到自己种植的东西是一种奇妙的体验...",
        textImg:
          "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS3ysdfv8PhvND0mf0tHC0AYoHn2DPB-eWKMg&usqp=CAU",
      },
    ];
  },
  components: {
    lacItem,
  },
};
</script>

<style lang="scss" scoped>
.replace {
  font-size: 28rpx;
}
</style>