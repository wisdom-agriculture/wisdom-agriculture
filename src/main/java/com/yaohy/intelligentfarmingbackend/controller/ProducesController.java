package com.yaohy.intelligentfarmingbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.App;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.Web;
import com.yaohy.intelligentfarmingbackend.pojo.domain.*;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.*;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.*;
import com.yaohy.intelligentfarmingbackend.service.*;
import com.yaohy.intelligentfarmingbackend.util.JwtUtil;
import com.yaohy.intelligentfarmingbackend.util.MinioUtil;
import com.yaohy.intelligentfarmingbackend.util.RedisUtil;
import com.yaohy.intelligentfarmingbackend.util.SnowFlakeOrderNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class ProducesController {

    @Autowired
    private ProduceReviewsService produceReviewsService;

    @Autowired
    private ProduceReviewsImagesService produceReviewsImagesService;

    @Autowired
    private ReviewItemService reviewItemService;

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

    @App
    @Web
    @RequestMapping(value = "/produces", method = RequestMethod.GET)
    public CommonResult<ProducesListResult> getAll() {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        ProducesListResult producesListResult = new ProducesListResult();
        List<ProducesResult> collect = producesService.list(new QueryWrapper<ProducesInfo>().eq("produces_category", 1).or().eq("produces_category", 2)).stream()
                .map(producesInfo -> {
                    ProducesResult producesResult = new ProducesResult();
                    ProducesSku producesSku = producesSkuService.getOne(new QueryWrapper<ProducesSku>().eq("produce_id", producesInfo.getId()).orderByAsc("produces_price").last("limit 1"));

                    producesResult.setId(producesInfo.getId());
                    producesResult.setProducesName(producesInfo.getProducesName());
                    producesResult.setProducesPrice(producesSku.getProducesPrice());
                    producesResult.setProducesPer(producesSku.getProducesPer());
                    producesResult.setTransactionPrice(producesSku.getTransactionPrice());

                    ProducesImages producesImages = producesImagesService.getOne(new QueryWrapper<ProducesImages>().eq("produce_id", producesInfo.getId()).last("limit 1"));

                    producesResult.setProducesImage(producesImages.getImageAddress());
                    producesResult.setProducesCategory(producesInfo.getProducesCategory());

                    return producesResult;

                }).collect(Collectors.toList());
        producesListResult.setProducesListResult(collect);

        return CommonResult.success(producesListResult);
    }

    @App
    @Web
    @RequestMapping(value = "/produces/category", method = RequestMethod.GET)
    public CommonResult<ProducesListResult> getAllById(@RequestParam("producesCategory") Integer producesCategory) {

        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));
        ProducesListResult producesListResult = new ProducesListResult();
        List<ProducesResult> collect = producesService.list(new QueryWrapper<ProducesInfo>().eq("produces_category", producesCategory)).stream()
                .map(producesInfo -> {
                    ProducesResult producesResult = new ProducesResult();
                    ProducesSku producesSku = producesSkuService.getOne(new QueryWrapper<ProducesSku>().eq("produce_id", producesInfo.getId()).orderByAsc("produces_price").last("limit 1"));

                    producesResult.setId(producesInfo.getId());
                    producesResult.setProducesName(producesInfo.getProducesName());
                    producesResult.setProducesPrice(producesSku.getProducesPrice());
                    producesResult.setProducesPer(producesSku.getProducesPer());
                    producesResult.setTransactionPrice(producesSku.getTransactionPrice());

                    ProducesImages producesImages = producesImagesService.getOne(new QueryWrapper<ProducesImages>().eq("produce_id", producesInfo.getId()).last("limit 1"));

                    producesResult.setProducesImage(producesImages.getImageAddress());
                    producesResult.setProducesCategory(producesInfo.getProducesCategory());

                    return producesResult;

                }).collect(Collectors.toList());
        producesListResult.setProducesListResult(collect);

        return CommonResult.success(producesListResult);
    }

    @App
    @Web
    @RequestMapping(value = "/cars", method = RequestMethod.GET)
    public CommonResult<CarsListResult> getCars() {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        ShoppingCar shoppingCar = shoppingCarService.getOne(new QueryWrapper<ShoppingCar>().eq("user_id", userId));
        if (shoppingCar == null){
            return CommonResult.fail("该用户购物车没有物品");
        }
        Integer carId = shoppingCar.getId();

        List<CarResult> carResultList = shoppingCarItemsService.list(new QueryWrapper<ShoppingCarItems>().eq("car_id",carId)).stream()
                .filter(distinctByKey1(s -> s.getShopName()))
                .map(shoppingCarItems -> {
                    CarResult carResult = new CarResult();
                    String shopName = shoppingCarItems.getShopName();
                    carResult.setShopName(shopName);
                    List<CarResult.CarItemResult> carList = shoppingCarItemsService.list(new QueryWrapper<ShoppingCarItems>().eq("shop_name", shopName)).stream()
                            .map(shoppingCarItems1 -> {
                                CarResult.CarItemResult carItemResult = new CarResult.CarItemResult();
                                carItemResult.setId(shoppingCarItems1.getId());
                                carItemResult.setProducesId(shoppingCarItems1.getProducesId());
                                carItemResult.setProducesName(shoppingCarItems1.getProducesName());
                                ProducesImages produceImage = producesImagesService.getOne(new QueryWrapper<ProducesImages>().eq("produce_id", shoppingCarItems1.getProducesId()).last("limit 1"));
                                carItemResult.setProducesImage(produceImage.getImageAddress());
                                carItemResult.setProducesWeight(shoppingCarItems1.getProducesWeight());
                                carItemResult.setProducesPer(shoppingCarItems1.getProducesPer());
                                carItemResult.setProducesPrice(shoppingCarItems1.getProducesPrice());
                                carItemResult.setProducesSkuId(shoppingCarItems1.getProducesSkuId());
                                return carItemResult;
                            }).collect(Collectors.toList());
                    carResult.setCarItemResults(carList);
                    return carResult;
                }).collect(Collectors.toList());

        CarsListResult carsListResult = new CarsListResult();
        carsListResult.setCarResultList(carResultList);
        return CommonResult.success(carsListResult);
    }

    @App
    @Web
    @RequestMapping(value = "/cars", method = RequestMethod.DELETE)
    public CommonResult<NoData> deleteCars(@RequestBody Integer[] ids) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        shoppingCarItemsService.removeByIds(Arrays.asList(ids));

        return CommonResult.success("true");
    }

    @App
    @Web
    @RequestMapping(value = "/favorites", method = RequestMethod.GET)
    public CommonResult<FavoritesListResult> favorites() {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        List<UserFavorites> shopNameList = userFavoritesService.list(new QueryWrapper<UserFavorites>().select("shop_name").eq("user_id", userId).groupBy("shop_name"));

        List<UserFavoritesResult> userFavoritesResults = new ArrayList<>();
        for (UserFavorites userFavorites : shopNameList) {

            UserFavoritesResult userFavoritesResult = new UserFavoritesResult();
            userFavoritesResult.setShopName(userFavorites.getShopName());


            List<UserFavoritesResult.UserFavoritesItemResults> collect = userFavoritesService.list(new QueryWrapper<UserFavorites>().eq("user_id", userId).in("shop_name", userFavorites.getShopName())).stream()
                    .map(userFavorites1 -> {
                        UserFavoritesResult.UserFavoritesItemResults userFavoritesItemResults = new UserFavoritesResult.UserFavoritesItemResults();

                        userFavoritesItemResults.setId(userFavorites1.getId());
                        userFavoritesItemResults.setProducesId(userFavorites1.getProducesId());
                        userFavoritesItemResults.setProducesName(userFavorites1.getProducesName());

                        ProducesInfo producesInfo = producesService.getOne(new QueryWrapper<ProducesInfo>().eq("id", userFavorites1.getProducesId()));
                        userFavoritesItemResults.setProducesDescription(producesInfo.getProducesDescription());
                        userFavoritesItemResults.setProducesPrice(userFavorites1.getProducesPrice());
                        userFavoritesItemResults.setProducesPer(userFavorites1.getProducesPer());

                        ProducesImages producesImages = producesImagesService.getOne(new QueryWrapper<ProducesImages>().eq("produce_id", userFavorites1.getProducesId()).last("limit 1"));
                        userFavoritesItemResults.setProducesImage(producesImages.getImageAddress());
                        return userFavoritesItemResults;
                    }).collect(Collectors.toList());
            userFavoritesResult.setUserFavoritesItemResults(collect);
            userFavoritesResults.add(userFavoritesResult);
        }

//        List<UserFavoritesResult> collect = userFavoritesService.list(new QueryWrapper<UserFavorites>().eq("user_id", userId)).stream()
//                .map(userFavorites -> {
//
//                    UserFavoritesResult userFavoritesItemResult = new UserFavoritesResult();
//
//                    userFavoritesItemResult.setId(userFavorites.getId());
//                    userFavoritesItemResult.setProducesId(userFavorites.getProducesId());
//                    userFavoritesItemResult.setProducesName(userFavorites.getProducesName());
//
//                    ProducesInfo producesInfo = producesService.getOne(new QueryWrapper<ProducesInfo>().eq("id", userFavorites.getProducesId()));
//                    userFavoritesItemResult.setProducesDescription(producesInfo.getProducesDescription());
////                    userFavoritesItemResult.setProducesSpecification(producesInfo.getProducesSpecification());
//                    userFavoritesItemResult.setProducesPrice(userFavorites.getProducesPrice());
//                    userFavoritesItemResult.setProducesPer(userFavorites.getProducesPer());
//
//                    ProducesImages producesImages = producesImagesService.getOne(new QueryWrapper<ProducesImages>().eq("produce_id", userFavorites.getProducesId()).last("limit 1"));
//                    userFavoritesItemResult.setProducesImage(producesImages.getImageAddress());
//
//                    ProducesSku producesSku = producesSkuService.getOne(new QueryWrapper<ProducesSku>().eq("produce_id", userFavorites.getProducesId()).last("limit 1"));
//                    userFavoritesItemResult.setShopName(producesInfo.getShopName());
//                    return userFavoritesItemResult;
//                }).collect(Collectors.toList());

        FavoritesListResult favoritesListResult = new FavoritesListResult();
        favoritesListResult.setUserFavoritesListResults(userFavoritesResults);
        return CommonResult.success(favoritesListResult);
    }

    @App
    @Web
    @RequestMapping(value = "/favorites", method = RequestMethod.DELETE)
    public CommonResult<NoData> deleteFavorites(@RequestBody Integer[] ids) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        userFavoritesService.removeByIds(Arrays.asList(ids));

        return CommonResult.success("true");
    }


    @App
    @Web
    @RequestMapping(value = "/produce", method = RequestMethod.GET)
    public CommonResult<ProduceResult> getProduce(@RequestParam("producesId") Integer producesId) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        ProduceResult produceResult = new ProduceResult();

        ProducesInfo produce = producesService.getOne(new QueryWrapper<ProducesInfo>().eq("id", producesId));

        produceResult.setId(produce.getId());
        produceResult.setProducesName(produce.getProducesName());
        produceResult.setProducesDescription(produce.getProducesDescription());
        produceResult.setProducesStatus(produce.getProducesStatus());
        produceResult.setShoppingAddress(produce.getShoppingAddress());
        produceResult.setShoppingAddressRemark(produce.getShoppingAddressRemark());

        List<ProduceResult.ProduceSkuInfo> produceSkuInfoList = producesSkuService.list(new QueryWrapper<ProducesSku>().eq("produce_id", produce.getId())).stream()
                .map(producesSku -> {
                    ProduceResult.ProduceSkuInfo produceSkuInfo = new ProduceResult.ProduceSkuInfo();
                    produceSkuInfo.setProducesSkuId(producesSku.getId());
                    produceSkuInfo.setProducesSpecification(producesSku.getProducesSpecification());
                    produceSkuInfo.setProducesPrice(producesSku.getProducesPrice());
                    produceSkuInfo.setProducesWeight(producesSku.getProducesWeight());
                    produceSkuInfo.setProducesPer(producesSku.getProducesPer());
                    return produceSkuInfo;

                }).collect(Collectors.toList());
        produceResult.setProduceSkuInfoList(produceSkuInfoList);

        ProducesSku lowPrice = producesSkuService.getOne(new QueryWrapper<ProducesSku>().select("min(produces_price) as produces_price").eq("produce_id", producesId));
        ProducesSku higPrice = producesSkuService.getOne(new QueryWrapper<ProducesSku>().select("max(produces_price) as produces_price").eq("produce_id", producesId));
        produceResult.setLowProducesPrice(lowPrice.getProducesPrice());
        produceResult.setHigProducesPrice(higPrice.getProducesPrice());

        UserFavorites userFavorites = userFavoritesService.getOne(new QueryWrapper<UserFavorites>().eq("user_id", userId).eq("produces_id",produce.getId()));
        if (userFavorites != null){
            produceResult.setIsFavorites(userFavorites.getProducesId().equals(produce.getId()));
        }else {
            produceResult.setIsFavorites(false);
        }

        List<ProduceResult.ImagesList> produceImagesList = producesImagesService.list(new QueryWrapper<ProducesImages>().eq("produce_id", producesId)).stream()
                .sorted(Comparator.comparing(ProducesImages::getImageOrder))
                .map(producesImages -> {
                    ProduceResult.ImagesList imagesList = new ProduceResult.ImagesList();
                    imagesList.setProduceId(producesImages.getProduceId());
                    imagesList.setImageAddress(producesImages.getImageAddress());
                    imagesList.setImageOrder(producesImages.getImageOrder());
                    return imagesList;
                }).collect(Collectors.toList());

        produceResult.setImagesList(produceImagesList);

        List<ProduceResult.ReviewResult> reviews = produceReviewsService.list(new QueryWrapper<ProduceReviews>().eq("produce_id", producesId)).stream()
                .map(produceReviews1 -> {
                    ProduceResult.ReviewResult reviewResult = new ProduceResult.ReviewResult();

                    if (produceReviews1 != null) {
                        reviewResult.setReviewId(produceReviews1.getId());
                        User user = userService.getOne(new QueryWrapper<User>().eq("id", produceReviews1.getUserId()));
                        reviewResult.setUserName(user.getNickname());
                        reviewResult.setUserImage(user.getImgUrl());
                        reviewResult.setContent(produceReviews1.getContent());
                        reviewResult.setCreateTime(produceReviews1.getCreateTime());

                        List<String> reviewImages = produceReviewsImagesService.list(new QueryWrapper<ProduceReviewsImages>().eq("review_id", produceReviews1.getId())).stream()
                                .map(produceReviewsImages -> {
                                    String s;
                                    s = produceReviewsImages.getReviewImages();
                                    return s;
                                }).collect(Collectors.toList());
                        reviewResult.setReviewImages(reviewImages);
                    }

                    return reviewResult;
                }).collect(Collectors.toList());

        produceResult.setReviewResult(reviews);
        int count = produceReviewsService.list(new QueryWrapper<ProduceReviews>().eq("produce_id", producesId)).size();
        produceResult.setCount(count);

        return CommonResult.success(produceResult);
    }

    @App
    @Web
    @RequestMapping(value = "/favorites", method = RequestMethod.POST)
    public CommonResult<NoData> addFavorites(@RequestBody ProduceParam produceParam) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));
        User userFavoritesInfo = userService.getOne(new QueryWrapper<User>().eq("id", userId));
        if (userFavoritesInfo == null){
            return CommonResult.fail("用户不存在");
        }

        UserFavorites userFavorites = new UserFavorites();
        ProducesInfo producesInfo = producesService.getOne(new QueryWrapper<ProducesInfo>().eq("id", produceParam.getProduceId()));
        ProducesSku producesSku = producesSkuService.getOne(new QueryWrapper<ProducesSku>().eq("produce_id", produceParam.getProduceId()).last("limit 1"));
        ProducesImages produceImage = producesImagesService.getOne(new QueryWrapper<ProducesImages>().eq("produce_id", produceParam.getProduceId()).last("limit 1"));

        userFavorites.setUserId(userId);
        userFavorites.setProducesId(produceParam.getProduceId());
        userFavorites.setShopName(producesInfo.getShopName());
        userFavorites.setProducesName(producesInfo.getProducesName());
        userFavorites.setProducesPer(producesSku.getProducesPer());
        userFavorites.setProducesImage(produceImage.getId());
        userFavorites.setProducesPrice(producesSku.getProducesPrice());
        userFavoritesService.save(userFavorites);

        return CommonResult.success("true");
    }

    @App
    @Web
    @RequestMapping(value = "/cars", method = RequestMethod.POST)
    public CommonResult<NoData> addCars(@RequestBody CarParam carParam) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        List<ShoppingCar> user = shoppingCarService.list(new QueryWrapper<ShoppingCar>().eq("user_id", userId));

        if (user.size() == 0) {
            ShoppingCar shoppingCar = new ShoppingCar();
            shoppingCar.setUserId(userId);
            shoppingCarService.save(shoppingCar);
        }

        ShoppingCarItems shoppingCarItems = new ShoppingCarItems();
        ProducesInfo producesInfo = producesService.getOne(new QueryWrapper<ProducesInfo>().eq("id", carParam.getProducesId()));
        ProducesSku producesSku = producesSkuService.getOne(new QueryWrapper<ProducesSku>().eq("produce_id", carParam.getProducesSkuId()).last("limit 1"));
        ProducesImages produceImage = producesImagesService.getOne(new QueryWrapper<ProducesImages>().eq("produce_id", carParam.getProducesId()).last("limit 1"));

        ShoppingCar shopCar = shoppingCarService.getOne(new QueryWrapper<ShoppingCar>().eq("user_id", userId));
        shoppingCarItems.setCarId(shopCar.getId());
        shoppingCarItems.setShopName(producesInfo.getShopName());
        shoppingCarItems.setProducesId(carParam.getProducesId());
        shoppingCarItems.setProducesName(producesInfo.getProducesName());
        shoppingCarItems.setProducesPer(producesSku.getProducesPer());
        shoppingCarItems.setProducesPrice(producesSku.getProducesPrice());
        shoppingCarItems.setProducesWeight(producesSku.getProducesWeight());
        shoppingCarItems.setProducesImage(produceImage.getId());
        shoppingCarItems.setProducesSkuId(producesSku.getId());

        shoppingCarItemsService.save(shoppingCarItems);

        return CommonResult.success("true");
    }

    @App
    @Web
    @RequestMapping(value = "/user/address", method = RequestMethod.GET)
    public CommonResult<UserShoppingAddressListResult> getUserAddress() {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));
        List<UserShoppingAddressListResult.UserShoppingAddressResult> userShoppingAddressResults = userShoppingAddressService.list(new QueryWrapper<UserShoppingAddress>().eq("user_id", userId)).stream()
                .map(userShoppingAddress -> {
                    UserShoppingAddressListResult.UserShoppingAddressResult userShoppingAddressResult = new UserShoppingAddressListResult.UserShoppingAddressResult();

                    userShoppingAddressResult.setId(userShoppingAddress.getId());
                    userShoppingAddressResult.setUserShoppingAddress(userShoppingAddress.getAddress());
                    userShoppingAddressResult.setUserName(userShoppingAddress.getUserName());
                    userShoppingAddressResult.setUserPhone(userShoppingAddress.getUserPhone());

                    return userShoppingAddressResult;
                }).collect(Collectors.toList());

        UserShoppingAddressListResult userShoppingAddressListResult = new UserShoppingAddressListResult();
        userShoppingAddressListResult.setUserShoppingAddressResults(userShoppingAddressResults);
        return CommonResult.success(userShoppingAddressListResult);
    }

    @App
    @Web
    @RequestMapping(value = "/user/address", method = RequestMethod.POST)
    public CommonResult<NoData> addUserAddress(@RequestBody UserShoppingAddressParam userShoppingAddressParam) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));

        UserShoppingAddress userShoppingAddress = new UserShoppingAddress();
        userShoppingAddress.setUserId(userId);
        userShoppingAddress.setUserName(userShoppingAddressParam.getUserName());
        userShoppingAddress.setUserPhone(userShoppingAddressParam.getUserPhone());
        userShoppingAddress.setAddress(userShoppingAddressParam.getAddress());
        userShoppingAddress.setDefaultAddress(0);
        userShoppingAddressService.save(userShoppingAddress);

        return CommonResult.success("true");
    }

    @App
    @Web
    @RequestMapping(value = "/user/address", method = RequestMethod.DELETE)
    public CommonResult<NoData> deleteUserAddress(@RequestParam Integer id) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        userShoppingAddressService.removeById(id);

        return CommonResult.success("true");
    }

    @App
    @Web
    @RequestMapping(value = "/user/address", method = RequestMethod.PUT)
    public CommonResult<NoData> updateUserAddress(@RequestBody UserShoppingAddressUpdateParam userShoppingAddressUpdateParam) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));

        UserShoppingAddress userShoppingAddress = new UserShoppingAddress();

        userShoppingAddress.setId(userShoppingAddressUpdateParam.getId());

        userShoppingAddress.setUserId(userId);
        userShoppingAddress.setUserName(userShoppingAddressUpdateParam.getUserName());
        userShoppingAddress.setUserPhone(userShoppingAddressUpdateParam.getUserPhone());
        userShoppingAddress.setAddress(userShoppingAddressUpdateParam.getAddress());


        userShoppingAddressService.updateById(userShoppingAddress);

        return CommonResult.success("true");
    }

    @App
    @Web
    @RequestMapping(value = "/user/address/number", method = RequestMethod.GET)
    public CommonResult<Integer> defaultAddressNumber() {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        List<UserShoppingAddress> userShoppingAddressList = userShoppingAddressService.list(new QueryWrapper<UserShoppingAddress>().eq("user_id", userId).eq("default_address",1));
        int defaultAddressNumber = userShoppingAddressList.size();

        return CommonResult.success(defaultAddressNumber);
    }

    @App
    @Web
    @RequestMapping(value = "/settlement", method = RequestMethod.POST)
    public CommonResult<ProduceSettlementResult> getSettlement(@RequestBody ProduceSettlementParam produceSettlementParam) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));
        ProduceSettlementResult produceSettlementResult = new ProduceSettlementResult();
        produceSettlementResult.setUserName(user.getNickname());
        produceSettlementResult.setUserPhone(user.getPhone());
        UserShoppingAddress userShoppingAddress = userShoppingAddressService.getOne(new QueryWrapper<UserShoppingAddress>().eq("user_id", userId).eq("default_address", 0));

        produceSettlementResult.setUserShoppingAddress(userShoppingAddress.getAddress());

        produceSettlementResult.setShopName(produceSettlementParam.getShopName());
        ProduceSettlementResult.ProducesList producesList = new ProduceSettlementResult.ProducesList();
        producesList.setProducesId(produceSettlementParam.getProduceId());
        producesList.setProducesName(produceSettlementParam.getProduceName());
        ProducesImages producesImage = producesImagesService.getOne(new QueryWrapper<ProducesImages>().eq("produce_id", produceSettlementParam.getProduceId()).eq("image_order", 0));
        producesList.setProducesImage(producesImage.getImageAddress());
        producesList.setProducesPer(produceSettlementParam.getProducesPer());
        producesList.setProducesWeight(produceSettlementParam.getProduceWeight());
        producesList.setProducesPrice(produceSettlementParam.getProducePrice() * produceSettlementParam.getProduceWeight());
        producesList.setFreightPrice(produceSettlementParam.getFreightPrice());
        produceSettlementResult.setTotalPrice(produceSettlementParam.getProducePrice() * produceSettlementParam.getProduceWeight());

        ArrayList<ProduceSettlementResult.ProducesList> producesLists = new ArrayList<>();
        producesLists.add(producesList);
        produceSettlementResult.setProducesList(producesLists);
        return CommonResult.success(produceSettlementResult);
    }

    @App
    @Web
    @RequestMapping(value = "/cars/settlement", method = RequestMethod.POST)
    public CommonResult<CarSettlementResult> settlementByCars(@RequestBody List<CarSettlementParam> carSettlementParamList) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));
        CarSettlementResult carSettlementResult = new CarSettlementResult();

        User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));

        carSettlementResult.setUserName(user.getNickname());
        carSettlementResult.setUserPhone(user.getPhone());
        UserShoppingAddress userShoppingAddress = userShoppingAddressService.getOne(new QueryWrapper<UserShoppingAddress>().eq("user_id", userId).eq("default_address", 0));



        ArrayList<CarSettlementProducesListResult> carSettlementProducesListResults = new ArrayList<>();
        double totalPrice = 0;
        for (CarSettlementParam carSettlementParam : carSettlementParamList) {

            CarSettlementProducesListResult carSettlementProducesListResult = new CarSettlementProducesListResult();

            carSettlementProducesListResult.setShopName(carSettlementParam.getShopName());
            List<CarSettlementParam.ProducesList> producesListList = carSettlementParam.getProducesListList();
            ArrayList<CarSettlementProducesListResult.ProducesList> producesLists = new ArrayList<>();
            for (CarSettlementParam.ProducesList producesList : producesListList) {
                CarSettlementProducesListResult.ProducesList carProducesList = new CarSettlementProducesListResult.ProducesList();
                carProducesList.setProducesId(producesList.getProduceId());
                carProducesList.setProducesName(producesList.getProduceName());
                carProducesList.setProducesImage(producesList.getProduceImage());
                carProducesList.setProducesWeight(producesList.getProduceWeight());
                carProducesList.setProducesPer(producesList.getProducesPer());
                carProducesList.setProducesPrice(producesList.getProducePrice());
                carProducesList.setFreightPrice(producesList.getFreightPrice());
                producesLists.add(carProducesList);
                totalPrice += producesList.getProducePrice();
                if (producesList.getFreightPrice() != "免邮"){
                    totalPrice += Integer.parseInt((producesList.getFreightPrice()));
                }
            }
            carSettlementProducesListResult.setProducesList(producesLists);

            carSettlementProducesListResults.add(carSettlementProducesListResult);
        }

        carSettlementResult.setCarSettlementProducesListResults(carSettlementProducesListResults);
        carSettlementResult.setTotalPrice(totalPrice);

        return CommonResult.success(carSettlementResult);
    }

    @App
    @Web
    @RequestMapping(value = "/payWays", method = RequestMethod.GET)
    public CommonResult<PayWaysResult> getPayWays() {
        PayWaysResult payWaysResult = new PayWaysResult();
        List<PayWaysResult.PayWay> payWayList = payWaysService.list().stream()
                .map(payWays -> {
                    PayWaysResult.PayWay payWay = new PayWaysResult.PayWay();
                    payWay.setId(payWays.getId());
                    payWay.setPayWay(payWays.getPayWay());
                    payWay.setIsDefault(payWays.getIsDefault());
                    return payWay;
                }).collect(Collectors.toList());
        payWaysResult.setPayWayList(payWayList);
        return CommonResult.success(payWaysResult);
    }

    //提交订单
    @App
    @Web
    @Transactional
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public CommonResult<NoData> submitOrder(@RequestBody OrderParam orderParam) {

        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));

        Orders orders = new Orders();

        String orderNo = new SnowFlakeOrderNumber(0, 0).createOrderNo();
        orders.setOrderNumber(orderNo);
        orders.setCreateTime(new Date());
        orders.setUserId(user.getId());
        orders.setUserName(user.getNickname());
        orders.setUserPhone(user.getPhone());
        orders.setAddress(orderParam.getAddress());
        orders.setAmountPayable(0.00);
        orders.setOrderSource("");
        orders.setProducesCategory(0);
        orders.setShopId(0);
        orders.setOrderStatus(0);
        orders.setPayTime(new Date());
        orderService.save(orders);

        Double sum = 0.0;
        for (Integer produceSkuId : orderParam.getProducesSKuId()) {

            ProducesSku producesSku = producesSkuService.getOne(new QueryWrapper<ProducesSku>().eq("id", produceSkuId));
            ProducesInfo producesInfo = producesService.getOne(new QueryWrapper<ProducesInfo>().eq("id", producesSku.getProduceId()));

            OrderItems orderItems = new OrderItems();
            orderItems.setOrderId(orders.getId());
            orderItems.setProducesId(producesInfo.getId());
            orderItems.setProducesName(producesInfo.getProducesName());
            orderItems.setProducesPrice(producesSku.getProducesPrice());
            orderItems.setProducesWeight(producesSku.getProducesWeight());
            orderItems.setProducesPer(producesSku.getProducesPer());
            ProducesImages producesImages = producesImagesService.getOne(new QueryWrapper<ProducesImages>().eq("produce_id", producesInfo.getId()).last("limit 1"));
            orderItems.setProducesImage(producesImages.getId());
            orderItems.setShopName(producesInfo.getShopName());
            orderItems.setFreightPrice(producesSku.getFreightPrice());
            orderItemsService.save(orderItems);
            sum += producesSku.getProducesPrice();
        }

        orders.setAmountPayable(sum);
        orders.setOrderStatus(0);
        orders.setPaymentMethods("支付宝");
        orders.setIsPay(0);
        orders.setIsPost(0);
        orders.setIsReceipted(0);
        orders.setAlipayNo("");
//        orders.setProducesCategory();

        orderService.updateById(orders);

        return CommonResult.success("true");
    }

    @App
    @Web
    @RequestMapping(value = "/app/order", method = RequestMethod.GET)
    public CommonResult<AppOrderResult> appOrder() {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));
        if (Objects.isNull(user)){
            return CommonResult.fail("该用户不存在");
        }



        AppOrderResult appOrderResult = new AppOrderResult();

        List<Orders> ordersList = orderService.list(new QueryWrapper<Orders>().eq("user_id", userId).eq("is_pay",1).eq("is_receipted",0));

        List<MyOrderResult> myOrderResults = new ArrayList<>();
        //1238
        for (Orders orders : ordersList) {

            List<OrderItems> shopNameGroup = orderItemsService.list(new QueryWrapper<OrderItems>().select("shop_name").eq("order_id", orders.getId()).groupBy("shop_name"));
            for (OrderItems orderItems : shopNameGroup) {
                MyOrderResult myOrderResult = new MyOrderResult();
                myOrderResult.setOrderId(orders.getId());
                myOrderResult.setCreateTime(orders.getCreateTime());
                myOrderResult.setShopName(orderItems.getShopName());

                List<MyOrderResult.MyGoods> collect = orderItemsService.list(new QueryWrapper<OrderItems>().eq("shop_name", myOrderResult.getShopName()).eq("order_id", orders.getId())).stream()
                        .map(orderItems1 -> {
                            MyOrderResult.MyGoods myGoods = new MyOrderResult.MyGoods();
                            myGoods.setProduceId(orderItems1.getProducesId());
                            myGoods.setProduceName(orderItems1.getProducesName());

                            ProducesImages produceImage = producesImagesService.getOne(new QueryWrapper<ProducesImages>().eq("produce_id", orderItems1.getProducesId()).last("limit 1"));
                            myGoods.setProduceImage(produceImage.getImageAddress());
                            myGoods.setProduceWeight(orderItems1.getProducesWeight());
                            myGoods.setProducePer(orderItems1.getProducesPer());
                            return myGoods;
                        }).collect(Collectors.toList());
                myOrderResult.setMyGoodsList(collect);

                myOrderResults.add(myOrderResult);
            }

        }

        appOrderResult.setMyOrderList(myOrderResults);
        return CommonResult.success(appOrderResult);
    }

    @App
    @Web
    @RequestMapping(value = "/app/order/category", method = RequestMethod.GET)
    public CommonResult<AppOrderResult> appOrderByState(@RequestParam("isPost") Integer isPost, @RequestParam("isReceipted") Integer IsReceipted) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));
        if (Objects.isNull(user)){
            return CommonResult.fail("该用户不存在");
        }



        AppOrderResult appOrderResult = new AppOrderResult();

        List<Orders> ordersList = orderService.list(new QueryWrapper<Orders>().eq("user_id", userId).eq("is_pay",1).eq("is_receipted",IsReceipted).eq("is_post",isPost));

        List<MyOrderResult> myOrderResults = new ArrayList<>();
        //1238
        for (Orders orders : ordersList) {

            List<OrderItems> shopNameGroup = orderItemsService.list(new QueryWrapper<OrderItems>().select("shop_name").eq("order_id", orders.getId()).groupBy("shop_name"));
            for (OrderItems orderItems : shopNameGroup) {
                MyOrderResult myOrderResult = new MyOrderResult();
                myOrderResult.setOrderId(orders.getId());
                myOrderResult.setCreateTime(orders.getCreateTime());
                myOrderResult.setShopName(orderItems.getShopName());

                List<MyOrderResult.MyGoods> collect = orderItemsService.list(new QueryWrapper<OrderItems>().eq("shop_name", myOrderResult.getShopName()).eq("order_id", orders.getId())).stream()
                        .map(orderItems1 -> {
                            MyOrderResult.MyGoods myGoods = new MyOrderResult.MyGoods();
                            myGoods.setProduceId(orderItems1.getProducesId());
                            myGoods.setProduceName(orderItems1.getProducesName());

                            ProducesImages produceImage = producesImagesService.getOne(new QueryWrapper<ProducesImages>().eq("produce_id", orderItems1.getProducesId()).last("limit 1"));
                            myGoods.setProduceImage(produceImage.getImageAddress());
                            myGoods.setProduceWeight(orderItems1.getProducesWeight());
                            myGoods.setProducePer(orderItems1.getProducesPer());
                            return myGoods;
                        }).collect(Collectors.toList());
                myOrderResult.setMyGoodsList(collect);

                myOrderResults.add(myOrderResult);
            }

        }

        appOrderResult.setMyOrderList(myOrderResults);
        return CommonResult.success(appOrderResult);
    }

    @App
    @Web
    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public CommonResult<NoData> refund(@RequestBody RefundReasonParam refundReasonParam) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));
        User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));

        OrderRefundReason orderRefundReason = new OrderRefundReason();
        orderRefundReason.setOrderId(refundReasonParam.getOrderId());

        Orders order = orderService.getOne(new QueryWrapper<Orders>().eq("id", refundReasonParam.getOrderId()));
        OrderItems orderItems = orderItemsService.getOne(new QueryWrapper<OrderItems>().eq("id", refundReasonParam.getOrderId()));
        orderRefundReason.setRefundReason(refundReasonParam.getRefundReason());
        orderRefundReason.setOrderNumber(order.getOrderNumber());
        orderRefundReason.setProduceId(refundReasonParam.getProduceId());
        orderRefundReason.setUserId(user.getId());
        orderRefundReason.setUserName(user.getNickname());
        orderRefundReason.setUserPhone(user.getPhone());

        orderRefundReasonService.save(orderRefundReason);

        return CommonResult.success("true");
    }

    @App
    @Web
    @RequestMapping(value = "/arrival", method = RequestMethod.POST)
    public CommonResult<NoData> refund(@RequestBody Integer id) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        Orders order = new Orders();
        order.setId(id);
        order.setOrderStatus(2);
        orderService.updateById(order);

        return CommonResult.success("true");
    }


    @App
    @Web
    @RequestMapping(value =  "/order/management", method = RequestMethod.GET)
    public CommonResult<OrderManagementListResult> getOrderManagement(){

        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        List<OrderManagementListResult.OrderManagementResult> collect = orderService.list(new QueryWrapper<Orders>().eq("shop_id",userId)).stream()
                .map(order -> {

                    OrderManagementListResult.OrderManagementResult orderManagementResult = new OrderManagementListResult.OrderManagementResult();

                    orderManagementResult.setId(order.getId());
                    orderManagementResult.setOrderNumber(order.getOrderNumber());
                    orderManagementResult.setCreateTime(order.getCreateTime());
                    orderManagementResult.setAmountPayable(order.getAmountPayable());
                    orderManagementResult.setIsPay(order.getIsPay());
                    orderManagementResult.setIsPost(order.getIsPost());

                    return orderManagementResult;
                }).collect(Collectors.toList());

        OrderManagementListResult orderManagementListResult = new OrderManagementListResult();
        orderManagementListResult.setOrderManagementResult(collect);
        return CommonResult.success(orderManagementListResult);
    }

    @App
    @Web
    @RequestMapping(value =  "/order/details", method = RequestMethod.GET)
    public CommonResult<OrderDetailsResult> getOrderManagementById(@RequestParam Integer id){

        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        OrderDetailsResult orderDetailsResult = new OrderDetailsResult();

        Orders order = orderService.getOne(new QueryWrapper<Orders>().eq("id", id));
        OrderItems orderItems = orderItemsService.getOne(new QueryWrapper<OrderItems>().eq("order_id", id).last("limit 1"));

        orderDetailsResult.setId(order.getId());
        orderDetailsResult.setOrderNumber(order.getOrderNumber());
        orderDetailsResult.setProducesId(orderItems.getProducesId());
        orderDetailsResult.setProducesName(orderItems.getProducesName());
        orderDetailsResult.setProducesWeight(orderItems.getProducesWeight());
        orderDetailsResult.setCustomerId(order.getUserId());
        orderDetailsResult.setCustomerName(order.getUserName());
        orderDetailsResult.setCustomerPhone(order.getUserPhone());
        orderDetailsResult.setAddress(order.getAddress());
        orderDetailsResult.setOrderRemark(order.getOrderRemark());
        orderDetailsResult.setAmountPayable(order.getAmountPayable());
        orderDetailsResult.setPaymentMethods(order.getPaymentMethods());

        return CommonResult.success(orderDetailsResult);
    }

    @App
    @Web
    @RequestMapping(value =  "/order/address", method = RequestMethod.POST)
    public CommonResult<NoData> updateOrderManagementById(@RequestBody OrderAddressParam orderAddressParam){

        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        Orders order = new Orders();

        order.setId(orderAddressParam.getId());
        order.setCreateTime(new Date());
        order.setAddress(orderAddressParam.getAddress());

        orderService.updateById(order);

        return CommonResult.success("true");
    }

    @App
    @Web
    @RequestMapping(value =  "/orders", method = RequestMethod.DELETE)
    public CommonResult<NoData> deleteOrders(@RequestBody WebParam webParam){

        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        orderService.removeByIds(Arrays.asList(webParam.getItems()));

        return CommonResult.success("true");
    }

    @App
    @Web
    @RequestMapping(value =  "/orders", method = RequestMethod.POST)
    public CommonResult<NoData> updateIsReceipted(@RequestBody WebParam webParam){

        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        List<Orders> orders = orderService.listByIds(Arrays.asList(webParam.getItems()));
        System.out.println(orders);
        List<Orders> ordersList = orders.stream().map(order -> {
            order.setIsReceipted(1);

            return order;

        }).collect(Collectors.toList());

        orderService.updateBatchById(ordersList);

        return CommonResult.success("true");
    }

    @App
    @Web
    @RequestMapping(value =  "/post", method = RequestMethod.POST)
    public CommonResult<NoData> updateIsPost(@RequestBody WebParam webParam){

        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        List<Orders> orders = orderService.listByIds(Arrays.asList(webParam.getItems()));
        System.out.println(orders);
        List<Orders> ordersList = orders.stream().map(order -> {
            order.setIsPost(1);

            return order;

        }).collect(Collectors.toList());

        orderService.updateBatchById(ordersList);

        return CommonResult.success("true");
    }

    @App
    @Web
    @RequestMapping(value =  "/produces/management", method = RequestMethod.GET)
    public CommonResult<ProducesManagementListResult> producesManagement(){

        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        List<ProducesManagementListResult.ProducesManagementResult> producesManagementResultList = producesService.list().stream()
                .map(producesInfo -> {

                    ProducesManagementListResult.ProducesManagementResult producesManagementResult = new ProducesManagementListResult.ProducesManagementResult();

                    Integer id = producesInfo.getId();
                    producesManagementResult.setId(id);
                    producesManagementResult.setProducesName(producesInfo.getProducesName());

                    ProducesSku producesSku = producesSkuService.getOne(new QueryWrapper<ProducesSku>().eq("produce_id", id).last("limit 1"));
                    producesManagementResult.setProducesPrice(producesSku.getProducesPrice());

                    producesManagementResult.setProducesStatus(producesInfo.getProducesStatus());
                    producesManagementResult.setCreateTime(producesInfo.getCreateTime());

                    return producesManagementResult;
                }).collect(Collectors.toList());

        ProducesManagementListResult producesManagementListResult = new ProducesManagementListResult();
        producesManagementListResult.setProducesManagementResults(producesManagementResultList);
        return CommonResult.success(producesManagementListResult);
    }

    @App
    @Web
    @RequestMapping(value =  "/produces", method = RequestMethod.DELETE)
    public CommonResult<NoData> deleteProduces(@RequestBody WebParam webParam){
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        List<ProducesInfo> producesInfos = producesService.listByIds(Arrays.asList(webParam.getItems()));
        System.out.println(producesInfos);
        for (ProducesInfo producesInfo : producesInfos) {

            producesSkuService.remove(new QueryWrapper<ProducesSku>().eq("produce_id",producesInfo.getId()));
            producesImagesService.remove(new QueryWrapper<ProducesImages>().eq("produce_id",producesInfo.getId()));
        }

        producesService.removeByIds(Arrays.asList(webParam.getItems()));

        return CommonResult.success("true");
    }

    @App
    @Web
    @RequestMapping(value =  "/produce/status", method = RequestMethod.POST)
    public CommonResult<NoData> updateProduceStatus(@RequestBody ProduceStatusParam produceStatusParam){
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        ProducesInfo producesInfo = new ProducesInfo();

        producesInfo.setId(produceStatusParam.getId());

        Integer producesStatus = produceStatusParam.getProducesStatus();

        if (producesStatus == 0){
            producesStatus = 0;
            producesInfo.setProducesStatus(producesStatus);
            producesService.updateById(producesInfo);
        }

        if (producesStatus == 1){
            producesStatus = 1;
            producesInfo.setProducesStatus(producesStatus);
            producesService.updateById(producesInfo);
        }

        return CommonResult.success("true");
    }


    @App
    @Web
    @RequestMapping(value =  "/produces", method = RequestMethod.POST)
    public CommonResult<NoData> updateProduce(ProduceWebParam produceWebParam, MultipartFile... files) throws IOException {

        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));

        System.out.println("上传的图片数："+files.length);

        for (MultipartFile file : files) {


            if (Objects.isNull(file)) continue;

            ProducesInfo producesInfo = new ProducesInfo();
            ProducesSku producesSku = new ProducesSku();
            ProducesImages producesImages = new ProducesImages();

            producesInfo.setProducesName(produceWebParam.getProducesName());
            producesInfo.setProducesDescription(produceWebParam.getProducesDescription());
            producesInfo.setProducesStatus(0);

            producesService.save(producesInfo);

            Integer id = producesInfo.getId();

            producesSku.setProduceId(id);
//            producesSku.setShoppingAddress(produceWebParam.getShoppingAddress());
//            producesSku.setShopName(user.getNickname());
            producesSkuService.save(producesSku);

            producesImages.setProduceId(id);

            String originalFilename = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();


            String name = "/produce/"+producesImages.getId()+".jpeg";

            producesImages.setImageAddress("https://image.yaohy.cn/intelligent-farming"+name);
            producesImagesService.updateById(producesImages);

            MinioUtil.uploadJpg(file,name);

        }

        return CommonResult.success("true");
    }



    @App
    @Web
    @RequestMapping(value =  "/produce/search", method = RequestMethod.POST)
    public CommonResult<ProducesManagementListResult> produceSearch(String id, String name){
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        QueryWrapper<ProducesInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper = new QueryWrapper();
        if (id != "" && id != null){
            queryWrapper.like("id",id);
        }

        if (name != "" && name != null){
            queryWrapper.like("produce_name",name);
        }

        List<ProducesManagementListResult.ProducesManagementResult> producesManagementResultList = producesService.list(queryWrapper).stream()
                .map(producesInfo -> {

                    ProducesManagementListResult.ProducesManagementResult producesManagementResult = new ProducesManagementListResult.ProducesManagementResult();
                    producesManagementResult.setId(producesInfo.getId());
                    producesManagementResult.setProducesName(producesInfo.getProducesName());

                    ProducesSku producesSku = producesSkuService.getOne(new QueryWrapper<ProducesSku>().eq("produce_id", producesInfo.getId()));
                    producesManagementResult.setProducesPrice(producesSku.getProducesPrice());

                    producesManagementResult.setProducesStatus(producesInfo.getProducesStatus());
                    producesManagementResult.setCreateTime(producesInfo.getCreateTime());

                    return producesManagementResult;
                }).collect(Collectors.toList());

        ProducesManagementListResult producesManagementListResult = new ProducesManagementListResult();
        producesManagementListResult.setProducesManagementResults(producesManagementResultList);
        return CommonResult.success(producesManagementListResult);

    }

    @App
    @Web
    @RequestMapping(value =  "/order/search", method = RequestMethod.POST)
    public CommonResult<OrderManagementListResult> orderSearch(String number, Double price){
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper = new QueryWrapper();

        if (price != 0 && price != null){

            queryWrapper.like("amount_payable",price);
            orderService.list(queryWrapper);
        }

        if (number != "" && price != null){

            queryWrapper.like("order_number",number);
            orderService.list(queryWrapper);
        }

        List<OrderManagementListResult.OrderManagementResult> collect = orderService.list(queryWrapper).stream()
                .map(order -> {

                    OrderManagementListResult.OrderManagementResult orderManagementResult = new OrderManagementListResult.OrderManagementResult();

                    orderManagementResult.setId(order.getId());
                    orderManagementResult.setOrderNumber(order.getOrderNumber());
                    orderManagementResult.setCreateTime(order.getCreateTime());
                    orderManagementResult.setAmountPayable(order.getAmountPayable());
                    orderManagementResult.setIsPay(order.getIsPay());
                    orderManagementResult.setIsPost(order.getIsPost());

                    return orderManagementResult;
                }).collect(Collectors.toList());

        OrderManagementListResult orderManagementListResult = new OrderManagementListResult();
        orderManagementListResult.setOrderManagementResult(collect);
        return CommonResult.success(orderManagementListResult);

    }

    @App
    @Web
    @RequestMapping(value =  "/myOrders", method = RequestMethod.GET)
    public CommonResult<MyOrderListResult> myOrders(){
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        List<MyOrderListResult.MyOrder> myOrderList = orderService.list(new QueryWrapper<Orders>().eq("user_id", userId)).stream()
                .map(orders -> {

                    MyOrderListResult.MyOrder myOrderListResult = new MyOrderListResult.MyOrder();

                    myOrderListResult.setId(orders.getId());
                    myOrderListResult.setOrderNumber(orders.getOrderNumber());

                    OrderItems orderItem = orderItemsService.getOne(new QueryWrapper<OrderItems>().eq("order_id", orders.getId()).last("limit 1"));
                    ProducesImages producesImage = producesImagesService.getOne(new QueryWrapper<ProducesImages>().eq("produce_id", orderItem.getProducesId()).last("limit 1"));
                    myOrderListResult.setProducesPrice(orderItem.getProducesPrice());
                    myOrderListResult.setProducesWeight(orderItem.getProducesWeight());
                    myOrderListResult.setAllPay(orderItem.getProducesPrice() * orderItem.getProducesWeight());
                    myOrderListResult.setIsPay(0);
                    myOrderListResult.setIsPost(0);
                    myOrderListResult.setCreateTime(orders.getCreateTime());
                    myOrderListResult.setUrl(producesImage.getImageAddress());
                    return myOrderListResult;
                }).collect(Collectors.toList());

        MyOrderListResult myOrderListResult = new MyOrderListResult();
        myOrderListResult.setMyOrderList(myOrderList);

        return CommonResult.success(myOrderListResult);
    }


    //是否已经收货
    @App
    @Web
    @RequestMapping(value =  "/myOrders/receipted", method = RequestMethod.POST)
    public CommonResult<NoData> receipted(@RequestBody Integer id){
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        if (userId == null){
            return CommonResult.fail("用户不存在");
        }

        Orders orders = new Orders();
        orders.setId(id);
        orders.setIsReceipted(1);
        orderService.updateById(orders);

        return CommonResult.success("true");
    }


    @App
    @Web
    @RequestMapping(value =  "/equipment", method = RequestMethod.GET)
    public CommonResult<EquipmentHomeResult> getEquipments(){

        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        EquipmentHomeResult equipmentHomeResult = new EquipmentHomeResult();



        List<EquipmentHomeResult.Equipments> collect = producesService.list(new QueryWrapper<ProducesInfo>().eq("produces_category", 3)).stream()
                .map(producesInfo -> {

                    EquipmentHomeResult.Equipments equipments = new EquipmentHomeResult.Equipments();

                    ProducesSku producesSku = producesSkuService.getOne(new QueryWrapper<ProducesSku>().eq("id", producesInfo.getId()));

                    equipments.setProducesId(producesInfo.getId());
                    equipments.setProducesName(producesInfo.getProducesName());

                    ProducesImages producesImage = producesImagesService.getOne(new QueryWrapper<ProducesImages>().eq("produce_id", producesInfo.getId()).last("limit 1"));
                    equipments.setProducesImage(producesImage.getImageAddress());
                    equipments.setProducesPrice(producesSku.getProducesPrice());

                    return equipments;
                }).collect(Collectors.toList());


//        equipmentHomeResult.setSwiperImages();
        equipmentHomeResult.setEquipmentsList(collect);

        return CommonResult.success(equipmentHomeResult);
    }

    @App
    @Web
    @RequestMapping(value =  "/equipment/detail", method = RequestMethod.GET)
    public CommonResult<EquipmentDetailResult> getEquipmentInfo(@RequestParam Integer produceId){

        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        EquipmentDetailResult equipmentDetailResult = new EquipmentDetailResult();

        ProducesInfo producesInfo = producesService.getOne(new QueryWrapper<ProducesInfo>().eq("id", produceId));
        ProducesSku producesSku = producesSkuService.getOne(new QueryWrapper<ProducesSku>().eq("produce_id", produceId));
        ProducesImages producesImages = producesImagesService.getOne(new QueryWrapper<ProducesImages>().eq("produce_id", produceId).last("limit 1"));

        equipmentDetailResult.setProduceId(producesInfo.getId());
        equipmentDetailResult.setProduceSkuId(producesSku.getId());
        equipmentDetailResult.setProduceName(producesInfo.getProducesName());
        equipmentDetailResult.setProduceImage(producesImages.getImageAddress());
        equipmentDetailResult.setProducePrice(producesSku.getProducesPrice());
        equipmentDetailResult.setFreightPrice(producesSku.getFreightPrice());
        equipmentDetailResult.setProduceDescription(producesInfo.getProducesDescription());
//        equipmentDetailResult.setProducesSpecification(producesInfo.getProducesSpecification());

        return CommonResult.success(equipmentDetailResult);
    }

    //设备详情点购买后的界面
//    @App
//    @Web
//    @RequestMapping(value =  "/equipment/shopping", method = RequestMethod.GET)
//    public CommonResult<NoData> getEquipmentInfo(@RequestParam Double produceWeight){
//
//        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));
//
//
//
//        return CommonResult.success();
//    }

    @App
    @Web
    @RequestMapping(value =  "/equipment/orders", method = RequestMethod.GET)
    public CommonResult<EquipmentOrderCenterResult> getEquipmentOrders(){
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        EquipmentOrderCenterResult equipmentOrderCenterResult = new EquipmentOrderCenterResult();

        List<EquipmentOrderCenterResult.OrderManagementResult> collect = orderService.list(new QueryWrapper<Orders>().eq("produces_category", 3).eq("user_id", userId)).stream()
                .map(orders -> {
                    EquipmentOrderCenterResult.OrderManagementResult orderManagementResult = new EquipmentOrderCenterResult.OrderManagementResult();

                    orderManagementResult.setId(orders.getId());
                    orderManagementResult.setOrderNumber(orders.getOrderNumber());

                    OrderItems orderItems = orderItemsService.getOne(new QueryWrapper<OrderItems>().eq("order_id", orders.getId()));

                    orderManagementResult.setProducesName(orderItems.getProducesName());

                    ProducesImages producesImages = producesImagesService.getOne(new QueryWrapper<ProducesImages>().eq("produce_id", orderItems.getProducesId()).last("limit 1"));
                    orderManagementResult.setProducesImage(producesImages.getImageAddress());
                    orderManagementResult.setProducesPrice(orderItems.getProducesPrice());
                    orderManagementResult.setProducesPer(orderItems.getProducesPer());
                    orderManagementResult.setProducesWeight(orderItems.getProducesWeight());
                    orderManagementResult.setAmountPayable(orders.getAmountPayable());
                    orderManagementResult.setCreateTime(orders.getCreateTime());
                    orderManagementResult.setIsPay(orders.getIsPay());
                    orderManagementResult.setIsPost(orders.getIsPost());

                    return orderManagementResult;
                }).collect(Collectors.toList());
        equipmentOrderCenterResult.setOrderManagementResult(collect);
        return CommonResult.success(equipmentOrderCenterResult);
    }

    @App
    @Web
    @RequestMapping(value =  "/equipment/", method = RequestMethod.DELETE)
    public CommonResult<NoData> deleteEquipmentOrder(@RequestParam Integer produceId){
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        orderItemsService.remove(new QueryWrapper<OrderItems>().eq("order_id",produceId));
        orderService.remove(new QueryWrapper<Orders>().eq("id",produceId));

        return CommonResult.success("取消订单成功");
    }

    @App
    @Web
    @RequestMapping(value =  "/web/order", method = RequestMethod.POST)
    public CommonResult<OrderAlipayResult> submitWebOrder(@RequestBody WebSubmitOrderParam webSubmitOrderParam){
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));
        ProducesSku producesSku = producesSkuService.getOne(new QueryWrapper<ProducesSku>().eq("id", webSubmitOrderParam.getProduceSkuId()));
        ProducesInfo producesInfo = producesService.getOne(new QueryWrapper<ProducesInfo>().eq("id", producesSku.getProduceId()));

        Orders orders = new Orders();
        String orderNo = new SnowFlakeOrderNumber(0, 0).createOrderNo();
        orders.setOrderNumber(orderNo);
        orders.setCreateTime(new Date());
        orders.setUserId(user.getId());
        orders.setUserName(user.getNickname());
        orders.setUserPhone(user.getPhone());
        orders.setAddress(webSubmitOrderParam.getAddress());

        orders.setLogisticsCompany("");
        orders.setLogisticsNumber("");
        orders.setAmountPayable(0.00);
        orders.setOrderSource("");
        orders.setProducesCategory(3);
        orders.setShopId(0);
        orders.setOrderStatus(0);
        orders.setPayTime(new Date());
        orderService.save(orders);

        Double sum = 0.0;
        OrderItems orderItems = new OrderItems();

        orderItems.setOrderId(orders.getId());
        orderItems.setProducesId(producesInfo.getId());
        orderItems.setProducesName(producesInfo.getProducesName());
        orderItems.setProducesPrice(producesSku.getProducesPrice());
        orderItems.setProducesWeight(webSubmitOrderParam.getProducesWeight());
        orderItems.setProducesPer(producesSku.getProducesPer());
        ProducesImages producesImages = producesImagesService.getOne(new QueryWrapper<ProducesImages>().eq("produce_id", producesInfo.getId()).last("limit 1"));
        orderItems.setProducesImage(producesImages.getId());
        orderItems.setShopName(producesInfo.getShopName());
        orderItems.setFreightPrice(producesSku.getFreightPrice());
        orderItemsService.save(orderItems);
        sum = (producesSku.getProducesPrice() * webSubmitOrderParam.getProducesWeight()) + producesSku.getFreightPrice();

        orders.setAmountPayable(sum);
        orders.setOrderStatus(0);
        orders.setPaymentMethods("支付宝");
        orders.setIsPay(0);
        orders.setIsPost(0);
        orders.setIsReceipted(0);
        orders.setAlipayNo("");
//        orders.setProducesCategory();

        orderService.updateById(orders);

        OrderAlipayResult orderAlipayResult = new OrderAlipayResult();
        orderAlipayResult.setOrderNumber(orderNo);
        return CommonResult.success(orderAlipayResult);
    }

    static <T> Predicate<T> distinctByKey1(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
