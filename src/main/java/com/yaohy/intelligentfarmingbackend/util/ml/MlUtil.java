package com.yaohy.intelligentfarmingbackend.util.ml;

import com.yaohy.intelligentfarmingbackend.util.request.RequestUtils;
import com.yaohy.intelligentfarmingbackend.util.request.dto.Param;

public class MlUtil {

    private MlUtil(){}

    public static CropRecommendTo cropRecommend(String N,String P,String K,String temperature,String humidity,String ph,String rainfall){

        return RequestUtils.POST(
//                MlConstant.url + "/crop_pred/predict",
                MlConstant.url +"/crop_pred_ch/predict",
                new Param(
                        "N", N,
                        "P", P,
                        "K", K,
                        "temperature", temperature,
                        "humidity", humidity,
                        "ph", ph,
                        "rainfall", rainfall
                ), CropRecommendTo.class
        );

    }

}
