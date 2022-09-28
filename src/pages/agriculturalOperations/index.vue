<template>
  <div class="agriculturalOperations">
    <div class="checkList">
      <div class="landCheck" @click="landCheckShow = true">
        <span>{{ land }}</span>
        <span><u-icon name="arrow-down" bold></u-icon></span>
      </div>
    </div>
    <div v-if="isHaveData">
      <div
        class="operateList"
        v-for="(item, index) in operateInfoList"
        :key="index"
      >
        <card
          :date="transformTime(item.time)"
          :operatePerson="item.operator"
          :content="item.content"
          :place="item.name"
        ></card>
      </div>
    </div>

    <u-empty
      mode="data"
      icon="../../static/icon/grow.png"
      text="暂无种植记录"
      width="64"
      height="64"
      v-else
    >
    </u-empty>
    <u-picker
      :show="landCheckShow"
      :columns="lancColumns"
      @confirm="landConfirm"
      @close="landCheckShow = false"
      :closeOnClickOverlay="true"
    ></u-picker>
  </div>
</template>

<script>
import card from "./optionCard.vue";
export default {
  components: {
    card,
  },
  data: () => ({
    land: "所有地块",
    landCheckShow: false,
    operateInfoList: [],
    lancColumns: [["所有地块"]],
  }),
  methods: {
    landConfirm(e) {
      this.land = e.value[0];
      if (e.indexs[0] === 0) {
        this.getAllOperation;
      } else {
        this.getOperationById(e.indexs[0]);
      }
      this.landCheckShow = false;
    },
    async getAllOperation() {
      const { data } = await uni.$http.get("/api/fields/operations", {
        current: 1,
        size: 10,
      });
      let list = data.data.operationResultList;
      list.forEach((item) => {
        this.lancColumns[0].push(item.name);
      });
      this.operateInfoList = list;
    },
    async getOperationById(id) {
      const { data } = await uni.$http.get("/api/fields/operations", {
        fieldID: id,
        current: 1,
        size: 10,
      });
      let list = data.data.operationResultList;
      this.operateInfoList = list;
    },
    transformTime(time) {
      let times = new Date(time);
      let timeStr = `${times.getFullYear()}-${
        times.getMonth() + 1
      }-${times.getDay()}`;
      return timeStr;
    },
  },
  computed: {
    isHaveData() {
      if (this.operateInfoList.length === 0) {
        return false;
      } else {
        return true;
      }
    },
  },

  watch: {},

  // 页面周期函数--监听页面加载
  onLoad() {
    this.getAllOperation();
  },
  // 页面周期函数--监听页面初次渲染完成
  onReady() {},
  // 页面周期函数--监听页面显示(not-nvue)
  onShow() {},
  // 页面周期函数--监听页面隐藏
  onHide() {},
  // 页面周期函数--监听页面卸载
  onUnload() {},
  // 页面处理函数--监听用户下拉动作
};
</script>

<style scoped lang="scss">
.checkList {
  display: flex;
  align-items: center;
  justify-content: space-around;
  height: 6vh;
  .landCheck {
    display: flex;
    align-items: center;
  }
  .kindCheck {
    display: flex;
    align-items: center;
  }
  ::v-deep .u-icon__icon {
    margin-left: 3vw;
  }
}
.card {
  width: 92vw;
  box-sizing: border-box;
  margin: 0px auto 10px;
}
.operateList {
  margin-top: 20px;
}
.u-empty {
  margin-top: 30vh !important;
}
</style>
