package com.yaohy.intelligentfarmingbackend.pojo.dto.param;

import lombok.Data;

import java.util.List;

@Data
public class PlantAttributeParam {

    private Integer id;

    private List<attr> attrList;

    @Data
    public static class attr{
        private String name;
        private String description;
    }

}
