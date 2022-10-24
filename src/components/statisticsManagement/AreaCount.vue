<template>
    <div class="areaContainer">
        <div></div>
        <div>地块名称</div>
        <div>作物名称</div>
        <div>播种面积</div>
        <template v-for="(dataItem, index) in data" :key="index">
            <div>
                <div class="decorate">
                    {{ index+1 }}
                </div>
            </div>
            <div>
                {{ dataItem.name }}
            </div>
            <div>
                {{ dataItem.plant }}
            </div>
            <div>
                {{ dataItem.area }}亩
            </div>
        </template>
    </div>
</template>
<script lang="ts" setup>
import { ref, defineProps } from 'vue';
import apis, { Panel } from '@/tools/apis'
const data=ref<Panel.plotPlantArea[]>();
apis.panel.getPlantAreaOfPlots().then(res=>{
    console.log('asdf')
    if(res.status=='success'){
        data.value=res.data.data
    }
})
</script>
<style lang="less" scoped>
.decorate {
    width: 24px;
    height: 24px;
    border-radius: 50%;
    border: 3px solid rgb(2, 205, 230);
    display: flex;
    justify-content: center;
    align-items: center;
}

.title {
    background-color: transparent;
}


.areaContainer {
    height: 100%;
    width: 100%;
    text-align: center;
    display: grid;
    grid-template-columns: repeat(4, 25%);
    grid-template-rows: repeat(11, 9%);

    >div:nth-child(4-n) {
        color: #fff;
        font-weight: 600;
    }

    >div:nth-child(n-4) {
        color: rgb(191, 190, 190)
    }

}
</style>
