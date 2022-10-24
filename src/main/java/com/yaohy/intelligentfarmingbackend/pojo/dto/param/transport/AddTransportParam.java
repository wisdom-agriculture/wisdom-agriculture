package com.yaohy.intelligentfarmingbackend.pojo.dto.param.transport;

import lombok.Data;

import java.sql.Time;

/**
 * @author Valentine
 */
@Data
public class AddTransportParam {
    private Integer id;
    private Long cropsId;
    private Integer driverId;
    private Integer deptId;
    private String remarks;
}

