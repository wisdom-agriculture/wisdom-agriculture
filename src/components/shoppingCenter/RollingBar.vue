<template>
    <div class="rollingMain" :style="{height:cardHeight+'px'}">
        <div @click="currentMinus()" class="pageButton">
            <left-outlined style="font-size: 32px;" />
        </div>
        <div class="rollingBarWrapper">
            <div class="cards" v-for="(page,index) in pages" ref="cardNode" :key="index" :style="pageStyle"
                :class="index==current?'current':'notCurrent'">
                <div v-for="(card,jIndex) in page" :key="jIndex" style="width: 95%;">
                    <slot name="card" :data="card"></slot>
                </div>
            </div>
        </div>
        <div @click="currentAdd()" class="pageButton">
            <right-outlined style="font-size: 32px;" />
        </div>
    </div>
</template>
<script lang="ts" setup>
import { defineProps, ref, StyleValue, watch } from 'vue';
import { LeftOutlined, RightOutlined } from '@ant-design/icons-vue';
const props = defineProps<{ data: any[], pagination: number }>()
const pages = ref<any[][]>([]);
const cardNode = ref<HTMLDivElement[] | HTMLDivElement | undefined>()
const cardHeight = ref(10);
console.log(props.data)
watch(cardNode, (newValue) => {
    if (newValue != undefined) {
        if (Array.isArray(newValue)) cardHeight.value = newValue[0].clientHeight
        else cardHeight.value = newValue.clientHeight
    }
})
const current = ref(0);
const currentAdd = () => {
    if (current.value === pages.value.length - 1) current.value = 0;
    else current.value++;
}
const currentMinus = () => {
    if (current.value === 0) current.value = pages.value.length - 1;
    else current.value--;
}
const pageStyle: StyleValue = {
    gridTemplateColumns: "repeat(" + props.pagination + "," + (100 / props.pagination) + "%)"
}
const refreshPages = () => {
    let temp = [];
    for (let i of props.data) {
        temp.push(i);
        console.log(temp, 'temp')
        if ((temp.length % props.pagination === 0) || (temp.length === props.data.length)) {
            pages.value.push([...temp]);
            temp = [];
        }
    }
}
watch(() => props.data, refreshPages)
</script>
<style lang="less" scoped>
.rollingBarWrapper {
    position: relative;
    width: 100%;


    .cards {
        position: absolute;
        top: 0;
        left: 0;
        display: grid;
        justify-items: center;
        align-items: center;
        width: 100%;
        transition: 0.5s ease-in-out;
    }

    .notCurrent {
        opacity: 0;
    }

    .current {
        opacity: 1;
    }
}

.rollingMain {
    display: grid;
    grid-template-columns: 32px auto 32px;

    .pageButton {
        display: flex;
        justify-content: center;
        align-items: center;
        cursor: pointer;
    }

}
</style>