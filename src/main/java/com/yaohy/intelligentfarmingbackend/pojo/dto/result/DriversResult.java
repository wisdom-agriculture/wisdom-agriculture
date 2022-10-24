package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

/**
 * @author Valentine
 */
@Data
public class DriversResult {
    private Integer id;
    private String nickname;
    private String signature;
    private String imgUrl;
    private String farm;
    private String phone;
    private Integer deptId;
}
