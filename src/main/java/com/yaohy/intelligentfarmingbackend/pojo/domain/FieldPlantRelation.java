package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class FieldPlantRelation {

    @TableId(type = IdType.AUTO)
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
}
