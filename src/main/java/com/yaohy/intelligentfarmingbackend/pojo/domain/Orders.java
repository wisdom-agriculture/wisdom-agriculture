package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Orders {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String orderNumber;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private Integer userId;

    private String userName;

    private String userPhone;

    private String address;

    private String orderRemark;

    private Double amountPayable;

    private String orderSource;

    private Integer orderStatus;

    private String logisticsCompany;

    private String logisticsNumber;

    private String paymentMethods;

    private Integer isPost;

    private Integer isPay;

    private Integer isReceipted;

    private String alipayNo;

    private Date payTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer state;

    private Integer shopId;

    private Integer producesCategory;
}

