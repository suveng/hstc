package com.blgg.permission.modules.sys.dao;

import com.blgg.permission.modules.sys.entity.RoleMenu;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 Mapper 接口
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {


    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Long> queryMenuIdList(long roleId);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);

}
