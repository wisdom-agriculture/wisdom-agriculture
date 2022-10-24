package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

/**
 * @author Valentine
 */
@Data
public class Transport {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Long cropsId;
    private Integer driverId;
    private Date time;
    private Integer farmerId;
    private String farmerNickname;
    private String farmerTel;
    private String farmerDeptName;
    private Integer deptId;
    private Integer status;
    private Integer outFactoryStatus;
    private Integer retailerReceiveStatus;
    private String remarks;
}
