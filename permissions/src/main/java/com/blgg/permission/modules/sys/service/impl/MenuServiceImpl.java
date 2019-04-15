package com.blgg.permission.modules.sys.service.impl;

import com.blgg.permission.common.utils.Constant;
import com.blgg.permission.common.utils.MapUtils;
import com.blgg.permission.modules.sys.entity.Menu;
import com.blgg.permission.modules.sys.dao.MenuMapper;
import com.blgg.permission.modules.sys.service.MenuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.blgg.permission.modules.sys.service.RoleMenuService;
import com.blgg.permission.modules.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private UserService userService;


    @Override
    public List<Menu> queryListParentId(Long parentId, List<Long> menuIdList) {
        List<Menu> menuList = queryListParentId(parentId);
        if (menuIdList==null){
            return menuList;
        }

        List<Menu> menuList1=new ArrayList<>();
        for (Menu menu:menuList){
            if (menuIdList.contains(menu.getMenuId())){
                menuList1.add(menu);
            }
        }
        return menuList1;
    }

    //查询出parent_id为零的菜单
    @Override
    public List<Menu> queryListParentId(Long parentId) {
        return baseMapper.queryListParentId(parentId);
    }

    @Override
    public List<Menu> queryNotButtonList() {
        return baseMapper.queryNoButtonList();
    }

    @Override
    public List<Menu> getUserMenuList(Long userId) {
        //超级管理员，拥有最高权限
        if (userId==Constant.SUPER_ADMIN){
            return getAllMenuList(null);
        }

        //用户列表菜单
        List<Long> menuIdList=userService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    @Override
    public void delete(Long menuId) {
        //删除菜单
        this.deleteById(menuId);
        //删除菜单与角色关联
        roleMenuService.deleteByMap(new MapUtils().put("menu_id",menuId));
    }


    /**
     * 获取所有菜单列表
     */
    private List<Menu> getAllMenuList(List<Long> menuIdList){
        //查询根菜单列表
        List<Menu> menuList = queryListParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归
     */
    private List<Menu> getMenuTreeList(List<Menu> menuList, List<Long> menuIdList){
        List<Menu> subMenuList = new ArrayList<Menu>();

        for(Menu entity : menuList){
            //目录
            if(entity.getType() == Constant.MenuType.CATALOG.getValue()){
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }
}
