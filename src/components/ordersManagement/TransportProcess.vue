<template>
    <div>
        <a-modal :visible="visible" title="添加地块" @cancel="cancel">
            <template #footer>
            </template>
            <div v-for="(item,index) in data" :key="index" class="transportProcessWrapper">
                <div class="decoration">
                    <div class="upper blackLine" />
                    <div class="point" />
                    <div class="lower blackLine" />
                </div>
                <div>
                    <div>{{item.content}}</div>
                    <sub>{{item.time}}</sub>
                </div>
            </div>
        </a-modal>
    </div>
</template>
<script lang="ts" setup>
import { ref, defineProps } from 'vue';
const props = defineProps<{
    visible: boolean,
    cancel: () => void
}>()
const data = [{
    content: '邮件投递到： 四川省甘孜藏族自治州理塘自提点，投递员：丁真，电话：15151515151',
    time: '2022-06-26 15:15:15'
}, {
    content: '邮件投递到： 四川省甘孜藏族自治州理塘自提点，投递员：丁真，电话：15151515151',
    time: '2022-06-26 15:15:15'
}, {
    content: '邮件投递到： 四川省甘孜藏族自治州理塘自提点，投递员：丁真，电话：15151515151',
    time: '2022-06-26 15:15:15'
}, {
    content: '邮件投递到： 四川省甘孜藏族自治州理塘自提点，投递员：丁真，电话：15151515151',
    time: '2022-06-26 15:15:15'
}, {
    content: '邮件投递到： 四川省甘孜藏族自治州理塘自提点，投递员：丁真，电话：15151515151',
    time: '2022-06-26 15:15:15'
}, {
    content: '邮件投递到： 四川省甘孜藏族自治州理塘自提点，投递员：丁真，电话：15151515151',
    time: '2022-06-26 15:15:15'
}, {
    content: '邮件投递到： 四川省甘孜藏族自治州理塘自提点，投递员：丁真，电话：15151515151',
    time: '2022-06-26 15:15:15'
}, {
    content: '邮件投递到： 四川省甘孜藏族自治州理塘自提点，投递员：丁真，电话：15151515151',
    time: '2022-06-26 15:15:15'
},]
</script>
<style lang="less" scoped>
.decoration {
    position: relative;
    display: flex;
    flex-direction: column;

    .blackLine {
        height: 40px;
        width: 2px;
        left: 7px;
        position: absolute;
        background-color: #bbbbbb;
    }

    .point {
        width: 16px;
        height: 16px;
        position: relative;
        top:8px;
        background-color: #bbbbbb;
        border-radius: 50%;
    }

    .upper {
        top: -8px;
    }

    .lower {
        top: 20px;
    }
}

.transportProcessWrapper {
    display: grid;
    grid-template-columns: 32px auto;
    sub{
        vertical-align: super;
    }
}
</style>
