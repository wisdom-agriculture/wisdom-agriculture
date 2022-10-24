package com.yaohy.intelligentfarmingbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaohy.intelligentfarmingbackend.pojo.domain.ZoneImage;
import org.springframework.web.multipart.MultipartFile;

public interface ZoneImageService extends IService<ZoneImage> {

    public void save(Integer zoneId, MultipartFile[] multipartFiles);

}
