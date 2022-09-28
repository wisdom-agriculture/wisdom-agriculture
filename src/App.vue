<script>
export default {
  onLaunch: function () {},
  onShow: function () {},
  onHide: function () {},
};
</script>

<style lang="scss">
/*每个页面公共css */
@import "uview-ui/index.scss";
uni-page-body {
  height: 100%;
}
image {
  width: 100%;
  height: 100%;
  display: block;
}

* {
  margin: 0%;
  padding: 0%;
}
// 单行省略
.overflowOmit {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
// 多行省略
.overflowMore {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
</style>
