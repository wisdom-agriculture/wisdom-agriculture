/*
    将页面跳转进行了一下提取, item表示要跳转的页面的名称
*/
// 跳转到除tag页
exports.toOtherPage = (item, data = {}) => {
  let url = `/pages/${item}/index?`;
  for (key in data) {
    url += `${key}=${data[key]}&`;
    console.log(key);
    console.log(data[key]);
  }
  uni.navigateTo({
    url: url,
  });
};
// 跳转到tag页
exports.toTabPage = (item) => {
  uni.switchTab({
    url: `/pages/${item}/index`,
  });
};
// 跳转到上number页
exports.toPreviousPage = (number = 1) => {
  uni.navigateBack({
    delta: number,
    success: () => {
      console.log(1);
    },
  });
};
// 带参跳转
exports.toOtherPWithPram = (item, paramName, paramVal) => {
  uni.navigateTo({
    url: `/pages/${item}/index?${paramName}=${paramVal}`,
  });
};
