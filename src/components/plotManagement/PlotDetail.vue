<template>
    <div class="plotDetail">
        <div v-if="!isEditing">
            <a-button type="text" @click="() => isEditing = !isEditing" class="toggleButton">
                <svg xmlns="http://www.w3.org/2000/svg"
                    class="styles__StyledSVGIconPathComponent-sc-16fsqc8-0 fPsHiw svg-icon-path-icon"
                    viewBox="0 0 1240 1024" width="25" height="25">
                    <defs data-reactroot=""></defs>
                    <g>
                        <path
                            d="M1017.576256 970.610198 26.694901 970.610198c-14.682196 0-26.694901 12.012705-26.694901 26.694901 0 14.682196 12.012705 26.694901 26.694901 26.694901l990.881355 0c14.682196 0 26.694901-12.012705 26.694901-26.694901C1044.271157 982.622904 1032.258451 970.610198 1017.576256 970.610198L1017.576256 970.610198z"
                            p-id="1930" fill="#2FAF48"></path>
                        <path
                            d="M65.736194 893.695515c2.502647 0 4.838451-0.333686 7.341098-1.001059l164.340484-46.88292c4.337921-1.167902 8.342157-3.503706 11.512176-6.840568l516.37949-516.212647c10.511117-10.344274 10.511117-27.362273 0-37.706548-10.511117-10.511117-27.362273-10.511117-37.706548 0L280.963833 731.357148l-86.758428-86.758428c-3.003176-3.003176-6.673725-5.172137-10.67796-6.340039l436.96216-436.96216c10.511117-10.344274 10.511117-27.362273 0-37.706548-10.511117-10.511117-27.362273-10.511117-37.706548 0L70.407801 675.965229c-4.171078 4.171078-6.840568 9.510058-7.507941 15.349568l-23.524881 172.015268c-1.167902 9.009529 2.168961 17.852215 9.009529 23.858568C53.222959 891.359711 59.396155 893.695515 65.736194 893.695515L65.736194 893.695515zM114.454388 707.331737l35.537587-35.537587c1.334745 3.837392 3.336863 7.507941 6.340039 10.67796l86.758428 86.758428-27.028587 27.028587-118.29178 33.869156L114.454388 707.331737 114.454388 707.331737z"
                            p-id="1931" fill="#2FAF48"></path>
                        <path
                            d="M778.823735 267.86693c5.172137 4.671608 11.679019 7.007412 18.019058 7.007412 7.174255 0 14.348509-2.836333 19.687489-8.509l94.266369-101.941153c4.838451-5.172137 7.341098-12.179549 7.007412-19.18696-0.333686-7.007412-3.336863-13.681137-8.509-18.519588l-129.47027-119.626525c-10.844804-10.010588-27.69596-9.343215-37.706548 1.501588l-94.099526 101.941153c-10.010588 10.844804-9.343215 27.69596 1.501588 37.706548L778.823735 267.86693 778.823735 267.86693zM763.307324 64.651997l90.262134 83.254722-58.06141 62.733017-90.095291-83.254722L763.307324 64.651997 763.307324 64.651997z"
                            p-id="1932" fill="#2FAF48"></path>
                        <path
                            d="M1173.741426 720.679188 1212.449033 681.971581 1221.792248 691.314797 1183.084642 730.022403Z"
                            p-id="1933" fill="#2FAF48"></path>
                        <path
                            d="M1233.771935 679.221723l-7.668361 7.668361-9.320007-9.320007 7.668361-7.668361 9.320007 9.320007Z"
                            p-id="1934" fill="#2FAF48"></path>
                        <path d="M1173.741426 720.679188 1169.737191 734.026638 1173.741426 720.679188Z" p-id="1935"
                            fill="#2FAF48"></path>
                        <path d="M1169.737191 734.026638 1183.084642 730.022403 1169.737191 734.026638Z" p-id="1936"
                            fill="#2FAF48"></path>
                        <path d="M1167.901917 739.031932 1237.976032 739.031932 1167.901917 739.031932Z" p-id="1937"
                            fill="#2FAF48"></path>
                    </g>
                </svg>
            </a-button>
            <div>
                名称：{{ plantName }}
            </div>
            <div>
                品种：{{ plantType }}
            </div>
            <div>
                播种面积：{{ plantArea }}亩
            </div>
            <div>
                {{ plantTime }}种植
            </div>
        </div>
        <div v-if="isEditing" class="editBlock">
            <div>
                名称：
            </div>
            <div>
                <a-input v-model:value="plantName"></a-input>
            </div>
            <div>
                品种：
            </div>
            <div>
                <a-input v-model:value="plantType"></a-input>
            </div>
            <div>
                播种面积：
            </div>
            <div style="display: flex;">
                <div>
                    <a-input style="width: 100%;" v-model:value="plantArea"></a-input>
                </div>
                <div>
                    亩
                </div>
            </div>
            <div>
                种植时间：
            </div>
            <div>
                <a-input v-model:value="plantTime"></a-input>
            </div>
            <div class='confirmButtonContainer'>
                <span>
                    <a-button type="text" @click="() => isEditing = !isEditing" style="color: rgba(189,49,36,1);">确定
                    </a-button>
                </span>
                <span>
                    <a-button type="text" @click="() => isEditing = !isEditing" style="color:rgba(47,175,72,1)">取消
                    </a-button>
                </span>
            </div>
        </div>
    </div>
</template>
<script lang="ts" setup>
import { ref } from 'vue'
import FloatingCard from './FloatingCard.vue';
const plantName = ref('一号白菜')
const plantType = ref('白菜')
const plantArea = ref(200)
const plantTime = ref('2022-7-18')
const isEditing = ref(false)

</script>
<style lang="less" scoped>
.plotDetail {
    margin: auto;
    text-align: left;
    position: relative;
    padding-top: 16px;
    padding-left: 4px;

    .toggleButton {
        position: absolute;
        top: -24px;
        right: -32px;
    }

    >.editBlock {
        display: grid;
        grid-template-columns: 40% 60%;
        gap: 4px 0;
        text-align: right;
        font-size: 16px;
        white-space: nowrap;

        >.confirmButtonContainer {
            display: flex;
            flex-direction: row;
            justify-content: space-evenly;
            grid-column-end: span 2;

            >span {
                display: block;
            }
        }
    }
}
</style>