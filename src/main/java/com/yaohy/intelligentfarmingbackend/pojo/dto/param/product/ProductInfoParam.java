package com.yaohy.intelligentfarmingbackend.pojo.dto.param.product;

import lombok.Data;

/**
 * @author Valentine
 */
@Data
public class ProductInfoParam {
    private Integer id;
    private String productName;
    private String ingredients;
    private String leader;
    private String phone;
    private String workshop;
    private String hour;
    private String storageMethod;
    private String netContent;
    private String recommended;
    private String remark;
}
