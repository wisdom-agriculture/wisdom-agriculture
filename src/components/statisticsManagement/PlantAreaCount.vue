<template>
    <div style="height: 100%;">
        <div ref="pieChartElement" style="height:34%;width:100%;border-bottom: 1px solid #5b6b73;"></div>
        <div class="plantAreaLabel">
            <div>作物种植面积统计（Top5）</div>
            <div>单位：亩</div>
        </div>
        <div ref="barChartElement" style="height:70%;width:100%;margin: auto;"></div>
    </div>
</template>
<script setup lang="ts">
import { ref, onMounted } from 'vue';
import apis, { Panel } from '@/tools/apis';
import * as echarts from 'echarts';
import { number } from 'vue-types';
const barChartElement = ref<HTMLDivElement | null>(null);
const barChartControler = ref<echarts.ECharts | null>(null);
const pieChartElement = ref<HTMLDivElement | null>(null);
const pieChartControler = ref<echarts.ECharts | null>(null);
onMounted(() => {
    if (barChartElement.value && pieChartElement.value) {
        pieChartControler.value = echarts.init(pieChartElement.value);
        pieChartControler.value.setOption({
            legend: {
                type: 'scroll',
                orient: 'vertical',
                pageIconColor: "#fff",
                pageIconInactiveColor: "#fff",
                pageTextStyle: {
                    color: "#fff"
                },
                right: 10,
                top: 10,
                bottom: 10,
                textStyle: {
                    color: '#fff'
                }
            },
            tooltip: {
                trigger: 'item'
            },
            // series: [
            //     {
            //         name: '作物种植面积统计',
            //         type: 'pie',
            //         center: ['30%', '50%'],
            //         data: [
            //             { value: 1048, name: 'Search Engine' },
            //             { value: 735, name: 'Direct' },
            //             { value: 580, name: 'Email' },
            //             { value: 484, name: 'Union Ads' },
            //             { value: 300, name: 'Video Ads' }
            //         ]
            //     }
            // ],
        });
        barChartControler.value = echarts.init(barChartElement.value);
        barChartControler.value.setOption({
            tooltip: {},
            xAxis: {
                boundarGap: true,
                axisLine: {
                    lineStyle: {
                        color: '#BFBEBE',
                    },
                },
            },
            yAxis: {
                type: 'category',
                // data: ['一号青菜', '芒果', '葡萄', '西瓜', '二号青菜'],
                axisLabel: {
                    align: 'left',
                    width: 40,
                    overflow: 'truncate',
                    margin: 40
                },
                axisLine: {
                    lineStyle: {
                        color: '#BFBEBE'
                    }
                },
            },
            // series: [
            //     {
            //         name: '作物种植面积统计',
            //         type: 'bar',
            //         data: [100, 150, 200, 250, 300],
            //     }
            // ],
            color: ['#02CDE6'],
            rich: {

            }
        });
        let pieData: { value: number, name: string }[] = []
        let barData: number[] = []
        let barAxis: string[] = []
        apis.panel.getPlantArea().then(res => {
            if (res.status == 'success') {
                let temp = [...res.data.data]
                temp.sort((a, b) => b.area - a.area)
                for (let i of res.data.data) {
                    pieData.push({
                        value: i.area,
                        name: i.plant
                    })
                }
                for (let i in res.data.data) {
                    if ((i as unknown as number) < 5) {
                        barData.push(temp[i].area)
                        barAxis.push(temp[i].plant)
                    }
                }
                (pieChartControler.value as any).setOption({
                    series: [
                        {
                            name: '作物种植面积统计',
                            type: 'pie',
                            center: ['30%', '50%'],
                            data: pieData
                        }
                    ],
                });
                (barChartControler.value as any).setOption({
                    yAxis: {
                        data: barAxis,
                    },
                    series: [
                        {
                            name: '作物种植面积统计',
                            type: 'bar',
                            data: barData,
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