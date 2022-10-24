package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.List;

@Data
public class FieldPlantResult {

    private Integer id;
    private List<PlantResult> plantList;

}
