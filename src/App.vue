<template>
  <a-layout>
    <a-layout-header><img src="@/assets/logo.png" id="logo" />智慧农业综合服务云平台</a-layout-header>
    <a-layout-content>
      <!-- <div id="nav">
        <router-link to="/">Home</router-link> |
        <router-link to="/about">About</router-link>
      </div> -->
      <router-view />
    </a-layout-content>
  </a-layout>
</template>

<style lang="less">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: rgb(var(--green-8));
  background: #fff;
  --green-1: 246, 255, 237;
  --green-2: 217, 247, 190;
  --green-3: 183, 235, 143;
  --green-4: 149, 222, 100;
  --green-5: 149, 222, 100;
  --green-6: 47,175,72;
  --green-7: 56, 158, 13;
  --green-8: 35, 120, 4;
  --green-9: 19, 82, 0;
  --green-10: 9, 43, 0;

  .ant-layout-header,
  .ant-layout-content,
  .ant-layout-footer {
    background: #fff;
  }

  .ant-tabs-top>.ant-tabs-nav::before,
  .ant-tabs-bottom>.ant-tabs-nav::before,
  .ant-tabs-top>div>.ant-tabs-nav::before,
  .ant-tabs-bottom>div>.ant-tabs-nav::before {
    display: none;
  }

  .ant-tabs-tab.ant-tabs-tab-active .ant-tabs-tab-btn {
    color: rgba(0, 0, 0, 0.75) !important;
    font-size: 24px;
  }

  .ant-tabs-ink-bar {
    background-color: rgb(var(--green-6)) !important;
  }


  .ant-layout-header {
    #logo {
      height: 56px;
      width: 56px;
      margin-right: 8px;
    }

    font-size: 20px;
    font-weight: 600;
    color: rgba(var(--green-9), 0.75);
  }

  min-height: 100%;
}

#nav {
  height: 50px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
