<template>
    <div class="ordersManagement">
        <TransportProcess :visible="transportProcessVisible" :cancel="cancelTP"></TransportProcess>
        <a-table :data-source="data" :pagination="{pageSize:7}" :columns="columns">
            <template #bodyCell="{column,record}">
                <div v-if="column.key === 'product'">
                    <div>
                        订单编号：{{record.serial}}
                    </div>
                    <div class="product">
                        <img src="" alt="">
                        <div>
                            {{record.product}}
                        </div>
                    </div>
                </div>
                <div v-if="column.key === 'sum'">
                    {{record.value * record.count}}
                </div>
                <div v-if="column.key === 'isPay'">
                    <div v-if="record.isPay" class="yes">
                        是
                    </div>
                    <div v-else class="no">
                        否
                    </div>
                </div>
                <div v-if="column.key === 'isReceive'">
                    <div v-if="record.isReceive" class="yes">
                        是
                    </div>
                    <div v-else class="no">
                        否
                    </div>
                </div>
                <div v-if="column.key === 'action'">
                    <div v-if="!record.isPay">
                        <a-button type="link">付款</a-button>
                    </div>
                    <div v-else-if="!record.isReceive">
                        <a-button type="link">收货</a-button>
                    </div>
                    <div>
                        <a-button type="link" @click="openTP">物流追踪</a-button>
                    </div>
                </div>

            </template>
        </a-table>
    </div>
</template>
<script lang="ts" setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import TransportProcess from '@/components/ordersManagement/TransportProcess.vue';


const columns = ref([
    {
        title: '商品',
        dataIndex: 'product',
        key: 'product',
        align: 'center'
    },
    {
        title: '单价',
        dataIndex: 'value',
        key: 'value',
        align: 'center'
    },
    {
        title: '数量',
        key: 'count',
        dataIndex: 'count',
        align: 'center'
    },
    {
        title: '合计(￥)',
        key: 'sum',
        align: 'center'
    },
    {
        title: '下单时间',
        key: 'time',
        dataIndex: 'time',
        align: 'center'
    },
    {
        title: '是否付款',
        key: 'isPay',
        dataIndex: 'isPay',
        align: 'center'
    },
    {
        title: '是否收货',
        key: 'isReceive',
        dataIndex: 'isReceive',
        align: 'center'
    },
    {
        title: '操作',
        key: 'action',
        align: 'center'
    },
]);
const data = [{
    key: 0,
    serial: '1234567890',
    product: "智能灌溉器",
    value: 120,
    count: 1,
    time: "2022-08-16 9：00：00",
    isPay: false,
    isReceive: false,
}, {
    key: 0,
    serial: '1234567890',
    product: "智能灌溉器",
    value: 120,
    count: 1,
    time: "2022-08-16 9：00：00",
    isPay: false,
    isReceive: false,
},]
const router = useRouter();
const transportProcessVisible = ref(false)//以下TP均为Transport Process
const openTP = () => {
    transportProcessVisible.value = true
}
const cancelTP = () => {
    transportProcessVisible.value = false
}

</script>
<style lang="less" scoped>
.product {
    display: flex;
    justify-content: center;
    align-items: center
}

.product>img {
    display: block;
    width: 64px;
    height: 64px
}

.yes {
    color: #2faf48
}

.no {
    color: #bd3124
}
</style>