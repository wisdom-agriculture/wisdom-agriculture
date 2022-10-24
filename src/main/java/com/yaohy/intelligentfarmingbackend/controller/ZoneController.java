package com.yaohy.intelligentfarmingbackend.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.App;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.Web;
import com.yaohy.intelligentfarmingbackend.pojo.domain.*;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.ZoneCommentParam;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.*;
import com.yaohy.intelligentfarmingbackend.pojo.to.ZoneStateTo;
import com.yaohy.intelligentfarmingbackend.service.*;
import com.yaohy.intelligentfarmingbackend.util.JwtUtil;
import com.yaohy.intelligentfarmingbackend.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class ZoneController {

    @Autowired
    private ZoneService zoneService;
    @Autowired
    private ZoneImageService zoneImageService;
    @Autowired
    private ZoneLikeService zoneLikeService;
    @Autowired
    private ZoneCommentService zoneCommentService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRelationService userRelationService;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisUtil redisUtil;

    @App
    @Web
    @RequestMapping(value = "/zones", method = RequestMethod.POST)
    public CommonResult upload(
            HttpServletRequest request,
            @RequestParam("content") String content
//                                MultipartFile[] files
//                               @RequestParam(value = "imgs") MultipartFile[] imgs
//                               @RequestParam(value = "img0", required = false) MultipartFile img0,
//                               @RequestParam(value = "img1", required = false) MultipartFile img1,
//                               @RequestParam(value = "img2", required = false) MultipartFile img2,
//                               @RequestParam(value = "img3", required = false) MultipartFile img3,
//                               @RequestParam(value = "img4", required = false) MultipartFile img4,
//                               @RequestParam(value = "img5", required = false) MultipartFile img5,
//                               @RequestParam(value = "img6", required = false) MultipartFile img6,
//                               @RequestParam(value = "img7", required = false) MultipartFile img7,
//                               @RequestParam(value = "img8", required = false) MultipartFile img8
    ) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));
        //save zone
        Zone zone = new Zone();
        zone.setUserId(userId);
        zone.setContent(content);
        zone.setTime(new Date().getTime());
        zoneService.save(zone);

        Integer zoneId = zone.getId();
//        zoneImageService.save(zoneId,files);
//        zoneImageService.save(zoneId, img0, img1, img2, img3, img4, img5, img6, img7, img8);

        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest mulReq = (MultipartHttpServletRequest) request;
            MultiValueMap<String, MultipartFile> multiFileMap = mulReq.getMultiFileMap();
            MultipartFile[] imgs = new MultipartFile[multiFileMap.size()];

            Set<String> keySet = multiFileMap.keySet();
            int i = 0;
            for (String s : keySet) {
                imgs[i++] = multiFileMap.get(s).get(0);
            }

            zoneImageService.save(zoneId, imgs);
        }

        return CommonResult.success();
    }

    @App
    @Web
    @RequestMapping(value = "/zones",method = RequestMethod.DELETE)
    public CommonResult remove(@RequestParam("id") Integer id){
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));
        Zone zone = zoneService.getById(id);
        if (Objects.isNull(zone)) return CommonResult.fail("该博客不存在");
        if (!zone.getUserId().equals(userId)) return CommonResult.fail("请勿操作不属于自己的博客");

        zoneService.removeById(id);
        return CommonResult.success();
    }

    @App
    @Web
    @RequestMapping(value = "/zones", method = RequestMethod.GET)
    public CommonResult getAll(@RequestParam(value = "userId",required = false) Integer userid,
                               @RequestParam(value = "id",required = false) Integer zoneid) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));
        List<Zone> zoneList;
        if (Objects.nonNull(userid))zoneList = zoneService.list(new QueryWrapper<Zone>().eq("user_id",userid));
        else if (Objects.nonNull(zoneid)) zoneList = zoneService.list(new QueryWrapper<Zone>().eq("id",zoneid));
        else zoneList = zoneService.list();


        List<ZoneImage> zoneImageList = zoneImageService.list();
        List<ZoneLike> zoneLikeList = zoneLikeService.list();
        List<ZoneComment> zoneCommentList = zoneCommentService.list();
        Map<Integer, User> userMap = userService.list().stream().collect(Collectors.toMap(User::getId, Function.identity()));
        Map<Integer, Integer> userRelationMap = userRelationService.list(new QueryWrapper<UserRelation>().eq("from_id", userId))
                .stream().collect(Collectors.toMap(UserRelation::getToId, UserRelation::getFromId));

        Set<Integer> zoneLikeIdSet = zoneLikeList.stream().filter(zoneLike -> Objects.equals(zoneLike.getFromId(), userId))
                .map(ZoneLike::getZoneId).collect(Collectors.toSet());

        Map<Integer, Long> zoneLikeNumMap = zoneList.stream().collect(Collectors.toMap(Zone::getId, zone -> {
            Integer zoneId = zone.getId();
            return zoneLikeList.stream().filter(zoneLike -> zoneLike.getZoneId().equals(zoneId)).count();
        }));

        Map<Integer, Long> zoneCommentNumMap = zoneList.stream().collect(Collectors.toMap(Zone::getId, zone -> {
            Integer zoneId = zone.getId();
            return zoneCommentList.stream().filter(zoneComment -> zoneComment.getZoneId().equals(zoneId)).count();
        }));

        List<ZoneResult> zones = zoneList.stream().map(zone -> {
            ZoneResult zoneResult = new ZoneResult();
            zoneResult.setId(zone.getId());
            zoneResult.setUserId(zone.getUserId());

            User user = userMap.get(zone.getUserId());
            zoneResult.setNickname(user.getNickname());
            zoneResult.setImgUrl(user.getImgUrl());
            zoneResult.setIsSubscribed(userRelationMap.containsKey(user.getId()));

            zoneResult.setContent(zone.getContent());
            zoneResult.setTime(zone.getTime());
            zoneResult.setIsLike(zoneLikeIdSet.contains(zone.getId()));

            zoneResult.setLikeNum(Math.toIntExact(zoneLikeNumMap.get(zone.getId())));
            zoneResult.setCommentNum(Math.toIntExact(zoneCommentNumMap.get(zone.getId())));

            List<String> zoneImgList = zoneImageList.stream().filter(
                    zoneImage -> zoneImage.getZoneId().equals(zone.getId())
            ).map(ZoneImage::getUrl).collect(Collectors.toList());

            String[] zoneImgs = new String[zoneImgList.size()];
            zoneImgList.toArray(zoneImgs);
            zoneResult.setImgs(zoneImgs);

            return zoneResult;
        }).collect(Collectors.toList());

        ZoneListResult zoneListResult = new ZoneListResult();
        zoneListResult.setZoneList(zones);

        return CommonResult.success(zoneListResult);
    }

    @App
    @Web
    @RequestMapping(value = "/zones/filter/likes", method = RequestMethod.GET)
    public CommonResult getLikes() {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));
        List<Zone> zoneList = zoneService.list();
        List<ZoneImage> zoneImageList = zoneImageService.list();
        List<ZoneLike> zoneLikeList = zoneLikeService.list();
        List<ZoneComment> zoneCommentList = zoneCommentService.list();
        Map<Integer, User> userMap = userService.list().stream().collect(Collectors.toMap(User::getId, Function.identity()));
        Map<Integer, Integer> userRelationMap = userRelationService.list(new QueryWrapper<UserRelation>().eq("from_id", userId))
                .stream().collect(Collectors.toMap(UserRelation::getToId, UserRelation::getFromId));

        Set<Integer> zoneLikeIdSet = zoneLikeList.stream().filter(zoneLike -> Objects.equals(zoneLike.getFromId(), userId))
                .map(ZoneLike::getZoneId).collect(Collectors.toSet());

        Map<Integer, Long> zoneLikeNumMap = zoneList.stream().collect(Collectors.toMap(Zone::getId, zone -> {
            Integer zoneId = zone.getId();
            return zoneLikeList.stream().filter(zoneLike -> zoneLike.getZoneId().equals(zoneId)).count();
        }));

        Map<Integer, Long> zoneCommentNumMap = zoneList.stream().collect(Collectors.toMap(Zone::getId, zone -> {
            Integer zoneId = zone.getId();
            return zoneCommentList.stream().filter(zoneComment -> zoneComment.getZoneId().equals(zoneId)).count();
        }));

        List<ZoneResult> zones = zoneList.stream()
                .filter(zone -> zoneLikeIdSet.contains(zone.getId()))
                .map(zone -> {
            ZoneResult zoneResult = new ZoneResult();
            zoneResult.setId(zone.getId());
            zoneResult.setUserId(zone.getUserId());

            User user = userMap.get(zone.getUserId());
            zoneResult.setNickname(user.getNickname());
            zoneResult.setImgUrl(user.getImgUrl());
            zoneResult.setIsSubscribed(userRelationMap.containsKey(user.getId()));

            zoneResult.setContent(zone.getContent());
            zoneResult.setTime(zone.getTime());
            zoneResult.setIsLike(true);

            zoneResult.setLikeNum(Math.toIntExact(zoneLikeNumMap.get(zone.getId())));
            zoneResult.setCommentNum(Math.toIntExact(zoneCommentNumMap.get(zone.getId())));

            List<String> zoneImgList = zoneImageList.stream().filter(
                    zoneImage -> zoneImage.getZoneId().equals(zone.getId())
            ).map(ZoneImage::getUrl).collect(Collectors.toList());

            String[] zoneImgs = new String[zoneImgList.size()];
            zoneImgList.toArray(zoneImgs);
            zoneResult.setImgs(zoneImgs);

            return zoneResult;
        }).collect(Collectors.toList());

        ZoneListResult zoneListResult = new ZoneListResult();
        zoneListResult.setZoneList(zones);

        return CommonResult.success(zoneListResult);
    }

    @App
    @Web
    @RequestMapping(value = "/zones/filter/subscribes", method = RequestMethod.GET)
    public CommonResult getSubscribes() {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));
        List<Zone> zoneList = zoneService.list();
        List<ZoneImage> zoneImageList = zoneImageService.list();
        List<ZoneLike> zoneLikeList = zoneLikeService.list();
        List<ZoneComment> zoneCommentList = zoneCommentService.list();
        Map<Integer, User> userMap = userService.list().stream().collect(Collectors.toMap(User::getId, Function.identity()));
        Map<Integer, Integer> userRelationMap = userRelationService.list(new QueryWrapper<UserRelation>().eq("from_id", userId))
                .stream().collect(Collectors.toMap(UserRelation::getToId, UserRelation::getFromId));

        Set<Integer> zoneLikeSet = zoneLikeList.stream().filter(zoneLike -> Objects.equals(zoneLike.getFromId(), userId))
                .map(ZoneLike::getZoneId).collect(Collectors.toSet());

        Map<Integer, Long> zoneLikeNumMap = zoneList.stream().collect(Collectors.toMap(Zone::getId, zone -> {
            Integer zoneId = zone.getId();
            return zoneLikeList.stream().filter(zoneLike -> zoneLike.getZoneId().equals(zoneId)).count();
        }));

        Map<Integer, Long> zoneCommentNumMap = zoneList.stream().collect(Collectors.toMap(Zone::getId, zone -> {
            Integer zoneId = zone.getId();
            return zoneCommentList.stream().filter(zoneComment -> zoneComment.getZoneId().equals(zoneId)).count();
        }));

        List<ZoneResult> zones = zoneList.stream()
                .map(zone -> {
                    ZoneResult zoneResult = new ZoneResult();
                    zoneResult.setId(zone.getId());
                    zoneResult.setUserId(zone.getUserId());

                    User user = userMap.get(zone.getUserId());
                    zoneResult.setNickname(user.getNickname());
                    zoneResult.setImgUrl(user.getImgUrl());
                    zoneResult.setIsSubscribed(userRelationMap.containsKey(user.getId()));

                    zoneResult.setContent(zone.getContent());
                    zoneResult.setTime(zone.getTime());
                    zoneResult.setIsLike(zoneLikeSet.contains(zone.getId()));

                    zoneResult.setLikeNum(Math.toIntExact(zoneLikeNumMap.get(zone.getId())));
                    zoneResult.setCommentNum(Math.toIntExact(zoneCommentNumMap.get(zone.getId())));

                    List<String> zoneImgList = zoneImageList.stream().filter(
                            zoneImage -> zoneImage.getZoneId().equals(zone.getId())
                    ).map(ZoneImage::getUrl).collect(Collectors.toList());

                    String[] zoneImgs = new String[zoneImgList.size()];
                    zoneImgList.toArray(zoneImgs);
                    zoneResult.setImgs(zoneImgs);

                    return zoneResult;
                }).filter(ZoneResult::getIsSubscribed).collect(Collectors.toList());

        ZoneListResult zoneListResult = new ZoneListResult();
        zoneListResult.setZoneList(zones);

        return CommonResult.success(zoneListResult);
    }

    @App
    @Web
    @RequestMapping(value = "/zones/likes", method = RequestMethod.GET)
    public CommonResult setLike(@RequestParam("id") Integer id) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));
        ZoneLike zoneLike = zoneLikeService.getOne(new QueryWrapper<ZoneLike>().eq("zone_id", id).eq("from_id", userId));
        if (Objects.nonNull(zoneLike)) return CommonResult.fail("请勿重复点赞");

        zoneLike = new ZoneLike();
        zoneLike.setZoneId(id);
        zoneLike.setFromId(userId);
        zoneLikeService.save(zoneLike);

        User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));
        Zone zone = zoneService.getOne(new QueryWrapper<Zone>().eq("id", id));

        ZoneStateTo zoneStateTo = new ZoneStateTo();
        zoneStateTo.setUserId(user.getId());
        zoneStateTo.setNickname(user.getNickname());
        zoneStateTo.setImgUrl(user.getImgUrl());
        zoneStateTo.setId(zone.getId());
        zoneStateTo.setContent(zone.getContent());
        zoneStateTo.setMsg("点赞了这条圈子");

        ZoneImage zoneImage = zoneImageService.getOne(new QueryWrapper<ZoneImage>().eq("zone_id", id).orderByAsc("id")
                .last("limit 1"));

        zoneStateTo.setImg(Objects.nonNull(zoneImage)? zoneImage.getUrl():null);

        zoneStateTo.setTime(new Date().getTime());
        redisUtil.sadd("zl_"+zone.getUserId(), JSONUtil.toJsonStr(zoneStateTo));


        return CommonResult.success("true");
    }

    @App
    @Web
    @RequestMapping(value = "/zones/likes", method = RequestMethod.DELETE)
    public CommonResult cancelLike(@RequestParam("id") Integer id) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));
        if (Objects.isNull(zoneLikeService.getOne(new QueryWrapper<ZoneLike>().eq("zone_id", id).eq("from_id", userId))))
            return CommonResult.fail("尚未点赞");
        zoneLikeService.remove(new QueryWrapper<ZoneLike>().eq("zone_id", id).eq("from_id", userId));

        return CommonResult.success("true");
    }

    @App
    @Web
    @RequestMapping(value = "/zones/comments", method = RequestMethod.POST)
    public CommonResult addComment(@RequestBody ZoneCommentParam param) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        Long time = new Date().getTime();

        ZoneComment zoneComment = new ZoneComment();
        zoneComment.setZoneId(param.getZoneId());
        zoneComment.setFromId(userId);
        zoneComment.setParentId(Objects.nonNull(param.getParentId()) ? param.getParentId() : 0);
        zoneComment.setContent(param.getContent());
        zoneComment.setTime(time);
        zoneCommentService.save(zoneComment);

        User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));
        Zone zone = zoneService.getOne(new QueryWrapper<Zone>().eq("id", param.getZoneId()));
        ZoneStateTo zoneStateTo = new ZoneStateTo();
        zoneStateTo.setUserId(user.getId());
        zoneStateTo.setNickname(user.getNickname());
        zoneStateTo.setImgUrl(user.getImgUrl());
        zoneStateTo.setId(zone.getId());
        zoneStateTo.setContent(zone.getContent());
        zoneStateTo.setMsg(param.getContent());

        ZoneImage zoneImage = zoneImageService.getOne(new QueryWrapper<ZoneImage>().eq("zone_id", param.getZoneId()).orderByAsc("id")
                .last("limit 1"));

        zoneStateTo.setImg(Objects.nonNull(zoneImage)? zoneImage.getUrl():null);
        zoneStateTo.setTime(time);
        redisUtil.sadd("zc_"+zone.getUserId(), JSONUtil.toJsonStr(zoneStateTo));


        return CommonResult.success();
    }

    @App
    @Web
    @RequestMapping(value = "/zones/comments", method = RequestMethod.GET)
    public CommonResult getComment(@RequestParam("id") Integer id) {
        ZoneCommentListResult zoneCommentListResult = new ZoneCommentListResult();
        zoneCommentListResult.setCommentList(zoneCommentService.list(new QueryWrapper<ZoneComment>().eq("zone_id", id))
                .stream()
                .filter(zoneComment -> zoneComment.getParentId().equals(0))
                .map(zoneComment -> getZoneCommentResult(zoneComment.getId()))
                .collect(Collectors.toList()));

        return CommonResult.success(zoneCommentListResult);
    }

    private ZoneCommentResult getZoneCommentResult(Integer id) {
        ZoneComment zoneComment = zoneCommentService.getById(id);
        User user = userService.getById(zoneComment.getFromId());
        List<ZoneComment> zoneCommentList = zoneCommentService.list(new QueryWrapper<ZoneComment>().eq("parent_id", zoneComment.getId()));


        ZoneCommentResult zoneCommentResult = new ZoneCommentResult();
        zoneCommentResult.setId(id);
        zoneCommentResult.setUserId(user.getId());
        zoneCommentResult.setNickname(user.getNickname());
        zoneCommentResult.setImgUrl(user.getImgUrl());
        zoneCommentResult.setContent(zoneComment.getContent());
        zoneCommentResult.setTime(zoneComment.getTime());
        if (!zoneCommentList.isEmpty())
            zoneCommentResult.setCommentList(zoneCommentList.stream().map(zc -> getZoneCommentResult(zc.getId())).collect(Collectors.toList()));

        return zoneCommentResult;
    }

    @App
    @Web
    @RequestMapping(value = "/zones/comments", method = RequestMethod.DELETE)
    public CommonResult delComment(@RequestParam("id") Integer id) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));
        ZoneComment zoneComment = zoneCommentService.getById(id);
        if (!zoneComment.getFromId().equals(userId)) return CommonResult.fail();
        delZoneCommentResult(id);

        return CommonResult.success();
    }

    private void delZoneCommentResult(Integer id) {
        List<ZoneComment> zoneCommentList = zoneCommentService.list(new QueryWrapper<ZoneComment>().eq("parent_id", id));
        zoneCommentService.removeById(id);
        if (zoneCommentList.isEmpty()) return;
        zoneCommentList.stream().forEach(zoneComment -> delZoneCommentResult(zoneComment.getId()));
    }

    @App
    @Web
    @RequestMapping(value = "/zones/states",method = RequestMethod.GET)
    public CommonResult zoneState(@RequestParam("md") Integer mode){
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));
        System.out.println(userId);
        ZoneStateResult zoneStateResult = new ZoneStateResult();
        zoneStateResult.setLikeNum(redisUtil.ssize("zl_"+userId));
        zoneStateResult.setCommentNum(redisUtil.ssize("zc_"+userId));
        zoneStateResult.setAtNum(0L);

        List<ZoneStateTo> zoneStateToList = new ArrayList<>();
        if (mode == 0){
            zoneStateToList.addAll(
                    redisUtil.spopAll("zl_"+userId).stream()
                            .map(s -> JSONUtil.toBean(s, ZoneStateTo.class))
                            .collect(Collectors.toList()));
        }
        if (mode == 1) {
            zoneStateToList.addAll(
                    redisUtil.spopAll("zc_"+userId).stream()
                            .map(s -> JSONUtil.toBean(s, ZoneStateTo.class))
                            .collect(Collectors.toList()));
            Collections.sort(zoneStateToList);
        }


        zoneStateResult.setZoneStateToList(zoneStateToList);

        return CommonResult.success(zoneStateResult);
    }


}
