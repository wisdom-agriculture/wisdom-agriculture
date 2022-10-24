package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.List;

@Data
public class AppOrderResult {

    private List<MyOrderResult> myOrderList;

}
