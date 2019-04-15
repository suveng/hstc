package com.blgg.permission.modules.sys.dao;

import com.blgg.permission.modules.sys.entity.Menu;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<Menu> queryListParentId(Long parentId);


    List<Menu> queryNoButtonList();
}
