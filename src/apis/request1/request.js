import { $http, NoLoadhttp } from ".";
// 将封装的展示消息提示
uni.$showMsg = (title = "数据加载失败！", duration = 1500) => {
  uni.showToast({
    title,
    duration,
    icon: "",
  });
};

uni.$http = $http;
uni.$NoLoadhttp = NoLoadhttp;
NoLoadhttp.baseUrl = "";
$http.baseUrl = "";

NoLoadhttp.beforeRequest = (options) => {
  let token = uni.getStorageSync("token");
  const headers = {
    Authorization: `${token}`,
  };
  options.header = headers;
};
$http.beforeRequest = (options) => {
  let token = uni.getStorageSync("token");
  const headers = {
    Authorization: `${token}`,
  };
  options.header = headers;
  uni.showLoading({
    title: "加载中",
  });
};
$http.afterRequest = function (options) {
  const { data } = options;
  uni.hideLoading();
};
