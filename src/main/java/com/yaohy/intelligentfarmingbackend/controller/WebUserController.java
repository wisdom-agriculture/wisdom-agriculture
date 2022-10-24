package com.yaohy.intelligentfarmingbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yaohy.intelligentfarmingbackend.mapper.DeptMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Dept;
import com.yaohy.intelligentfarmingbackend.pojo.domain.User;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.UserParam;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.TokenResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.WebTokenResult;
import com.yaohy.intelligentfarmingbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

@RestController
@RequestMapping("/web")
public class WebUserController {

    @Autowired
    private UserService userService;
    @Resource
    private DeptMapper deptMapper;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResult<TokenResult> Register(@RequestBody UserParam param) {

        return userService.webRegister(param.getPhone(), param.getPassword(), param.getCode());
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult<WebTokenResult> Login(@RequestBody UserParam param) {
        WebTokenResult webTokenResult = null;
        if (Objects.nonNull(param.getCode())) {
            webTokenResult = new WebTokenResult(userService.webDirectLogin(param.getPhone(), param.getCode()));
        } else {
            webTokenResult = new WebTokenResult(userService.webLogin(param.getPhone(), param.getPassword()));
        }
        if(webTokenResult.getToken() == null) {
            return CommonResult.success(webTokenResult);
        }
        User user = userService.getOne(new QueryWrapper<User>().eq("phone", param.getPhone()));
        if (user == null) {
            return CommonResult.fail("用户不存在");
        }
        Integer deptId = user.getDeptId();

        if (deptId != null) {
            Dept dept = deptMapper.selectById(deptId);
            if (dept == null) {
                return CommonResult.fail("用户异常");
            }
            Integer parentId = dept.getParentId();
            String permission = null;
            if (deptId == 112) {
                //driver
                permission = "driver";
            } else if (parentId == 119) {
                //material
                permission = "material";
            } else if (parentId == 123) {
                //product
                permission = "product";
            } else if (parentId == 110) {
                //farmer
                permission = "farmer";
            } else if (parentId == 125) {
                //retailer
                permission = "retailer";
            }

            webTokenResult.setPermission(permission);
        }

        return CommonResult.success(webTokenResult);
    }

}
