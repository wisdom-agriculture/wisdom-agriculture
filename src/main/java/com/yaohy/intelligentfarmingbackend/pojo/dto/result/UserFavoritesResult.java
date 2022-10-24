package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.List;

@Data
public class UserFavoritesResult {

        private String shopName;

        private List<UserFavoritesItemResults> userFavoritesItemResults;
        @Data
        public static class UserFavoritesItemResults{

                private Integer id;
                private String producesName;
                private Integer producesId;
                private String producesDescription;
                //        private String producesSpecification;
                private Double producesPrice;
                private String producesPer;
                private String producesImage;

        }



}
