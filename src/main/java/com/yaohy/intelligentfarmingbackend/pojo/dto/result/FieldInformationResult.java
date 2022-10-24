package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Valentine
 */
@Data
public class FieldInformationResult {
    private Integer id;
    private String name;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer area;
    private String location;
    private String description;
    private Integer userId;
    private Integer renterId;
    private Integer statusId;
    private String deptName;
    private Date time;
    private Long cropsId;
    private String cropsName;
    private String image;
}
