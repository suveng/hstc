package com.blgg.permission.modules.sys.service;

import com.blgg.permission.modules.sys.entity.RoleMenu;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 服务类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
public interface RoleMenuService extends IService<RoleMenu> {

    //查询角色对应菜单
    List<Long> queryMenuIdList(long roleId);

    //保存角色与菜单关系
    void saveOrUpdate(Long roleId, List<Long> menuIdList);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);
}
