package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.List;

@Data
public class CarSettlementProducesListResult {

    private String shopName;

    private List<ProducesList> producesList;


    @Data
    public static class ProducesList{
        private Integer producesId;
        private String producesName;
        private Double producesWeight;
        private String producesPer;
        private Double producesPrice;
        private String producesImage;
        private String freightPrice;

    }

}
