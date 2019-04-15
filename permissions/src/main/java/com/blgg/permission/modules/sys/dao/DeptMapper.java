package com.blgg.permission.modules.sys.dao;

import com.blgg.permission.modules.sys.entity.Dept;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 部门管理 Mapper 接口
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
public interface DeptMapper extends BaseMapper<Dept> {

    /**
     * 查询子部门ID列表
     * @param parentId  上级部门ID
     */
    List<Long> queryDeptIdList(Long parentId);
}
