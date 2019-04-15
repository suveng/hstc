package com.blgg.permission.modules.sys.service;

import com.blgg.permission.modules.sys.entity.Dept;
import com.baomidou.mybatisplus.service.IService;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 部门管理 服务类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
public interface DeptService extends IService<Dept> {

    //查询部门列表
    List<Dept> queryList(HashMap<String,Object> stringObjectHashMap);

    /**
     * 查询子部门ID列表
     * @param parentId  上级部门ID
     */
    List<Long> queryDetpIdList(Long parentId);

    /**
     * 获取子部门ID，用于数据过滤
     */
    List<Long> getSubDeptIdList(Long deptId);
}
