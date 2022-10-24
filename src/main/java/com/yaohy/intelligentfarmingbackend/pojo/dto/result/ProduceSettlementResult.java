package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.List;

@Data
public class ProduceSettlementResult {

    private String userName;

    private String userPhone;

    private String userShoppingAddress;

    private String shopName;

    private Double totalPrice;

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
