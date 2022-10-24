package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FieldResult {

    private Integer id;
    private String name;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer area;
    private Integer userId;
    private Object location;
    private String description;
    private Integer renterId;
    private Integer statusId;
    private Integer isCropsStatus;
}
