<template>
    <div class="GoodsEditWrapper">
        <div class="goBack">
            <left-circle-outlined class="button" @click="router.back()" />
            <div class="description">
                添加商品信息
            </div>
        </div>
        <div class="formEntity">

            <a-steps v-model:current="currentStep">
                <a-step title="基本信息" />
                <a-step title="商品图片" />
                <a-step title="商品内容" />
                <a-step title="完成" />
            </a-steps>
            <div style="margin:16px 0">
                <basic-info v-if="currentStep===0"></basic-info>
                <pic-upload v-else-if="currentStep===1"></pic-upload>
                <rich-text v-else></rich-text>
            </div>
        </div>

    </div>
</template>
<script lang="ts" setup>
import { LeftCircleOutlined } from '@ant-design/icons-vue';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import BasicInfo from '@/components/goodsEdit/BasicInfo.vue';
import PicUpload from '@/components/goodsEdit/PicUpload.vue';
import RichText from '@/components/goodsEdit/RichText.vue';
export type GoodsDetailBasic = {
    name: string,
    value: number,
    size: string,
    count: number,
    location: string,
    phone: string,
    type: 'products' | 'plot',
}
export type GoodsDetailItem = {
    basic: GoodsDetailBasic,
    pics: string[],
    content: string,
}
const router = useRouter();
const currentStep = ref(0);
const data = ref<GoodsDetailItem>({
    basic: {
        name: '',
        value: 0,
        size: '',
        count: 0,
        location: '',
        phone: '',
        type: 'products',
    },
    pics: [],
    content: '',
})
</script>
<style lang="less" scoped>
.GoodsEditWrapper {
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

    img {
        height: 240px;
        width: 240px;
        display: block;
        margin: 48px auto;
    }
    .formEntity{
        margin:16px;
        
    }
}
</style>