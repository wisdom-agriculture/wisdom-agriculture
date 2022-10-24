<template>
    <a-table :columns="columns" :data-source="data" :pagination="{ pageSize: 20 } " :row-selection="rowSelection">
        <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'abnormal'">
                <span style="color:red">{{record.abnormal}}</span>
            </template>
            <template v-if="column.key === 'status'">
                <div v-if="record.status">
                    <a-tag color="red">过高</a-tag>
                </div>
                <div v-if="!record.status">
                    <a-tag color="red">过低</a-tag>
                </div>
            </template>
            <template v-if="column.key==='normal'">
                {{record.normal[0]}}~{{record.normal[1]}}
            </template>
            <template v-if="column.key === 'time'">
                {{moment(record.time).format('YYYY-MM-DD hh:mm:ss')}}
            </template>
        </template>
    </a-table>
</template>
<script lang="ts" setup>
import { ref, watch } from 'vue';
// import { message, Modal } from 'ant-design-vue';
import moment from 'moment'
// import apis, { diseaseItem } from '@/tools/apis'
// import { useRouter } from 'vue-router'
type diseaseItem = {
    id: number,
    fieldName: string,
    plant: string,
    time: number,
    item: string,
    abnormal: string,
    normal: [string, string],
    status: 0 | 1
}
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
        dataIndex: 'item',
        title: '检测项目',
        key: 'item',
        align: 'center'
    },
    {
        dataIndex: 'abnormal',
        title: '异常值',
        key: 'abnormal',
        align: 'center'
    },
    {
        dataIndex: 'normal',
        title: '正常值范围',
        key: 'normal',
        align: 'center'
    },
    {
        dataIndex: 'status',
        title: '状态',
        key: 'status',
        align: 'center'
    },
];
const data = ref<diseaseItem[]>([{
    id: 1,
    fieldName: '杨桃试验田',
    plant: '杨桃',
    time: 1665772114614,
    item: '含水量',
    abnormal: '700mg/m3',
    normal: ['300mg/m3', '500mg/m3'],
    status: 1
}, {
    id: 2,
    fieldName: '杨桃试验田',
    plant: '杨桃',
    time: 1665772114614,
    item: '含水量',
    abnormal: '700mg/m3',
    normal: ['300mg/m3', '500mg/m3'],
    status: 1
}, {
    id: 3,
    fieldName: '杨桃试验田',
    plant: '杨桃',
    time: 1665772114614,
    item: '含水量',
    abnormal: '700mg/m3',
    normal: ['300mg/m3', '500mg/m3'],
    status: 1
},
])
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
// const route = useRoute()
// const diseaseList = ref<diseaseItem[]>([]);
// const loading = ref(true)
// apis.getDisease({ id: Number(route.params.id) })
//     .then(res => {
//         if (res.status == 'success') {
//             diseaseList.value = res.data.data.diseaseList
//         } else if (res.status == 'false') {
//             message.error(res.message)
//         }
//         loading.value = false
//     })
// const rowSelection = ref({
//     checkStrictly: false,
//     onChange: (selectedRowKeys: (string | number)[], selectedRows: diseaseItem[]) => {
//         console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
//     },
//     onSelect: (record: diseaseItem, selected: boolean, selectedRows: diseaseItem[]) => {
//         console.log(record, selected, selectedRows);
//     },
//     onSelectAll: (selected: boolean, selectedRows: diseaseItem[], changeRows: diseaseItem[]) => {
//         console.log(selected, selectedRows, changeRows);
//     },
// });

</script>
<style lang="less" scoped>
.img {
    width: 160px;
    height: 90px;
}
</style>