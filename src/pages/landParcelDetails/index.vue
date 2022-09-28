<template>
  <div class="landParcelDetails">
    <u-icon
      name="arrow-left"
      class="exit"
      color="#fff"
      :bold="true"
      :size="19"
      @click="exit()"
    ></u-icon>
    <div class="container">
      <div class="farmIng">
        <img src="../../static/img/farm.png" />
      </div>
      <div class="tabs">
        <div class="tab" @click="tabAction = 0">
          <span :class="[tabAction === 0 ? 'active' : '']">基本信息</span>
        </div>
        <div class="tab" @click="tabAction = 1">
          <span :class="[tabAction === 1 ? 'active' : '']">种植日志</span>
        </div>
      </div>
      <div class="content">
        <div class="baseInfo" v-if="isdateInfo">
          <card title="种植信息">
            <template>
              <div class="plantingInformation">
                <img src="../../static/img/vegetable.png" alt="" />
                <div class="greensInfo">
                  <div class="greensName">青菜</div>
                  <div class="kind">品种: 无</div>
                  <div class="data">
                    <div class="date">2021-05-12移栽</div>
                    <div class="day">第30天</div>
                  </div>
                </div>
              </div>
            </template>
          </card>
          <card title="土地环境">
            <template>
              <div class="landEnviron">
                <div class="landInfoList">
                  <div class="landItem">
                    <img
                      src="../../static/icon/sun.png"
                      alt=""
                      class="landIcon"
                    />
                    光照强度: 1000lx
                  </div>
                  <div class="landItem">
                    <img
                      src="../../static/icon/co2.png"
                      alt=""
                      class="landIcon"
                    />
                    CO<sub>2</sub>浓度
                  </div>
                  <div class="landItem">
                    <img
                      src="../../static/icon/landItem.png"
                      alt=""
                      class="landIcon"
                    />
                    土壤温度: 7℃
                  </div>
                  <div class="landItem">
                    <img
                      src="../../static/icon/humidity.png"
                      alt=""
                      class="landIcon"
                    />
                    土壤湿度：66%
                  </div>
                  <div class="landItem">
                    <img
                      src="../../static/icon/PH.png"
                      alt=""
                      class="landIcon"
                    />
                    土壤PH值：6.0
                  </div>
                </div>
              </div>
            </template>
          </card>
          <card title="土地信息">
            <template>
              <div class="landInfo">
                <div class="firmData">厂商:天津市xxx有限公司</div>
                <div class="location">地址:天津市西青区xxx农场</div>
              </div>
            </template>
          </card>
        </div>
        <div class="plantingLog" v-else>
          <div class="content" v-if="isHaveData">
            <template>
              <u-steps
                current="2"
                direction="column"
                :dot="true"
                activeColor="#2faf48"
                class="steps"
              >
                <div
                  class="u-step-item"
                  v-for="(item, index) in operationList"
                  :key="index"
                >
                  <u-steps-item
                    :title="'操作人' + item.operator"
                    :desc="item.content + '\n' + transformTime(item.time)"
                  >
                  </u-steps-item>
                </div>
              </u-steps>
            </template>
          </div>
          <u-empty
            mode="data"
            icon="../../static/icon/grow.png"
            text="此地暂无种植记录"
            width="64"
            height="64"
            v-else
          >
          </u-empty>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import card from "./card.vue";
export default {
  name: "landParcelDetails",
  components: {
    card,
  },
  props: {},
  data() {
    return {
      tabAction: 1,
      path: 1,
      operationList: [],
      id: 0,
    };
  },
  computed: {
    isdateInfo() {
      if (this.tabAction === 0) {
        return true;
      } else {
        return false;
      }
    },
    isHaveData() {
      if (this.operationList.length === 0) {
        return false;
      } else {
        return true;
      }
    },
  },
  methods: {
    exit() {
      console.log(this.path);
      if (this.path == 1) {
        uni.navigateTo({
          url: "/pages/landParcdl/index",
        });
      } else {
        uni.navigateTo({
          url: "/pages/farmMap/index",
        });
      }
    },
    async getOperations() {
      const { data } = await uni.$http.get("/api/fields/operations", {
        fieldId: this.id,
        current: 1,
        size: 10,
      });
      if (data.code !== "00000") {
        return uni.$showMsg(data.message);
      }
      this.operationList = data.data.operationResultList;
      uni.$showMsg(data.message);
    },
    transformTime(time) {
      let times = new Date(time);
      let timeStr = `${times.getFullYear()}-${
        times.getMonth() + 1
      }-${times.getDay()} ${times.getHours()}:${times.getMinutes()}`;
      return timeStr;
    },
  },
  watch: {},

  // 页面周期函数--监听页面加载
  onLoad(option) {
    this.path = option.path;
    this.id = option.id;
    console.log(option);
    this.getOperations();
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
.landParcelDetails {
  height: 100%;
  overflow: scroll;
}
.exit {
  position: absolute;
  top: 1vh;
  left: 1vh;
}
.container {
  .tabs {
    display: flex;
    height: 6vh;
    box-shadow: 0px 1px 1px rgba(0, 0, 0, 0.1);
    justify-content: space-around;
    align-items: flex-end;
    span {
      font-size: 15px;
      font-weight: 600;
      display: block;
      border-bottom: 2px solid transparent;
      padding: 5px 8px;
    }
    .active {
      border-bottom: 2px solid #4ba234;
      color: #4ba234;
    }
  }
}
.content {
  margin-top: 4vh;
  .plantingInformation {
    display: flex;
    img {
      height: 11vh;
    }
    .greensInfo {
      flex: 1;
      margin-left: 3vw;
    }
    .greensName {
      font-weight: 600;
    }
    .kind {
      color: #bcbcc1;
      font-weight: 600;
      font-size: 15px;
    }
    .data {
      display: flex;
      color: #bcbcc1;
      font-weight: 600;
      font-size: 15px;
      .day {
        color: #4ba234;
        margin-left: 12vw;
      }
    }
  }
}
.u-empty {
  margin-top: 20vh !important;
}
.card {
  margin-bottom: 2vh;
}
.landEnviron {
  .landInfoList {
    display: flex;
    flex-wrap: wrap;
    img {
      height: 25px;
      width: 25px;
    }
    .landItem {
      display: flex;
      align-items: center;
      margin-top: 2vh;
      margin-right: 3vw;
      width: 40vw;
    }
  }
}
.landInfo {
  margin-left: 3vw;
}
.location {
  margin-top: 1vh;
}
.plantingLog {
  .content {
    width: 90%;
    margin: 0 auto;
    background-color: #fff;
    border-radius: 5vw;
    box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.4);
  }
}
.steps {
  padding-top: 3vh;
}

.u-step-item {
  display: flex;
  margin-left: 6vw;
  ::v-deep .u-text__value span {
    font-weight: 600;
  }
  ::v-deep .u-text__value--content span {
    font-size: 14px;
  }
  ::v-deep .u-text__value--main span {
    font-size: 14px;
  }

  .opName {
    font-size: 14px;
    font-weight: 600;
    margin-right: 2vw;
  }
}
</style>
