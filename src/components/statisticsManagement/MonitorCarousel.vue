<template>
    <div>
        <div v-if="visible" class="fullScreen">
            <div class="mask" @click="toggle()" />
            <future-card title="一号地块">
                <div class="img">
                    <img src="@/assets/v2_rbsv9f.jpg" draggable="false" />
                </div>
            </future-card>
        </div>
        <a-carousel arrows :dots="false">
            <template #prevArrow>
                <div class="custom-slick-arrow" style="left: 10px; z-index: 1">
                    <left-circle-outlined />
                </div>
            </template>
            <template #nextArrow>
                <div class="custom-slick-arrow" style="right: 10px">
                    <right-circle-outlined />
                </div>
            </template>
            <div v-for="i in [0,1,2,3]" :key="i">
                <div class="monitorContainer">
                    <div v-for="j in [0,1,2,3,4,5]" :key="j" @click="toggle()">
                        <img class="img" src="@/assets/v2_rbsv9f.jpg" draggable="false" />
                        <div class="plotLabel">一号地块</div>
                    </div>
                </div>
            </div>
        </a-carousel>
    </div>
</template>
<script lang="ts" setup>
import { ref } from 'vue';
import { LeftCircleOutlined, RightCircleOutlined } from '@ant-design/icons-vue';
import FutureCard from './FutureCard.vue';
const visible = ref(false)
const toggle = () => visible.value = !visible.value
</script>
<style scoped lang="less">
/* For demo */
.monitorContainer {
    display: flex;
    justify-content: space-evenly;
    flex-wrap: wrap;
    flex-direction: row;
    height: 43vh;

    >* {
        border: 1px solid #02cde6;
        display: block;
        width: 30%;
        margin: 1%;
        position: relative;
        cursor: pointer;
    }

    .img {
        height: 100%;
        width: 100%;
    }

    .plotLabel {
        position: absolute;
        text-align: center;
        background-color: rgba(0, 0, 0, 0.5);
        padding: 2px;
        color: #fff;
        bottom: 0;
        width: 100%;
    }
}

.ant-carousel {
    height: 100%;
}

.ant-carousel :deep(.slick-slide) {
    text-align: center;
    height: 100%;
    line-height: 100%;
    background: #364d79;
    overflow: hidden;
}

.ant-carousel :deep(.slick-arrow.custom-slick-arrow) {
    width: 25px;
    height: 25px;
    font-size: 25px;
    color: #fff;
    background-color: rgba(31, 45, 61, 0.11);
    opacity: 0.3;
    z-index: 1;
}

.ant-carousel :deep(.custom-slick-arrow:before) {
    display: none;
}

.ant-carousel :deep(.custom-slick-arrow:hover) {
    opacity: 0.5;
}

.ant-carousel :deep(.slick-slide h3) {
    color: #fff;
}


.fullScreen {
    z-index: 3;
    position: fixed;
    left: 0;
    top: 0;
    display: flex;
    background-color: rgba(0, 0, 0, 0.50);
    width: 100vw;
    height: 100vh;

    >div {
        margin: auto;
        width: 90vw;
        height: 90vh;
    }
    .mask{
        position: absolute;
        top:0;
        left: 0;
        width: 100vw;
        height: 100vh;
        z-index: -1;
    }
    .img{
        height: 85vh;
        >img{
            width: 100%;
            height: 100%;
        }
    }
}
</style>
  