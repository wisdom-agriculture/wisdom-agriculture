package com.yaohy.intelligentfarmingbackend.controller;

import com.yaohy.intelligentfarmingbackend.config.authority.annotation.App;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.Web;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.UploadResult;
import com.yaohy.intelligentfarmingbackend.util.MinioUtil;
import com.yaohy.intelligentfarmingbackend.util.TimeUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Random;

/**
 * @author Valnetine
 */
@RequestMapping("/upload")
@RestController
public class ImageController {
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    @Web
    @App
    public CommonResult uploadImage(MultipartFile multipartFile) {
        if (!multipartFile.isEmpty()){
            // 获取文件的后缀
            String fileFinalName = Objects.requireNonNull(multipartFile.getOriginalFilename()).substring(multipartFile.getOriginalFilename().lastIndexOf("."), multipartFile.getOriginalFilename().length());
            if (fileFinalName.equals(".jpg") || fileFinalName.equals(".png") || fileFinalName.equals(".PNG")){
                long time = TimeUtil.getDate().getTime();
                Random random = new Random();

                String name = "/image/" + time + random.nextInt(1000) +".jpg";
                MinioUtil.uploadJpg(multipartFile,name);

                return CommonResult.success(new UploadResult("https://image.yaohy.cn/intelligent-farming"+name));
            }
            else {
                return CommonResult.fail("文件格式错误，只支持 .jpg / .png 文件");
            }
        } else {
            return CommonResult.fail("图片上传失败");
        }
    }
}
