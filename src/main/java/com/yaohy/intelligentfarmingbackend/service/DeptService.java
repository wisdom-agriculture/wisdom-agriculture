package com.yaohy.intelligentfarmingbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Dept;

import java.util.List;

/**
 * @author Valentine
 */
public interface DeptService extends IService<Dept> {
    /**
     * 查询部门管理数据
     * @param dept
     * @return
     */
    public List<Dept> selectDeptList(Dept dept);

    /**
     * 构建树结构
     * @param depts
     * @return
     */
    public List<Dept> buildDeptTree(List<Dept> depts);

    /**
     * 根据部门ID查询信息
     * @param deptId
     * @return
     */
    public Dept selectDeptById(Integer deptId);

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    public int selectNormalChildrenDeptById(Integer deptId);

    /**
     * 是否存在部门子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public boolean hasChildByDeptId(Long deptId);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkDeptExistUser(Long deptId);

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    public boolean checkDeptNameUnique(Dept dept);

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int insertDept(Dept dept);

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int updateDept(Dept dept);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteDeptById(Long deptId);
}
