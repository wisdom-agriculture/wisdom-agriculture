<template>
    <a-modal :visible="visible" title="产品溯源ID" @cancel="closer()" width="800px">
        <template #footer>
        </template>
        <div class="traceCode">
            {{data}}
        </div>
    </a-modal>
</template>
<script lang="ts" setup>
import apis from '@/tools/apis';
import { message } from 'ant-design-vue';
import { defineProps, ref, watch } from 'vue';
const props = defineProps<{
    visible: boolean,
    closer: () => void,
    cropsId: bigint
}>()
const data = ref('')
watch(() => props.cropsId, newValue => {
    getData(newValue)
})
const getData = async (arg0: bigint) => {
    const hide = message.loading('加载中', 0)
    await apis.trace.retailer.getProdId({ cropsId: arg0 }).then(res => {
        if (res.status == 'success') {
            if (res.data.data.length !== 0) {
                data.value = res.data.data[res.data.data.length - 1].Record.product_id
            } else {
                message.error('获取失败')
            }
        }
        else if (res.status == 'false') {
            message.error(res.message)
        }
    })
    hide()
}
</script>
<style lang="less" scoped>
.traceCode {
    width: 700px;

    >img {
        width: 100%;
    }
}
</style>