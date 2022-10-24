<template>
    <a-tabs v-model:activeKey="activeKey" centered>
        <a-tab-pane key="1" tab="登录">
            <div id="sign-in-tab">
                <a-input v-model:value="phone" placeholder="手机号"></a-input>
                <div id="validationContainer">
                    <a-input v-model:value="validationNumber" placeholder="验证码"></a-input>
                    <a-button :class="VNButtonClass" @click="getValidationNumber">{{ VNMessage }}</a-button>
                </div>
                <div id="privacyAgreement">
                    登录代表同意<a-button @click="goPrivacyAgreement" type="link">《用户隐私协议》</a-button>
                </div>
                <div id="loginContainer">
                    <green-button @click="logIn">开启智慧种植之旅</green-button>
                </div>
                <div id="thirdPartySignIn">
                    <div id="thirdPartyDevider">第三方登录</div>
                    <div id="thirdPartyIcons">
                        <qq-outlined id="qqIcon" />
                        <wechat-outlined id="wechatIcon" />
                        <weibo-circle-outlined id="weiboIcon" />
                    </div>
                </div>
            </div>
        </a-tab-pane>
    </a-tabs>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router'
import { QqOutlined, WechatOutlined, WeiboCircleOutlined } from '@ant-design/icons-vue'
import GreenButton from '@/components/GreenButton.vue'
import apis from '@/tools/apis'
import { message } from 'ant-design-vue';
let activeKey = ref('1');
let phone = ref('');
let validationNumber = ref('');


let VNGetTime = ref(0);//VN = validation number
let VNButtonClass = ref('');
let VNMessage = ref('获取验证码')
const getValidationNumber = () => {
    if(VNGetTime.value===0){
        VNGetTime.value = Date.now();
        VNButtonClass.value = "disabled"
        VNCountDown();
        apis.vertification({phone:phone.value}).then((res:string)=>{
            message.success({content:res,duration:10})
        })
    }
}
const VNCountDown = () => {
    let timeSlice = 60 - ((Date.now() - VNGetTime.value) / 1000);
    if (timeSlice < 0) {
        VNButtonClass.value = "";
        VNMessage.value = "获取验证码";
        VNGetTime.value=0;
    } else {
        VNMessage.value = String(Math.floor(timeSlice))+'S';
        setTimeout(() => { VNCountDown() }, 500);
    }
}

const router = useRouter();
const goPrivacyAgreement = () => {
    router.push('PrivacyAgreement')
}
const logIn = async () => {
    const result=await apis.login({
        phone:phone.value,
        code:validationNumber.value
    })
    if(result==='success'){
        message.success({content:'登录成功'})
        router.push('SignUp')
    }else{
        message.error(result)
    }
}
</script>

<style scoped lang="less">
#sign-in-tab {
    padding: 24px 64px;

    #validationContainer {
        position: relative;

        button {
            position: absolute;
            top: 0;
            right: 0;
            border-radius: 20px;
            background: #fff;
            color: rgb(var(--green-6));
            border-color: rgb(var(--green-6));
        }
        .disabled{
            color: #00000050 !important; 
            border-color: #00000050 !important;
        }
        .ant-btn:hover,
        .ant-btn:focus,
        .ant-btn:active {
            text-decoration: none;
            background: #fff;
            color: rgb(var(--green-6));
            border-color: rgb(var(--green-6));
        }
    }

    #privacyAgreement {
        text-align: center;

        button {
            padding: 0;
            color: rgb(var(--green-6));
        }
    }

    #loginContainer {
        button {
            width: 100%;
            height: 60px;
            color: #fff;
            font-size: 18px;
            font-weight: 600;
            margin-top: 16px;
            border-radius: 30px;
            border-color: rgb(var(--green-6));
            background: rgb(var(--green-6));
        }
    }

    #thirdPartySignIn {
        text-align: center;
        margin-top: 24px;

        #thirdPartyDevider {
            font-weight: 600;
        }

        #thirdPartyDevider::after,
        #thirdPartyDevider::before {
            margin: 0 8px;
            content: "";
            display: inline-block;
            position: relative;
            bottom: 0.4em;
            border-color: rgb(var(--green-3));
            border-style: none none solid;
            border-width: 0.2em;
            width: 108px;
        }

        #thirdPartyIcons {
            font-size: 36px;
            text-align: center;

            span {
                display: inline-block;
                margin: 0 24px;
            }

            #qqIcon {
                color: #2979FF
            }

            #wechatIcon {
                color: #01C666
            }

            #weiboIcon {
                color: #F96654
            }
        }
    }

    .ant-input {
        margin: 8px;
        border-style: none none solid;
        border-radius: 0;
        margin-bottom: 32px;
    }

    .ant-input:focus,
    .ant-input:hover {
        box-shadow: 0px 3px 3px -2px rgb(var(--green-6)) !important;
        border-color: rgba(var(--green-6), 0.5);
    }


}
</style>
