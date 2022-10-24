<template>
    <a-modal :visible="visible" title="农作物链上数据详情" @cancel="closer()" width="800px">
        <template #footer>
        </template>
        <a-descriptions>
            <a-descriptions-item label="农作物ID">{{plantData?.crops_id}}</a-descriptions-item>
            <a-descriptions-item label="农作物名称">{{plantData?.crops_name}}</a-descriptions-item>
            <a-descriptions-item label="地址">{{plantData?.address}}</a-descriptions-item>
            <a-descriptions-item label="农户">{{plantData?.farmer_name}}</a-descriptions-item>
            <a-descriptions-item label="施肥情况">{{plantData?.apply_fertilizer_cycle}}</a-descriptions-item>
            <a-descriptions-item label="种植方式">{{plantData?.plant_mode}}</a-descriptions-item>
            <a-descriptions-item label="肥料名称">{{plantData?.fertilizer_name}}</a-descriptions-item>
            <a-descriptions-item label="是否套袋种植">{{plantData?.bagging_status}}</a-descriptions-item>
            <a-descriptions-item label="农户电话">{{plantData?.farmer_tel}}</a-descriptions-item>
            <a-descriptions-item label="种植年度">{{plantData?.year}}</a-descriptions-item>
            <a-descriptions-item label="育苗周期">{{plantData?.grow_seedlings_cycle}}</a-descriptions-item>
            <a-descriptions-item label="灌溉周期">{{plantData?.irrigation_cycle}}</a-descriptions-item>
            <a-descriptions-item label="除草周期">{{plantData?.weed_cycle}}</a-descriptions-item>
            <a-descriptions-item label="登记时间">{{plantData?.register_time}}</a-descriptions-item>
            <a-descriptions-item label="备注">{{plantData?.remarks}}</a-descriptions-item>
        </a-descriptions>
    </a-modal>
</template>
<script lang="ts" setup>
import { defineProps } from 'vue';
import { Trace } from '@/tools/apis'
const props = defineProps<{
    visible: boolean,
    closer: () => void,
    plantData?: Trace.Farmer.plantDetail | null,
}>()
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