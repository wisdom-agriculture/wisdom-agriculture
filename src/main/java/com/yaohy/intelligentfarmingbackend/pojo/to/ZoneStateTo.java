package com.yaohy.intelligentfarmingbackend.pojo.to;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class ZoneStateTo implements Comparable<ZoneStateTo> {

    private Integer userId;
    private String nickname;
    private String imgUrl;
    private Integer Id;
    private String content;
    private String msg;
    private String img;
    private Long time;


    @Override
    public int compareTo(@NotNull ZoneStateTo o) {
        return (int) (o.getTime() - this.getTime());
    }
}
