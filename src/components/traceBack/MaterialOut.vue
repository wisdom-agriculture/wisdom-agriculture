<template>
    <a-modal :visible="visible" title="联系运输" @cancel="controller.close()" width="800px">
        <template #footer>
            <a-button key="back" @click="controller.close">Return</a-button>
            <a-button key="submit" type="primary" @click="submit">Submit
            </a-button>
        </template>
        <div class="materialOut">
            <div>检测结果:</div>
            <div>
                <a-input v-model:value="data.checkResult"> </a-input>
            </div>
            <div>备注:</div>
            <div>
                <a-input v-model:value="data.remark"> </a-input>
            </div>
            <div>检测图片</div>
            <div style="text-align: left;">
                <a-upload v-model:file-list="fileList" action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
                    list-type="picture">
                    <a-button>
                        <upload-outlined></upload-outlined>
                        upload
                    </a-button>
                </a-upload>
            </div>
        </div>
    </a-modal>
</template>
<script lang="ts" setup>
import { ref, defineProps,defineEmits } from 'vue';
import { message, UploadProps } from 'ant-design-vue';
import apis, { Trace } from '@/tools/apis';
const data = ref<Trace.Material.outCropBody>({
    id: 0,
    checkResult: '',
    remark: '',
    photo: '',
})
const emits=defineEmits(['success'])
const props = defineProps<{
    visible: boolean,
    controller: {
        open: (id: number) => void,
        close: () => void,
        add: () => void
    },
    id: number
}>()
const submit = () => {
    apis.trace.material.outCrops({
        id: props.id,
        checkResult: data.value.checkResult,
        remark: data.value.remark,
        photo: data.value.photo
    }).then(res => {
        if (res.status == 'success') {
            message.success(res.data.message)
            emits('success')
        } else if (res.status == 'false') {
            message.error(res.message);
        }
        props.controller.close()
    })
}
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