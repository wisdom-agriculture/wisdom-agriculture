package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.List;

@Data
public class CarSettlementResult {

    private String userName;

    private String userPhone;

    private String userShoppingAddress;

    private List<CarSettlementProducesListResult> carSettlementProducesListResults;

    private Double totalPrice;

}
