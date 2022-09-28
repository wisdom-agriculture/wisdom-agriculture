<template>
  <view class="content">
    <u-toast ref="uToast" />
    <view class="map_container">
      <map
        id="myMap"
        min-scale="15"
        class="map"
        :scale="scale"
        :latitude="latitude"
        :longitude="longitude"
        :markers="markers"
        show-location
      >
        <cover-view class="exit">
          <u-icon
            class="return"
            name="arrow-left"
            color="#000000"
            size="30"
            @click="toPreviousPage(1)"
          ></u-icon>
        </cover-view>
        <cover-view class="add" @tap="scale++">
          <cover-image
            src="../../static/img/add.png"
            style="width: 35rpx; height: 35rpx"
          ></cover-image>
        </cover-view>
        <cover-view
          class="desc"
          @tap="scale--"
          style="border: 1rpx solid #babdc5"
        >
          <cover-image
            src="../../static/img/dec.png"
            style="width: 35rpx; height: 35rpx"
          ></cover-image>
        </cover-view>
        <cover-view class="predictCon">
          <div class="predict">预计6.2送达</div>
          <div>离开荔枝湾</div>
        </cover-view>
      </map>
    </view>
    <view class="map_text">
      <view class="left">
        <u-icon
          :name="require('../../static/icon/remind.svg')"
          color="#000000"
          style="margin: 10rpx 0 0 10rpx"
          size="17"
          @click="toPreviousPage(1)"
        ></u-icon>
      </view>
      <view class="right"
        >【温馨提示】
        受疫情及交通管控影响,您的包裹可能会延迟配送,请耐心等待,感谢您的理解!</view
      >
    </view>
    <view class="map_bottom">
      <view class="topcon">
        <u--image
          src="https://img1.baidu.com/it/u=3240733246,3576207883&fm=253&fmt=auto&app=138&f=JPEG?w=332&h=500"
          width="35"
          style="height: 30rpx; margin: -80rpx 30rpx 0 20rpx"
          shape="square"
          mode="widthFix"
        ></u--image>
        <span>运输中</span>
      </view>
      <view class="bottomcon">
        <view class="deliver">
          <u--image
            src="https://img0.baidu.com/it/u=4077900726,3714983654&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500"
            width="31"
            style="height: 50rpx; margin: 10rpx"
            shape="circle"
            mode="widthFix"
          ></u--image>
          <div>中通快递</div>
          <div>{{ id }}</div>
          <div class="copy" @click="copyID(id)">复制</div>
        </view>
        <view class="receive">
          <u--image
            :src="require('../../static/icon/receive.svg')"
            width="30"
            style="height: 50rpx; margin: 10rpx"
            shape="circle"
            mode="widthFix"
          ></u--image>
          <div class="addr">
            [收货地址] 天津市西青区 李七庄街道 宾水西XXXXXXXX
          </div>
        </view>
        <transport-item :isCurrent="true"> </transport-item>
        <latter v-for="(item, index) in latters" :key="index"></latter>
        <transport-item :isCurrent="false"> </transport-item>
        <u-line
          class="line"
          length="60rpx"
          direction="col"
          color="#999999"
        ></u-line>
      </view>
    </view>
  </view>
</template>

<script>
import { toPreviousPage } from "../../utils/pageJump";
import latter from "./components/latter.vue";
import transportItem from "./components/transportItem.vue";
import amap from "../../lib/amap-wx.130.js";
export default {
  data() {
    return {
      latitude: 35.084158,
      longitude: 115.200983,
      mapCtx: null,
      scale: 6,
      indexChos: -1,
      firstClick: 0,
      latters: [1, 2],
      id: 66666699999,
      amapPlugin: null,
      markers: [
        {
          latitude: 38.484158,
          longitude: 117.200983,
          width: 40,
          height: 40,
          iconPath: "../../static/icon/dot-orange.svg",
          callout: {
            content: `收 | 天津市`, //文本
            color: "#fff", //文字颜色
            fontSize: 15, //文本大小
            borderRadius: 3, //边框圆角
            bgColor: "#fead51", //背景颜色
            display: "ALWAYS", //常显
            padding: 8,
            textAlign: "center",
          },
          style: {
            backGroundColor: "yellow",
          },
        },
      ],
    };
  },
  async onLoad() {
    this.amapPlugin = new amap.AMapWX({
      key: "b52c9faac388cc9da0ba84dfa0e78f18",
    });
    console.log(this.amapPlugin);
    uni.getLocation({
      type: "gcj02",
      success: (res) => {
        console.log(res);
      },
      fail: (res) => {
        console.log("错误：" + JSON.stringify(res));
      },
    });
  },
  onShow() {
    // this.getInitLocation();
  },
  methods: {
    toPreviousPage,
    copyID() {
      console.log("复制快递单号");
    },

    copyID(id) {
      uni.setClipboardData({
        data: String(id),
      });
    },
  },
  components: {
    latter,
    transportItem,
  },
};
</script>

<style scoped lang="scss">
.content {
  width: 100%;
  height: 100%;
}
.map_container {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
}
.map {
  width: 100%;
  height: 100%;
  .exit {
    position: absolute;
    left: 10rpx;
    top: 20rpx;
  }
  .predictCon {
    padding: 10rpx;
    width: 200rpx;
    height: 85rpx;
    background-color: #fff;
    position: absolute;
    left: 250rpx;
    top: 565rpx;
    font-size: 26rpx;
    border-radius: 10rpx;
    .predict {
      color: #ef8a2e;
      font-size: 32rpx;
      font-weight: 700;
      margin-bottom: 10rpx;
    }
  }
  .add,
  .desc {
    background-color: #fefeff;
    position: absolute;
    display: flex;
    align-items: center;
    justify-content: center;
    border: 1rpx solid #babdc5;
  }
  .add {
    height: 80rpx;
    right: 15rpx;
    top: 20rpx;
    border-radius: 3rpx 3rpx 0 0;
  }
  .desc {
    height: 80rpx;
    right: 15rpx;
    top: 100rpx;
    border-radius: 0 0 3rpx 3rpx;
    border-top: 0;
  }
  .img-item {
    width: 60rpx;
    height: 60rpx;
  }
}
.map_text,
.map_bottom {
  width: 97%;
  position: absolute;
  bottom: 550rpx;
  left: 50%;
  transform: translateX(-50%);
  align-items: center;
  justify-content: space-around;
  background: #fff;
  display: flex;
  height: 110rpx;
  border-radius: 20rpx;
  .left {
    margin-left: 20rpx;
    background-color: #fdeee3;
    width: 55rpx;
    height: 55rpx;
    border-radius: 50%;
    font-size: 27rpx;
    flex: 0.5;
  }
  .right {
    flex: 6;
    padding: 0 20rpx;
    box-sizing: border-box;
    font-size: 26rpx;
    font-weight: 700;
  }
}
.map_bottom {
  bottom: 420rpx;
  display: block;
  background-color: #f7f8fa;
  border-radius: 20rpx 20rpx 0 0;

  .topcon {
    height: 100rpx;
    display: flex;
    align-items: center;
    span {
      font-weight: 700;
      font-size: 32rpx;
    }
  }
  .bottomcon {
    background-color: #fff;
    border-radius: 20rpx;
    height: 600rpx;
    .deliver,
    .receive {
      display: flex;
      align-items: center;
      padding: 10rpx 20rpx 0;
      font-size: 28rpx;
      div {
        padding-top: 10rpx;
      }
      .addr {
        padding: 20rpx 0 0 0;
      }
      .copy {
        margin-left: 60rpx;
        color: #b5b5b6;
      }
    }
    .receive {
      color: #9b9b9b;
    }
  }
  .line {
    width: 20rpx;
    margin: -8rpx 30rpx 0 50rpx !important;
  }
}
</style>