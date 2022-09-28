import Vue from 'vue'
import App from './App'
import uView from "uview-ui";
import "./apis/request1/request"
Vue.config.productionTip = false

App.mpType = 'app'
Vue.use(uView)
const app = new Vue({
  ...App
})
app.$mount()
