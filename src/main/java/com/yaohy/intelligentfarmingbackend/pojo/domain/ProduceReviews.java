package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class ProduceReviews {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer produceId;

    private Integer userId;

    private String content;

    private Integer goodNumber;

    private Integer forwardNumber;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private Integer isRead;

}
