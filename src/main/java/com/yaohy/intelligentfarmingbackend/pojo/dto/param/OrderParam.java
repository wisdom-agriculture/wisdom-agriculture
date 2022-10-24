package com.yaohy.intelligentfarmingbackend.pojo.dto.param;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.awt.*;
import java.util.List;

@Data
public class OrderParam {

    private String address;

    private Integer[] producesSKuId;

    private String orderRemark;

}
