package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.ZoneImageMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.ZoneImage;
import com.yaohy.intelligentfarmingbackend.service.ZoneImageService;
import com.yaohy.intelligentfarmingbackend.util.MinioUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Objects;

@Service
public class ZoneImageServiceImpl extends ServiceImpl<ZoneImageMapper, ZoneImage> implements ZoneImageService {
    @Override
    public void save(Integer zoneId, MultipartFile[] multipartFiles) {
        for (MultipartFile multipartFile : multipartFiles) {
            if (Objects.isNull(multipartFile)) continue;
            ZoneImage zoneImage = new ZoneImage();
            zoneImage.setZoneId(zoneId);
            baseMapper.insert(zoneImage);

            String name = "/zone/"+zoneImage.getId()+".jpeg";
            zoneImage.setUrl("https://image.yaohy.cn/intelligent-farming"+name);

            baseMapper.updateById(zoneImage);
            MinioUtil.uploadJpg(multipartFile,name);
        }
    }
}
