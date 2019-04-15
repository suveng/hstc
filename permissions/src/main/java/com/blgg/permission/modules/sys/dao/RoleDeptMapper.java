package com.blgg.permission.modules.sys.dao;

import com.blgg.permission.modules.sys.entity.RoleDept;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色与部门对应关系 Mapper 接口
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
public interface RoleDeptMapper extends BaseMapper<RoleDept> {

    List<Long> queryDeptIdList(Long[] longs);

    int deleteBatch(Long[] roleIds);
}
