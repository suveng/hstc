package com.blgg.permission.modules.sys.dao;

import com.blgg.permission.modules.sys.entity.UserRole;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 Mapper 接口
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Long> queryRoleIdList(Long userId);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);
}
