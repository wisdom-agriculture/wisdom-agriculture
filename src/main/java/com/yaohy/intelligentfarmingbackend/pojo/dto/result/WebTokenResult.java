package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import lombok.Data;
import org.apache.el.parser.Token;

/**
 * @author Valentine
 */
@Data
public class WebTokenResult {
    private String token;
    private String permission;

    public WebTokenResult(CommonResult<TokenResult> tokenResultCommonResult) {
        if(tokenResultCommonResult.getData() != null) {
            token = tokenResultCommonResult.getData().getToken();
        }
    }

    public WebTokenResult() {

    }
}
