<template>
  <div class="dailyCrops">
    <div class="plantImg">
      <img :src="imgUrl" />
    </div>
    <div class="plantList" v-for="(item, index) in plantList" :key="index">
      <card-vue
        :title="item.name"
        :description="item.description"
        class="plantCard"
      ></card-vue>
    </div>
  </div>
</template>

<script>
import cardVue from "./card.vue";
import navVue from "../../components/nav.vue";
export default {
  components: {
    cardVue,
    navVue,
  },
  data: () => ({
    plantList: [],
    imgUrl:
      "https://i.epochtimes.com/assets/uploads/2020/10/shutterstock_244687741-600x400.jpg",
  }),
  computed: {},
  methods: {
    async getData() {
      const { data: res } = await uni.$http.get("/api/plants/recommendation");
      if (res.code !== "00000") {
        return uni.$showMsg(res.message);
      }
      this.plantList = res.data.attrList;
      if (res.data.img !== null) {
        this.imgUrl = res.data.img;
      }
    },
  },
  watch: {},

  // 页面周期函数--监听页面加载
  onLoad() {
    this.getData();
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
.plantCard {
  margin-bottom: 40rpx;
}

.plantImg {
  margin-bottom: 30rpx;
  img {
    width: 100%;
  }
}
.dailyCrops {
  padding-bottom: 30rpx;
}
</style>
