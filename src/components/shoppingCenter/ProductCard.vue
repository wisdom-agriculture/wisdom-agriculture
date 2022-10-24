<template>
    <div class="productCard" @click="()=>{if(data.name!='加载中'){router.push({name:'ProductDetail',params:{id:data.id}})}}">
        <img :src="data.img" alt="">
        <div>
            {{data.name}}
        </div>
        <div>
            <div>￥{{data.price}}</div>
            <div>详情</div>
        </div>
    </div>
</template>
<script lang="ts" setup>
import { defineProps } from 'vue'
import { useRouter } from 'vue-router';
export type recommendData = {
    name: string,
    img: string,
    price: number,
    id: number
}
const router = useRouter()
const props = defineProps<{ data: recommendData }>()
console.log(props)
</script>
<style lang="less" scoped>
.productCard {
    padding: 12px;
    height: calc(100vh - 500px);
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
    font-size: 24px;
    border-color: rgb(64, 149, 229);
    border-width: 1px;
    border-style: solid;
    border-radius: 10px;
    color: #000;

    >img:nth-child(1) {
        display: block;
        width: 180px;
        height: 180px;
    }

    >div:nth-child(3) {
        display: flex;
        justify-content: space-around;
        width: 100%;

        >div:nth-child(1) {
            color: red;
        }

        >div:nth-child(2) {
            color: aqua;
        }
    }
}
</style>