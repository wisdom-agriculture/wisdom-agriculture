package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SensorResult {

    private Integer id;
    private String type;
    private String objectId;

}
