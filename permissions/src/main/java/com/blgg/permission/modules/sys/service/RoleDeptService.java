package com.blgg.permission.modules.sys.service;

import com.blgg.permission.modules.sys.entity.RoleDept;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 角色与部门对应关系 服务类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
public interface RoleDeptService extends IService<RoleDept> {

    /**
     * 根据角色ID，获取部门ID列表
     */
    List<Long> queryDeptIdList(Long[] roleIds) ;

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);

    void saveOrUpdate(Long roleId, List<Long> deptIdList);
}
