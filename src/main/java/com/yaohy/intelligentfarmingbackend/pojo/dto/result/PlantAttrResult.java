package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import com.yaohy.intelligentfarmingbackend.pojo.domain.PlantAttribute;
import lombok.Data;

import java.util.List;

@Data
public class PlantAttrResult {

    private Integer id;
    private String name;
    private String img;

    private List<PlantAttribute> attrList;

}
