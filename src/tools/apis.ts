import { message } from "ant-design-vue";
import { useRouter } from "vue-router";
import JSONBig from 'json-bigint'

export interface baseRes {
    code: string,
    message: string,
}
interface dataRes<T> extends baseRes {
    data: T
}
export interface loginBody {
    phone: string,
    code: string
}
export type loginResType = string;
export interface loginRes extends baseRes {
    data: {
        token: string,
        permission: null | string,
    }
}
export interface recommendBody {
    N: number,
    P: number,
    K: number,
    humidity: number,
    temperature: number,
    ph: number
}
export type recommendRes = baseRes;
export type vertificationBody = {
    phone: string
}
export interface getPlotRes extends baseRes {
    data: {
        fieldList: {
            id: number,
            name: string,
            latitude: number,
            longitude: number,
            userId: number,
            location: [string, string][],
            description: string | null,
            renterId: number,
            statusId: number | null,
        }[]
    }
}
export interface getOperationBody {
    fieldId: number,
    current: number,
    size: number,
}
export interface operationItem {
    "fieldId": number,
    "operator": string,
    "phone": string,
    "time": number,
    "content": string,
    "remark": string
}
export interface operationResItem extends operationItem {
    "id": number,
    "name": string,
}
export interface getOperationRes extends baseRes {
    data: {
        operationResultList: operationResItem[]
    }
}
export interface diseaseItem {
    "id": number,
    "fieldName": string,
    "plant": string,
    "time": number,
    "status": 0 | 1,
    "img": string,
    "disease": string
}
export interface getDiseaseRes extends baseRes {
    data: {
        diseaseList: diseaseItem[]
    }
}
export interface getSingleSensorRes extends baseRes {
    data: {
        id: number,
        objectId: string,
        status: 'Online'
    }
}
export interface plantType {
    id: number,
    name: string,
    imgUrl: string
}
export interface getPlantTypesRes extends baseRes {
    data: {
        plantList: plantType[]
    }
}
export interface newPlotBody {
    name:string,
    latitude:number,
    longitude:number,
    location: [number, number][]
}
export namespace Trace {
    export interface transportContactBody {
        cropsId: bigint,
        driverId: number,
        deptId: number,
        remarks: string
    }
    export interface transportCompany {
        "id": number,
        "parentId": number,
        "ancestors": string,
        "deptName": string,
        "leader": string,
        "phone": string,
        "email": string,
        "status": string,
        "createTime": string
    }
    export interface getTransportCompanyRes extends baseRes {
        data: transportCompany[]
    }
    export namespace Farmer {
        export interface plantStatus {
            "id": number,
            "fieldId": number,
            "plantId": number,
            "plantStatusId": number,
            "userId": number,
            "cropsId": bigint,
            "cropsName": string,
            "status": null | 0 | 1,
            "machineStatus": null | 0 | 1,
            "outFactoryStatus": null | 0 | 1,
            "productWriteStatus": null | 0 | 1
        }
        export interface getPlantListRes extends baseRes {
            data: plantStatus[]
        }
        export interface plantDetail {
            "address": string,
            "apply_fertilizer_cycle": string,
            "bagging_status": string,
            "crops_id": string,
            "crops_name": string,
            "farmer_id": string,
            "farmer_name": string,
            "farmer_tel": string,
            "fertilizer_name": string,
            "grow_seedlings_cycle": string,
            "irrigation_cycle": string,
            "plant_mode": string,
            "register_time": string,
            "remarks": string,
            "weed_cycle": string,
            "year": string
        }
        export interface getPlantDetailRes extends baseRes {
            data: plantDetail
        }
        export interface addPlantBody {
            "plantId": number,
            "fieldId": number,
            "plantStatusId": 1,
            "cropType": string,
            "fertilizerName": string,
            "plantingMethod": string,
            "plantingYear": string,
            "bagging": string,
            "seedingPeriod": string,
            "irrigationCycle": string,
            "fertilizerCycle": string,
            "weedingCycle": string,
            "detailedAddress": string,
            "remark": string,
        }
        export interface plantProcess {
            "Key": string,
            "Record": {
                "crops_bak_id": string,    //cropsId
                "crops_grow_id": string,       //过程Id
                "crops_grow_photo_url": string,
                "grow_status": string,
                "illumination_status": string,
                "record_time": string,
                "remarks": string,
                "temperature": string,
                "water_content": string,
            }
        }
        export interface getplantProcessRes extends baseRes {
            data: plantProcess[]
        }
    }
    export namespace Driver {
        export interface driverItem {
            "id": number,
            "nickname": string,
            "signature": string,
            "imgUrl": string,
            "farm": string,
            "phone": string,
            "deptId": number
        }
        export interface getDriverListRes extends baseRes {
            data: driverItem[]
        }
        export interface transportItem {
            "id": number,
            "cropsId": bigint,
            "driverId": number,
            "time": string,
            "farmerId": number,
            "farmerNickname": string,
            "farmerTel": string,
            "farmerDeptName": string,
            "deptId": number,
            "status": number,
            "outFactoryStatus": null,
            "retailerReceiveStatus": null,
            "remarks": string
        }
        export interface getTransportListRes extends baseRes {
            data: transportItem[]
        }
    }
    export namespace Material {
        export interface plantDetail {
            "id": number,
            "cropsId": bigint,
            "driverId": number,
            "time": null,
            "farmerId": number,
            "farmerTel": string,
            "farmerDeptName": null,
            "farmerNickname": string,
            "deptId": number,
            "status": number,
            "outFactoryStatus": 0 | 1,
            "retailerReceiveStatus": null,
            "remarks": string
        }
        export interface getPlantsDetailRes extends baseRes {
            data: plantDetail[]
        }
        export interface outCropBody {
            "id": number,
            "checkResult": string,
            "remark": string,
            "photo": string
        }
    }
    export namespace Manufacturer {
        export interface plantItem {
            "id": number,
            "cropsId": bigint,
            "farmerNickname": string,
            "farmerTel": string,
            "farmerDeptName": string,
            "cropsName": string,
            "time": string,
            "machingStatus": number,
            "outFactoryStatus": number,
            "productWriteStatus": number,
            "retailerReceiveStatus": number,
            "product_id": null
        }
        export interface getPlantListRes extends baseRes {
            data: plantItem[]
        }
        export interface setPlantInfoBody {
            "id": number,
            "productName": string,
            "ingredients": string,
            "leader": string,
            "phone": string,
            "workshop": string,
            "hour": string,
            "storageMethod": string,
            "netContent": string,
            "recommended": string,
            "remark": string
        }
    }
    export namespace Retailer {
        export interface plantItem {
            "id": number,
            "cropsId": bigint,
            "farmerNickname": string,
            "farmerTel": string,
            "farmerDeptName": string,
            "cropsName": string,
            "time": string,
            "machingStatus": number,
            "outFactoryStatus": number,
            "productWriteStatus": number,
            "retailerReceiveStatus": number,
            "product_id": null
        }
        export interface getPlantListRes extends baseRes {
            data: plantItem[]
        }
        export interface prodInfo {
            Key: string,
            Record: {
                crops_id: string,
                product_id: string,
                remarks: string,
                retailer: string,
                retailer_id: string,
                retailer_name: string,
                retailer_tel: string,
            }
        }
        export interface getProdIdRes extends baseRes {
            data: prodInfo[]
        }
    }
    export namespace User {
        export interface prodInfo {
            "Key": string,
            "Record": {
                "Net_Content": string,
                "cooking_recommend": string,
                "crops_id": string,
                "factory": string,
                "keep_mathod": string,
                "leader": string,
                "leader_tel": string,
                "mixed_ingredients": string,
                "product_id": string,
                "product_name": string,
                "remarks": string,
                "time": string,
                "work_hours": string,
                "workshop": string
            }
        }
        export interface getProdInfoRes extends baseRes {
            data: prodInfo[]
        }
    }
}
export namespace Panel {
    export interface plotPlantArea {
        "name": string,
        "plant": string,
        "area": number
    }
    export interface plantAreaOfPlotsRes extends baseRes {
        data: plotPlantArea[]
    }
    export interface plantArea {
        "plant": string,
        "area": number
    }
    export interface plantAreaRes extends baseRes {
        data: plantArea[]
    }
    export interface sensorStatus {
        "name": string,
        "status": 0 | 1,
        "field": string,
    }
    export interface sensorStatusRes extends baseRes {
        data: sensorStatus[]
    }
    export interface diseaseAlert {
        "name": string,
        "percentage": number
    }
    export interface diseaseAlertRes extends baseRes {
        data: diseaseAlert[]
    }
    export interface diseaseByTime {
        "name": string,
        "time": number[]
    }
    export interface diseaseByTimeRes extends baseRes {
        data: diseaseByTime[]
    }
}
export namespace Shop {
    export interface getRecDetailBody {
        produceId: number;
    }
    export interface recommendItem {
        "producesId": number,
        "producesName": string,
        "producesImage": string,
        "producesPrice": number
    }
    export interface getRecommendRes extends baseRes {
        data: {
            "swiperImages": null,
            "equipmentsList": recommendItem[]
        }
    }
    export interface recDetailItem {
        freightPrice: number,
        produceDescription: string,
        produceId: number,
        produceImage: string,
        produceName: string,
        producePrice: number
        producesSpecification: null
        produceSkuId: number
    }
    export interface getRecDetailRes extends baseRes {
        data: recDetailItem
    }
    export interface receiveAddress {
        "id": number,
        "userShoppingAddress": string,
        "userName": string,
        "userPhone": string,
    }
    export interface getReceiveAddressRes extends baseRes {
        data: {
            userShoppingAddressResults: receiveAddress[]
        }
    }
    export interface submitOrderBody {
        produceSkuId: number,
        producesWeight: number,
        address: string,
        orderRemark: string
    }
    export interface submitOrderRes extends baseRes {
        data: {
            orderNumber: string
        }
    }
    export interface getAlipayBody {
        subject: string,
        traceNo: string,
        totalAmount: number
    }
    export interface orderItem {
        "id": number,
        "orderNumber": string,
        "amountPayable": number,
        "createTime": string,
        "isPay": number,
        "isPost": number
    }
    export interface getOrdersListRes extends baseRes {
        data: {
            orderManagementResult: orderItem[]
        }
    }
    export interface orderDetail {
        "id": number,
        "orderNumber": string,
        "producesId": number,
        "producesName": string,
        "producesWeight": number,
        "customerId": number,
        "customerName": string,
        "customerPhone": string,
        "address": string,
        "orderRemark": string,
        "amountPayable": number,
        "paymentMethods": string
    }
    export type getOrderDetailRes = dataRes<orderDetail>
}
export namespace Goods {
    export interface goodsItem {
        "id": number,
        "producesName": string,
        "producesPrice": number,
        "producesStatus": number,
        "createTime": string
    }
    export type getGoodsListRes = dataRes<{ producesManagementResults: goodsItem[] }>
}
const getURLConstructor = (url: string, body: any) => {
    //接受一个对象构造成GET请求的url
    let temp = url + "?";
    // eslint-disable-next-line
    for (let i in body) {
        if (typeof body[i] === 'bigint') {
            temp += i + '=' + body[i].toString() + '&'
        } else temp += i + '=' + body[i] + '&'
    }
    temp = temp.slice(0, temp.length - 1)
    return temp
}
const baseURL = "https://farm.yaohy.cn";
const apiURL = {
    login: "/web/login",
    recommend: "/fields/crop_pred",
    vertification: '/verification/codes',
    plotLog: '/fields/operations',
    field: '/fields',
    operation: '/fields/operations',
    disease: '/fields/disease_pred',
    sensorStatus: '/sensors/statuses',
    getPlantTypes: '/plants',
    trace: {
        transportContact: '/transport',
        farmer: {
            list: '/farmer/list',
            getPlantDetail: '/trace/cropsInfo',
            getPlantProcess: '/trace/cropsProcess',
            getCompanyId: '/farmer/factoryId',
            addPlant: '/farmer/plants',
            addDetail: '/farmer/recordCropsGrowOnChain'
        },
        driver: {
            driverList: '/driver/drivers',
            list: '/driver/list',
            startTransport: '/driver/beginTransport',
            finishTransport: '/driver/finishTransport'
        },
        material: {
            list: '/material/list',
            companyList: '/material/factoryId',
            outCrop: '/material/outCrops'
        },
        manufacturer: {
            list: '/product/list',
            companyList: '/product/factoryId',
            setPlantInfo: '/product/productInfo',
            outCrop: '/product/productOutFactory'
        },
        retailer: {
            list: '/retailer/list',
            receive: '/retailer/receive',
            getProdId: '/retailer/queryRetailer'
        },
        user: {
            getTraceInfo: '/trace/retailerInfo',
            getProdInfo: '/trace/productInfo'
        }
    },
    panel: {
        plantAreaOfPlots: '/panel/fieldPlantArea',
        plantArea: '/panel/plantArea',
        sensor: '/panel/sensor',
        alertCount: '/panel/diseaseAlarm',
        alertTimeCount: '/panel/diseaseTime',
    },
    shop: {
        recommend: '/equipment',
        productDetail: '/equipment/detail',
        receiveAddress: '/user/address',
        submitOrder: '/web/order',
        alipay: '/alipay/pay',
        getOrderList: '/order/management',
        getOrderDetail: '/order/details',
        editAddress: '/order/address',
        postProduct: '/post'
    },
    goods: {
        getGoodsList: '/produces/management',
        switchStatus: '/produce/status',
        deleteProduct: '/produces'
    }
}
type fetchAPIResType<T> = { status: 'success', data: T } | { status: 'false', message: string } | { status: 'NoAuth' }
type fetchAPIRequestType = (
    input: string | RequestInfo,
    init?: RequestInit | undefined,
    isPOST?: boolean
) => Promise<fetchAPIResType<any>>
const fetchAPI: fetchAPIRequestType = async (input, init?, isPOST?) => {
    const auth = localStorage.getItem('auth') as string | undefined;
    if (auth !== undefined) {
        const headers = init?.method == 'POST'
            ? new Headers({
                'Authorization': auth,
                'content-type': 'application/json'
            })
            : new Headers({ 'Authorization': auth });
        try {
            const res = await fetch(input, { ...init, headers });
            const resText = await res.text()
            const resObj = JSONBig.parse(resText) as baseRes;
            if (resObj.code === '00000') {
                return {
                    status: 'success',
                    data: resObj
                }
            } else if ('message' in resObj) {
                return {
                    status: 'false',
                    message: resObj.message,
                }
            } else {
                return {
                    status: 'false',
                    message: '服务器错误'
                }
            }
        } catch (e) {
            return {
                status: 'false',
                message: '请求错误，请检查网络连接，若网络连接无问题则请联系服务器管理员'
            }
        }
    } else {
        return {
            status: 'NoAuth',
        }
    }
}
const test = () => {
    console.log('test')
}
export default {
    login: async (arg0: loginBody): Promise<loginResType> => {
        let result: loginResType = 'error'
        await fetch(baseURL + apiURL.login, {
            method: 'POST',
            body: JSON.stringify(arg0),
            headers: new Headers({
                'content-type': 'application/json'
            })
        })
            .then(res => res.json())
            .then(function (res: loginRes | baseRes) {
                if ('data' in res && res.data.token !== null) {
                    localStorage.setItem('auth', res.data.token);
                    if(res.data.permission)
                    localStorage.setItem('role',res.data.permission)
                    result = 'success'
                }
                else if ('code' in res) {
                    result = res.message
                }
                else result = 'error'
            })
            .catch(() => {
                result = 'error'
            })
        return result
    },
    recommend: async (arg0: recommendBody): Promise<fetchAPIResType<recommendRes>> => {
        const fetchObj = await fetchAPI(getURLConstructor(baseURL + apiURL.recommend, arg0), {
            method: 'GET',
        })
        return fetchObj
    },
    recommendById: async (arg0: { id: number }): Promise<fetchAPIResType<recommendRes>> => {
        const fetchObj = await fetchAPI(getURLConstructor(baseURL + apiURL.recommend, arg0), {
            method: 'GET',
        })
        return fetchObj
    },
    vertification: async (arg0: vertificationBody) => {
        const result = await fetch(getURLConstructor(baseURL + apiURL.vertification, arg0)).then(res => res.json())
        if ('message' in result) return result.message;
        else return 'error'
    },
    getPlot: async (): Promise<fetchAPIResType<getPlotRes>> => {
        const fetchObj = await fetchAPI(baseURL + apiURL.field, {
            method: 'GET'
        })
        return fetchObj
    },
    newPlot: async (arg0: newPlotBody): Promise<fetchAPIResType<baseRes>> => {
        return fetchAPI(baseURL + apiURL.shop.postProduct, {
            body: JSONBig.stringify(arg0),
            method: 'POST',
        })
    },
    getOperation: async (arg0: getOperationBody): Promise<fetchAPIResType<getOperationRes>> => {
        const fetchObj = await fetchAPI(getURLConstructor(baseURL + apiURL.operation, arg0), {
            method: 'GET',
        })
        return fetchObj
    },
    newOperation: async (arg0: operationItem): Promise<fetchAPIResType<baseRes>> => {
        const fetchObj = await fetchAPI(baseURL + apiURL.operation, {
            body: JSON.stringify(arg0),
            method: 'POST',
        })
        return fetchObj
    },
    getDisease: async (arg0: { id: number }): Promise<fetchAPIResType<getDiseaseRes>> => {
        const fetchObj = await fetchAPI(getURLConstructor(baseURL + apiURL.disease, arg0), {
            method: 'GET',
        })
        return fetchObj
    },
    getPlantTypes: async (): Promise<fetchAPIResType<getPlantTypesRes>> => {
        const fetchObj = await fetchAPI(baseURL + apiURL.getPlantTypes, {
            method: 'GET',
        })
        return fetchObj
    },
    getSingleSensor: async (arg0: { id: number }): Promise<fetchAPIResType<getSingleSensorRes>> => {
        const fetchObj = await fetchAPI(getURLConstructor(baseURL + apiURL.disease, arg0), {
            method: 'GET',
        })
        return fetchObj
    },
    trace: {
        transportContact: async (arg0: Trace.transportContactBody): Promise<fetchAPIResType<baseRes>> => {
            const fetchObj = await fetchAPI(baseURL + apiURL.trace.transportContact, {
                body: JSONBig.stringify(arg0),
                method: 'POST',
            })
            return fetchObj
        },
        farmer: {
            getPlantList: async (): Promise<fetchAPIResType<Trace.Farmer.getPlantListRes>> => {
                const fetchObj = await fetchAPI(baseURL + apiURL.trace.farmer.list, {
                    method: 'GET',
                })
                return fetchObj
            },
            getPlantDetail: async (arg0: { id: bigint }): Promise<fetchAPIResType<Trace.Farmer.getPlantDetailRes>> => {
                const fetchObj = await fetchAPI(getURLConstructor(baseURL + apiURL.trace.farmer.getPlantDetail, arg0), {
                    method: 'GET',
                })
                return fetchObj
            },
            getPlantProcess: async (arg0: { id: bigint }): Promise<fetchAPIResType<Trace.Farmer.getplantProcessRes>> => {
                const fetchObj = await fetchAPI(getURLConstructor(baseURL + apiURL.trace.farmer.getPlantProcess, arg0), {
                    method: 'GET',
                })
                return fetchObj
            },
            addPlant: async (arg0: Trace.Farmer.addPlantBody): Promise<fetchAPIResType<baseRes>> => {
                const fetchObj = await fetchAPI(baseURL + apiURL.trace.farmer.addPlant, {
                    body: JSON.stringify(arg0),
                    method: 'POST',
                })
                return fetchObj
            },
            getTransportCompany: async (): Promise<fetchAPIResType<Trace.getTransportCompanyRes>> => {
                const fetchObj = await fetchAPI(baseURL + apiURL.trace.farmer.getCompanyId, {
                    method: 'GET',
                })
                return fetchObj
            },
        },
        material: {
            getPlantList: async (): Promise<fetchAPIResType<Trace.Material.getPlantsDetailRes>> => {
                const fetchObj = await fetchAPI(baseURL + apiURL.trace.material.list, {
                    method: 'GET',
                })
                return fetchObj
            },
            getTransportCompany: async (): Promise<fetchAPIResType<Trace.getTransportCompanyRes>> => {
                const fetchObj = await fetchAPI(baseURL + apiURL.trace.material.companyList, {
                    method: 'GET',
                })
                return fetchObj
            },
            setMaterialOut: async (arg0: { id: number }): Promise<fetchAPIResType<baseRes>> => {
                const fetchObj = await fetchAPI(baseURL + apiURL.trace.driver.startTransport, {
                    body: JSONBig.stringify(arg0),
                    method: 'POST',
                })
                return fetchObj
            },
            outCrops: async (arg0: Trace.Material.outCropBody): Promise<fetchAPIResType<baseRes>> => {
                const fetchObj = await fetchAPI(baseURL + apiURL.trace.material.outCrop, {
                    body: JSONBig.stringify(arg0),
                    method: 'POST',
                })
                return fetchObj
            }
        },
        driver: {
            getDriverList: async (): Promise<fetchAPIResType<Trace.Driver.getDriverListRes>> => {
                const fetchObj = await fetchAPI(baseURL + apiURL.trace.driver.driverList, {
                    method: 'GET',
                })
                return fetchObj
            },
            getTransportList: async (): Promise<fetchAPIResType<Trace.Driver.getTransportListRes>> => {
                const fetchObj = await fetchAPI(baseURL + apiURL.trace.driver.list, {
                    method: 'GET',
                })
                return fetchObj
            },
            startTransport: async (arg0: { id: number }): Promise<fetchAPIResType<baseRes>> => {
                const fetchObj = await fetchAPI(baseURL + apiURL.trace.driver.startTransport, {
                    body: JSONBig.stringify(arg0),
                    method: 'POST',
                })
                return fetchObj
            },
            finishTransport: (arg0: { id: number }): Promise<fetchAPIResType<baseRes>> => {
                return fetchAPI(baseURL + apiURL.trace.driver.finishTransport, {
                    body: JSONBig.stringify(arg0),
                    method: 'POST',
                })
            },
        },
        manufacturer: {
            getPlantList: (): Promise<fetchAPIResType<Trace.Manufacturer.getPlantListRes>> => {
                return fetchAPI(baseURL + apiURL.trace.manufacturer.list, {
                    method: 'GET'
                })
            },
            getTransportCompany: async (): Promise<fetchAPIResType<Trace.getTransportCompanyRes>> => {
                return fetchAPI(baseURL + apiURL.trace.manufacturer.companyList, {
                    method: 'GET',
                })
            },
            setPlantInfo: async (arg0: Trace.Manufacturer.setPlantInfoBody): Promise<fetchAPIResType<baseRes>> => {
                return fetchAPI(baseURL + apiURL.trace.manufacturer.setPlantInfo, {
                    body: JSONBig.stringify(arg0),
                    method: 'POST',
                })
            },
            outCrops: async (arg0: { cropsId: bigint }): Promise<fetchAPIResType<baseRes>> => {
                return fetchAPI(baseURL + apiURL.trace.manufacturer.outCrop, {
                    body: JSONBig.stringify(arg0),
                    method: 'POST',
                })
            },
        },
        retailer: {
            getPlantList: (): Promise<fetchAPIResType<Trace.Retailer.getPlantListRes>> => {
                return fetchAPI(baseURL + apiURL.trace.retailer.list, {
                    method: 'GET'
                })
            },
            receive: async (arg0: { id: number }): Promise<fetchAPIResType<baseRes>> => {
                return fetchAPI(baseURL + apiURL.trace.retailer.receive, {
                    body: JSONBig.stringify(arg0),
                    method: 'POST',
                })
            },
            getProdId: (arg0: { cropsId: bigint }): Promise<fetchAPIResType<Trace.Retailer.getProdIdRes>> => {
                return fetchAPI(getURLConstructor(baseURL + apiURL.trace.retailer.getProdId, arg0), {
                    method: 'GET'
                })
            },
        },
        user: {
            getTraceInfo: (arg0: { id: bigint }): Promise<fetchAPIResType<Trace.Retailer.prodInfo>> => {
                return fetchAPI(getURLConstructor(baseURL + apiURL.trace.user.getTraceInfo, arg0), {
                    method: 'GET'
                })
            },
            getProdInfo: (arg0: { id: bigint }): Promise<fetchAPIResType<Trace.User.getProdInfoRes>> => {
                return fetchAPI(getURLConstructor(baseURL + apiURL.trace.user.getProdInfo, arg0), {
                    method: 'GET'
                })
            },
        }
    },
    panel: {
        getPlantAreaOfPlots: (): Promise<fetchAPIResType<Panel.plantAreaOfPlotsRes>> => {
            return fetchAPI(baseURL + apiURL.panel.plantAreaOfPlots, {
                method: 'GET'
            })
        },
        getPlantArea: (): Promise<fetchAPIResType<Panel.plantAreaRes>> => {
            return fetchAPI(baseURL + apiURL.panel.plantArea, {
                method: 'GET'
            })
        },
        getSensorStatus: (): Promise<fetchAPIResType<Panel.sensorStatusRes>> => {
            return fetchAPI(baseURL + apiURL.panel.sensor, {
                method: 'GET'
            })
        },
        getDiseaseAlert: (): Promise<fetchAPIResType<Panel.diseaseAlertRes>> => {
            return fetchAPI(baseURL + apiURL.panel.alertCount, {
                method: 'GET'
            })
        },
        getDiseaseByTime: (): Promise<fetchAPIResType<Panel.diseaseByTimeRes>> => {
            return fetchAPI(baseURL + apiURL.panel.alertTimeCount, {
                method: 'GET'
            })
        },
    },
    shop: {
        getRecommend: (): Promise<fetchAPIResType<Shop.getRecommendRes>> => {
            return fetchAPI(baseURL + apiURL.shop.recommend, {
                method: 'GET',
            })
        },
        getRecommendDetail: (arg0: Shop.getRecDetailBody): Promise<fetchAPIResType<Shop.getRecDetailRes>> => {
            return fetchAPI(getURLConstructor(baseURL + apiURL.shop.productDetail, arg0), {
                method: 'GET',
            })
        },
        getReceiveAddress: (): Promise<fetchAPIResType<Shop.getReceiveAddressRes>> => {
            return fetchAPI(baseURL + apiURL.shop.receiveAddress, {
                method: 'GET',
            })
        },
        getAlipay: (arg0: Shop.getAlipayBody): Promise<string> => {
            const auth = localStorage.getItem('auth') as string | undefined; 3
            if (auth) {
                return fetch(getURLConstructor(baseURL + apiURL.shop.alipay, arg0), {
                    method: 'GET',
                    headers: new Headers({
                        'Authorization': auth,
                    })
                }).then(res => res.text())
            } else { return Promise.resolve('') }
        },
        submitOrder: async (arg0: Shop.submitOrderBody): Promise<fetchAPIResType<Shop.submitOrderRes>> => {
            return fetchAPI(baseURL + apiURL.shop.submitOrder, {
                body: JSONBig.stringify(arg0),
                method: 'POST',
            })
        },
        getOrderList: (): Promise<fetchAPIResType<Shop.getOrdersListRes>> => {
            return fetchAPI(baseURL + apiURL.shop.getOrderList, {
                method: 'GET',
            })
        },
        getOrderDetail: (arg0: { id: number }): Promise<fetchAPIResType<Shop.getOrderDetailRes>> => {
            return fetchAPI(getURLConstructor(baseURL + apiURL.shop.getOrderDetail, arg0), {
                method: 'GET',
            })
        },
        editAddress: async (arg0: { id: number, address: string }): Promise<fetchAPIResType<baseRes>> => {
            return fetchAPI(baseURL + apiURL.shop.editAddress, {
                body: JSONBig.stringify(arg0),
                method: 'POST',
            })
        },
        postProduct: async (arg0: { items: number[] }): Promise<fetchAPIResType<baseRes>> => {
            return fetchAPI(baseURL + apiURL.shop.postProduct, {
                body: JSONBig.stringify(arg0),
                method: 'POST',
            })
        },
    },
    goods: {
        getGoodsList: (): Promise<fetchAPIResType<Goods.getGoodsListRes>> => {
            return fetchAPI(baseURL + apiURL.goods.getGoodsList, {
                method: 'GET',
            })
        },
        switchStatus: async (arg0: { id: string, producesStatus: string }): Promise<fetchAPIResType<baseRes>> => {
            return fetchAPI(baseURL + apiURL.goods.switchStatus, {
                body: JSONBig.stringify(arg0),
                method: 'POST',
            })
        },
        deleteProduct: async (arg0: { items: number[] }): Promise<fetchAPIResType<baseRes>> => {
            return fetchAPI(baseURL + apiURL.goods.deleteProduct, {
                body: JSONBig.stringify(arg0),
                method: 'POST',
            })
        },
    },
    test
};
(window as any).SetAuth = {
    driver: () => localStorage.setItem('auth', 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJiaWJlciIsImVuZCI6IlYwVkNYMVZUUlZJPSIsImlkIjoiMiIsImV4cCI6MTY2NTU4Mjg2Mn0.3s7ERxJaq2D7dUC1Fnc-fAC-WOFhMkvn_o6eNyng1ME'),
    farmer: () => localStorage.setItem('auth', 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJiaWJlciIsImVuZCI6IlYwVkNYMVZUUlZJPSIsImlkIjoiMyIsImV4cCI6MTY2NTU4NDMxM30.V5BffVTSZPC7st0lHVyNzdLVSHQBpN41WkCMCBpSph8'),
    product: () => localStorage.setItem('auth', 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJiaWJlciIsImVuZCI6IlYwVkNYMVZUUlZJPSIsImlkIjoiNSIsImV4cCI6MTY2NTU4NDY1Nn0.2OYswqkiwb_DD5uCF5BfOsEC2MgGR4zOa_8g6P_usAg'),
    material: () => localStorage.setItem('auth', 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJiaWJlciIsImVuZCI6IlYwVkNYMVZUUlZJPSIsImlkIjoiNyIsImV4cCI6MTY2NTU4NDcyMH0.PQ8zdtCKceqTvEUrcfN4qkjpBwgAN9jU9pjLCtsTJHo'),
    retailer: () => localStorage.setItem('auth', 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJiaWJlciIsImVuZCI6IlYwVkNYMVZUUlZJPSIsImlkIjoiOCIsImV4cCI6MTY2NTc1NDU4OH0.HDALpWPMvFwTa9MsS9maKfn_-2UljmKZYxYJEa-5lvI'),
    tester: () => localStorage.setItem('auth', 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJiaWJlciIsImVuZCI6IlYwVkNYMVZUUlZJPSIsImlkIjoiMSIsImV4cCI6MTY2NjA5MzEwNH0.DgAlp5zDxRGthaWqROGeY4e-JlZdXjc-PdV7-xuUmRY'),
    test,
    fetchAPI,
}