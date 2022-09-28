<template>
  <div class="card">
    <div class="operate">
      <div class="left">
        <span class="day">{{ date }}</span>
        <span class="work">{{ operate }}</span>
      </div>
      <div class="right">
        <div class="person">
          <u-icon name="account" color="#C5DCF7"></u-icon>
          <span>{{ operatePerson }}</span>
        </div>
      </div>
    </div>
    <div class="content">{{ content }}</div>
    <div class="place">
      <img src="../../static/icon/icon_agi.png" alt="" />
      <span>{{ place }}</span>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    date: {
      type: String,
      required: true,
    },
    operate: {
      type: String,
      required: false,
    },
    operatePerson: {
      type: String,
      default: "操作人",
    },
    content: {
      type: String,
      required: true,
    },
    place: {
      type: String,
      required: true,
    },
  },
  data() {
    return {};
  },
  methods: {},
  onLoad() {},
};
</script>

<style scoped lang="scss">
.card {
  box-shadow: 0 0 5px rgba($color: #333, $alpha: 0.3);
  border-radius: 5px;
  padding: 2vh;
  .operate {
    display: flex;
    justify-content: space-between;
    margin-bottom: 2vh;
    .left {
      .day {
        background-color: #4ba234;
        color: #fff;
        font-size: 15px;
        padding: 2px 15px;
        border-radius: 6px;
        margin-right: 3vw;
      }
      .work {
        font-weight: 600;
        font-size: 15px;
      }
    }
    .right .person {
      display: flex;
      align-items: center;
      span {
        font-size: 15px;
        color: #c5dcf7;
      }
    }
  }
  .content {
    font-weight: 600;
    font-size: 14px;
    color: #a29f9f;
    margin-bottom: 2vh;
  }
  .place {
    display: flex;
    align-items: center;
    span {
      color: #4ba234;
      font-size: 15px;
      font-weight: 600;
      margin-left: 1vw;
    }
  }
}
</style>
