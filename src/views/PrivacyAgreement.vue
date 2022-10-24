<template>

  <div class="PrivacyAgreement">
    <header>
      <a-button type="text" :onclick="goBack">
        <arrow-left-outlined />
      </a-button>
      <h1>智慧农业综合服务云平台隐私政策</h1>
    </header>
    <div>
      您好，欢迎来到智慧农业综合服务云平台。</div>
    <div>
      本《隐私政策》适用于我们提供的一切智慧农业综合服务云平台服务。当您使用我们任何单项服务时，您同意接受本《隐私政策》以及我们在该单项服务中发出的特定隐私信息类政策条款（下列称“特定条款”）的保护，在此情况下特定条款与本政策条款同时对您产生效力。如特定条款与本《隐私政策》条款存在同类条款的不一致约定，则在特定条款约束范围内应以特定条款为准。如我们提供的某一单项服务不适用本《隐私政策》的，我们在该服务中会以适当方式明示排除适用本《隐私政策》。请注意我们不时地会检查我们的政策，因此有关的措施会随之变化。我们恳请您定期光顾本页面，以确保对我们《隐私政策》最新版本始终保持了解。如果您使用或继续使用智慧农业综合服务云平台服务，这都表示您同意我们按照本《隐私政策》收集、使用、储存和分享您的信息。希望您仔细阅读《隐私政策》（以下简称“本政策”），详细了解我们对信息的收集、使用方式，以便您更好地了解我们的服务并在是否选择服务以及选择何种服务时更加符合您的真实意思表示。
    </div>
    <div>
      本隐私政策将帮助您了解以下内容：</div>
    <div>
      1.我们如何收集和使用您的个人信息</div>
    <div>
      2.我们如何共享、转让、公开披露您的个人信息</div>
    <div>
      3.我们如何保护您的个人信息</div>
    <div>
      4.您如何管理您的个人信息/您的权利</div>
    <div>
      5.存储信息的地点和期限，您的个人信息如何在全球范围转移</div>
    <div>
      6.本隐私政策如何更新</div>
    <div>
      7.广告/我们向您发送的信息</div>
    <div>
      8.适用范围</div>
    <div>
      9.其他</div>
    <div>
      我们会根据合法正当、必要、透明的原则，基于本政策所述的目的，收集和使用您的个人信息。如果我们将您的个人信息用于本政策未载明的其它用途，或基于其他特定目的而收集您的个人信息，我们将以合理的方式告知您，并在使用前再次征得您的同意。</div>
    <div>
      为实现我们的基本业务功能，我们需要向您收集以下个人信息，若您拒绝提供，我们将无法向您提供相应的服务。我们会出于本政策所述的以下目的，收集和使用您的个人信息：
    </div>

  </div>
</template>

<script setup lang="ts">
/* eslint-disable */
import { ArrowLeftOutlined } from '@ant-design/icons-vue';
import { useRouter } from 'vue-router';
const router = useRouter();
const goBack = () => router.push('/')
</script>

<style lang="less">
.PrivacyAgreement {
  font-size: 16px;

  div{
    margin:0 32px;
  }

  h1 {
    text-align: center;
  }

  button {
    position: absolute;
  }

  text-indent: 2em;
}
</style>
