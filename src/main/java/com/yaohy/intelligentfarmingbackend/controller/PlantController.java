package com.yaohy.intelligentfarmingbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.App;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.Web;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Plant;
import com.yaohy.intelligentfarmingbackend.pojo.domain.PlantAttribute;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.PlantAttributeParam;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.PlantParam;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.PlantAttrResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.PlantAttrListResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.PlantListResult;
import com.yaohy.intelligentfarmingbackend.service.PlantAttributeService;
import com.yaohy.intelligentfarmingbackend.service.PlantService;
import com.yaohy.intelligentfarmingbackend.util.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class PlantController {

    @Autowired
    private PlantService plantService;
    @Autowired
    private PlantAttributeService plantAttributeService;

    @App
    @Web
    @RequestMapping(value = "/plants", method = RequestMethod.POST)
    public CommonResult add(@RequestParam("name") String name,
                            @RequestParam("img") MultipartFile multipartFile) {
        Plant plant = new Plant();
        plant.setName(name);
        plantService.save(plant);

        String path = "/plant/" + plant.getId() + ".jpeg";
        plant.setImgUrl("https://image.yaohy.cn/intelligent-farming" + path);

        plantService.updateById(plant);
        MinioUtil.uploadJpg(multipartFile, path);

        return CommonResult.success();
    }

    @App
    @Web
    @RequestMapping(value = "/plants", method = RequestMethod.PUT)
    public CommonResult update(@RequestParam("id") Integer id,
                               @RequestParam("name") String name,
                               @RequestParam("img") MultipartFile multipartFile) {
        Plant plant = new Plant();
        plant.setId(id);
        plant.setName(name);
        plantService.updateById(plant);

        String path = "/plant/" + plant.getId() + ".jpeg";
        plant.setImgUrl("https://image.yaohy.cn/intelligent-farming" + path);

        plantService.updateById(plant);
        MinioUtil.uploadJpg(multipartFile, name);

        return CommonResult.success();
    }

    @App
    @Web
    @RequestMapping(value = "/plants", method = RequestMethod.GET)
    public CommonResult get() {
        List<Plant> plantList = plantService.list();
//        List<PlantAttribute> plantAttributeList = plantAttributeService.list();
//
//        ArrayList<PlantAttrResult> plantAttrResultArrayList = new ArrayList<>();
//
//        plantList.forEach(plant -> {
//            PlantAttrResult plantAttrResult = new PlantAttrResult();
//            plantAttrResult.setId(plant.getId());
//            plantAttrResult.setName(plant.getName());
//            plantAttrResult.setAttrList(plantAttributeList.stream().filter(
//                    plantAttribute -> Objects.equals(plantAttribute.getPlantId(), plant.getId())
//            ).collect(Collectors.toList()));
//            plantAttrResultArrayList.add(plantAttrResult);
//        });
//
//        PlantListResult plantListResult = new PlantListResult();
//        plantListResult.setPlantList(plantAttrResultArrayList);

        PlantListResult plantListResult = new PlantListResult();
        plantListResult.setPlantList(plantList);

        return CommonResult.success(plantListResult);
    }

    @App
    @Web
    @RequestMapping(value = "/plants/attrs", method = RequestMethod.POST)
    public CommonResult addAttr(@RequestBody PlantAttributeParam param) {

        Integer id = param.getId();
        List<PlantAttributeParam.attr> attrList = param.getAttrList();
        attrList.forEach(attr -> {
            PlantAttribute plantAttribute = new PlantAttribute();
            plantAttribute.setPlantId(id);
            plantAttribute.setName(attr.getName());
            plantAttribute.setDescription(attr.getDescription());

            plantAttributeService.save(plantAttribute);
        });

        return CommonResult.success();
    }

    @App
    @Web
    @RequestMapping(value = "/plants/attrs", method = RequestMethod.PUT)
    public CommonResult updateAttr(@RequestBody PlantAttributeParam param) {

        Integer id = param.getId();
        List<PlantAttributeParam.attr> attrList = param.getAttrList();

        plantAttributeService.removeByIds(
                plantAttributeService.list(
                        new QueryWrapper<PlantAttribute>().eq("plant_id", id))
                        .stream().map(PlantAttribute::getId).collect(Collectors.toList()));

        attrList.forEach(attr -> {
            PlantAttribute plantAttribute = new PlantAttribute();
            plantAttribute.setPlantId(id);
            plantAttribute.setName(attr.getName());
            plantAttribute.setDescription(attr.getDescription());

            plantAttributeService.save(plantAttribute);
        });

        return CommonResult.success();
    }

    @App
    @Web
    @RequestMapping(value = "/plants/attrs", method = RequestMethod.GET)
    public CommonResult get(@RequestParam("ids") Integer[] ids) {

        List<PlantAttrResult> plantAttrResultList = Arrays.stream(ids).map(id -> {
            Plant plant = plantService.getById(id);
            List<PlantAttribute> plantAttributeList = plantAttributeService.list(new QueryWrapper<PlantAttribute>().eq("plant_id", plant.getId()));
            PlantAttrResult plantAttrResult = new PlantAttrResult();
            plantAttrResult.setId(plant.getId());
            plantAttrResult.setName(plant.getName());
            plantAttrResult.setImg(plant.getImgUrl());
            plantAttrResult.setAttrList(plantAttributeList);

            return plantAttrResult;
        }).collect(Collectors.toList());

        PlantAttrListResult plantAttrListResult = new PlantAttrListResult();
        plantAttrListResult.setPlantList(plantAttrResultList);

        return CommonResult.success(plantAttrListResult);
    }

    @App
    @Web
    @RequestMapping(value = "/plants/recommendation", method = RequestMethod.GET)
    public CommonResult recommend() {

        List<Plant> plantList = plantService.list();
        if (plantList.isEmpty()) return CommonResult.success();
        Collections.shuffle(plantList);
        Plant plant = plantList.get(0);
        List<PlantAttribute> plantAttributeList = plantAttributeService.list(new QueryWrapper<PlantAttribute>().eq("plant_id", plant.getId()));
        PlantAttrResult plantAttrResult = new PlantAttrResult();
        plantAttrResult.setId(plant.getId());
        plantAttrResult.setName(plant.getName());
        plantAttrResult.setImg(plant.getImgUrl());
        plantAttrResult.setAttrList(plantAttributeList);

        return CommonResult.success(plantAttrResult);
    }

}
