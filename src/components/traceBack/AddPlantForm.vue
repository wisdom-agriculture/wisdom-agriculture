<template>
    <a-modal :visible="visible" title="记录作物" @ok="addData()" @cancel="controller.close()" width="800px">
        <template #footer>
            <a-button key="back" @click="controller.close()">Return</a-button>
            <a-button key="submit" type="primary" @click="addData()">Submit
            </a-button>
        </template>
        <div class="addPlantForm">
            <div>作物名称</div>
            <div>
                <a-select :options="plantNameOptions" v-model:value="plantData.plantId" style="width:230px;text-align: left;"></a-select>
            </div>
            <div>作物类型</div>
            <div>
                <a-select v-model:value="plantData.cropType" style="width:230px;text-align: left;">
                    <a-select-option value="粮食作物">粮食作物</a-select-option>
                    <a-select-option value="经济作物">经济作物</a-select-option>
                    <a-select-option value="蔬菜作物">蔬菜作物</a-select-option>
                    <a-select-option value="果类">果类</a-select-option>
                    <a-select-option value="野生果类">野生果类</a-select-option>
                    <a-select-option value="饲料作物">饲料作物</a-select-option>
                    <a-select-option value="蔬菜作物">蔬菜作物</a-select-option>
                    <a-select-option value="药用作物">药用作物</a-select-option>
                </a-select>
            </div>
            <div>肥料名称</div>
            <div>
                <a-input v-model:value="plantData.fertilizerName"></a-input>
            </div>
            <div>种植方法</div>
            <div>
                <a-select v-model:value="plantData.plantingMethod" style="width:230px;text-align: left;">
                    <a-select-option value="大棚种植">大棚种植</a-select-option>
                    <a-select-option value="露天种植">露天种植</a-select-option>
                </a-select>
            </div>
            <div>种植年度</div>
            <div>
                <a-input v-model:value="plantData.plantingYear"></a-input>
            </div>
            <div>是否套袋</div>
            <div>
                <a-select v-model:value="plantData.bagging" style="width:230px;text-align: left;">
                    <a-select-option value="已套袋">已套袋</a-select-option>
                    <a-select-option value="未套袋">未套袋</a-select-option>
                </a-select>
            </div>
            <div>育苗周期</div>
            <div>
                <a-input v-model:value="plantData.seedingPeriod"></a-input>
            </div>
            <div>灌溉周期</div>
            <div>
                <a-input v-model:value="plantData.irrigationCycle"></a-input>
            </div>
            <div>施肥周期</div>
            <div>
                <a-input v-model:value="plantData.fertilizerCycle"></a-input>
            </div>
            <div>除草周期</div>
            <div>
                <a-input v-model:value="plantData.weedingCycle"></a-input>
            </div>
            <div>详细地址</div>
            <div>
                <a-input v-model:value="plantData.detailedAddress"></a-input>
            </div>
            <div></div>
            <div></div>
            <div>备注</div>
            <div style="grid-column-start: 2;grid-column-end: 5;">
                <a-textarea v-model:value="plantData.remark" @change="apis.test"></a-textarea>
            </div>
        </div>
    </a-modal>
</template>
<script lang="ts" setup>
import { ref, defineProps } from 'vue';
import apis, { Trace } from '@/tools/apis'
import { SelectProps } from 'ant-design-vue';
const props = defineProps<{
    visible: boolean,
    controller: {
        open: () => void,
        close: () => void,
        add: (arg0: Trace.Farmer.addPlantBody) => void
    }
}>()
const plotNow = localStorage.getItem('plotNow') ? Number(localStorage.getItem('plotNow')) : 1;
const plantData = ref<Trace.Farmer.addPlantBody>({
    "plantId": 1,
    "fieldId": plotNow,
    "plantStatusId": 1,
    "cropType": '粮食作物',
    "fertilizerName": '',
    "plantingMethod": '大棚种植',
    "plantingYear": '',
    "bagging": '未套袋',
    "seedingPeriod": '',
    "irrigationCycle": '',
    "fertilizerCycle": '',
    "weedingCycle": '',
    "detailedAddress": '',
    "remark": '',
})
const plantNameOptions = ref<SelectProps['options']>([]);
apis.getPlantTypes()
    .then(res => {
        if (res.status == 'success') {
            let temp = []
            for (let i of res.data.data.plantList) {
                temp.push({
                    value: i.id,
                    label: i.name
                })
            }
            plantNameOptions.value = temp;
            plantData.value.plantId=temp[0].value
        }
    })
const addData = () => {
    props.controller.add(plantData.value)
    plantData.value = {
        "plantId": 1,
        "fieldId": plotNow,
        "plantStatusId": 1,
        "cropType": '粮食作物',
        "fertilizerName": '',
        "plantingMethod": '大棚种植',
        "plantingYear": '',
        "bagging": '未套袋',
        "seedingPeriod": '',
        "irrigationCycle": '',
        "fertilizerCycle": '',
        "weedingCycle": '',
        "detailedAddress": '',
        "remark": '',
    }
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