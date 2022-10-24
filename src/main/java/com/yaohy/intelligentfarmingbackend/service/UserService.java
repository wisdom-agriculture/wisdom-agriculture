package com.yaohy.intelligentfarmingbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaohy.intelligentfarmingbackend.pojo.domain.User;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.TokenResult;

public interface UserService extends IService<User> {

    public CommonResult<TokenResult> webRegister(String phone, String password,String code);

    public CommonResult<TokenResult> webLogin(String phone,String password);

    public CommonResult<TokenResult> webDirectLogin(String phone,String code);

    public CommonResult<TokenResult> appRegister(String phone, String password,String code);

    public CommonResult<TokenResult> appLogin(String phone,String password);

    public CommonResult<TokenResult> appDirectLogin(String phone,String code);

    public CommonResult getVerificationCode(String phone);


}
