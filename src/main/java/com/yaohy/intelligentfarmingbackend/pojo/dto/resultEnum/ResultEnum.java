package com.yaohy.intelligentfarmingbackend.pojo.dto.resultEnum;
import lombok.Getter;

/**
 * API操作码
 */
@Getter
public enum ResultEnum {
    SUCCESS("00000", "请求正常"),
    FAILED("A0400", "请求失败");

    private String code;
    private String message;

    private ResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
