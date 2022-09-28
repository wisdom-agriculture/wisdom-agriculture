<template>
  <div class="origins-detail">
    <div class="top">
      <u-icon
        class="icon"
        :name="require('../../static/img/close.png')"
        color="#6F6962"
        size="22"
        @click="toPreviousPage(1)"
      ></u-icon>
      <span>溯源信息</span>
      <u-icon
        class="icon"
        :name="require('../../static/img/share.png')"
        color="#2979ff"
        size="22"
        @click="popShare"
      ></u-icon>
    </div>
    <div class="con1">
      <u--image
        class="img"
        :src="require('../../static/img/vegtable.png')"
        width="130"
        shape="circle"
        mode="widthFix"
      ></u--image>
      <span class="vegName">{{ productBase.product_name }}</span>
      <span class="baseInfo">产品基本信息</span>
    </div>
    <div class="productInfo">
      <div class="left">
        <div class="item">
          <span>净含量:</span>
          <span>{{ productBase.Net_Content }}</span>
        </div>
        <div class="item">
          <span>保存方法:</span>
          <span>{{ productBase.keep_mathod }}</span>
        </div>
        <div class="item">
          <span>食用推荐:</span>
          <span>{{ productBase.cooking_recommend }}</span>
        </div>
        <div class="item">
          <span>生产厂商:</span>
          <span>{{ productBase.factory }}</span>
        </div>
      </div>
      <div class="right">
        <div class="item">
          <span>生产厂商负责:</span>
          <span>{{ productBase.leader }}</span>
        </div>
        <div class="item">
          <span>电话:</span>
          <span>{{ productBase.leader_tel }}</span>
        </div>
        <div class="item">
          <span>生产车间:</span>
          <span>{{ productBase.workshop }}</span>
        </div>
        <div class="item">
          <span>生产工时:</span>
          <span>{{ productBase.work_hours }}</span>
        </div>
      </div>
    </div>
    <div class="title">农产信息</div>
    <div class="arigInfo">
      <div class="left">
        <div class="item">
          <span>农产名字:</span>
          <span>{{ cropsInfo.crops_name }}</span>
        </div>
        <div class="item">
          <span>种植户:</span>
          <span>{{ cropsInfo.farmer_name }}</span>
        </div>
        <div class="item">
          <span>电话:</span>
          <span>{{ cropsInfo.farmer_tel }}</span>
        </div>
        <div class="item">
          <span>地址:</span>
          <span>{{ cropsInfo.address }}</span>
        </div>
        <div class="item">
          <span>种植方式:</span>
          <span>{{ cropsInfo.plant_mode }}</span>
        </div>
        <div class="item">
          <span>年度:</span>
          <span>{{ cropsInfo.year }}</span>
        </div>
      </div>
      <div class="right">
        <div class="item">
          <span>套袋方式:</span>
          <span>{{ cropsInfo.bagging_status }}</span>
        </div>
        <div class="item">
          <span>使用肥料:</span>
          <span>{{ cropsInfo.fertilizer_name }}</span>
        </div>
        <div class="item">
          <span>育苗周期:</span>
          <span>{{ cropsInfo.grow_seedlings_cycle }}</span>
        </div>
        <div class="item">
          <span>灌溉周期:</span>
          <span>{{ cropsInfo.irrigation_cycle }}</span>
        </div>
        <div class="item">
          <span>除草周期:</span>
          <span>{{ cropsInfo.weed_cycle }}</span>
        </div>
        <div class="item">
          <span>施肥周期:</span>
          <span>{{ cropsInfo.apply_fertilizer_cycle }}</span>
        </div>
      </div>
    </div>
    <div class="title">厂商质检</div>
    <div class="quality">
      <div class="left">
        <u--image
          class="img"
          :src="materialInfo.testing_photo_url"
          height="190"
          width="130"
          shape="square"
          mode="aspectFit"
        ></u--image>
      </div>
      <div class="right">
        <div class="item">
          <span>负责人:</span>
          <span>{{ materialInfo.leader ? materialInfo.leader : "无" }}</span>
        </div>
        <div class="item">
          <span>电话:</span>
          <span>{{
            materialInfo.leader_tel ? materialInfo.leader_tel : "无"
          }}</span>
        </div>
        <div class="item">
          <span>原料商场:</span>
          <span>{{
            materialInfo.factory_name ? materialInfo.factory_name : "无"
          }}</span>
        </div>
        <div class="item">
          <span>入厂时间:</span>
          <span>{{
            materialInfo.in_factory_time
              ? materialInfo.in_factory_time.substring(0, 10)
              : "无"
          }}</span>
        </div>
        <div class="item">
          <span>出厂时间:</span>
          <span>{{
            materialInfo.out_factory_time
              ? materialInfo.out_factory_time.substring(0, 10)
              : "无"
          }}</span>
        </div>
        <div class="item">
          <span>检测结果:</span>
          <span>{{
            materialInfo.testing_result ? materialInfo.testing_result : "无"
          }}</span>
        </div>
        <div class="item">
          <span>备注:</span>
          <span>{{ materialInfo.remarks ? materialInfo.remarks : "无" }}</span>
        </div>
      </div>
    </div>
    <div class="title">种植过程</div>
    <process
      v-for="(item, index) in growsProcess"
      :key="index + 1000"
      :process="item"
      class="process"
    ></process>
    <!-- <div class="title">生产行为</div> -->
    <!-- <div class="productDo">
      <div class="item">
        <span>操作人ID:</span>
        <span>6960493007959232512</span>
      </div>
      <div class="item">
        <span>操作人:</span>
        <span>权志龙</span>
      </div>
      <div class="item">
        <span>操作电话:</span>
        <span>13834204796</span>
      </div>
      <div class="item">
        <span>操作时间:</span>
        <span>2022-08-03 15:14:12</span>
      </div>
      <div class="item">
        <span>操作内容:</span>
        <span>包装</span>
      </div>
    </div> -->
    <div class="title">零售商信息</div>
    <div class="productDo">
      <div class="item">
        <span>产品ID:</span>
        <span>{{ retailor.product_id }}</span>
      </div>
      <div class="item">
        <span>产品名称:</span>
        <span>{{ productBase.product_name }}</span>
      </div>
      <div class="item">
        <span>零售点:</span>
        <span>{{ retailor.retailer_name }}</span>
      </div>
      <div class="item">
        <span>电话:</span>
        <span>{{ retailor.retailer_tel }}</span>
      </div>
    </div>
    <div class="title">流通行为</div>
    <circulation
      class="circulation"
      v-for="(item, index) in circulations"
      :key="index"
      :circulation="item"
    ></circulation>
    <u-popup :show="show" @close="close" height="800" mode="bottom">
      <div class="share-wrap">
        <div class="title">
          <u-icon class="icon" size="20" name="share"></u-icon>
          <text style="padding-left: 10rpx">分享</text>
        </div>
        <div class="share-list">
          <div>
            <u-image
              :src="require('../../static/icon/gplant.png')"
              width="40"
              height="40"
            ></u-image>
            <span>我的圈子</span>
          </div>
          <div>
            <u-image
              src="data:image/png;base64,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"
              width="40"
              height="40"
            ></u-image>
            <span>微信</span>
          </div>
          <div>
            <u-image
              src="data:image/png;base64,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"
              width="40"
              height="40"
            ></u-image>
            <span>QQ</span>
          </div>
          <div>
            <u-image
              src="data:image/jpeg;base64,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"
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
import circulation from "./components/circulation.vue";
import process from "./components/process.vue";
import { toPreviousPage } from "../../utils/pageJump";
export default {
  data: () => ({
    productBase: {},
    cropsInfo: {},
    materialInfo: {},
    cropsId: 0,
    growsProcess: [],
    circulations: [],
    retailor: {},
    show: false,
  }),
  components: {
    circulation,
    process,
  },
  async onLoad(options) {
    this.productId = options.id;
    await this.getInfo(
      "/api/trace/retailerInfo" + "?id=",
      "6960490590303686656",
      "retailor"
    );
    await this.getInfo(
      "/api/trace/productInfo" + "?id=",
      this.productId,
      "productBase"
    );
    await this.getInfo(
      "/api/trace/cropsInfo" + "?id=",
      this.productId,
      "cropsInfo"
    );
    await this.getInfo(
      "/api/trace/materialInfo" + "?id=",
      "6955364718710099968",
      "materialInfo"
    );

    await this.getArrInfo(
      "/api/trace/cropsProcess" + "?id=",
      "6955364718710099968",
      "growsProcess"
    );
    await this.getArrInfo(
      "/api/trace/transportProcess" + "?id=",
      "6961251753807319040",
      "circulations"
    );
  },
  methods: {
    toPreviousPage,
    close() {
      this.show = false;
    },
    popShare() {
      this.show = true;
    },
    async getInfo(apiStr, id, name) {
      let {
        data: { data, code },
      } = await uni.$NoLoadhttp.get(apiStr + id);
      if (code == "00000" && (name == "cropsInfo" || name == "retailor")) {
        this[name] = data;
      } else {
        if (data.length == 0) {
        } else {
          this[name] = data[0].Record;
        }
      }
    },
    async getArrInfo(apiStr, id, name) {
      let {
        data: { data, code },
      } = await uni.$NoLoadhttp.get(apiStr + id);
      if (code == "00000" && data.length !== 0) {
        this[name] = data;
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.origins-detail {
  background-color: rgba(250, 255, 249);
  padding-bottom: 120rpx;
  .top {
    font-size: 36rpx;
    padding: 35rpx 0;
    display: flex;
    justify-content: space-between;
    background-color: #fff;
    .icon {
      margin: 0 35rpx;
    }
  }
  .img {
    height: 300rpx;
    margin: 0 auto;
  }
  .con1 {
    padding-top: 15rpx;
    display: flex;
    flex-direction: column;
    align-items: center;
    font-size: 34rpx;
    .vegName {
      font-size: 36rpx;
      color: #528e38;
      font-weight: 1000;
      margin-bottom: 40rpx;
    }
  }
  .arigInfo,
  .productInfo,
  .quality,
  .process,
  .productDo,
  .circulation {
    font-size: 28rpx;
    padding: 30rpx 20rpx;
    margin: 30rpx 30rpx;
    display: flex;
    border: 1rpx solid #d0f1c7;
    border-radius: 40rpx;
    line-height: 60rpx;
    color: #4ba27a;
    font-family: PingFangSC-regular;
    .img {
      margin: 10px auto;
    }
    .left {
      padding: 0 15rpx;
      border-right: 2rpx solid #d0f1c7;
      width: 50%;
      box-sizing: border-box;
    }
    .right {
      padding-left: 40rpx;
      width: 50%;
      box-sizing: border-box;
    }
    .item {
      span:last-child {
        // display: block;
        padding-left: 10rpx;
      }
    }
  }
  .arigInfo,
  .quality,
  .process,
  .productDo,
  .circulation {
    background-color: #ffffff;
    color: #000;
  }
  .quality,
  .process {
    .left {
      border: 0;
      padding-right: 5rpx;
      width: 40%;
    }
    .right {
      width: 60%;
    }
  }
  .title {
    padding-left: 60rpx;
    font-weight: 700;
  }
  .productDo,
  .circulation {
    display: block;
  }
}
@import "./../planting/static/reco-item.scss";
</style>