package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.DeptMapper;
import com.yaohy.intelligentfarmingbackend.mapper.RoleMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Dept;
import com.yaohy.intelligentfarmingbackend.service.DeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Valentine
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {
    @Resource
    private RoleMapper roleMapper;

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Override
    public List<Dept> selectDeptList(Dept dept)
    {
        return null;
    }

    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    @Override
    public List<Dept> buildDeptTree(List<Dept> depts)
    {
        List<Dept> returnList = new ArrayList<Dept>();
        List<Long> tempList = new ArrayList<Long>();
        for (Dept dept : depts)
        {
            tempList.add(dept.getId().longValue());
        }
        for (Iterator<Dept> iterator = depts.iterator(); iterator.hasNext();)
        {
            Dept dept = (Dept) iterator.next();
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


    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public Dept selectDeptById(Integer deptId)
    {
        return baseMapper.selectById(deptId);
    }

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    @Override
    public int selectNormalChildrenDeptById(Integer deptId)
    {
        //return baseMapper.selectNormalChildrenDeptById(deptId);
        return 0;
    }

    /**
     * 是否存在子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public boolean hasChildByDeptId(Long deptId)
    {
        Long result = baseMapper.selectCount(new QueryWrapper<Dept>().eq("parent_id", deptId));
        return result > 0 ? true : false;
    }

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId)
    {
        int result = 0;//baseMapper.checkDeptExistUser(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public boolean checkDeptNameUnique(Dept dept)
    {
        Long deptId = dept.getId() == null ? -1L : dept.getId();
        Dept info = null;//baseMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (info != null && info.getId().longValue() != deptId.longValue())
        {
            return false;
        }
        return true;
    }

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int insertDept(Dept dept)
    {
//        Dept info = null//baseMapper.selectDeptById(dept.getParentId());
//        // 如果父节点不为正常状态,则不允许新增子节点
//        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus()))
//        {
//            throw new Exception("部门停用，不允许新增");
//        }
//        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
//        return baseMapper.insertDept(dept);
        return 0;
    }

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int updateDept(Dept dept)
    {
        Dept newParentDept = baseMapper.selectById(dept.getParentId());
        Dept oldDept = baseMapper.selectById(dept.getId());
        if (newParentDept != null && oldDept != null)
        {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getId().longValue(), newAncestors, oldAncestors);
        }
        int result = baseMapper.updateById(dept);
        if ("1".equals(dept.getStatus()))
        {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatus(dept);
        }
        return result;
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatus(Dept dept)
    {
        dept = baseMapper.selectById(dept.getId());
        baseMapper.updateById(dept);
    }

    /**
     * 修改子元素关系
     *
     * @param deptId 被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors)
    {
        List<Dept> children = null;//baseMapper.selectChildrenDeptById(deptId);
        for (Dept child : children)
        {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            //baseMapper.updateDeptChildren(children);
        }
    }

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(Long deptId)
    {
        return baseMapper.deleteById(deptId);
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<Dept> list, Dept t)
    {
        // 得到子节点列表
        List<Dept> childList = getChildList(list, t);
        //t.setChildren(childList);
        for (Dept tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<Dept> getChildList(List<Dept> list, Dept t)
    {
        List<Dept> tlist = new ArrayList<Dept>();
        Iterator<Dept> it = list.iterator();
        while (it.hasNext())
        {
            Dept n = (Dept) it.next();
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
    private boolean hasChild(List<Dept> list, Dept t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
