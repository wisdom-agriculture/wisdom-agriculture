package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.Date;

/**
 * @author Valentine
 */
@Data
public class CropsVoResult {
    private Integer id;

    private Long cropsId;

    private String farmerNickname;

    private String farmerTel;

    private String farmerDeptName;

    private String cropsName;

    private Date time;

    private Integer machingStatus;

    private Integer outFactoryStatus;

    private Integer productWriteStatus;

    private Integer retailerReceiveStatus;

    private Long product_id;
}
