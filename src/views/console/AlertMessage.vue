<template>
    <a-table :columns="columns" :data-source="diseaseList" :pagination="{ pageSize: 20 } " :loading="loading"
        :row-selection="rowSelection">
        <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'action'">
                <a-button type="text" style="color:#4095E5" @click="showConfirm()" v-if="!record.status">已解决
                </a-button>
            </template>
            <template v-if="column.key === 'status'">
                <div v-if="record.status">
                    <a-tag color="red">已解决</a-tag>
                </div>
                <div v-if="!record.status">
                    <a-tag color="blue">未解决</a-tag>
                </div>
            </template>
            <template v-if="column.key === 'img'">
                <img :src='record.img' alt="" class="img">
            </template>
            <template v-if="column.key === 'time'">
                {{moment(record.time).format('YYYY-MM-DD hh:mm:ss')}}
            </template>
        </template>
    </a-table>
</template>
<script lang="ts" setup>
import { ref, watch } from 'vue';
import { message, Modal } from 'ant-design-vue';
import moment from 'moment'
import apis, { diseaseItem } from '@/tools/apis'
import { useRoute } from 'vue-router'
const columns = [
    {
        dataIndex: 'id',
        title: '序号',
        key: 'id',
        align: 'center'
    },
    {
        dataIndex: 'fieldName',
        title: '地块名称',
        key: 'fieldName',
        align: 'center'
    },
    {
        dataIndex: 'plant',
        title: '作物名称',
        key: 'plant',
        align: 'center'
    },
    {
        dataIndex: 'time',
        title: '报警时间',
        key: 'time',
        align: 'center'
    },
    {
        dataIndex: 'status',
        title: '状态',
        key: 'status',
        align: 'center'
    },
    {
        dataIndex: 'img',
        title: '检测图片',
        key: 'img',
        align: 'center'
    },
    {
        dataIndex: 'disease',
        title: '疾病名称',
        key: 'disease',
        align: 'center'
    }, {
        key: 'action',
        title: '操作',
        align: 'center'
    },
];
const route = useRoute()
const diseaseList = ref<diseaseItem[]>([]);
const loading = ref(true)
apis.getDisease({ id: Number(route.params.id) })
    .then(res => {
        if (res.status == 'success') {
            diseaseList.value = res.data.data.diseaseList
        } else if (res.status == 'false') {
            message.error(res.message)
        }
        loading.value = false
    })
const rowSelection = ref({
    checkStrictly: false,
    onChange: (selectedRowKeys: (string | number)[], selectedRows: diseaseItem[]) => {
        console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
    },
    onSelect: (record: diseaseItem, selected: boolean, selectedRows: diseaseItem[]) => {
        console.log(record, selected, selectedRows);
    },
    onSelectAll: (selected: boolean, selectedRows: diseaseItem[], changeRows: diseaseItem[]) => {
        console.log(selected, selectedRows, changeRows);
    },
});
const showConfirm = () => {
    Modal.confirm({
        title: '请确定是否已解决问题',
        onOk() {
            console.log('OK');
        },
        onCancel() {
            console.log('Cancel');
        },
        class: 'test',
    });
}

</script>
<style lang="less" scoped>
.img {
    width: 160px;
    height: 90px;
}
</style>