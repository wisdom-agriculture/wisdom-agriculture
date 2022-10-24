package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Dept {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer parentId;
    private String ancestors;
    private String deptName;
    private String leader;
    private String phone;
    private String email;
    private String status;
    private Date createTime;
}
