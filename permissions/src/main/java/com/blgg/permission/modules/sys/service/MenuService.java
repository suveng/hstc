package com.blgg.permission.modules.sys.service;

import com.blgg.permission.modules.sys.entity.Menu;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
public interface MenuService extends IService<Menu> {

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @param menuIdList  用户菜单ID
     */
    List<Menu> queryListParentId(Long parentId, List<Long> menuIdList);

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<Menu> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<Menu> queryNotButtonList();

    /**
     * 获取用户菜单列表
     */
    List<Menu> getUserMenuList(Long userId);

    /**
     * 删除
     */
    void delete(Long menuId);
}
