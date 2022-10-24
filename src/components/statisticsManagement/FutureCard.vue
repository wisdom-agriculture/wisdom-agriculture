<template>
    <div class="container">
        <div class="corner leftTop"> <svg xmlns="http://www.w3.org/2000/svg"
                class="styles__StyledSVGIconPathComponent-sc-16fsqc8-0 cSzqoU svg-icon-path-icon fill"
                viewBox="0 0 24 24">
                <defs data-reactroot=""></defs>
                <g>
                    <path d="M10 6L8.59 7.41 13.17 12l-4.58 4.59L10 18l6-6z"></path>
                </g>
            </svg></div>
        <div class="corner rightTop"><svg xmlns="http://www.w3.org/2000/svg"
                class="styles__StyledSVGIconPathComponent-sc-16fsqc8-0 cSzqoU svg-icon-path-icon fill"
                viewBox="0 0 24 24">
                <defs data-reactroot=""></defs>
                <g>
                    <path d="M10 6L8.59 7.41 13.17 12l-4.58 4.59L10 18l6-6z"></path>
                </g>
            </svg></div>
        <div class="corner leftBottom"><svg xmlns="http://www.w3.org/2000/svg"
                class="styles__StyledSVGIconPathComponent-sc-16fsqc8-0 cSzqoU svg-icon-path-icon fill"
                viewBox="0 0 24 24">
                <defs data-reactroot=""></defs>
                <g>
                    <path d="M10 6L8.59 7.41 13.17 12l-4.58 4.59L10 18l6-6z"></path>
                </g>
            </svg></div>
        <div class="corner rightBottom"><svg xmlns="http://www.w3.org/2000/svg"
                class="styles__StyledSVGIconPathComponent-sc-16fsqc8-0 cSzqoU svg-icon-path-icon fill"
                viewBox="0 0 24 24">
                <defs data-reactroot=""></defs>
                <g>
                    <path d="M10 6L8.59 7.41 13.17 12l-4.58 4.59L10 18l6-6z"></path>
                </g>
            </svg></div>
        <div class="titleContainer">
            <div class="outer titleDecorate">
                <svg xmlns="http://www.w3.org/2000/svg"
                    class="styles__StyledSVGIconPathComponent-sc-16fsqc8-0 fxvrPc svg-icon-path-icon fill"
                    viewBox="0 0 24 24">
                    <defs data-reactroot=""></defs>
                    <g>
                        <path d="M10 6L8.59 7.41 13.17 12l-4.58 4.59L10 18l6-6z"></path>
                    </g>
                </svg>
            </div>
            <div class="inner titleDecorate">
                <svg xmlns="http://www.w3.org/2000/svg"
                    class="styles__StyledSVGIconPathComponent-sc-16fsqc8-0 fxvrPc svg-icon-path-icon fill"
                    viewBox="0 0 24 24">
                    <defs data-reactroot=""></defs>
                    <g>
                        <path d="M10 6L8.59 7.41 13.17 12l-4.58 4.59L10 18l6-6z"></path>
                    </g>
                </svg>
            </div>
            <div class="title">
                {{ title }}
            </div>
            <div class="inner right titleDecorate">
                <svg xmlns="http://www.w3.org/2000/svg"
                    class="styles__StyledSVGIconPathComponent-sc-16fsqc8-0 fxvrPc svg-icon-path-icon fill"
                    viewBox="0 0 24 24">
                    <defs data-reactroot=""></defs>
                    <g>
                        <path d="M10 6L8.59 7.41 13.17 12l-4.58 4.59L10 18l6-6z"></path>
                    </g>
                </svg>
            </div>
            <div class="outer right titleDecorate">
                <svg xmlns="http://www.w3.org/2000/svg"
                    class="styles__StyledSVGIconPathComponent-sc-16fsqc8-0 fxvrPc svg-icon-path-icon fill"
                    viewBox="0 0 24 24">
                    <defs data-reactroot=""></defs>
                    <g>
                        <path d="M10 6L8.59 7.41 13.17 12l-4.58 4.59L10 18l6-6z"></path>
                    </g>
                </svg>
            </div>
        </div>
        <div class="contentContainer">
            <slot></slot>
        </div>
    </div>
</template>
<script lang="ts" setup>
import { defineProps } from 'vue';
const props = defineProps({
    title: String,
})
</script>
<style lang="less" scoped>
.container {
    border: rgb(26, 96, 152) solid 1px;
    position: relative;
    width: 100%;
}

.corner {
    fill: rgb(2, 205, 230);
    position: absolute;
    width: 24px;
    height: 24px;
}

.leftTop {
    left: -11px;
    top: -11px;
    transform: rotate(225deg);
}

.rightTop {
    right: -11px;
    top: -11px;
    transform: rotate(315deg);
}

.leftBottom {
    left: -11px;
    bottom: -11px;
    transform: rotate(135deg);
}

.rightBottom {
    right: -11px;
    bottom: -11px;
    transform: rotate(45deg);
}

.titleDecorate {
    width: 24px;
    height: 24px;
}

.outer {
    fill: rgb(45, 143, 219);
}

.inner {
    fill: rgb(154, 206, 247);
}

.right {
    transform: rotate(180deg);
}

.titleContainer {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    background-color: rgb(5, 29, 63);
    color:rgb(35, 125, 191)
}
.contentContainer{
    background-color:rgb(9, 48, 104)
}
</style>