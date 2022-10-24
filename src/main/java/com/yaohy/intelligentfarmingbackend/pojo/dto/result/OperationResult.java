package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

@Data
public class OperationResult {

    private Integer id;
    private Integer fieldId;
    private String name;
    private String operator;
    private String phone;
    private Long time;
    private String content;
    private String remark;

}
