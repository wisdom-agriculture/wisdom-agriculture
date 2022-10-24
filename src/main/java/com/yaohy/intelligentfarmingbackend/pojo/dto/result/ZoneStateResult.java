package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import com.yaohy.intelligentfarmingbackend.pojo.to.ZoneStateTo;
import lombok.Data;

import java.util.List;

@Data
public class ZoneStateResult {

    private Long likeNum;
    private Long commentNum;
    private Long AtNum;
    private List<ZoneStateTo> zoneStateToList;

}
