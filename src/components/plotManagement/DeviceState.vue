<template>
    <a-table :columns="columns" :data-source="data" :pagination="{ pageSize: 7 }">
        <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'state'">
                <div v-if="record.state" class="stateDiv stateOpen">
                    <div />
                    <div>开启</div>
                </div>
                <div v-else class="stateDiv stateClose">
                    <div />
                    <div>关闭</div>
                </div>
            </template>
            <template v-if="column.key === 'action'">
            <a-button type="text" style="color:rgb(189, 49, 36)">移除</a-button>
            </template>
        </template>
    </a-table>
</template>
<script lang="ts" setup>
import { ref } from 'vue';
const columns = [
    {
        dataIndex: 'name',
        key: 'name',
    },
    {
        dataIndex: 'state',
        key: 'state',
    }, {
        key: 'action',
    },
];
const data = [{
    key: 0,
    name: '光照强度传感器',
    state: true
}, {
    key: 1,
    name: 'CO2浓度传感器',
    state: false
}, {
    key: 2,
    name: '土壤温度传感器',
    state: false
}, {
    key: 3,
    name: '土壤湿度传感器',
    state: true
}, {
    key: 4,
    name: '土壤pH值传感器',
    state: false
}, {
    key: 5,
    name: '1号监控设备',
    state: true
},]
</script>
<style lang="less" scoped>
.stateDiv {
    display: flex;
    justify-content: center;
    align-items: center;

    >div:first-child {
        width: 1em;
        height: 1em;
        background-color: rgb(189, 49, 36);
    }
}

.stateClose {
    color: rgb(189, 49, 36);

    >div:first-child {
        background-color: rgb(189, 49, 36);
    }
}

.stateOpen {
    color: rgb(47, 175, 72);

    >div:first-child {
        background-color: rgb(47, 175, 72);
    }
}
</style>
