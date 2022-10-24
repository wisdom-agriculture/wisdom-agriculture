<template>
    <div class="productDetailWrapper">
        <div class="goBack">
            <left-circle-outlined class="button" />
            <div class="description">
                商品详情
            </div>
        </div>
        <floating-card class="cardMargin">
            <div class="productLabel">
                <div class="pImage">
                    <img :src="data.produceImage" alt="">
                </div>
                <div class="pDetail">
                    <h3>{{data.produceName}}</h3>
                    <div>
                        <div>价格：</div>
                        <div class="value">￥{{data.producePrice}}</div>
                        <div>配送：</div>
                        <div>
                            <div>天津市西青区 至 全国</div>
                            <div>快递：{{data.freightPrice==0?'免运费':'￥'+data.freightPrice}}</div>
                        </div>
                        <div>数量：</div>
                        <div>
                            <a-input-number v-model:value="value1" size="large" :min="1" :max="100000" />
                        </div>
                    </div>
                    <green-button style="width:240px" @click="()=>{if(data.produceName!=='加载中...')router.push({name:'ProductPurchase',params:{
                    id:data.produceId,
                    count:value1,
                    skuid:data.produceSkuId,
                    prodName:data.produceName,
                    prodImg:data.produceImage,
                    prodPrice:data.producePrice,
                    prodFreight:data.freightPrice
                    }})}">
                        立即购买</green-button>
                </div>
            </div>
        </floating-card>
        <floating-card class="cardMargin">
            <div class="pInfo">
                <h3>商品概述</h3>
                <p>{{data.produceDescription}}</p>
            </div>
        </floating-card>
    </div>
</template>
<script lang="ts" setup>
import GreenButton from '@/components/GreenButton.vue';
import FloatingCard from '@/components/FloatingCard.vue';
import { LeftCircleOutlined } from '@ant-design/icons-vue';
import { ref } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import apis, { Shop } from '@/tools/apis'
const value1 = ref(1)
const router = useRouter()
const route = useRoute()
const data = ref<Shop.recDetailItem>({
    freightPrice: 0,
    produceDescription: '',
    produceId: 0,
    produceImage: '',
    produceName: '加载中...',
    producePrice: 0,
    producesSpecification: null,
    produceSkuId: 0,
})
const getData = () => {
    apis.shop.getRecommendDetail({ produceId: Number(route.params.id) }).then(res => {
        if (res.status == 'success') {
            data.value = res.data.data
        }
    })
}
getData()
</script>
<style lang="less" scoped>
.productDetailWrapper {
    color: #000;

    .goBack {
        display: flex;

        .button {
            font-size: 48px;
            width: 48px;
            margin-right: 8px;
        }

        .description {
            line-height: 48px;
            font-size: 18px;
            text-align: center;
            vertical-align: middle;
            background-color: rgba(239, 239, 239, 0.58);
            color: rgb(108, 108, 108);
            flex-grow: 1;
            border-radius: 8px;
        }
    }

    .productLabel {
        display: grid;
        grid-template-columns: 40% 60%;
        height: 320px;

        .pImage {
            height: 300px;
            width: 300px;
            margin: 0 16px 0 auto;

            >img {
                width: 100%;
                height: 100%;
            }
        }

        .pDetail {
            font-size: 18px;

            h3 {
                font-weight: 600;
            }

            >div {
                display: grid;
                grid-template-columns: 108px auto;
                gap: 24px;
            }

            .value {
                font-size: 24px;
                color: red;
            }
        }
    }

    .cardMargin {
        margin: 12px;
        padding: 12px
    }

    .pInfo {
        font-size: 20px;

        h3 {
            font-weight: 600;
        }

        p {
            margin-left: 32px;
        }
    }
}
</style>