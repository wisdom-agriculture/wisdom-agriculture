package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.List;

@Data
public class ProducesManagementListResult {

    private List<ProducesManagementResult> producesManagementResults;

    @Data
    public static class ProducesManagementResult{

        private Integer id;

        private String producesName;

        private Double producesPrice;

        private Integer producesStatus;

        private String CreateTime;
    }

}
