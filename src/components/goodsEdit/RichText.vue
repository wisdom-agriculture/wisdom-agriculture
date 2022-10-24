<template>
    <div ref="elem" id="richTextContent" style="width: 100%;height:100%"></div>
</template>
<script lang="ts" setup>
import Vditor from 'vditor'
import 'vditor/dist/index.css';
import { ref,watch } from 'vue';
const elem=ref<HTMLDivElement|undefined>()
console.log('1321')
watch(elem,newValue=>{
    if(newValue!==undefined){
        let vditor=new Vditor(newValue,{
            height:'600px',
            cache:{
                id:'richTextContent'
            }
        });
    }
})
</script>