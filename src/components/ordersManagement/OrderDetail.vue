<template>
    <a-modal :visible="visible" title="产品基本信息" @cancel="closer()" width="800px">
        <template #footer>
        </template>
        <div class="orderDetail">
            <div>
                订单编号：
            </div>
            <div>{{data?.orderNumber}}</div>
            <div>
                商品编号：
            </div>
            <div>{{data?.producesId}}</div>
            <div>
                商品名称：
            </div>
            <div>{{data?.producesName}}</div>
            <div>
                购买数量：
            </div>
            <div>{{data?.producesWeight}}</div>
            <div>
                用户ID：
            </div>
            <div>{{data?.customerId}}</div>
            <div>
                收货人姓名：
            </div>
            <div>{{data?.customerName}}</div>
            <div>
                收货人电话号：
            </div>
            <div>{{data?.customerPhone}}</div>
            <div>
                收货地址：
            </div>
            <div style="position: relative;">
                <a-input :disabled="isDisabled" v-model:value="data.address" style="text-align: right;"></a-input>
                <a-button type="link" style="position:absolute;right:-64px" @click="editAddress">修改</a-button>
            </div>
            <div>
                收货备注
            </div>
            <div>{{data?.orderRemark}}</div>
            <div>
                合计（元）
            </div>
            <div>{{data?.amountPayable}}</div>
            <div>
                付款方式
            </div>
            <div>{{data?.paymentMethods}}</div>
        </div>
    </a-modal>
</template>
<script lang="ts" setup>
import apis, { Shop } from '@/tools/apis';
import { message } from 'ant-design-vue';
import { ref, watch } from 'vue';
const props = defineProps<{
    visible: boolean,
    closer: () => void,
    id: number
}>()
const key = 'order_loading'
const data = ref<Shop.orderDetail>({
    "id": 0,
    "orderNumber": '',
    "producesId": 0,
    "producesName": '',
    "producesWeight": 0,
    "customerId": 0,
    "customerName": '',
    "customerPhone": '',
    "address": '',
    "orderRemark": '',
    "amountPayable": 0,
    "paymentMethods": ''
})
const getData = () => {
    message.loading({
        content: '加载中',
        duration: 0,
        key
    })
    apis.shop.getOrderDetail({ id: props.id }).then(res => {
        if (res.status == 'success') {
            data.value = res.data.data
        } else if (res.status == 'false') {
            message.error(res.message)
        }
        message.destroy(key)
    })
}
const isDisabled = ref(true)
watch(() => props.id, (newValue) => getData())
const editAddress = () => {
    isDisabled.value = !isDisabled.value
    if (isDisabled.value) {
        apis.shop.editAddress({ id: props.id, address: data.value.address });
    }
}
</script>
<style lang="less" scoped>
.orderDetail {
    width: 700px;
    display: grid;
    grid-template-columns: repeat(2, 16% 34%);
    text-align: right;
    font-weight: 600;

    >div {
        margin: 4px;
    }
}
</style>