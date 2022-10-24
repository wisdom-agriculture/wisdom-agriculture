package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.List;

@Data
public class PayWaysResult {

    private List<PayWay> payWayList;

    @Data
    public static class PayWay{

        private Integer id;

        private String payWay;

        private Integer isDefault;
    }

}
