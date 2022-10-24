<template>
    <a-modal :visible="visible" title="联系运输" @cancel="controller.close()" width="800px">
        <template #footer>
            <a-button key="back" @click="controller.close()">Return</a-button>
            <a-button key="submit" type="primary">Submit
            </a-button>
        </template>
        <div class="materialOut">
            <div>任务名称</div>
            <div>
                <a-input> </a-input>
            </div>
            <div>任务详情:</div>
            <div>
                <a-textarea> </a-textarea>
            </div>
        </div>
    </a-modal>
</template>
<script lang="ts" setup>
import { ref, defineProps } from 'vue';
import type { UploadProps } from 'ant-design-vue';
export interface missionReportItem {
    name:string,
    detail: string,
}
const props = defineProps<{
    visible: boolean,
    controller: {
        open: () => void,
        close: () => void,
        add: () => void
    }
}>()
const fileList: UploadProps['fileList'] = [];
</script>
<style lang="less" scoped>
.materialOut {
    width: 700px;
    display: grid;
    grid-template-columns: 18% 80%;
    gap: 2%;
    text-align: right;
    font-weight: 600;

    >div {
        margin: 4px 0;
    }
}
</style>