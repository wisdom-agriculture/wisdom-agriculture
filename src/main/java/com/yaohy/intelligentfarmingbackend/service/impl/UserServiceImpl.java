package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.constant.RoleConstant;
import com.yaohy.intelligentfarmingbackend.mapper.RoleMapper;
import com.yaohy.intelligentfarmingbackend.mapper.UserMapper;
import com.yaohy.intelligentfarmingbackend.mapper.UserRoleRelationMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Role;
import com.yaohy.intelligentfarmingbackend.pojo.domain.User;
import com.yaohy.intelligentfarmingbackend.pojo.domain.UserRelation;
import com.yaohy.intelligentfarmingbackend.pojo.domain.UserRoleRelation;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.TokenResult;
import com.yaohy.intelligentfarmingbackend.service.UserRelationService;
import com.yaohy.intelligentfarmingbackend.service.UserService;
import com.yaohy.intelligentfarmingbackend.util.EncryptUtil;
import com.yaohy.intelligentfarmingbackend.util.JwtUtil;
import com.yaohy.intelligentfarmingbackend.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;
    @Autowired
    private UserRelationService userRelationService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public synchronized CommonResult<TokenResult> webRegister(String phone, String password,String code) {
        if (!checkVerificationCode(phone,code)) return CommonResult.fail("验证失败");
        if (!checkIfNotExistsByPhoneAndRole(phone,password,RoleConstant.WEB_USER.getRole())) return CommonResult.fail("账户已存在");

        insertRelation(phone,RoleConstant.WEB_USER.getRole());

        return CommonResult.success("注册成功");
    }

    @Override
    public synchronized CommonResult<TokenResult> webLogin(String phone, String password) {

        return getTokenIfExistsByPhoneAndRole(phone,password,RoleConstant.WEB_USER.getRole(),RoleConstant.WEB_USER.getCode());
    }

    @Override
    public synchronized CommonResult<TokenResult> webDirectLogin(String phone, String code) {
        if (!checkVerificationCode(phone,code)) return CommonResult.fail("验证失败");
        if (checkIfNotExistsByPhoneAndRole(phone,RoleConstant.WEB_USER.getRole())) insertRelation(phone,RoleConstant.WEB_USER.getRole());

        return getTokenIfExistsByPhoneAndRole(phone, RoleConstant.WEB_USER.getRole(),RoleConstant.WEB_USER.getCode());
    }

    @Override
    public synchronized CommonResult<TokenResult> appRegister(String phone, String password,String code) {
        if (!checkVerificationCode(phone,code)) return CommonResult.fail("验证失败");
        if (!checkIfNotExistsByPhoneAndRole(phone,password,RoleConstant.APP_USER.getRole())) return CommonResult.fail("账户已存在");

        insertRelation(phone,RoleConstant.APP_USER.getRole());

        return CommonResult.success("注册成功");
    }

    @Override
    public synchronized CommonResult<TokenResult> appLogin(String phone, String password) {

        return getTokenIfExistsByPhoneAndRole(phone,password,RoleConstant.APP_USER.getRole(),RoleConstant.APP_USER.getCode());
    }

    @Override
    public synchronized CommonResult<TokenResult> appDirectLogin(String phone, String code) {
        if (!checkVerificationCode(phone,code)) return CommonResult.fail("验证失败");
        if (checkIfNotExistsByPhoneAndRole(phone,RoleConstant.APP_USER.getRole())) insertRelation(phone,RoleConstant.APP_USER.getRole());

        return getTokenIfExistsByPhoneAndRole(phone, RoleConstant.APP_USER.getRole(),RoleConstant.APP_USER.getCode());
    }

    @Override
    public synchronized CommonResult getVerificationCode(String phone) {
        Pattern phonePattern = Pattern.compile("^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$");
        boolean phoneMatchResult = phonePattern.matcher(phone).matches();
        if (!phoneMatchResult) return CommonResult.fail("请输入正确的手机号");

//        if (redisUtil.exists(phone)) return CommonResult.fail("验证码24h内有效，请勿重复获取验证码");
        if (redisUtil.exists(phone)) return CommonResult.fail("验证码24h内有效，请勿重复获取验证码,你现在的验证码为"+redisUtil.get(phone));

        Random random = new Random();
        StringBuilder verificationCode = new StringBuilder();

        for (int i=0;i<6;i++){
            verificationCode.append(random.nextInt(10));
        }

        String code = verificationCode.toString();
        redisUtil.set(phone,code,1L, TimeUnit.DAYS);
//        MessageUtil.send(phone,code);


        return CommonResult.success("获取验证码成功，验证码为"+code);
//        return CommonResult.success("获取验证码成功");
    }

    public synchronized Boolean checkIfNotExistsByPhoneAndRole(String phone,String password,String role){

        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("phone", phone));
        if (Objects.isNull(user)) {
            //用户压根不存在则进行插入
            user = new User();
            user.setPhone(phone);
            user.setPassword(EncryptUtil.md5Encrypt(password));
            baseMapper.insert(user);
            return true;
        }

        //用户存在 添加关系
        Role r = roleMapper.selectOne(new QueryWrapper<Role>().eq("role", role));
        if (Objects.isNull(r)) return false;

        UserRoleRelation userRoleRelation = userRoleRelationMapper.selectOne(new QueryWrapper<UserRoleRelation>().eq("user_id", user.getId()).eq("role_id", r.getId()));

        return userRoleRelation == null;
    }

    public synchronized Boolean checkIfNotExistsByPhoneAndRole(String phone,String role){

        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("phone", phone));
        if (Objects.isNull(user)) {
            //用户压根不存在则进行插入
            user = new User();
            user.setPhone(phone);
            baseMapper.insert(user);
            return true;
        }

        //用户存在 添加关系
        Role r = roleMapper.selectOne(new QueryWrapper<Role>().eq("role", role));
        if (Objects.isNull(r)) return false;

        UserRoleRelation userRoleRelation = userRoleRelationMapper.selectOne(new QueryWrapper<UserRoleRelation>().eq("user_id", user.getId()).eq("role_id", r.getId()));

        return userRoleRelation == null;
    }

    public synchronized CommonResult<TokenResult> getTokenIfExistsByPhoneAndRole(String phone, String password,String role,String code){
        //check role
        Role r = roleMapper.selectOne(new QueryWrapper<Role>().eq("role", role));
        if (Objects.isNull(r)) return CommonResult.fail("登陆失败");

        //check user
        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("phone", phone));
        if (Objects.isNull(user)) return CommonResult.fail("登陆失败");

        //check relation
        UserRoleRelation userRoleRelation = userRoleRelationMapper.selectOne(new QueryWrapper<UserRoleRelation>().eq("user_id", user.getId()).eq("role_id", r.getId()));
        if (Objects.isNull(userRoleRelation)) return CommonResult.fail("登陆失败");

        //check password
        if (!user.getPassword().equals(EncryptUtil.md5Encrypt(password))) return CommonResult.fail("密码错误");

        String token = JwtUtil.sign(user.getId().toString(),code);
        TokenResult tokenResult = new TokenResult();
        tokenResult.setToken(token);
        return CommonResult.success(tokenResult);
    }

    public synchronized CommonResult<TokenResult> getTokenIfExistsByPhoneAndRole(String phone, String role,String code){
        //check role
        Role r = roleMapper.selectOne(new QueryWrapper<Role>().eq("role", role));
        if (Objects.isNull(r)) return CommonResult.fail("登陆失败");

        //check user
        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("phone", phone));
        if (Objects.isNull(user)) return CommonResult.fail("登陆失败");

        //check relation
        UserRoleRelation userRoleRelation = userRoleRelationMapper.selectOne(new QueryWrapper<UserRoleRelation>().eq("user_id", user.getId()).eq("role_id", r.getId()));
        if (Objects.isNull(userRoleRelation)) return CommonResult.fail("登陆失败");

        String token = JwtUtil.sign(user.getId().toString(),code);
        TokenResult tokenResult = new TokenResult();
        tokenResult.setToken(token);

        return CommonResult.success(tokenResult);
    }

    public synchronized void insertRelation(String phone,String role){
        Role r = roleMapper.selectOne(new QueryWrapper<Role>().eq("role", role));
        if (Objects.isNull(r)) return;

        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("phone", phone));

        //insert new relation
        UserRoleRelation userRoleRelation = new UserRoleRelation();
        userRoleRelation.setUserId(user.getId());
        userRoleRelation.setRoleId(r.getId());
        userRoleRelationMapper.insert(userRoleRelation);

        insertRelationToSelf(user.getId());
    }

    public synchronized Boolean checkVerificationCode(String phone,String code){
        if (!redisUtil.exists(phone)) return false;

        String c = (String)redisUtil.get(phone);
//        if (Objects.equals(c,code)) redisUtil.remove(phone);


        return c.equals(code) ? redisUtil.remove(phone):false;
    }

    public void insertRelationToSelf(Integer userId){
        UserRelation userRelation = userRelationService.getOne(new QueryWrapper<UserRelation>().eq("from_id", userId).eq("to_id",userId));
        if (Objects.isNull(userRelation)) {
            userRelation = new UserRelation();
            userRelation.setFromId(userId);
            userRelation.setToId(userId);
            userRelationService.save(userRelation);
        }
    }


}
