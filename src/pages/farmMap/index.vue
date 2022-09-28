<template>
  <div class="farmMap">
    <div class="quitMap" @click="toPreviousPage()">
      <img src="../../static/icon/x.svg" alt="quit" />
    </div>
    <map
      :latitude="lat"
      :longitude="lon"
      class="txMap"
      :polygons="polygons"
    ></map>
    <div class="operationBox">
      <div class="farmInfoTop">
        <div class="landCheck" @click="showPicker = true">
          <span>{{ landName }}</span>
          <img src="../../static/icon/arrow.svg" alt="" />
        </div>
        <div class="intoLand" @click="intoLand">进入地块</div>
      </div>
    </div>
    <u-picker
      :show="showPicker"
      :columns="farmColumns"
      :closeOnClickOverlay="true"
      @close="showPicker = false"
      @confirm="confirm"
      @cancel="showPicker = false"
    ></u-picker>
  </div>
</template>
<script>
import navVue from "../../components/nav.vue";
import { toPreviousPage, toOtherPage } from "../../utils/pageJump";
export default {
  components: {
    navVue,
  },
  data: () => ({
    lat: 40.040452,
    lon: 116.273486,
    latAndLon: [],
    mapKey: "b52c9faac388cc9da0ba84dfa0e78f18",
    showPicker: false,
    locations: [],
    polygons: [
      {
        points: [
          { latitude: 40.0414253657142, longitude: 116.2722415837743 },
          { latitude: 40.03942536171407, longitude: 116.2726277820093 },
          { latitude: 40.03970460886076, longitude: 116.27483769345417 },
          { latitude: 40.041404706498625, longitude: 116.27443003983899 },
        ],
        strokeColor: "#fff",
        strokeWidth: 2,
        fillColor: "#F296633e",
      },
    ],
    amapPlugin: "",
    greensList: [
      {
        img: "https://img.88tph.com/tphc.1/production/20180626/12582959.jpg",
        greenName: "菜花",
        greenKind: "无",
      },
      {
        img: "https://img.88tph.com/tphc.1/production/20180626/12582959.jpg",
        greenName: "菜花",
        greenKind: "无",
      },
      {
        img: "https://img.88tph.com/tphc.1/production/20180626/12582959.jpg",
        greenName: "菜花",
        greenKind: "无",
      },
    ],
    farmColumns: [[]],
    landName: "第一种植场",
  }),
  computed: {},
  methods: {
    toPreviousPage,
    toOtherPage,
    async getData() {
      const { data: res } = await uni.$http.get("/api/fields");
      if (res.code !== "00000") {
        return;
      }
      res.data.fieldList.forEach((item) => {
        this.farmColumns[0].push(item.name);
        this.latAndLon.push([item.latitude, item.longitude]);
        this.locations.push(this.changeArrayToLocal(item.location));
      });
      console.log(this.locations);
      [this.lat, this.lon] = this.latAndLon[0];
      this.landName = this.farmColumns[0][0];
    },
    confirm(data) {
      let index = data.indexs[0];
      this.landName = data.value[0];
      [this.lat, this.lon] = this.latAndLon[index];
      this.polygons[0].points = this.locations[index];
      console.log(this.polygons);
      this.showPicker = false;
    },
    intoLand() {
      toOtherPage("landParcelDetails", {
        path: 2,
      });
    },
    exit() {},
    changeArrayToLocal(array) {
      let newArray = [];
      for (let i = 0; i < array.length; i++) {
        let obj = {
          latitude: "",
          longitude: "",
        };
        obj.latitude = array[i][0];
        obj.longitude = array[i][1];
        newArray.push(obj);
      }
      return newArray;
    },
  },
  watch: {},

  // 页面周期函数--监听页面加载
  onLoad() {
    uni.getLocation({
      type: "gcj02",
      success: (res) => {},
      fail: (res) => {
        console.log("错误：" + JSON.stringify(res));
      },
    });
  },
  // 页面周期函数--监听页面初次渲染完成
  onReady() {
    this.getData();
  },
  // 页面周期函数--监听页面显示(not-nvue)
  onShow() {},
  // 页面周期函数--监听页面隐藏
  onHide() {},
  // 页面周期函数--监听页面卸载
  onUnload() {},
  // 页面处理函数--监听用户下拉动作
  // onPullDownRefresh() { uni.stopPullDownRefresh(); },
  // 页面处理函数--监听用户上拉触底
  // onReachBottom() {},
  // 页面处理函数--监听页面滚动(not-nvue)
  // onPageScroll(event) {},
  // 页面处理函数--用户点击右上角分享
  // onShareAppMessage(options) {},
};
</script>

<style scoped lang="scss">
.quitMap {
  position: fixed;
  top: 10rpx;
  left: 0px;
  font-size: 24px;
  z-index: 999;
}
#container {
  width: 300px;
  height: 180px;
}

.txMap {
  height: 100vh;
  width: 100vw;
}
.operationBox {
  height: 10vh;
  width: 100vw;
  background-color: rgba(0, 144, 124, 0.8);
  position: fixed;
  bottom: 0px;
  z-index: 999;
  border-radius: 5vw 5vw 0 0;
  .farmInfoTop {
    display: flex;
    border-bottom: 1px solid #333;
    align-items: center;
    height: 10vh;
    justify-content: center;
  }
  .landCheck {
    display: flex;
    color: #fff;
    font-weight: 600;
    align-items: center;
    img {
      height: 5vw;
      widows: 5vw;
      margin-left: 1vw;
    }
  }
  .intoLand {
    color: #fff;
    transform: translateX(17vw);
  }
}
.farmInfoBottom {
  .greensList {
    margin-top: 2vh;
    margin-left: 4vw;
    height: 20vh;
    padding-bottom: 6vh;
    overflow: scroll;
    .greensCard {
      width: 60vw;
      height: 24vw;
      background-color: rgba(0, 71, 62, 0.8);
      display: flex;
      align-items: center;
      border-radius: 2vw;
      margin-bottom: 1vh;
      .greensImg {
        margin-left: 3vw;
        img {
          width: 20vw;
          height: 20vw;
          border-radius: 50%;
        }
      }
      .greensInfo {
        margin-left: 3vw;
        .name {
          color: #fff;
          font-weight: 600;
        }
        .kind {
          color: #bfbfbf;
          font-size: 14px;
          margin-top: 2vh;
        }
      }
    }
  }
}
.blankSpace {
  height: 3vh;
}
</style>
