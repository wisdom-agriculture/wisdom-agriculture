<template>
    <a-table :columns="columns" :data-source="data" :pagination="{pageSize:7,current,loading:isLoading,total:current*7+1}"
        @change="getNewPage">
        <template #bodyCell="{ column,record }">
            <template v-if="column.key === 'decorate'">
                <div class="decoration" :ref="(el:any)=>{if(el!==null)el.parentNode.style.padding=0}">
                    <div class="blackLine"
                        :ref="(el:any)=>{if(el!==null)el.style.height=((el?.parentElement?.parentElement?.clientHeight-16)/2)+'px';}" />
                    <div class="greenPoint" />
                    <div class="blackLine"
                        :ref="(el:any)=>{if(el!==null)el.style.height=((el?.parentElement?.parentElement?.clientHeight-16)/2)+'px';}" />
                </div>
            </template>
            <template v-if="column.key === 'time'">
                {{moment(record.time).format('YYYY-MM-DD hh:mm:ss')}}
            </template>
        </template>
    </a-table>
</template>
<script lang="ts" setup>
import { defineProps, watch, ref } from 'vue';
import moment from 'moment'
import apis, { operationItem } from '@/tools/apis'
import { message } from 'ant-design-vue';
const columns = [
    {
        key: 'decorate',
    },
    {
        title: '操作人',
        dataIndex: 'operator',
        key: 'operator',
    },
    {
        title: '手机号',
        dataIndex: 'phone',
        key: 'phone',
    },
    {
        title: '时间',
        key: 'time',
        dataIndex: 'time',
    },
    {
        title: '工作内容',
        key: 'content',
        dataIndex: 'content',
    },
    {
        title: '备注',
        key: 'remark',
        dataIndex: 'remark',
    },
];
const props = defineProps<{ fieldId: number | null }>()
const isLoading = ref(false);
const data = ref<operationItem[]>([]);
const current = ref(1);
const getNewPage = async (page: any) => {
    if (props.fieldId) {
        console.log(page)
        isLoading.value=true;
        const res = await apis.getOperation({
            fieldId: props.fieldId,
            current: page.current,
            size: 7
        });
        if (res.status == 'success' && res.data.data.operationResultList.length !== 0) {
            data.value = res.data.data.operationResultList;
            current.value=page.current;
        } else {
            message.error('没有下一页');
            if(page.current==1){
                data.value=[];
            }
        }
    }
}
watch(() => props.fieldId, (newValue) => {
    if (typeof(newValue)=='number') {
        getNewPage({current:1})
    }
})
</script>
<style lang="less" scoped>
.decoration {
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    .blackLine {
        width: 2px;
        background-color: #bbbbbb;
    }

    .greenPoint {
        width: 16px;
        height: 16px;
        position: relative;
        background-color: #2faf48;
        border-radius: 50%;
    }
}
</style>
