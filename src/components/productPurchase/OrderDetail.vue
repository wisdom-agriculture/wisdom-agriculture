<template>
    <a-table :columns="columns" :data-source="data" :pagination="{pageSize:7}">
        <template #bodyCell="{ column,record }">
            <template v-if="column.key === 'product'">
                <div class="product">
                    <img :src="record.prodImg" alt="">
                    <div>{{record.product}}</div>
                </div>
            </template>
            <template v-if="column.key === 'count'">
                <div>
                    <a-input-number v-model:value="record.count" size="large" :min="1" :max="100000" />
                </div>
            </template>
            <template v-if="column.key === 'sum'">
                {{record.prodPrice*record.count+record.prodFreight}}
            </template>
        </template>
    </a-table>
</template>
<script lang="ts" setup>
import { ref, watch } from 'vue';
const props = defineProps<{
    sourceData: {
        prodName: string,
        prodImg: string,
        prodPrice: number,
        prodCount: number,
        prodFreight: number
    }[]
    changeCount: (arg0: number) => void
}>()
const data = ref([{
    product: props.sourceData[0].prodName,
    prodImg: props.sourceData[0].prodImg,
    prodPrice: props.sourceData[0].prodPrice,
    count: props.sourceData[0].prodCount,
    prodFreight:props.sourceData[0].prodFreight
}])
console.log(data.value)
watch(() => data.value[0].count, (newValue) => {
    props.changeCount(newValue)
})
const columns = ref([
    {
        title: '商品',
        dataIndex: 'product',
        key: 'product',
        align: 'center'
    },
    {
        title: '单价',
        dataIndex: 'prodPrice',
        key: 'prodPrice',
        align: 'center'
    },
    {
        title: '数量',
        key: 'count',
        dataIndex: 'count',
        align: 'center'
    },
    {
        title: '合计',
        key: 'sum',
        align: 'center'
    },
]);
</script>
<style lang="less" scoped>
.product {
    display: flex;
    justify-content: center;
    align-items: center;

    >img {
        display: block;
        width: 64px;
        height: 64px;
    }
}
</style>
