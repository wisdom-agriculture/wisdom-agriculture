package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class ProduceResult {

    private Integer id;

    private List<ImagesList> imagesList;

    private String producesName;

    private String producesDescription;

    private Integer producesStatus;

    private Double lowProducesPrice;

    private Double higProducesPrice;

    private String shoppingAddress;

    private String shoppingAddressRemark;

    private Boolean isFavorites;

    private List<ProduceSkuInfo> produceSkuInfoList;

    private List<ReviewResult> reviewResult;

    private Integer count;
    @Data
    public static class ImagesList{

        private Integer produceId;

        private String imageAddress;

        private Integer imageOrder;
    }

    @Data
    public static class ProduceSkuInfo{

        private Integer producesSkuId;

        private String producesSpecification;

        private Double producesPrice;

        private Double producesWeight;

        private String producesPer;

    }

    @Data
    public static class ReviewResult{

        private Integer ReviewId;

        private String userName;

        private String userImage;

        private String content;

        private Date createTime;

        private List<String> reviewImages;

    }
}
