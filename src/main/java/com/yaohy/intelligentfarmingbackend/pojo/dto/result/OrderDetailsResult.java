package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

@Data
public class OrderDetailsResult {

    private Integer id;

    private String orderNumber;

    private Integer producesId;

    private String producesName;

    private Double producesWeight;

    private Integer customerId;

    private String customerName;

    private String customerPhone;

    private String address;

    private String orderRemark;

    private Double amountPayable;

    private String paymentMethods;
}
