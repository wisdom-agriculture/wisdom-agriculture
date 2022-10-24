<template>
    <a-modal :visible="visible" title="记录作物" @ok="addData()" @cancel="controller.close()" width="800px">
        <template #footer>
            <a-button key="back" @click="controller.close()">Return</a-button>
            <a-button key="submit" type="primary" @click="addData()">Submit
            </a-button>
        </template>
        <div class="addPlantForm">
            <div>品名</div>
            <div>
                <a-input v-model:value="data.productName"></a-input>
            </div>
            <div>配料</div>
            <div>
                <a-input v-model:value="data.ingredients"></a-input>
            </div>
            <div>负责人</div>
            <div>
                <a-input v-model:value="data.leader"></a-input>
            </div>
            <div>电话</div>
            <div>
                <a-input v-model:value="data.phone"></a-input>
            </div>
            <div>生产车间</div>
            <div>
                <a-input v-model:value="data.workshop"></a-input>
            </div>
            <div>生产工时</div>
            <div>
                <a-input v-model:value="data.hour"></a-input>
            </div>
            <div>储藏方法</div>
            <div>
                <a-input v-model:value="data.storageMethod"></a-input>
            </div>
            <div>净含量</div>
            <div>
                <a-input v-model:value="data.netContent"></a-input>
            </div>
            <div>食用推荐</div>
            <div style="grid-column-start: 2;grid-column-end: 5;">
                <a-textarea v-model:value="data.recommended"></a-textarea>
            </div>
            <div>备注</div>
            <div style="grid-column-start: 2;grid-column-end: 5;">
                <a-textarea v-model:value="data.remark"></a-textarea>
            </div>
        </div>
    </a-modal>
</template>
<script lang="ts" setup>
import { ref, defineProps, defineEmits } from 'vue';
import apis, { Trace } from '@/tools/apis'
import { message } from 'ant-design-vue';
const props = defineProps<{
    visible: boolean,
    controller: {
        close: () => void,
    }
    id: number
}>()
const emits=defineEmits(['success'])
const data = ref<Trace.Manufacturer.setPlantInfoBody>({
    "id": props.id,
    "productName": '',
    "ingredients": '',
    "leader": '',
    "phone": '',
    "workshop": '',
    "hour": '',
    "storageMethod": '',
    "netContent": '',
    "recommended": '',
    "remark": ''
})
const addData = () => {
    apis.trace.manufacturer.setPlantInfo(data.value).then(res=>{
        if(res.status=='success'){
            emits('success');
            message.success(res.data.message)
        }else if(res.status=='false'){
            message.error(res.message)
        }
        props.controller.close();
    })
    props.controller.close();
}
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