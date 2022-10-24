package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ProducesInfo {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String producesName;

    private String shopName;

    private String shoppingAddress;

    private String shoppingAddressRemark;

    private String producesDescription;

    private Integer producesCategory;

    private Integer producesStatus;

    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;


}
