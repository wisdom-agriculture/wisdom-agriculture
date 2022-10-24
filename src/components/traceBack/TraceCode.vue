<template>
    <a-modal :visible="visible" title="生成溯源码" @cancel="closer()" width="800px">
        <template #footer>
                <a-button>下载
                </a-button>
        </template>
        <div class="traceCode">
            <img src="@/assets/traceCode.png" alt="">
        </div>
    </a-modal>
</template>
<script lang="ts" setup>
import { defineProps } from 'vue';
const props = defineProps<{
    visible: boolean,
    closer: () => void,
}>()
</script>
<style lang="less" scoped>
.traceCode {
    width: 700px;

    >img {
        width: 100%;
    }
}
</style>