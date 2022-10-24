<template>
    <div class="plantRecommend">
        <Transition name="fade">
            <div v-if="!recommendationVisible" @click="() => recommendationVisible = true" class="recommendationButton">
                <div>最大产量<br />作物预测</div>
            </div>
            <div v-else>
                <floating-card style="height:100%">
                    <div class="recommendResult">
                        <div>
                            预测结果
                        </div>
                        您在本地块种植<span>{{ currentRecommendation }}</span>作物产量最大
                    </div>
                </floating-card>
            </div>
        </Transition>
    </div>
</template>
<script lang="ts" setup>
import apis from '@/tools/apis';
import { watch,ref,defineProps } from 'vue';
import FloatingCard from '../FloatingCard.vue';
const props=defineProps<{fieldId:number|null}>()
const recommendationVisible = ref(false);
const currentRecommendation = ref('加载中...');
watch(()=>props.fieldId,async newValue=>{
    if(typeof(newValue)=='number'){
        const res=await apis.recommendById({id:newValue})
        if(res.status=='success'){
            currentRecommendation.value=res.data.message
        }
    }
})
</script>
<style lang="less" scoped>
.plantRecommend {
    width: 224px;
    height: 224px;
    position: relative;
    >div {
        width: 100%;
        height: 100%;
        position: absolute;
    }
}

.recommendationButton {
    border-radius: 50%;
    background-color: rgb(47, 175, 72);
    display: flex;

    >div {
        margin: auto;
        color: #fff;
        font-size: 25px;
        font-weight: 600;
    }
}

.recommendResult {
    padding: 16px;
    font-size: 20px;
    font-weight: 400;
    text-align: left;
    height: 100%;

    >div,
    >span {
        font-size: 25px;
        font-weight: 600;
    }

    >div {
        color: rgb(47, 175, 72);
    }
}
</style>
<style>
.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}
</style>