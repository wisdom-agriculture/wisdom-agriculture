package com.yaohy.intelligentfarmingbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yaohy.intelligentfarmingbackend.mapper.DeptMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Dept;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.dept.DeptBo;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.dept.TreeSelect;
import com.yaohy.intelligentfarmingbackend.util.request.ClassCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Valentine
 */

@RequestMapping("/dept")
@RestController
public class DeptController {
    @Resource
    private DeptMapper deptMapper;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult getList() {
        List<Dept> depts = deptMapper.selectList(new QueryWrapper<>());

        List<DeptBo> deptBos = new ArrayList<>();
        for (Dept dept : depts) {
            DeptBo  deptBo = new DeptBo();
            ClassCopyUtils.ClassCopy(deptBo, dept);
            deptBos.add(deptBo);
        }

        List<TreeSelect> treeSelects = buildDeptTreeSelect(deptBos);

        return CommonResult.success(treeSelects);
    }

    public List<TreeSelect> buildDeptTreeSelect(List<DeptBo> depts)
    {
        List<DeptBo> deptTrees = buildDeptTree(depts);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }


    public List<DeptBo> buildDeptTree(List<DeptBo> depts)
    {
        List<DeptBo> returnList = new ArrayList<DeptBo>();
        List<Long> tempList = new ArrayList<Long>();
        for (DeptBo dept : depts)
        {
            tempList.add(dept.getId().longValue());
        }
        for (Iterator<DeptBo> iterator = depts.iterator(); iterator.hasNext();)
        {
            DeptBo dept = (DeptBo) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId()))
            {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = depts;
        }
        return returnList;
    }

    private void recursionFn(List<DeptBo> list, DeptBo t)
    {
        // 得到子节点列表
        List<DeptBo> childList = getChildList(list, t);
        t.setChildren(childList);
        for (DeptBo tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }
    private List<DeptBo> getChildList(List<DeptBo> list, DeptBo t)
    {
        List<DeptBo> tlist = new ArrayList<DeptBo>();
        Iterator<DeptBo> it = list.iterator();
        while (it.hasNext())
        {
            DeptBo n = (DeptBo) it.next();
            if (n.getParentId() != null && n.getParentId().longValue() == t.getId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<DeptBo> list, DeptBo t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
