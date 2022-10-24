<template>
    <div v-if="isLoading">
        加载中...
    </div>
    <div v-else-if="diseaseList.length==0">
        该地块没有预警
    </div>
    <template v-else>
        <div v-for="(disease, index) in diseaseList" :key="index" class="diseaseItem">
            <svg xmlns="http://www.w3.org/2000/svg"
                class="styles__StyledSVGIconPathComponent-sc-16fsqc8-0 jOamfI svg-icon-path-icon fill"
                viewBox="0 0 1024 1024" width="35" height="35">
                <defs data-reactroot=""></defs>
                <g>
                    <path
                        d="M511.93 219.449c195.813 0 354.545 158.557 354.545 354.152v258.645a35.563 35.563 0 0 1-35.59 35.501h-637.91a35.563 35.563 0 0 1-35.58-35.501V573.593c0-195.587 158.73-354.144 354.535-354.144zM480.86 24.106h62.151c4.946 0 8.932 3.935 8.932 8.872v106.55a8.863 8.863 0 0 1-8.888 8.88h-62.239a8.897 8.897 0 0 1-8.888-8.88V32.979a8.866 8.866 0 0 1 8.888-8.872h0.044zM139.879 161.39a8.897 8.897 0 0 1 12.603 0l75.368 75.331a8.87 8.87 0 0 1 0 12.553l-43.938 43.98a8.897 8.897 0 0 1-12.561 0l-75.454-75.366a8.87 8.87 0 0 1 0-12.561l44.025-43.981-0.044 0.044z m744.181 0l44.035 43.945a8.87 8.87 0 0 1 0 12.545l-75.455 75.374a8.897 8.897 0 0 1-12.569 0l-44.078-43.98a8.87 8.87 0 0 1 0-12.554l75.454-75.365a8.897 8.897 0 0 1 12.561 0l0.052 0.035zM156.295 938.753h711.27a35.563 35.563 0 0 1 35.581 35.546v26.675c0 4.85-3.986 8.827-8.932 8.827H129.646a8.897 8.897 0 0 1-8.932-8.88v-26.665a35.555 35.555 0 0 1 35.59-35.503zM320.792 586.93v280.818h71.128v-280.82h-71.127z"
                        p-id="10287" fill="#d81e06"></path>
                </g>
            </svg>
            <span v-if="disease.type==='plant'">
                系统检测到本地块存在作物有<span>{{ disease.content }}</span>
            </span>
            <span v-if="disease.type==='plot'">
                系统检测到本地块<span>{{ disease.content }}</span>
            </span>
        </div>
    </template>
</template>
<script lang="ts" setup>
import apis from '@/tools/apis';
import { ref, defineProps, watch } from 'vue';
type disease = {
    type: 'plant' | 'plot',
    content: string
}
const isLoading = ref(true)
const props = defineProps<{ fieldId: number | null }>();
const diseaseList = ref<disease[]>([]);
watch(() => props.fieldId, async newValue => {
    if (newValue) {
        const res = await apis.getDisease({ id: newValue })
        if (res.status == 'success') {
            for (let i of res.data.data.diseaseList) {
                diseaseList.value.push({
                    type: 'plant',
                    content: i.disease
                })
            }
            isLoading.value=false
        }
    }
})
// const diseaseList: disease[] = [{
//     type: 'plant',
//     content:'斑点病'
// },{
//     type: 'plant',
//     content:'锈病'
// },{
//     type: 'plot',
//     content:'土壤温度过高'
// },]
</script>
<style lang="less" scoped>
.diseaseItem {
    display: flex;
    justify-content: space-evenly;
    align-items: center;
    line-height: 3em;
    font-weight: 400;

    >span>span {
        font-size: 24px;
        font-weight: 600;
    }
}
</style>
