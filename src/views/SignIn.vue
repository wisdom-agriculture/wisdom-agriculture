<template>
  <div id="loginCardContainer">
    <FloatingCard id="loginCard">
      <div id="projDescription">
        <h2> 智慧农业<br />
          综合服务云平台</h2>
        <div>种植推荐最大化作物产量、精准识别病虫害、一物一码区块链溯源，体验从种植到售卖的一站式综合服务</div>
      </div>
      <SignInTab id="signInTabs"></SignInTab>
    </FloatingCard>
    <div id="SignFooter">
      <div id="teamName">
        智慧农业工作室
      </div>
      <div id="copyrightFooter">
        Copyright © 2022-2024 智慧农业All Rights Reserved. 粤ICP备####号
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import FloatingCard from "@/components/FloatingCard.vue";
import SignInTab from "./SignInTab.vue";
</script>

<style scoped lang="less">
#loginCardContainer {
  height: 100%;

  #loginCard {
    width: 850px;
    height: 600px;
    margin-top: 75px;
    margin-bottom: 75px;
    display: flex;

    #projDescription {
      display: flex;
      flex-direction: column;
      justify-content: center;

      h2 {
        font-size: 30px;
        color: #fff;
        text-align: center;
      }

      div {
        width: 213px;
        font-size: 20px;
        margin: 24px auto;
      }

      width: 300px;
      background-color: rgb(var(--green-6));
      padding:32px;
      color:#fff;
    }

    #signInTabs {
      width: 100%;
      margin: auto
    }
  }

  #SignFooter {
    text-align: center;
    #teamName::after,
    #teamName::before{
      content: "";
      display: inline-block;
      position: relative;
      bottom: 0.4em;
      border-color: rgb(var(--green-6));
      border-style: none none solid;
      border-width: 0.2em;
      width: 42px;
    }
  }

  display: flex;
  align-items: center;
  flex-direction: column;
}
</style>
