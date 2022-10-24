package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.List;

@Data
public class SensorDataListResult {

    private List<SensorList> sensorList;

    @Data
    public static class SensorList{

        private Integer id;
        private List<Object> sensorDataList;

    }

}
