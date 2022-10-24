package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.List;

@Data
public class FavoritesListResult {
    private List<UserFavoritesResult> userFavoritesListResults;
}
