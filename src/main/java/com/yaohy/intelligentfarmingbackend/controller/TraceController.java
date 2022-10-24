package com.yaohy.intelligentfarmingbackend.controller;

import com.yaohy.intelligentfarmingbackend.constant.BlockChainConstant;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.trace.CropsIdParam;
import com.yaohy.intelligentfarmingbackend.util.request.RequestUtils;
import com.yaohy.intelligentfarmingbackend.util.request.dto.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Valentine
 */
@RestController
@RequestMapping("/trace")
public class TraceController {
    @RequestMapping(value = "/cropsInfo", method = RequestMethod.GET)
    public CommonResult trace(@RequestParam Long id) {

        Object result = RequestUtils.GET(BlockChainConstant.FARMER_API + "/queryCropsById", new Param("id", id));
        return CommonResult.success(result);
    }

    @RequestMapping(value = "/productInfo", method = RequestMethod.GET)
    public CommonResult getProductInfo(@RequestParam Long id) {
        Object result = RequestUtils.GET(BlockChainConstant.PRODUCT_API + "/queryProductInfoByCropsId", new Param("id", id));
        return CommonResult.success(result);
    }

    @RequestMapping(value = "/transportProcess", method = RequestMethod.GET)
    public CommonResult getTransport(@RequestParam Long id) {
        Object result = RequestUtils.GET(BlockChainConstant.Driver_API + "/queryTransportByCropsId", new Param("id", id));
        return CommonResult.success(result);
    }

    @RequestMapping(value = "/queryOperationByCropsId", method = RequestMethod.GET)
    public CommonResult getOperation(@RequestParam Long id) {
        Object result = RequestUtils.GET(BlockChainConstant.PRODUCT_API + "/queryOperationByCropsId", new Param("id", id));
        return CommonResult.success(result);
    }

    @RequestMapping(value = "/materialInfo", method = RequestMethod.GET)
    public CommonResult getMaterial(@RequestParam Long id) {
        Object result = RequestUtils.GET(BlockChainConstant.MATERIAL_API + "/queryMachiningByCropsId", new Param("id", id));
        return CommonResult.success(result);
    }

    @RequestMapping(value = "/cropsProcess", method = RequestMethod.GET)
    public CommonResult getCropsProcess(@RequestParam Long id) {
        Object result = RequestUtils.GET(BlockChainConstant.FARMER_API + "/queryCropsProcessByCropsId", new Param("id", id));
        return CommonResult.success(result);
    }

    @RequestMapping(value = "/retailerInfo", method = RequestMethod.GET)
    public CommonResult getRetailerInfo(@RequestParam Long id) {
        Object result = RequestUtils.GET(BlockChainConstant.RETAILER_API + "/queryRetailerById", new Param("id", id));
        return CommonResult.success(result);
    }
}
