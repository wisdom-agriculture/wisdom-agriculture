package com.yaohy.intelligentfarmingbackend.pojo.dto.param;

import lombok.Data;

@Data
public class OperationParam {

    private String fieldId;
    private String operator;
    private String phone;
    private Long time;
    private String content;
    private String remark;

}
