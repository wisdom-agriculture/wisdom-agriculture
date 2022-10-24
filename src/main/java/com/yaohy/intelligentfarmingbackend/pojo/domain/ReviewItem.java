package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class ReviewItem {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer reviewId;

    private Integer fromId;

    private Integer toId;

    private String content;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private Integer isRead;

}
