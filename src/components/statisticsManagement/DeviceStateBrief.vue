<template>
    <div style="height: 100%;display: flex;flex-direction: row;">
        <div ref="pieChartElement" style="height:100%;width:40%;border-bottom: 1px solid #5b6b73;"></div>
        <div style="height:100%;width:60%;margin: auto;" class="deviceBriefTable">
            <div class="title">
                <div>设备名称</div>
                <div>设备状态</div>
                <div>使用地块</div>
            </div>
            <div v-for="(i,index) in data" :key="index">
                <div>{{i.name}}</div>
                <div v-if="i.status" class="deviceOpen">已开启</div>
                <div v-else class="deviceClose">未开启</div>
                <div>{{i.field}}</div>
            </div>
        </div>
    </div>
</template>
<script setup lang="ts">
import { defineProps, ref, onMounted } from 'vue';
import apis, { Panel } from '@/tools/apis';
import * as echarts from 'echarts';
export type deviceBriefItem = {
    name: string,
    state: boolean,
    plot: string,
}
const pieChartElement = ref<HTMLDivElement | null>(null);
const pieChartControler = ref<echarts.ECharts | null>(null);
const data = ref<Panel.sensorStatus[]>([])
onMounted(() => {
    if (pieChartElement.value) {
        pieChartControler.value = echarts.init(pieChartElement.value);
        pieChartControler.value.setOption({
            tooltip: {
                trigger: 'item'
            },
            itemStyle: {
                borderRadius: 5,
                borderWidth: 4
            },
        });
        apis.panel.getSensorStatus().then(res => {
            let temp = []
            let [isOff, isOn] = [0, 0]
            if (res.status == 'success') {
                data.value = res.data.data;
                for (let i of res.data.data) {
                    i.status == 0 ? isOff++ : isOn++
                }
            }
            if (isOn) {
                temp.push({
                    value: isOn,
                    name: '已开启'
                })
            }
            if (isOff) {
                temp.push(
                    {
                        value: isOff,
                        name: '未开启'
                    }
                )
            }
            (pieChartControler.value as any).setOption({
                series: [
                    {
                        name: '作物种植面积统计',
                        type: 'pie',
                        data: temp,
                        radius: ['50%', '70%']
                    }
                ]
            })
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

.deviceBriefTable {
    overflow: auto;

    >div {
        display: flex;
        flex-direction: row;

        >div {
            width: 33%;
            line-height: 2em;
            text-align: center;
            vertical-align: middle;
        }

        .deviceOpen {
            color: rgba(75, 162, 52, 1);
        }

        .deviceClose {
            color: rgb(189, 49, 36)
        }
    }

    .title {
        color: #fff;
        font-weight: 600;
    }
}
</style>