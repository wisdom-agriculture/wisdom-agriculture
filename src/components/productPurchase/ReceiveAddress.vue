<template>
    <div class="addressCard" :class="selected==data.id?'selected':''" @click="onSelect(data.id)">
        <div>
            {{data.userName}}
        </div>
        <div>
            {{data.userShoppingAddress}} {{data.userPhone}}
        </div>
    </div>
</template>
<script lang="ts" setup>
import { defineProps, defineEmits, ref } from 'vue';
import { Shop } from '@/tools/apis'
const props = defineProps<{
    selected: number,
    data: Shop.receiveAddress,
    onSelect: (arg0: number) => void
}>()
</script>
<style lang="less" >
.addressCard {
    width: 360px;
    height: 160px;
    font-size: 18px;
    font-weight: 600;
    border-color: rgb(206, 206, 206);
    border-width: 2px;
    border-style: solid;
    border-radius: 10px;

    div {
        padding: 8px;
    }

    >div:first-of-type {
        border-bottom: 1px solid rgb(206, 206, 206);
    }

}

.selected {
    border-color: rgb(64, 149, 229);
}
</style>