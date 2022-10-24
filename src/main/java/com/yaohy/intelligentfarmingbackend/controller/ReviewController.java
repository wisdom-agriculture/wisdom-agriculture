package com.yaohy.intelligentfarmingbackend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.App;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.Web;
import com.yaohy.intelligentfarmingbackend.pojo.domain.*;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.ReviewParam;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.transport.ReplyParam;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.*;
import com.yaohy.intelligentfarmingbackend.service.*;
import com.yaohy.intelligentfarmingbackend.util.JwtUtil;
import com.yaohy.intelligentfarmingbackend.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class ReviewController {

    @Autowired
    private ProducesService producesService;

    @Autowired
    private ProducesImagesService producesImagesService;

    @Autowired
    private ProducesSkuService producesSkuService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ShoppingCarService shoppingCarService;

    @Autowired
    private ShoppingCarItemsService shoppingCarItemsService;

    @Autowired
    private UserFavoritesService userFavoritesService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserShoppingAddressService userShoppingAddressService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemsService orderItemsService;

    @Autowired
    private PayWaysService payWaysService;

    @Autowired
    private OrderRefundReasonService orderRefundReasonService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ProduceReviewsService produceReviewsService;

    @Autowired
    private ProduceReviewsImagesService produceReviewsImagesService;

    @Autowired
    private ReviewItemService reviewItemService;

    @Autowired
    private GoodItemService goodItemService;

    @App
    @Web
    @RequestMapping(value = "/review/detail", method = RequestMethod.GET)
    public CommonResult<ReviewDetailResult> getGoodList(@RequestParam Integer reviewId) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        ProduceReviews produceReviews = produceReviewsService.getOne(new QueryWrapper<ProduceReviews>().eq("id", reviewId));

        ReviewDetailResult reviewDetailResult = new ReviewDetailResult();

        User user = userService.getOne(new QueryWrapper<User>().eq("id", produceReviews.getUserId()));
        reviewDetailResult.setUserName(user.getNickname());
        reviewDetailResult.setUserImage(user.getImgUrl());
        reviewDetailResult.setContent(produceReviews.getContent());
        List<String> reviewImageList = produceReviewsImagesService.list(new QueryWrapper<ProduceReviewsImages>().eq("review_id", produceReviews.getId())).stream()
                .map(produceReviewsImages -> {
                    String s = "";
                    s = produceReviewsImages.getReviewImages();
                    return s;
                }).collect(Collectors.toList());
        reviewDetailResult.setReviewImages(reviewImageList);

        int goodNumber = goodItemService.list(new QueryWrapper<GoodItem>().eq("review_id", produceReviews.getId())).size();
        int reviewNumber = reviewItemService.list(new QueryWrapper<ReviewItem>().eq("review_id", produceReviews.getId())).size();
        reviewDetailResult.setReviewNumber(reviewNumber);
        reviewDetailResult.setGoodNumber(goodNumber);
        reviewDetailResult.setForwardNumber(produceReviews.getForwardNumber());

        List<ReviewDetailResult.OtherReview> otherReviewList = reviewItemService.list(new QueryWrapper<ReviewItem>().eq("review_id", produceReviews.getId())).stream()
                .map(reviewItem -> {
                    ReviewDetailResult.OtherReview otherReview = new ReviewDetailResult.OtherReview();
                    User fromUser = userService.getOne(new QueryWrapper<User>().eq("id", reviewItem.getFromId()));
                    otherReview.setFromUserId(fromUser.getId());
                    otherReview.setFromUserName(fromUser.getNickname());
                    otherReview.setFromUserImage(fromUser.getImgUrl());

                    User toUser = userService.getOne(new QueryWrapper<User>().eq("id", reviewItem.getToId()));
                    otherReview.setToUserId(toUser.getId());
                    otherReview.setToUserName(toUser.getNickname());
                    otherReview.setToUserImage(toUser.getImgUrl());

                    otherReview.setContent(reviewItem.getContent());
                    otherReview.setCreateTime(reviewItem.getCreateTime());
                    return otherReview;
                }).collect(Collectors.toList());
        reviewDetailResult.setOtherReviewList(otherReviewList);

        GoodItem goodItem = goodItemService.getOne(new QueryWrapper<GoodItem>().eq("from_id", userId).eq("review_id", reviewId));
        if (goodItem != null){
            reviewDetailResult.setIsGood(1);
        }else {
            reviewDetailResult.setIsGood(0);
        }

        return CommonResult.success(reviewDetailResult);
    }

    @App
    @Web
    @RequestMapping(value = "/review", method = RequestMethod.POST)
    public CommonResult<NoData> sentReview(@RequestBody ReviewParam reviewParam) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        ProduceReviews produceReviews = new ProduceReviews();
        produceReviews.setProduceId(reviewParam.getProduceId());
        produceReviews.setUserId(userId);
        produceReviews.setContent(reviewParam.getContent());
        produceReviews.setGoodNumber(0);
        produceReviews.setForwardNumber(0);
        produceReviews.setCreateTime(new Date());
        produceReviews.setIsRead(0);
        produceReviewsService.save(produceReviews);

        return CommonResult.success("true");
    }

    @App
    @Web
    @RequestMapping(value = "/reply", method = RequestMethod.POST)
    public CommonResult<NoData> reply(@RequestBody ReplyParam replyParam) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        ReviewItem reviewItem = new ReviewItem();

        reviewItem.setReviewId(replyParam.getReviewId());
        reviewItem.setFromId(userId);
        reviewItem.setToId(replyParam.getToUserId());
        reviewItem.setContent(replyParam.getContent());
        reviewItem.setCreateTime(new Date());
        reviewItem.setIsRead(0);
        reviewItemService.save(reviewItem);

        return CommonResult.success("true");
    }

    @App
    @Web
    @RequestMapping(value = "/good", method = RequestMethod.POST)
    public CommonResult<NoData> good(@RequestBody Integer reviewId) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        GoodItem goodItem = new GoodItem();
        goodItem.setReviewId(reviewId);
        goodItem.setFromId(userId);

        ProduceReviews produceReviewsServiceOne = produceReviewsService.getOne(new QueryWrapper<ProduceReviews>().eq("id", reviewId));
        goodItem.setToId(produceReviewsServiceOne.getUserId());
        goodItem.setIsRead(0);
        goodItemService.save(goodItem);

        return CommonResult.success("true");
    }

    //我的点赞
    @App
    @Web
    @RequestMapping(value = "/good", method = RequestMethod.GET)
    public CommonResult<MyGoodListResult> myGood() {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        List<MyGoodListResult.MyGood> collect = goodItemService.list(new QueryWrapper<GoodItem>().eq("from_id", userId)).stream()
                .map(goodItem -> {
                    MyGoodListResult.MyGood myGood = new MyGoodListResult.MyGood();

                    myGood.setReviewId(goodItem.getReviewId());

                    ProduceReviews produceReviews = produceReviewsService.getOne(new QueryWrapper<ProduceReviews>().eq("id", goodItem.getReviewId()));
                    myGood.setUserId(produceReviews.getUserId());

                    User user = userService.getOne(new QueryWrapper<User>().eq("id", produceReviews.getUserId()));
                    myGood.setUserName(user.getNickname());
                    myGood.setUserImage(user.getImgUrl());
                    myGood.setContent(produceReviews.getContent());

                    int reviewNumber = reviewItemService.list(new QueryWrapper<ReviewItem>().eq("review_id", produceReviews.getId())).size();
                    int goodNumber = goodItemService.list(new QueryWrapper<GoodItem>().eq("review_id", produceReviews.getId())).size();

                    myGood.setGoodNumber(goodNumber);
                    myGood.setReplyNumber(reviewNumber);
                    myGood.setForwardNumber(produceReviews.getForwardNumber());

                    return myGood;
                }).collect(Collectors.toList());

        MyGoodListResult myGoodListResult = new MyGoodListResult();
        myGoodListResult.setMyGoodList(collect);
        return CommonResult.success(myGoodListResult);
    }

    @App
    @Web
    @RequestMapping(value = "/number", method = RequestMethod.GET)
    public CommonResult<AllNewNumberResult> getAllNumber() {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        AllNewNumberResult allNewNumberResult = new AllNewNumberResult();
        int newGoodNumber = goodItemService.list(new QueryWrapper<GoodItem>().eq("to_id", userId).eq("is_read", 0)).size();
        int reviewNumber = reviewItemService.list(new QueryWrapper<ReviewItem>().eq("to_id", userId).eq("is_read", 0)).size();
        allNewNumberResult.setGoodNumber(newGoodNumber);
        allNewNumberResult.setReviewNumber(reviewNumber);
//        allNewNumberResult.setAtNumber();

        return CommonResult.success(allNewNumberResult);
    }

    @App
    @Web
    @RequestMapping(value = "/good/list", method = RequestMethod.GET)
    public CommonResult<GoodListResult> getGoodList() {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        goodItemService.update(new UpdateWrapper<GoodItem>().eq("to_id",userId).set("is_read",1));

        List<GoodListResult.GoodResult> collect = goodItemService.list(new QueryWrapper<GoodItem>().eq("to_id", userId)).stream()
                .map(goodItem -> {
                    GoodListResult.GoodResult goodResult = new GoodListResult.GoodResult();
                    goodResult.setReviewId(goodResult.getReviewId());
                    User user = userService.getOne(new QueryWrapper<User>().eq("id", goodItem.getFromId()));
                    goodResult.setUserName(user.getNickname());
                    goodResult.setUserImage(user.getImgUrl());

                    ProduceReviews produceReviews = produceReviewsService.getOne(new QueryWrapper<ProduceReviews>().eq("id", goodItem.getReviewId()));
                    goodResult.setContent(produceReviews.getContent());

                    ProduceReviewsImages produceReviewsImages = produceReviewsImagesService.getOne(new QueryWrapper<ProduceReviewsImages>().eq("review_id", goodItem.getReviewId()).last("limit 1"));
                    if (produceReviewsImages != null) {
                        goodResult.setReviewImage(produceReviewsImages.getReviewImages());
                    }
                    goodResult.setCreateTime(goodItem.getCreateTime());
                    return goodResult;
                }).collect(Collectors.toList());

        GoodListResult goodListResult = new GoodListResult();
        goodListResult.setGoodResultList(collect);
        return CommonResult.success(goodListResult);
    }

    @App
    @Web
    @RequestMapping(value = "/review/list", method = RequestMethod.GET)
    public CommonResult<ReviewListResult> getReviewList() {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        reviewItemService.update(new UpdateWrapper<ReviewItem>().eq("to_id",userId).set("is_read",1));

        List<ReviewListResult.Review> collect = reviewItemService.list(new QueryWrapper<ReviewItem>().eq("to_id", userId)).stream()
                .map(reviewItem -> {
                    ReviewListResult.Review review = new ReviewListResult.Review();
                    review.setReviewId(reviewItem.getReviewId());

                    User user = userService.getOne(new QueryWrapper<User>().eq("id", reviewItem.getFromId()));

                    review.setUserName(user.getNickname());
                    review.setUserImage(user.getImgUrl());
                    review.setContent(reviewItem.getContent());
                    ProduceReviewsImages produceReviewsImages = produceReviewsImagesService.getOne(new QueryWrapper<ProduceReviewsImages>().eq("review_id", reviewItem.getReviewId()).last("limit 1"));
                    if (produceReviewsImages != null) {
                        review.setReviewImage(produceReviewsImages.getReviewImages());
                    }
                    review.setCreateTime(reviewItem.getCreateTime());
                    return review;
                }).collect(Collectors.toList());

        ReviewListResult reviewListResult = new ReviewListResult();
        reviewListResult.setReviewList(collect);
        return CommonResult.success(reviewListResult);
    }

}
