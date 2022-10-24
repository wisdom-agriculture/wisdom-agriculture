package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;


@Data
public class DiseaseResult {

    private Integer id;
    private String fieldName;
    private String plant;
    private Long time;
    private Integer status;
    private String img;
    private String disease;

}
