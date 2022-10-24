import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router";
import SignIn from "@/views/SignIn.vue";
import SignUp from "@/views/SignUp.vue";
import DashBoard from "@/views/console/DashBoard.vue";
import OrdersManagement from "@/views/console/OrdersManagement.vue";
import PlotManagement from "@/views/console/PlotManagement.vue";
import StatisticsManagement from "@/views/console/StatisticsManagement.vue";
import AlertConsole from "@/views/console/AlertConsole.vue"
import EnvAlertSetting from '@/views/console/EnvAlertSetting.vue'
import PlantRecommend from "@/views/console/PlantRecommend.vue"
import TraceFarmer from "@/views/console/TraceFarmer.vue";
import TraceMaterial from "@/views/console/TraceMaterial.vue";
import TraceManufactor from "@/views/console/TraceManufactor.vue";
import TraceDriver from "@/views/console/TraceDriver.vue";
import TraceRetailer from "@/views/console/TraceRetailer.vue";
import RetailerManagement from "@/views/console/RetailerManagement.vue";
import BlockChainBrowse from "@/views/console/BlockChainBrowse.vue";
import GoodsManagement from "@/views/console/GoodsManagement.vue"
import DeviceShop from "@/views/console/DeviceShop.vue";
import ProductDetail from "@/views/console/ProductDetail.vue";
import ProductPurchase from "@/views/console/ProductPurchase.vue";
import AliPay from "@/views/console/AliPay.vue";
import ShoppingCenter from '@/views/console/ShoppingCenter.vue'
import GoodsEdit from "@/views/console/GoodsEdit.vue";




const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "SignIn",
    component: SignIn,
  },
  {
    path: "/PrivacyAgreement",
    name: "PrivacyAgreement",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/PrivacyAgreement.vue"),
  },
  {
    path: '/SignUp',
    name: "SignUp",
    component: SignUp
  },
  {
    path: '/DashBoard',
    name: "DashBoard",
    component: DashBoard,
    children: [{
      path: 'OrdersManagement',
      name: 'OrdersManagement',
      component: OrdersManagement
    }, {
      path: 'GoodsEdit',
      name: 'GoodsEdit',
      component: GoodsEdit
    }, {
      path: 'PlotManagement',
      name: 'PlotManagement',
      component: PlotManagement
    },{
      path: 'ShoppingCenter',
      name: 'ShoppingCenter',
      component: ShoppingCenter
    }, {
      path: 'TraceFarmer',
      name: 'TraceFarmer',
      component: TraceFarmer
    }, {
      path: 'TraceDriver',
      name: 'TraceDriver',
      component: TraceDriver
    }, {
      path: 'TraceManufactor',
      name: 'TraceManufactor',
      component: TraceManufactor
    }, {
      path: 'TraceRetailer',
      name: 'TraceRetailer',
      component: TraceRetailer
    }, {
      path: 'TraceMaterial',
      name: 'TraceMaterial',
      component: TraceMaterial
    }, {
      path: 'RetailerManagement',
      name: 'RetailerManagement',
      component: RetailerManagement
    }, {
      path: 'GoodsManagement',
      name: 'GoodsManagement',
      component: GoodsManagement
    }, {
      path: 'DeviceShop',
      name: 'DeviceShop',
      component: DeviceShop
    }, {
      path: 'AlertConsole',
      name: 'AlertConsole',
      component: AlertConsole
    }, {
      path: 'EnvAlertSetting',
      name: 'EnvAlertSetting',
      component: EnvAlertSetting
    }, {
      path: 'PlantRecommend',
      name: 'PlantRecommend',
      component: PlantRecommend
    }, {
      path: 'ProductDetail',
      name: 'ProductDetail',
      component: ProductDetail
    }, {
      path: 'ProductPurchase',
      name: 'ProductPurchase',
      component: ProductPurchase
    }, {
      path: 'AliPay',
      name: 'AliPay',
      component: AliPay
    }, {
      path: 'BlockChainBrowse',
      name: 'BlockChainBrowse',
      component: BlockChainBrowse
    }]
  }, {
    path: '/StatisticsManagement',
    name: 'StatisticsManagement',
    component: StatisticsManagement
  }
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
