<template>
    <div style="height: 100%;">
        <div ref="pieChartElement" style="height:100%;width:100%;"></div>
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
                    color: '#fff'
                }
            },
            tooltip: {
                trigger: 'item'
            },
        });
        apis.panel.getDiseaseAlert().then(res => {
            if (res.status == 'success') {
                let temp = [];
                for (let i of res.data.data) {
                    temp.push({
                        value: i.percentage,
                        name: i.name,
                    })
                };
                (pieChartControler.value as any).setOption({
                    series: [
                        {
                            name: '作物种植面积统计',
                            type: 'pie',
                            center: ['50%', '30%'],
                            radius: "60%",
                            roseType: 'radius',
                            itemStyle: {
                                borderRadius: 5
                            },
                            data: temp
                        }
                    ],
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