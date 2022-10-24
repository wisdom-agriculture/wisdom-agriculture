<template>
    <a-drawer :visible="visible" width="80vw" style="color: red" title="农作物链上过程详情" placement="right" @close="close()">
        <a-table :columns="columns" :data-source="data">
            <template #bodyCell="{ column, record }">
                <template v-if="column.key === 'crops_grow_photo_url'">
                    <img :src="record.crops_grow_photo_url" alt="" class="pictureClass">
                </template>
            </template>
        </a-table>
    </a-drawer>
</template>
<script setup lang="ts">
import { defineProps } from 'vue';
import { Trace } from '@/tools/apis'
const props = defineProps<{
    visible: boolean,
    close: () => void,
    data: Trace.Farmer.plantProcess[]
}>()
const columns = [
    {
        dataIndex: 'Key',
        title: '过程ID',
        key: 'Key',
    },
    {
        dataIndex: 'crops_bak_id',
        title: '作物ID',
        key: 'crops_bak_id',
    },
    {
        dataIndex: 'grow_status',
        title: '生长状态',
        key: 'grow_status',
    },
    {
        dataIndex: 'illumination_status',
        title: '光照情况',
        key: 'illumination_status',
    },
    {
        dataIndex: 'record_time',
        title: '记录时间',
        key: 'record_time',
    },
    {
        dataIndex: 'temperature',
        title: '温度',
        key: 'temperature',
    },
    {
        dataIndex: 'water_content',
        title: '水分状态',
        key: 'water_content',
    },
    {
        dataIndex: 'remarks',
        title: '备注',
        key: 'remarks',
    },
    {
        dataIndex: 'crops_grow_photo_url',
        title: '图片',
        key: 'crops_grow_photo_url',
    },
]

</script>
<style lang="less">
.pictureClass {
    height: 64px;
}
</style>