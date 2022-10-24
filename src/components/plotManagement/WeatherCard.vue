<template>
    <div class="weatherCard">
        <div v-for="(weather, index) in weatherData" class="weatherContainer" :key="index">
            <div>{{ weather.time }}</div>
            <svg xmlns="http://www.w3.org/2000/svg"
                class="styles__StyledSVGIconPathComponent-sc-16fsqc8-0 bjdJFk svg-icon-path-icon fill"
                viewBox="0 0 1024 1024" width="55" height="55">
                <defs data-reactroot=""></defs>
                <g>
                    <path
                        d="M743.52 396.192l-37.504-6.4-12.288-35.968a192.096 192.096 0 0 0-363.456 0l-12.288 35.968-37.504 6.4A106.72 106.72 0 0 0 298.656 608h426.688a106.656 106.656 0 0 0 18.176-211.808zM896 501.344A170.656 170.656 0 0 1 725.344 672H298.656a170.656 170.656 0 0 1-28.928-338.88 256.096 256.096 0 0 1 484.544 0A170.72 170.72 0 0 1 896 501.344zM384.96 728.224a32 32 0 0 1 62.08 15.552l-32 128a32 32 0 0 1-62.08-15.552l32-128zM576.96 728.224a32 32 0 0 1 62.08 15.552l-32 128a32 32 0 0 1-62.08-15.552l32-128z"
                        p-id="10556" data-spm-anchor-id="a313x.7781069.0.i12" class="selected">
                    </path>
                </g>
            </svg>
            <div>{{ weather.weather }}</div>
            <div>{{ weather.maxTemp }}°C~{{ weather.minTemp }}°C</div>
            <div>{{ weather.wind }}</div>
        </div>
    </div>
</template>
<script lang="ts" setup>
import { defineProps } from 'vue';
export type weatherDataType = {
    weather: string,
    wind: string,
    minTemp: number,
    maxTemp: number,
    time: string
}[]
const props = defineProps<{
    weatherData: weatherDataType
}>()
</script>
<style lang="less" scoped>
.weatherContainer {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 0 24px 24px 24px
}

.weatherCard {
    display: flex;
    flex-direction: row;
    justify-content: space-evenly;
    fill: #8a8a8a !important;
    color: #8a8a8a !important;
    font-size: 16px;
}

.weatherContainer:first-child {
    fill: #4095e5 !important;
    color: #000 !important;
}
</style>