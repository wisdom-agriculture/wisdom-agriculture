<template>
    <div class="traceContainer">
        <transport-contact origin-role="material" :visible="transportContactVisible" :closer="transportContactVisibleController.close" :crop-id="selectedCropId" @success="getData">
        </transport-contact>
        <material-out :id="materialSelectedId" @success="getData" :visible="materialVisible" :controller="materialVisibleController"></material-out>
        <div class="tableControllBar">
            <div>
                <a-input-search v-model:value="searchKeyword" placeholder="input search text" style="width: 200px" />
            </div>
            <div class="iconButton">
                <svg xmlns="http://www.w3.org/2000/svg"
                    class="styles__StyledSVGIconPathComponent-sc-16fsqc8-0 ikTEJk svg-icon-path-icon fill"
                    viewBox="0 0 1024 1024" width="28" height="28">
                    <defs data-reactroot=""></defs>
                    <g>
                        <path
                            d="M882.548896 835.161121c-5.650697 0-10.233062-4.582365-10.233062-10.233062L872.315834 615.20964l-192.749956 0c-5.650697 0-10.233062-4.582365-10.233062-10.233062s4.582365-10.233062 10.233062-10.233062l202.983018 0c5.650697 0 10.233062 4.582365 10.233062 10.233062l0 219.950458C892.781958 830.578756 888.200616 835.161121 882.548896 835.161121z"
                            p-id="3339"></path>
                        <path
                            d="M344.436168 415.054017 141.450081 415.054017c-5.650697 0-10.233062-4.581342-10.233062-10.233062l0-219.949434c0-5.65172 4.582365-10.233062 10.233062-10.233062 5.65172 0 10.233062 4.581342 10.233062 10.233062l0 209.716372 192.753025 0c5.650697 0 10.233062 4.581342 10.233062 10.233062S350.086865 415.054017 344.436168 415.054017z"
                            p-id="3340"></path>
                        <path
                            d="M510.329453 894.456598c-91.735307 0-180.443675-32.984229-249.785973-92.877317-68.622914-59.270918-114.077152-140.994198-127.988999-230.118028-0.871857-5.583159 2.949168-10.816347 8.532327-11.689227 5.584182-0.870834 10.81737 2.948145 11.689227 8.532327 27.650757 177.12714 178.023556 305.686121 357.553419 305.686121 160.446225 0 299.604612-103.051027 346.276584-256.429277 1.645476-5.40715 7.361665-8.460696 12.769838-6.810103 5.40715 1.644453 8.456602 7.361665 6.810103 12.768815-11.791557 38.754652-29.644157 75.261101-53.058426 108.503203-23.093974 32.788777-51.056839 61.646012-83.10986 85.769432C673.378969 867.946828 593.954035 894.456598 510.329453 894.456598zM874.22123 443.99721c-4.723581 0-8.969279-3.289929-9.997702-8.095375C828.795645 270.243911 679.961898 150.009526 510.329453 150.009526c-156.105361 0-294.118668 99.457176-343.4277 247.487627-1.785669 5.363148-7.581676 8.263198-12.941753 6.474458-5.362124-1.785669-8.261151-7.580652-6.474458-12.941753 12.517081-37.579897 30.786167-72.894194 54.296627-104.963587 23.229051-31.68463 51.072189-59.525722 82.756819-82.750679 65.840544-48.26219 143.918807-73.771167 225.791489-73.771167 44.900629 0 88.872097 7.713682 130.692574 22.926152 40.410362 14.699794 77.849042 36.023448 111.276363 63.378469 67.061348 54.880935 113.917516 131.511219 131.939985 215.774345 1.180895 5.526877-2.340301 10.964726-7.867178 12.146645C875.649765 443.922509 874.929358 443.99721 874.22123 443.99721z"
                            p-id="3341"></path>
                    </g>
                </svg>
            </div>
            <div class="iconButton">
                <svg xmlns="http://www.w3.org/2000/svg"
                    class="styles__StyledSVGIconPathComponent-sc-16fsqc8-0 ikTEJk svg-icon-path-icon fill"
                    viewBox="0 0 1024 1024" width="25" height="25">
                    <defs data-reactroot=""></defs>
                    <g>
                        <path
                            d="M202.666667 256h-42.666667a32 32 0 0 1 0-64h704a32 32 0 0 1 0 64H266.666667v565.333333a53.333333 53.333333 0 0 0 53.333333 53.333334h384a53.333333 53.333333 0 0 0 53.333333-53.333334V352a32 32 0 0 1 64 0v469.333333c0 64.8-52.533333 117.333333-117.333333 117.333334H320c-64.8 0-117.333333-52.533333-117.333333-117.333334V256z m224-106.666667a32 32 0 0 1 0-64h170.666666a32 32 0 0 1 0 64H426.666667z m-32 288a32 32 0 0 1 64 0v256a32 32 0 0 1-64 0V437.333333z m170.666666 0a32 32 0 0 1 64 0v256a32 32 0 0 1-64 0V437.333333z"
                            p-id="4282"></path>
                    </g>
                </svg>
            </div>
        </div>
        <a-table :columns="columns" :data-source="data" :pagination="{ pageSize: 20 }" :row-selection="rowSelection">
            <template #bodyCell="{ column, record }">
                <template v-if="column.key === 'cropsId'">
                    {{record.cropsId.toString()}}
                </template>
                <template v-if="column.key === 'action' && record.outFactoryStatus!=1">
                    <a-button type="text" style="color:#4095E5" @click="materialVisibleController.open(record.id)">出库</a-button>
                    <a-button type="text" style="color:#4095E5" @click="transportContactVisibleController.open(record.cropsId)">通知运输
                    </a-button>
                </template>
                <template v-if="column.key === 'outFactoryStatus'">
                    <div v-if="record.outFactoryStatus == 1">
                        <a-tag color="red">已出库</a-tag>
                    </div>
                    <div v-else>
                        <a-tag color="blue">已入库</a-tag>
                    </div>
                </template>
            </template>
        </a-table>
    </div>
</template>
<script lang="ts" setup>
import { ref } from 'vue';
import { PlusOutlined } from '@ant-design/icons-vue'
import MaterialOut from '@/components/traceBack/MaterialOut.vue';
import TransportContact from '@/components/traceBack/TransportContact.vue';
import apis, { Trace } from '@/tools/apis'
import { message } from 'ant-design-vue';
const columns = [
    {
        dataIndex: 'cropsId',
        title: '作物编号',
        key: 'cropsId',
        align: 'center'
    },
    {
        dataIndex: 'farmerNickname',
        title: '农户',
        key: 'farmerNickname',
        align: 'center'
    },
    {
        dataIndex: 'farmerDeptName',
        title: '原料来源',
        key: 'farmerDeptName',
        align: 'center'
    },
    {
        dataIndex: 'time',
        title: '时间',
        key: 'time',
        align: 'center'
    },
    {
        dataIndex: 'outFactoryStatus',
        title: '状态',
        key: 'outFactoryStatus',
        align: 'center'
    }, {
        key: 'action',
        title: '操作',
        align: 'center'
    },
];
const loading = ref(true)
const data = ref<Trace.Material.plantDetail[]>([])
const getData = () => apis.trace.material.getPlantList().then(res => {
    if (res.status == 'success') {
        data.value = res.data.data
    }
})
getData();
// const data = ref<TraceItemMaterial[]>([{
//     key: 0,
//     serial: '427563038383',
//     name: '丁婉仁',
//     materialFrom: '枝江材料厂',
//     time: '2022-07-20T20:16:19.000+0800',
//     status: false,
// }, {
//     key: 1,
//     serial: '763367378378',
//     name: '贾鑫棠',
//     materialFrom: '枝江材料厂',
//     time: '2022-07-20T20:16:19.000+0800',
//     status: false,
// }, {
//     key: 2,
//     serial: '376383863783',
//     name: '北极星',
//     materialFrom: '枝江材料厂',
//     time: '2022-07-20T20:16:19.000+0800',
//     status: true,
// }, {
//     key: 3,
//     serial: '373867378373',
//     name: '乃启林',
//     materialFrom: '枝江材料厂',
//     time: '2022-07-20T20:16:19.000+0800',
//     status: true,
// },])
const rowSelection = ref({
    checkStrictly: false,
    onChange: (selectedRowKeys: (string | number)[], selectedRows: Trace.Material.plantDetail[]) => {
        console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
    },
    onSelect: (record: Trace.Material.plantDetail, selected: boolean, selectedRows: Trace.Material.plantDetail[]) => {
        console.log(record, selected, selectedRows);
    },
    onSelectAll: (selected: boolean, selectedRows: Trace.Material.plantDetail[], changeRows: Trace.Material.plantDetail[]) => {
        console.log(selected, selectedRows, changeRows);
    },
});


const materialSelectedId=ref(0);
const materialVisible = ref(false);
const materialVisibleController = {
    open: (id:number) => {
        materialSelectedId.value=id
        materialVisible.value=true
    },
    close: () => materialVisible.value = false,
    add: () => {
        console.log('add')
    }
}
const selectedCropId = ref<bigint>(0n);
const transportContactVisible = ref(false);
const transportContactVisibleController = {
    open: (cropId: bigint) => {
        transportContactVisible.value = true;
        selectedCropId.value = cropId
    },
    close: () => transportContactVisible.value = false,
    add: (newTrans: Trace.transportContactBody) => {
        apis.trace.transportContact(newTrans).then(res => {
            if (res.status == 'success') {
                getData()
            } else if (res.status == 'false') {
                message.error(res.message)
            }
        })
    }
}

const searchKeyword = ref('')

</script>
<style lang="less" scoped>
.traceContainer {
    width: calc(100vw - 258px);
    height: calc(100vh - 64px)
}

.tableControllBar {
    display: flex;

    >div {
        margin: 0 8px;
    }

    >div:first-child {
        margin-right: auto;
    }

    .iconButton {
        width: 32px;
        height: 32px;
        border-radius: 50%;
        border-color: #707070;
        border-style: solid;
        border-width: 1px;
        display: flex;
        justify-content: center;
        align-items: center;

        >svg {
            width: 20px;
        }
    }
}
</style>
/