package com.yaohy.intelligentfarmingbackend.pojo.dto.param.dept;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Valentine
 */
@Data
public class DeptBo {
    private Integer id;
    private Integer parentId;
    private String ancestors;
    private String deptName;
    private String leader;
    private String phone;
    private String email;
    private String status;
    private Date createTime;
    private List<DeptBo> children = new ArrayList<>();
}
