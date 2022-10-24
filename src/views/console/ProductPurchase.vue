<template>
    <div class="purchaseWrapper">
        <div class="goBack">
            <left-circle-outlined class="button" />
            <div class="description">
                购买详情
            </div>
        </div>
        <h3>选择收货地址</h3>
        <rolling-bar :pagination="4" :data="data">
            <template #card="{data}">
                <receive-address :selected="selected" :data="data" :onSelect="selectAddr" />
            </template>
        </rolling-bar>
        <h3>确认订单信息</h3>
        <order-detail :changeCount="changeCount" :sourceData="[{
            prodName:$route.params.prodName as string,
            prodImg:$route.params.prodImg as string,
            prodPrice:Number($route.params.prodPrice as string),
            prodCount:Number(prodCount),
            prodFreight:Number($route.params.prodFreight as string),

        }]"></order-detail>
        <h3>备注</h3>
        <a-textarea placeholder="选填，付款后商家可见" v-model:value="remark"></a-textarea>
        <a-button @click="goPay" type="primary" style="margin:12px 12px 12px auto;display: block;">提交订单
        </a-button>
    </div>
</template>
<script lang="ts" setup>
import { LeftCircleOutlined } from '@ant-design/icons-vue';
import RollingBar from '@/components/shoppingCenter/RollingBar.vue';
import ReceiveAddress from '@/components/productPurchase/ReceiveAddress.vue';
import OrderDetail from '@/components/productPurchase/OrderDetail.vue';
import { useRouter, useRoute } from 'vue-router';
import { ref, computed } from 'vue';
import apis, { Shop } from '@/tools/apis';
const router = useRouter()
const route = useRoute()
console.log(route.params)
const selected = ref(0);
const selectAddr = (arg0: number) => {
    selected.value = arg0
    console.log(arg0)
}
const sourceData = ref<Shop.getReceiveAddressRes>()
const data = computed(() => {
    let sourceDataRef = sourceData.value
    if (sourceDataRef !== undefined) {
        if (sourceDataRef.data.userShoppingAddressResults.length !== 0)
            selected.value = sourceDataRef.data.userShoppingAddressResults[0].id
        return sourceDataRef.data.userShoppingAddressResults
    } else {
        return []
    }
})

const prodCount = ref(Number(route.params.count));
const changeCount = (arg0: number) => {
    prodCount.value = arg0;
}
const remark = ref('')
const getData = () => apis.shop.getReceiveAddress().then(res => {
    if (res.status == 'success') {
        sourceData.value = res.data
    }
})
const goPay = () => {
    apis.shop.submitOrder({
        produceSkuId: Number(route.params.skuid as string),
        producesWeight: prodCount.value,
        address: (data.value.find((i) => i.id == selected.value) as Shop.receiveAddress).userShoppingAddress,
        orderRemark: remark.value
    }).then(res => {
        if (res.status == 'success')
            router.push({
                name: 'AliPay', params: {
                    subject: route.params.prodName,
                    traceNo: res.data.data.orderNumber,
                    totalAmount: prodCount.value*Number(route.params.prodPrice)+Number(route.params.prodFreight)
                }
            })
    });
}
getData();
</script>
<style lang="less" scoped>
.purchaseWrapper {
    color: #000;
    padding: 0 5%;

    h3 {
        font-size: 24px;
        font-weight: 600;
    }

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
}
</style>