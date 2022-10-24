package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import com.yaohy.intelligentfarmingbackend.pojo.domain.Plant;
import lombok.Data;

import java.util.List;

@Data
public class PlantListResult {

    private List<Plant> plantList;

}
