package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import com.yaohy.intelligentfarmingbackend.pojo.domain.ProducesImages;
import lombok.Data;

import java.util.List;

@Data
public class EquipmentHomeResult {

    private List<String> swiperImages;

    private List<Equipments> equipmentsList;

    @Data
    public static class Equipments{

        private Integer producesId;
        private String producesName;
        private String producesImage;
        private Double producesPrice;

    }
}
