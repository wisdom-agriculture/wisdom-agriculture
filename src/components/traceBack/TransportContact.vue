<template>
    <a-modal :visible="visible" title="联系运输" @ok="addData()" @cancel="closer()" width="800px">
        <template #footer>
            <a-button key="back" @click="closer()">Return</a-button>
            <a-button key="submit" type="primary" @click="addData()">Submit
            </a-button>
        </template>
        <div class="addPlantForm">
            <div>选择司机</div>
            <div>
                <a-select v-model:value="TransportData.driverId" :options="driverOptions"
                    style="width:230px;text-align: left;">
                </a-select>
            </div>
            <div>原料厂商</div>
            <div>
                <a-select v-model:value="TransportData.deptId" :options="companyOptions"
                    style="width:230px;text-align: left;">
                </a-select>
            </div>
            <div>备注</div>
            <div style="grid-column-start: 2;grid-column-end: 5;">
                <a-textarea v-model:value="TransportData.remarks"></a-textarea>
            </div>
        </div>
    </a-modal>
</template>
<script lang="ts" setup>
import { ref, defineProps, defineEmits } from 'vue';
import apis, { Trace } from '@/tools/apis'
import { SelectProps, message } from 'ant-design-vue';
const props = defineProps<{
    visible: boolean,
    closer: () => void,
    cropId: bigint,
    originRole?: 'farmer' | 'material' | 'manufacturer'
}>()
const emits = defineEmits(['success'])
const driverOptions = ref<SelectProps['options']>([]);
apis.trace.driver.getDriverList().then(res => {
    if (res.status == 'success') {
        let temp: SelectProps['options'] = []
        for (let i of res.data.data) {
            temp.push({
                value: i.id,
                label: i.nickname
            })
        }
        driverOptions.value = temp
        if (res.data.data.length !== 0) {
            TransportData.value.driverId = res.data.data[0].id
        }
    }
})
const companyOptions = ref<SelectProps['options']>([]);
let transportFetchFunc
switch (props.originRole) {
    case 'material':
        transportFetchFunc = apis.trace.material.getTransportCompany
        break;
    case 'manufacturer':
        transportFetchFunc = apis.trace.manufacturer.getTransportCompany
        break;
    default:
        transportFetchFunc = apis.trace.farmer.getTransportCompany
        break;
}
transportFetchFunc()
    .then(res => {
        if (res.status == 'success') {
            let temp: SelectProps['options'] = []
            for (let i of res.data.data) {
                temp.push({
                    value: i.id,
                    label: i.deptName
                })
            }
            companyOptions.value = temp
            if (res.data.data.length !== 0) {
                TransportData.value.deptId = res.data.data[0].id
            }
        }
    })

const contactTransport = () => {
    apis.trace.transportContact({
        cropsId: props.cropId,
        driverId: TransportData.value.driverId,
        deptId: TransportData.value.deptId,
        remarks: TransportData.value.remarks
    }).then(res => {
        if (res.status == 'success') {
            emits('success');
            message.success('已通知该司机进行运输')
        } else if (res.status == 'false') {
            message.error(res.message)
        }
    })
}
const TransportData = ref<Trace.transportContactBody>({
    cropsId: 0n,
    driverId: 0,
    deptId: 0,
    remarks: ''
})

const addData = () => {
    contactTransport()
    TransportData.value = {
        cropsId: 0n,
        driverId: 0,
        deptId: 0,
        remarks: ''
    }
    props.closer();
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