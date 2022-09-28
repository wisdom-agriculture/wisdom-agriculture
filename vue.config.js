const path = require("path");
module.exports = {
  transpileDependencies: ["uview-ui"],
  devServer: {
    proxy: {
      "/myApi": {
        target: "http://localhost:8081/",
        pathRewrite: {
          "^/myApi": "",
        },
        secure: false,
        changeOrigin: true,
      },
      "/api": {
        target: "https://farm.yaohy.cn/",
        pathRewrite: {
          "^/api": "",
        },
        secure: false,
        changeOrigin: true,
      },
    },
  },
  configureWebpack: {
    resolve: {
      alias: {
        "@": path.resolve(__dirname, "./src"),
      },
    },
  },
};
