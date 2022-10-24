package com.yaohy.intelligentfarmingbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.App;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.Web;
import com.yaohy.intelligentfarmingbackend.pojo.domain.*;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.UserFanListResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.UserFanResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.UserResult;
import com.yaohy.intelligentfarmingbackend.service.*;
import com.yaohy.intelligentfarmingbackend.util.JwtUtil;
import com.yaohy.intelligentfarmingbackend.util.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRelationService userRelationService;
    @Autowired
    private ZoneService zoneService;
    @Autowired
    private ZoneLikeService zoneLikeService;
    @Autowired
    private FieldService fieldService;
    @Autowired
    private FieldPlantRelationService fieldPlantRelationService;

    @Autowired
    private HttpServletRequest request;

    @App
    @Web
    @RequestMapping(value = "/users/details", method = RequestMethod.POST)
    public CommonResult update(HttpServletRequest request,
                               @RequestParam(value = "nickname") String nickname,
                               @RequestParam(value = "signature") String signature,
                               @RequestParam(value = "farm") String farm,
                               @RequestParam(value = "phone") String phone) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));
        User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));
        user.setNickname(nickname);
        user.setSignature(signature);

//        MinioUtil.deleteJpg(name);

        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest mulReq = (MultipartHttpServletRequest) request;
            MultiValueMap<String, MultipartFile> multiFileMap = mulReq.getMultiFileMap();
            if (!multiFileMap.isEmpty()) {
                MultipartFile[] imgs = new MultipartFile[multiFileMap.size()];

                Set<String> keySet = multiFileMap.keySet();
                int i = 0;
                for (String s : keySet) {
                    imgs[i++] = multiFileMap.get(s).get(0);
                }
                String name = "/user/img/" + userId + ".jpg";
                MinioUtil.uploadJpg(imgs[0], name);
                user.setImgUrl("https://image.yaohy.cn/intelligent-farming" + name);
            }

        }

        user.setFarm(farm);
        user.setPhone(phone);

        userService.updateById(user);
        return CommonResult.success();
    }

    @App
    @Web
    @RequestMapping(value = "/users/details", method = RequestMethod.GET)
    public CommonResult get(@RequestParam(value = "id", required = false) Integer id) {
        Integer userId = Objects.nonNull(id) ? id : JwtUtil.decodeId(request.getHeader("Authorization"));

        UserResult userResult = new UserResult();
        User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));


        userResult.setId(userId);
        userResult.setNickname(user.getNickname());
        userResult.setSignature(user.getSignature());
        userResult.setPhone(user.getPhone());
        userResult.setFarm(user.getFarm());
        userResult.setImgUrl(user.getImgUrl());

        userResult.setSubscribeNum(Math.toIntExact(userRelationService.count(new QueryWrapper<UserRelation>().eq("from_id", userId))));
        userResult.setFanNum(Math.toIntExact(userRelationService.count(new QueryWrapper<UserRelation>().eq("to_id", userId))));

        List<Integer> zoneIdList = zoneService.list(new QueryWrapper<Zone>().eq("user_id", userId))
                .stream().map(Zone::getId).collect(Collectors.toList());
        userResult.setGetLikeNum(
                zoneIdList.isEmpty() ? 0 :
                        Math.toIntExact(
                                zoneLikeService.count(
                                        new QueryWrapper<ZoneLike>()
                                                .in("zone_id", zoneIdList))));

        List<Field> fieldList = fieldService.list(new QueryWrapper<Field>().eq("user_id", userId));
        List<Integer> fieldIdList = fieldList.stream().map(Field::getId).collect(Collectors.toList());

        List<FieldPlantRelation> fieldPlantRelationList = new ArrayList<>();
        if (fieldList.size() != 0){
            fieldPlantRelationList = fieldPlantRelationService.list(
                    new QueryWrapper<FieldPlantRelation>()
                            .eq("plant_status_id", 0).in("field_id", fieldIdList));
        }


        Integer areaSum = 0;
        for (Field field:fieldList){
            areaSum += field.getArea();
        }

        Integer areaNowSum = 0;
        for (FieldPlantRelation fieldPlantRelation:fieldPlantRelationList){
            areaNowSum += fieldPlantRelation.getArea();
        }

        userResult.setUsagePercentage(new Double((areaNowSum*1.0/areaSum)*100).intValue());
        userResult.setAreaSum(areaSum);
        userResult.setAreaNowSum(areaNowSum);
        userResult.setPlantAccount(fieldPlantRelationList.size());

        return CommonResult.success(userResult);
    }

    @App
    @Web
    @RequestMapping(value = "/users/subscribes", method = RequestMethod.PUT)
    public CommonResult subscribe(@RequestParam("id") Integer id) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        if (Objects.nonNull(userRelationService.getOne(new QueryWrapper<UserRelation>().eq("from_id", userId).eq("to_id", id))))
            return CommonResult.fail("请勿重复关注");
        UserRelation userRelation = new UserRelation();
        userRelation.setFromId(userId);
        userRelation.setToId(id);
        userRelationService.save(userRelation);

        return CommonResult.success();
    }

    @App
    @Web
    @RequestMapping(value = "/users/subscribes", method = RequestMethod.DELETE)
    public CommonResult remove(@RequestParam("id") Integer id) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));
        if (Objects.isNull(userRelationService.getOne(new QueryWrapper<UserRelation>().eq("from_id", userId).eq("to_id", id))))
            return CommonResult.fail("请先关注");
        userRelationService.remove(new QueryWrapper<UserRelation>().eq("from_id", userId).eq("to_id", id));


        return CommonResult.success();
    }

    @App
    @Web
    @RequestMapping(value = "/users/subscribes", method = RequestMethod.GET)
    public CommonResult getSubscribes(@RequestParam(value = "id", required = false) Integer id) {
        Integer userId = Objects.nonNull(id) ? id : JwtUtil.decodeId(request.getHeader("Authorization"));

        List<UserRelation> userRelationList = userRelationService.list(new QueryWrapper<UserRelation>().eq("to_id", userId).or().eq("from_id", userId));

        List<UserFanResult> userFanResultList = userRelationList.stream().filter(
                userRelation -> userRelation.getFromId().equals(userId)
        ).map(
                userRelation -> {
                    UserFanResult userFanResult = new UserFanResult();
                    User user = userService.getOne(new QueryWrapper<User>().eq("id", userRelation.getToId()));
                    userFanResult.setId(user.getId());
                    userFanResult.setNickname(user.getNickname());
                    userFanResult.setSignature(user.getSignature());
                    userFanResult.setImgUrl(user.getImgUrl());
                    userFanResult.setPhone(user.getPhone());
                    userFanResult.setIsMutual((userRelationList.stream().anyMatch(userR -> Objects.equals(userR.getToId(), userId) && Objects.equals(userR.getToId(), user.getId()))));
                    return userFanResult;
                }
        ).collect(Collectors.toList());

        UserFanListResult userFanListResult = new UserFanListResult();
        userFanListResult.setFanList(userFanResultList);


        return CommonResult.success(userFanListResult);
    }

    @App
    @Web
    @RequestMapping(value = "/users/fans", method = RequestMethod.GET)
    public CommonResult getFans(@RequestParam(value = "id", required = false) Integer id) {
        Integer userId = Objects.nonNull(id) ? id : JwtUtil.decodeId(request.getHeader("Authorization"));

        List<UserRelation> userRelationList = userRelationService.list(new QueryWrapper<UserRelation>().eq("to_id", userId).or().eq("from_id", userId));

        List<UserFanResult> userFanResultList = userRelationList.stream().filter(
                userRelation -> userRelation.getToId().equals(userId)
        ).map(
                userRelation -> {
                    UserFanResult userFanResult = new UserFanResult();
                    User user = userService.getOne(new QueryWrapper<User>().eq("id", userRelation.getFromId()));
                    userFanResult.setId(user.getId());
                    userFanResult.setNickname(user.getNickname());
                    userFanResult.setSignature(user.getSignature());
                    userFanResult.setImgUrl(user.getImgUrl());
                    userFanResult.setPhone(user.getPhone());
                    userFanResult.setIsMutual((userRelationList.stream()
                            .filter(userR -> userR.getFromId() == userId && userR.getToId() == user.getId()).count() != 0));
                    return userFanResult;
                }
        ).collect(Collectors.toList());

        UserFanListResult userFanListResult = new UserFanListResult();
        userFanListResult.setFanList(userFanResultList);


        return CommonResult.success(userFanListResult);
    }

}
