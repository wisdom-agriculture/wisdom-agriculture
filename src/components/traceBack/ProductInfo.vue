<template>
    <a-modal :visible="visible" title="产品基本信息" @cancel="closer()" width="800px">
        <template #footer>
        </template>
        <a-descriptions>
            <a-descriptions-item label="农作物ID">{{data.Record.crops_id}}</a-descriptions-item>
            <a-descriptions-item label="产品ID">{{data.Record.product_id}}</a-descriptions-item>
            <a-descriptions-item label="产品名称">{{data.Record.product_name}}</a-descriptions-item>
            <a-descriptions-item label="净含量">{{data.Record.Net_Content}}</a-descriptions-item>
            <a-descriptions-item label="保存方式">{{data.Record.keep_mathod}}</a-descriptions-item>
            <a-descriptions-item label="负责人">{{data.Record.leader}}</a-descriptions-item>
            <a-descriptions-item label="负责人电话">{{data.Record.leader_tel}}</a-descriptions-item>
            <a-descriptions-item label="配料">{{data.Record.mixed_ingredients}}</a-descriptions-item>
            <a-descriptions-item label="工时">{{data.Record.work_hours}}</a-descriptions-item>
            <a-descriptions-item label="车间">{{data.Record.workshop}}</a-descriptions-item>
            <a-descriptions-item label="数据上链时间">{{data.Record.time}}</a-descriptions-item>
            <a-descriptions-item label="食用推荐">{{data.Record.cooking_recommend}}</a-descriptions-item>
            <a-descriptions-item label="备注">{{data.Record.remarks}}</a-descriptions-item>
        </a-descriptions>
    </a-modal>
</template>
<script lang="ts" setup>
import { defineProps, ref, watch } from 'vue';
import apis, { Trace } from '@/tools/apis'
import { message } from 'ant-design-vue';
const props = defineProps<{
    visible: boolean,
    closer: () => void,
    cropsId: bigint
}>()
const data = ref<Trace.User.prodInfo>({
    Key: '0',
    Record: {
        "Net_Content": '0',
        "cooking_recommend": '0',
        "crops_id": '0',
        "factory": '0',
        "keep_mathod": '0',
        "leader": '0',
        "leader_tel": '0',
        "mixed_ingredients": '0',
        "product_id": '0',
        "product_name": '0',
        "remarks": '0',
        "time": '0',
        "work_hours": '0',
        "workshop": '0'
    }
})
watch(() => props.cropsId, newValue => {
    const hide = message.loading('加载中', 0);
    apis.trace.retailer.getProdId({ cropsId: newValue }).then(res => {
        if (res.status == 'success') {
            return apis.trace.user.getProdInfo({
                // eslint-disable-next-line
                id: BigInt(res.data.data[res.data.data.length - 1].Record.product_id)
            })
        } else if (res.status == 'false') {
            message.error(res.message)
        }
    }).then(res => {
        if (res && res.status == 'success') {
            if(res.data.data.length!==0){
                message.error('无产品信息')
            }else{
                data.value == res.data.data[res.data.data.length - 1]
            }
        } else if (res && res.status == 'false') {
            message.error(res.message)
        }
    }).finally(()=>{
        hide();
    })
})
</script>
<style lang="less" scoped>
.addPlantForm {
    width: 700px;
    display: grid;
    grid-template-columns: repeat(2, 16% 34%);
    text-align: center;
    font-weight: 600;

    >div {
        margin: 4px;
    }
}
</style>