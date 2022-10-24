<template>
    <div id="mapContainer" ref="mapContainer"></div>
</template>
<script lang="ts" setup>
/* eslint-disable */
//这bmapgl没有ts支持（悲
//此文件在乃老师支持下写完，bilibili关注乃琳，净化心灵
import { watch, ref, defineProps } from 'vue';
export type mapPoint = [number, number]
const props = defineProps<{
    location?: mapPoint | null,
    overlay?: mapPoint[] | null
}>();
const mapContainer = ref<HTMLDivElement | null>(null);
const mapEntity = ref<any>(null);
const zoomCtrl = new BMapGL.ZoomControl();  // 添加缩放控件
let trueLocation = ref<any>(null);
let trueOverlay = ref<any>([]);
const pointTranslator = new BMapGL.Convertor();
watch(mapContainer, (newValue) => {
    if (newValue) {
        mapEntity.value = new BMapGL.Map(newValue);
        (window as any).mapEntity=mapEntity.value
        mapEntity.value.addControl(zoomCtrl);
        mapEntity.value.centerAndZoom(new BMapGL.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
        mapEntity.value.enableScrollWheelZoom(true);
        if (props.location && props.overlay) {
            mapEntity.value.setCenter(new BMapGL.Point(props.location[0], props.location[1]))
            let overlay: any = [];
            for (let i in props.overlay) {
                overlay.push(new BMapGL.Point(i[0], i[1]))
            }
            let polygon = new BMapGL.Polygon(overlay, { strokeColor: "blue", strokeWeight: 2, strokeOpacity: 0.3 });
            mapEntity.value.addOverlay(polygon);
        }
    }
})
watch(props, (newValue) => {
    if (newValue.location && newValue.overlay && mapContainer) {
        mapEntity.value.clearOverlays();     
        let temp=[new BMapGL.Point(newValue.location[0],newValue.location[1])];
        for (let i of newValue.overlay) {
            temp.push(new BMapGL.Point(i[0], i[1]))
        }
        const callback=(res:{points:any[]})=>{
            console.log(res)
            mapEntity.value.setCenter(res.points[0])
            mapEntity.value.addOverlay(new BMapGL.Marker(res.points[0]));
            let polygon = new BMapGL.Polygon(res.points.slice(1,res.points.length), { strokeColor: "blue", strokeWeight: 2, strokeOpacity: 0.5 });
            mapEntity.value.addOverlay(polygon);
        }
        pointTranslator.translate(temp,3,5,callback)
    }
})
</script>
<style lang="less" scoped>
#mapContainer {
    height: calc(100% - 32px);
    width: 100%;
}
</style>