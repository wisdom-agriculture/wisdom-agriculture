package com.yaohy.intelligentfarmingbackend.pojo.dto.param;

import lombok.Data;

import java.util.List;

@Data
public class CarSettlementParam {

    private String shopName;

    private List<ProducesList> producesListList;

    @Data
    public static class ProducesList{

        private Integer produceId;

        private String produceName;

        private String produceImage;

        private Double producePrice;

        private String producesPer;

        private Double produceWeight;

        private String freightPrice;

    }

}
