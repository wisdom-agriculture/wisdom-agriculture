package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class ProduceReviewsImages {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer reviewId;

    private String reviewImages;

}
