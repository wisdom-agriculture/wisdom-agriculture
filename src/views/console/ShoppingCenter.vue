<template>
    <div class="shopContainer">
        <a-carousel :after-change="onChange">
            <div>
                <img src="@/assets/recommend.png" alt="">
            </div>
            <div>
                <img src="@/assets/recommend.png" alt="">
            </div>
            <div>
                <img src="@/assets/recommend.png" alt="">
            </div>
            <div>
                <img src="@/assets/recommend.png" alt="">
            </div>
        </a-carousel>
        <div class="devider">
            产品中心
        </div>
        <rolling-bar :data="data" :pagination="4">
            <template #card="{data}">
                <product-card :data="data">
                </product-card>
            </template>
        </rolling-bar>
    </div>
</template>
<script lang="ts" setup>
import { ref } from 'vue';
import RollingBar from '@/components/shoppingCenter/RollingBar.vue';
import ProductCard from '@/components/shoppingCenter/ProductCard.vue';
import apis, { Shop } from '@/tools/apis';
import { recommendData } from '@/components/shoppingCenter/ProductCard.vue'
import { message } from 'ant-design-vue';
const onChange = (current: number) => {
    console.log(current);
};
const getData = () => {
    apis.shop.getRecommend().then(res => {
        if (res.status == 'success') {
            let temp: recommendData[] = []
            for (let i of res.data.data.equipmentsList) {
                temp.push({
                    name: i.producesName,
                    price: i.producesPrice,
                    img: i.producesImage,
                    id: i.producesId
                })
            }
            data.value = temp
        } else if (res.status == 'false') {
            message.error(res.message);
        }
    })
}
getData();
const data = ref<recommendData[]>([{
    id: 0,
    img: '',
    price: 0,
    name: '加载中'
}])
</script>
<style scoped lang="less">
.shopContainer {
    width: 90%;
    margin: auto
}

.ant-carousel :deep(.slick-slide) {
    text-align: center;
    height: 360px;
    line-height: 360px;
    background: #364d79;
    overflow: hidden;
}

.ant-carousel :deep(.slick-slide h3) {
    color: #fff;
}

.devider {
    font-weight: 600;
    font-size: 24px;
    color: #000;
    margin: 12px
}
img{
    width:100%;
    height: 360px;
}
</style>