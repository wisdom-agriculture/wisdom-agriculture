package com.yaohy.intelligentfarmingbackend.pojo.dto.param;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class FieldParam {

    private Integer id;
    private String name;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer area;
    private List<String[]> location;
    private String description;
//    private Integer renterId;
//    private Integer bossId;
//    private Integer plantId;
    private Integer statusId;

}
