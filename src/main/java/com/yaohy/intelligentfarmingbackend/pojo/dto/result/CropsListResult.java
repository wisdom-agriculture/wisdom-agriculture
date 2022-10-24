package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.Date;

/**
 * @author Valentine
 */
@Data
public class CropsListResult {
    private Integer id;
    private Integer fieldId;
    private Integer plantId;
    private Integer plantStatusId;
    private Integer area;
    private Integer userId;
    private long cropsId;
    private String cropsName;
    private Integer status;
    private Integer machineStatus;
    private Integer outFactoryStatus;
    private Integer productWriteStatus;
    private Date time;
    private String userName;
    private String deptName;
}
