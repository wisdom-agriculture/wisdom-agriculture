<template>
    <div>
        <a-select v-model:value="currentDisplay">
            <a-select-option value="NPotency">含氮量</a-select-option>
            <a-select-option value="PPotency">含磷量</a-select-option>
            <a-select-option value="KPotency">含钾量</a-select-option>
            <a-select-option value="SoilTemp">土壤温度</a-select-option>
            <a-select-option value="SoilHumidity">土壤湿度</a-select-option>
            <a-select-option value="pH">土壤pH值</a-select-option>
        </a-select>
        <div id="envChartEntity" ref="chartElement" style="height:332px;width:100%"></div>
    </div>
</template>
<script setup lang="ts">
import { defineProps, ref, watch } from 'vue';
import { envDataClassification, envDataType } from '@/views/console/PlantRecommend.vue';
import * as echarts from 'echarts';

const currentDisplay = ref<envDataClassification>('pH');
const chartElement = ref<HTMLDivElement | null>(null);
const chartControler = ref<echarts.ECharts | null>(null);
watch(chartElement, (newValue) => {
    if (newValue) {
        console.log('computed')
        chartControler.value = echarts.init(newValue);
        chartControler.value.setOption({
            tooltip: {},
            xAxis: {
                data: ['4:00', '8:00', '12:00', '16:00', '20:00', '24:00'],
            },
            yAxis: {},
            series: [
                {
                    name: 'CO2浓度',
                    type: 'line',
                    data: [5, 20, 36, 10, 10, 20]
                }
            ],
            rich: {

            }
        });
    }
})


</script>