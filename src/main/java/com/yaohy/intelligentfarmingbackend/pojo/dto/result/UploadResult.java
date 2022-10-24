package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Valentine
 */
@Data
@ApiModel("返回图片url")
public class UploadResult {

    /**
     * 图片路径
     */
    @ApiModelProperty("图片url")
    private String uploadUrl;

    public UploadResult(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }
}
