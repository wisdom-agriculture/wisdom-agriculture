package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.List;

@Data
public class UserShoppingAddressListResult{

    private List<UserShoppingAddressResult> userShoppingAddressResults;

    @Data
    public static class UserShoppingAddressResult {

        private Integer id;

        private String userShoppingAddress;

        private String userName;

        private String userPhone;

    }

}
