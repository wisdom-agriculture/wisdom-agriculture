<template>
    <div class="plotManagement">
        <div class="leftPart">
            <div id="plotState">
                <div id="plotSelect" class="select">
                    <span> 地块</span>
                    <a-select :options="plots" v-model:value="plotNow"></a-select>
                    <a-button type="text" class="addPlot" @click="addPlotControler.open">添加地块</a-button>
                    <a-modal v-model:visible="addPlotVisible" title="添加地块" @ok="addPlotControler.close">
                        <template #footer>
                            <a-button key="back" @click="addPlotControler.close">Return</a-button>
                            <a-button key="submit" type="primary" @click="addPlotControler.close">Submit</a-button>
                        </template>
                        <div>
                            <div>地块名称
                                <a-input v-model:value="newPlotName"></a-input>
                            </div>
                            <div>地块坐标</div>
                            经度：<a-input v-model:value="newPlotLocationInput[0]" style="width:75px"></a-input>,
                            纬度：<a-input v-model:value="newPlotLocationInput[1]" style="width:75px"></a-input>
                            <div>地块区域</div>
                            <div v-for="i of newPlotOverlayInput">
                                经度：<a-input v-model:value="i[0]" style="width:75px"></a-input>,
                                纬度：<a-input v-model:value="i[1]" style="width:75px"></a-input>
                                <a-button type="link" @click="deletePoint(i)">删除
                                </a-button>
                            </div>
                            <a-button @click="()=>{newPlotOverlayInput.push([0,0])}">新建点</a-button>
                            <div style="height:400px">
                                <b-map :overlay="newPlotOverlaySet" :location="newPlotLocationPoint"></b-map>
                            </div>
                        </div>
                    </a-modal>
                    <a-button type="text" class="delPlot" @click="deletePlotControler.open">删除地块</a-button>
                    <a-modal v-model:visible="deletePlotVisible" title="删除地块" @ok="deletePlotControler.close">
                        <template #footer>
                            <a-button key="back" @click="deletePlotControler.close">Return</a-button>
                            <a-button key="submit" type="primary" @click="deletePlotControler.close">Submit</a-button>
                        </template>
                        <div>
                            <div>是否确认删除此地块？
                            </div>
                        </div>
                    </a-modal>
                </div>
                <floating-card label="天津市" class="cardPadding">
                    <weather-card :weather-data="weatherData"></weather-card>
                </floating-card>
                <div class="cardWithTitle">
                    环境实时数据
                    <floating-card class="cardPadding">
                        <env-card :env-data="envData"></env-card>
                    </floating-card>
                </div>
                <div class="cardWithTitle">
                    环境曲线趋势图
                    <floating-card class="cardPadding">
                        <env-charts></env-charts>
                    </floating-card>
                </div>
            </div>
        </div>
        <div class="middlePart">
            <div class="videoAndMap videoOrMapSelect select">
                <span @click="switchVideoAndMap(true)" :class="videoOrMap ? 'selected' : ''">视频监控</span>|
                <span @click="switchVideoAndMap(false)" :class="videoOrMap ? '' : 'selected'">地图信息</span>
                <video-monitor v-show="videoOrMap"></video-monitor>
                <b-map v-show="!videoOrMap" :location="currentLocation" :overlay="currentOverlay"></b-map>
            </div>
            <div class="cardWithTitle" style="position:relative">
                种植日志
                <div class="addLog" @click="addLogControler.open">添加操作</div>
                <a-modal v-model:visible="addLogVisible" title="添加操作" @ok="addLogControler.close">
                    <template #footer>
                        <a-button key="back" @click="addLogControler.close">取消</a-button>
                        <a-button key="submit" type="primary" @click="newOperation">提交</a-button>
                    </template>
                    <div class="informationUpdate">
                        <div>操作人</div>
                        <div>
                            <a-input v-model:value="newOperationItem.operator"></a-input>
                        </div>
                        <div>手机号</div>
                        <div>
                            <a-input v-model:value="newOperationItem.phone"></a-input>
                        </div>
                        <div>时间</div>
                        <div>
                            <input type="datetime-local" @change="dateTimeListener" />
                        </div>
                        <div>工作内容</div>
                        <div>
                            <a-input v-model:value="newOperationItem.content"></a-input>
                        </div>
                        <div>备注</div>
                        <div>
                            <a-input v-model:value="newOperationItem.remark"></a-input>
                        </div>
                    </div>
                </a-modal>
                <plot-log :field-id="plotNow"></plot-log>
            </div>
        </div>
        <div class="rightPart">
            <div class="doubleCard">
                <div class="cardWithTitle">
                    种植推荐
                    <plant-recommend :field-id="plotNow"></plant-recommend>
                </div>
                <div class="cardWithTitle">
                    种植信息
                    <floating-card class="cardPadding" style="height:224px;display: flex;">
                        <plot-detail></plot-detail>
                    </floating-card>
                </div>
            </div>
            <div class="cardWithTitle">
                疾病虫害与环境阈值预警
                <floating-card class="cardPadding" style="position:relative">
                    <a-button type="link"
                        style="position:absolute;top:4px;right: 8px;font-size: 16px;font-weight: normal;"
                        @click="router.push({name:'AlertConsole',params:{id:plotNow}})">
                        详情
                    </a-button>
                    <disease-forecast :field-id="plotNow"></disease-forecast>
                </floating-card>
            </div>
            <div class="cardWithTitle" style="position:relative">
                设备运行状态
                <div class="addLog" @click="addDeviceControler.open">添加设备</div>
                <a-modal v-model:visible="addDeviceVisible" title="添加设备" @ok="addDeviceControler.close">
                    <template #footer>
                        <a-button key="back" @click="addDeviceControler.close">Return</a-button>
                        <a-button key="submit" type="primary" @click="addDeviceControler.close">Submit</a-button>
                    </template>
                    <div class="informationUpdate" style="grid-template-rows:auto">
                        <div>设备名称</div>
                        <div>
                            <a-input></a-input>
                        </div>
                    </div>
                </a-modal>
                <device-state></device-state>
            </div>
        </div>
    </div>
</template>
<script lang="ts" setup>
import { message, SelectProps } from 'ant-design-vue';
import { ref, computed, watch } from 'vue';
import FloatingCard from '@/components/FloatingCard.vue';
import WeatherCard, { weatherDataType } from '@/components/plotManagement/WeatherCard.vue';
import EnvCard, { envDataExtendType } from '@/components/plotManagement/EnvCard.vue'
import EnvCharts from '@/components/plotManagement/EnvCharts.vue';
import BMap, { mapPoint } from '@/components/plotManagement/BMap.vue';
import VideoMonitor from '@/components/plotManagement/VideoMonitor.vue'
import PlotLog from '@/components/plotManagement/PlotLog.vue';
import PlantRecommend from '@/components/plotManagement/PlantRecommend.vue';
import PlotDetail from '@/components/plotManagement/PlotDetail.vue';
import DiseaseForecast from '@/components/plotManagement/DiseaseForecast.vue';
import DeviceState from '@/components/plotManagement/DeviceState.vue';
import apis, { getPlotRes, operationItem } from '@/tools/apis';
import { useRouter } from 'vue-router';
const plotNow = ref<number | null>(null);
const plotData = ref<getPlotRes | null>(null)
const router = useRouter();

//由此开始前后端交互
const loadMsgKey = 'plotLoading';
message.loading({ content: '数据加载中', key: loadMsgKey })
const getPlot = async () => {
    const res = await apis.getPlot()
    if (res.status === 'success') {
        plotData.value = res.data;
        plotNow.value = res.data.data.fieldList[0].id
    } else if (res.status === 'false') {
        message.error({ content: res.message, duration: 3 });
    } else {
        message.error({ content: '未登录', duration: 2 });
        router.push('/SignIn');
    }
}
Promise.all([
    getPlot(),
]).then(() => {
    message.destroy(loadMsgKey);
})
//结束
watch(plotNow, (newValue) => {
    if (newValue) {
        localStorage.setItem('plotNow', String(newValue))
    } else {
        localStorage.removeItem('plotNow')
    }
})
const plots = computed<SelectProps['options']>(() => {
    let optionArray = [];
    if (plotData.value !== null) {
        for (let i of plotData.value.data.fieldList) {
            optionArray.push({
                label: i.name,
                value: i.id
            })
        }
        return optionArray;
    } else {
        return [{
            label: '加载中...',
            disabled: true,
        }]
    }
})
const currentLocation = computed<null | mapPoint>(() => {
    const currentPlot = plotData.value?.data.fieldList.find(i => i.id === plotNow.value)
    if (currentPlot) {
        console.log(currentPlot)
        return [currentPlot.longitude, currentPlot.latitude]
    } else {
        return null
    }
})
const currentOverlay = computed<null | mapPoint[]>(() => {
    const currentPlot = plotData.value?.data.fieldList.find(i => i.id === plotNow.value)
    if (currentPlot) {
        let overlayArray: mapPoint[] = []
        for (let i of currentPlot.location) {
            let point: mapPoint[] = [[Number(i[0]), Number(i[1])]]
            overlayArray.push(...point)
        }
        console.log(overlayArray)
        return overlayArray
    } else {
        return null
    }
})
const numberConvertor = (i: mapPoint): mapPoint => {
    return [Number(i[0]), Number(i[1])]
}
const newPlotName = ref('')
const newPlotLocationInput = ref<mapPoint>([0, 0])
const newPlotLocationPoint = computed(() => {

    return numberConvertor(newPlotLocationInput.value)
})
const newPlotOverlayInput = ref<mapPoint[]>([[0, 0]])
const newPlotOverlaySet = computed(() => {
    return newPlotOverlayInput.value.map(numberConvertor)
})
const deletePoint = (i: mapPoint) => {
    newPlotOverlayInput.value = newPlotOverlayInput.value.filter((j, index) => {
        return j[0] != i[0] && j[1] != i[1]
    })
}

const weatherData = ref<weatherDataType>([{
    weather: '小雨',
    wind: '北风一级',
    minTemp: 14,
    maxTemp: 23,
    time: "1日 星期五"
}, {
    weather: '小雨',
    wind: '北风二级',
    minTemp: 14,
    maxTemp: 23,
    time: "2日 星期六"
}, {
    weather: '小雨',
    wind: '东风二级',
    minTemp: 14,
    maxTemp: 23,
    time: "3日 星期日"
}])
const envData = ref<envDataExtendType>({
    NPotency: 10,
    PPotency: 10,
    KPotency: 10,
    SoilHumidity: 10,
    SoilTemp: 10,
    pH: 10,
    CO2: 10,
    illumination: 10,
})
const videoOrMap = ref(false);
const switchVideoAndMap = (to: boolean) => {
    videoOrMap.value = to
}
const addLogVisible = ref(false)
const addLogControler = {
    open: () => addLogVisible.value = true,
    close: () => addLogVisible.value = false
}
const dateTimeListener = (e: any) => {
    if (e.target) {
        newOperationItem.value.time = e.target.valueAsNumber
    }
}
const newOperationItem = ref({
    fieldId: 0,
    operator: '',
    phone: '',
    time: Date.now(),
    content: '',
    remark: '',
})
const newOperation = async () => {
    if (typeof (plotNow.value) == 'number') {
        newOperationItem.value.fieldId = plotNow.value;
        const res = await apis.newOperation(newOperationItem.value)
        if (res.status == 'success') {
            message.success(res.data.message)
        } else if (res.status == 'false') {
            message.error(res.message)
        }
    } else {
        message.error('当前未选中地块')
    }
    addLogControler.close()
}
const addDeviceVisible = ref(false)
const addDeviceControler = {
    open: () => addDeviceVisible.value = true,
    close: () => addDeviceVisible.value = false
}

const addPlotVisible = ref(false)
const addPlotControler = {
    open: () => addPlotVisible.value = true,
    close: () => addPlotVisible.value = false
}
const addPlot = () => {
    if (
        newPlotName.value &&
        newPlotOverlaySet.value.length > 2
    )
        apis.newPlot({
            name: newPlotName.value,
            longitude: newPlotLocationPoint.value[0],
            latitude: newPlotLocationPoint.value[1],
            location: newPlotOverlaySet.value
        }).then(res=>{
            if(res.status=='false'){
                message.error(res.message)
            }
        })
}

const deletePlotVisible = ref(false)
const deletePlotControler = {
    open: () => deletePlotVisible.value = true,
    close: () => deletePlotVisible.value = false
}

</script>
<style lang="less" scoped>
.plotManagement {
    color: #000;
    display: flex;
    flex-direction: row;

    >div {
        margin: 0 8px
    }
}

.select {
    font-size: 18px;

    span {
        margin: 16px;
        font-weight: 600;
        cursor: pointer;
        user-select: none;
    }
}

.videoOrMapSelect {
    color: #9a9a9a;

    .selected {
        color: #000;
    }
}

#plotSelect {
    span:first-child {
        cursor: auto
    }

    .addPlot {
        color: #2faf48;
    }

    .delPlot {
        color: #bd3124
    }
}


.cardPadding {
    padding: 15px
}

.cardWithTitle {
    text-align: center;
    margin-top: 16px;
    font-size: 20px;
    font-weight: 600;
}

.videoAndMap {
    height: 480px;
    width: 800px;
}

.addLog {
    margin: 0 0 0 auto;
    color: rgba(47, 175, 72, 1);
    font-size: 18px;
    font-weight: 400;
    position: absolute;
    top: 0;
    right: 0;
    cursor: pointer;
}

.informationUpdate {
    display: grid;
    grid-template-columns: 33% 66%;
    grid-template-rows: repeat(4, 64px);

    div:nth-child(n) {
        margin: 12px
    }

    div:nth-child(2n-1) {
        text-align: right;
    }
}

.doubleCard {
    display: grid;
    grid-template-columns: 50% 50%;
    width: 450px;
}
</style>
<style>
.ant-table-tbody {
    overflow: hidden;
}
</style>