<template>
    <div id="signUpContainer">
        为了您更好的使用本平台，请您填写真实信息
        <div id="profileForm">
            <div id="avatarUpdate">
                <img src="" alt="">
                <a-upload :max-count="1" :show-upload-list="false">
                    <a-button type="text">点击上传头像</a-button>
                </a-upload>
            </div>
            <div id="informationUpdate">
                <div>
                    姓名：
                </div>
                <div>
                    <a-input></a-input>
                </div>
                <div>
                    身份：
                </div>
                <a-select ref="select" :options="identity" @change="handleChange">
                </a-select>
                <div>
                    企业名称：
                </div>
                <div>
                    <a-input></a-input>
                </div>
                <div>
                    企业联系方式：
                </div>
                <div>
                    <a-input></a-input>
                </div>
            </div>
            <div id="confirmContainer">
                <green-button @click="confirmEdit">
                    确认修改
                </green-button>
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref, watch } from 'vue';
import { useRouter } from 'vue-router'
import { SelectProps, UploadProps } from 'ant-design-vue';
import GreenButton from '@/components/GreenButton.vue'
const router = useRouter();
const identity = ref<SelectProps['options']>([
    {
        label: "超级管理员",
        value: "SA"
    },
    {
        label: "种植户",
        value: "plotOwner"
    },
    {
        label: "司机",
        value: "driver"
    },
    {
        label: "原料厂商负责人",
        value: "material"
    },
    {
        label: "加工厂员工",
        value: "employee"
    },
    {
        label: "加工厂商负责人",
        value: "manufacturer"
    },
    {
        label: "零售商",
        value: "retailer"
    },
])
const handleChange = (value: string) => {
    console.log(value)
}
const avatarFile = ref<UploadProps['fileList']>([])

const confirmEdit = () => {
    const role = ref('farmer');
    const roleNow = localStorage.getItem('role');
    role.value = typeof (roleNow) === 'string' ? roleNow : 'farmer'
    switch (role.value) {
        case 'farmer':
            router.push("DashBoard/PlotManagement")
            break;
        case 'material':
            router.push("DashBoard/TraceMaterial")
            break;
        case 'product':
            router.push("DashBoard/TraceManufactor")
            break;
        case 'retailer':
            router.push("DashBoard/TraceRetailer")
            break;
        case 'driver':
            router.push("DashBoard/TraceDriver")
            break;
    
        default:
            break;
    }
}
</script>

<style lang="less" scoped>
#signUpContainer {
    padding: 0 64px;
    font-size: 20px;
    font-weight: 600;

    #profileForm {
        margin: auto;
        width: 640px;

        *:nth-child(n) {
            margin: auto;
        }

        #avatarUpdate {
            width: 160px;
            height: 160px;
            border-radius: 50%;
            margin: 32px auto;
            border-style: solid;
            border-width: 1px;
            border-color: rgba(var(--green-6), 0.30);
            display: flex;


            background-color: #efefef;

            button {
                margin: auto;
                color: rgb(var(--green-6));
                font-size: 20px;
                font-weight: 600;
            }
        }

        #informationUpdate {
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

        #confirmContainer {
            width: 320px;
            margin: auto;
        }
    }


}
</style>