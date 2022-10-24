package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.List;

@Data
public class CarResult {

    private String shopName;
    private List<CarItemResult> carItemResults;

    @Data
    public static class CarItemResult{
        private Integer id;
        private Integer producesId;
        private Integer producesSkuId;
        private String producesName;
//        private String producesDescription;
//        private String producesSpecification;
        private Double producesWeight;
        private String producesPer;
        private Double producesPrice;
        private String producesImage;
    }

}
