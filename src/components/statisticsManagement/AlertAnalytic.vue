<template>
    <div style="height: 100%;">
        <div>疾病虫害发生时段统计（次/月）</div>
        <div ref="pieChartElement" style="height:90%;width:100%;"></div>
    </div>
</template>
<script setup lang="ts">
import { defineProps, ref, onMounted } from 'vue';
import * as echarts from 'echarts';
import apis from '@/tools/apis';
const pieChartElement = ref<HTMLDivElement | null>(null);
const pieChartControler = ref<echarts.ECharts | null>(null);
onMounted(() => {
    if (pieChartElement.value) {
        pieChartControler.value = echarts.init(pieChartElement.value);
        pieChartControler.value.setOption({
            legend: {
                bottom: 10,
                textStyle: {
                    color: "#fff",
                    fontSize: 18
                }
            },
            tooltip: {
                trigger: 'item'
            },
            xAxis: {
                type: 'category',
                data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']

            },
            yAxis: {
                type: 'value',
                minInterval: 1
            },
        });
        apis.panel.getDiseaseByTime().then(res => {
            if (res.status == 'success') {
                let temp = []
                for (let i of res.data.data) {
                    temp.push({
                        name: i.name,
                        data: i.time,
                        type: 'line',
                    })
                };
                (pieChartControler.value as any).setOption({
                    series: temp
                })
            }
        })
    }
})
</script>
<style lang="less" scoped>
.plantAreaLabel {
    display: flex;
    justify-content: space-between;

    :first-child {
        color: rgb(191, 190, 190)
    }

    :last-child {
        color: #fff
    }
}
</style>