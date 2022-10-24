<template>
  <div class="mmt-card" :style="
    'border-radius: ' +
    props.borderRadius +
    'px'
  ">
    <slot name="label">
      <h2 class="mmt-card-label" v-if="props.label">{{ props.label }}</h2>
    </slot>
    <slot>a</slot>
  </div>
</template>
<script setup lang="ts">
import { defineProps } from 'vue';
const props = defineProps({
  label: { type: String, default: "" },
  borderRadius: { type: Number, default: 30 },
});
</script>
<style lang="less" scoped>
div.mmt-card {
  box-shadow: 4px 4px 8px #c4c4c4, -4px -4px 8px #ffffff;
  overflow: hidden;
}

h2.mmt-card-label {
  font-size: 20px;
  margin: 0 0 16px 0;
}
</style>
