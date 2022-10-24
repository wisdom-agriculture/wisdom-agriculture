<template>
    <div class="blockChain">
        <floating-card class="card" :border-radius="4">
            <h3 style="text-align: center"> 当前区块信息</h3>
            <floating-card class="card" :border-radius="4" v-for="(block,index) in data" :key="index" style="margin:8px">
                <div>
                    区块编号：<span>{{block.serial}}</span>
                </div>
                <div>
                    前置Hash：<span style="color:#7BD785 ;">{{block.foreHash}}</span>
                </div>
                <div>
                    后置Hash：<span style="color:#7BD785 ;">{{block.hindHash}}</span>
                </div>
            </floating-card>
        </floating-card>
        <floating-card class="card" :border-radius="4">
            <h3 style="text-align: center"> 区块链网络信息</h3>
            <floating-card class="card" :border-radius="4" style="text-align: center">
                区块高度：50
            </floating-card>
            <floating-card class="card" :border-radius="4" style="text-align: center">
                通道：asfdasdfsadsa
            </floating-card>
            <floating-card class="card" :border-radius="4" style="text-align: center">
                <h3>节点</h3>
                <p>证书节点：<a-tag color="red">ca.trace.com</a-tag></p>
                <p>共识节点：<a-tag color="yellow">ca.trace.com</a-tag></p>
                <p>农户节点：<a-tag color="blue">ca.trace.com</a-tag></p>
                <p>原料厂商节点：<a-tag color="blue">ca.trace.com</a-tag></p>
                <p>生产厂商节点：<a-tag color="blue">ca.trace.com</a-tag></p>
                <p>零售商节点：<a-tag color="blue">ca.trace.com</a-tag></p>
                <p>物流公司节点：<a-tag color="blue">ca.trace.com</a-tag></p>
            </floating-card>
        </floating-card>
    </div>
</template>
<script lang="ts" setup>
import FloatingCard from '@/components/FloatingCard.vue';
export type blockInterface = {
    serial: string,
    foreHash: string,
    hindHash: string
}
const data: blockInterface[] = [
    {
        serial: '45',
        foreHash: '399F90C66B6C1E7F722785797D74B893',
        hindHash: '7B279180FB31A84245E3184F82F46D94'
    },
    {
        serial: '46',
        foreHash: '399F90C66B6C1E7F722785797D74B893',
        hindHash: '7B279180FB31A84245E3184F82F46D94'
    }
]
</script>
<style lang="less" scoped>
.blockChain{
    display: grid;
    grid-template-columns: 60% 40%;
}
.card{
    padding:16px;
    font-size: 20px;
    color:#000
}
</style>