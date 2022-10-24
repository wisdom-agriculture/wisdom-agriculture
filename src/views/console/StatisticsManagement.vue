<template>
    <div class="plotManagement">
        <div class="titlePart" data-v-1a745142>
            <div class="before lineContainer" data-v-1a745142>
                <div class="line top"></div>
                <div class="line mid"></div>
                <div class="line bottom"></div>
            </div>
            <div class="title">
                <div class="line blue up"></div>
                <div class="line blue down" data-v-1a745142></div>
                <div data-v-1a745142> 智慧农业大数据看板 </div>
            </div>
            <div class="after lineContainer" data-v-1a745142>
                <div class="line top" data-v-1a745142></div>
                <div class="line mid" data-v-1a745142></div>
                <div class="line bottom" data-v-1a745142></div>
            </div>
        </div>
        <div class="contentPart">
            <div class="leftPart">
                <future-card title="地块种植面积统计">
                    <div style="height:33vh;padding:8px">
                        <area-count></area-count>
                    </div>
                </future-card>
                <future-card title="作物种植面积统计">
                    <div style="height:40vh;padding:8px">
                        <plant-area-count></plant-area-count>
                    </div>
                </future-card>
            </div>
            <div class="middlePart">
                <future-card title="全部地块实时监控">
                    <div style="height:45vh;padding:8px">
                        <monitor-carousel></monitor-carousel>
                    </div>
                </future-card>
                <future-card title="设备概况">
                    <div style="height:28vh;padding:8px">
                        <device-state-brief></device-state-brief>
                    </div>
                </future-card>
            </div>
            <div class="rightPart">
                <future-card title="疾病虫害报警统计">
                    <div style="height:33vh;padding:8px">
                        <alert-count></alert-count>
                    </div>
                </future-card>
                <future-card title="疾病虫害时段分析">
                    <div style="height:40vh;padding:8px">
                        <alert-analytic></alert-analytic>
                    </div>
                </future-card>
            </div>
        </div>
    </div>
</template>
<script lang="ts" setup>
import { SelectProps } from 'ant-design-vue';
import { ref } from 'vue';
import FutureCard from '@/components/statisticsManagement/FutureCard.vue';
import AreaCount from '@/components/statisticsManagement/AreaCount.vue';
import PlantAreaCount from '@/components/statisticsManagement/PlantAreaCount.vue';
import MonitorCarousel from '@/components/statisticsManagement/MonitorCarousel.vue';
import DeviceStateBrief from '@/components/statisticsManagement/DeviceStateBrief.vue';
import AlertCount from '@/components/statisticsManagement/AlertCount.vue';
import AlertAnalytic from '@/components/statisticsManagement/AlertAnalytic.vue'

</script>
<style lang="less" scoped>
.plotManagement {
    color: #000;
    display: flex;
    flex-direction: column;
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background-image: url(../../assets/background.png);
    background-size: cover;

    .titlePart {
        height: 12vh;
        padding: 5vh 0 0 0;
        color: #fff;
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: center;
        margin-bottom: 5vh;

        .line {
            height: 0;
            border-width: 0 0 1px 0;
            border-style: solid;
            border-color: #2480b4;

        }

        .top {
            width: 20vw
        }

        .mid {
            width: 17vw
        }

        .bottom {
            width: 14vw
        }

        .lineContainer {
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            height: 100%
        }

        .before {
            align-items: flex-end
        }

        .after {
            align-items: flex-start
        }

        .title {
            padding: 12px 64px;
            display: flex;
            justify-content: center;
            align-items: center;
            box-shadow: 0 0 6px 0 #a2a1a1;
            font-size: 2rem;
            font-weight: 900;
            background-color: #041a40;
            position: relative
        }

        .blue {
            border-color: #02cde6;
            position: absolute;
            border-width: 0 0 2px 0
        }

        .up {
            top: 0;
            width: 66%
        }

        .down {
            bottom: 0;
            width: 33%
        }



    }


    >.contentPart {
        display: grid;
        padding: 8px;
        grid-template-columns: 24vw 46vw 24vw;
        -moz-column-gap: 2vw;
        column-gap: 2vw;
        color: #bfbebe;

        >div>div {
            margin-bottom: 12px
        }
    }
}
</style>