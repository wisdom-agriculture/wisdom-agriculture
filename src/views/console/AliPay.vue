<template>
    <div class="AliPayWrapper">
        <div class="goBack">
            <left-circle-outlined class="button" @click="router.back()" />
            <div class="description">
                支付
            </div>
        </div>
        <div v-html="data">
        </div>
    </div>
</template>
<script lang="ts" setup>
import { LeftCircleOutlined } from '@ant-design/icons-vue';
import { useRouter, useRoute } from 'vue-router';
import apis from '@/tools/apis';
import { ref } from 'vue'
const route = useRoute();
const data = ref('')
const getData = () => {
    apis.shop.getAlipay({
        subject: route.params.subject as string,
        traceNo: route.params.traceNo as string,
        totalAmount: Number(route.params.totalAmount as string)
    }).then(res => {
        // let temp=document.createElement('div');
        // temp.innerHTML=res;
        data.value = res
        setTimeout(() => {
            document.forms[0].submit()
        }, 200)
    })
}
getData()
const router = useRouter()
</script>
<style lang="less" scoped>
.AliPayWrapper {
    color: #000;

    .goBack {
        display: flex;

        .button {
            font-size: 48px;
            width: 48px;
            margin-right: 8px;
        }

        .description {
            line-height: 48px;
            font-size: 18px;
            text-align: center;
            vertical-align: middle;
            background-color: rgba(239, 239, 239, 0.58);
            color: rgb(108, 108, 108);
            flex-grow: 1;
            border-radius: 8px;
        }
    }

    img {
        height: 240px;
        width: 240px;
        display: block;
        margin: 48px auto;
    }
}
</style>