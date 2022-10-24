package com.yaohy.intelligentfarmingbackend.util.ml;

import lombok.Data;

@Data
public class CropRecommendTo {

    private String label;
    private String status;
    private Integer request_id;
    private String message;

}
