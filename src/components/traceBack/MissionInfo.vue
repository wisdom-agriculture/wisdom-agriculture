<template>
    <a-modal :visible="visible" title="产品基本信息" @cancel="closer()" width="800px">
        <template #footer>
        </template>
        <a-descriptions>
            <a-descriptions-item label="任务ID">727272727272</a-descriptions-item>
            <a-descriptions-item label="任务名称">奶淇琳</a-descriptions-item>
            <a-descriptions-item label="作物ID">727272727272</a-descriptions-item>
            <a-descriptions-item label="发布时间">2222年22月22日 22:22:22</a-descriptions-item>
            <a-descriptions-item label="状态">涮入火锅</a-descriptions-item>
            <a-descriptions-item label="详细内容">毛肚肉片</a-descriptions-item>
        </a-descriptions>
    </a-modal>
</template>
<script lang="ts" setup>
import { defineProps } from 'vue';
const props = defineProps<{
    visible: boolean,
    closer:()=>void,
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