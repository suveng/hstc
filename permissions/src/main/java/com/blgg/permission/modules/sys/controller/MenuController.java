package com.blgg.permission.modules.sys.controller;


import com.blgg.permission.common.annotation.SysLog;
import com.blgg.permission.common.exception.BLGGException;
import com.blgg.permission.common.utils.Constant;
import com.blgg.permission.common.utils.R;
import com.blgg.permission.modules.sys.entity.Menu;
import com.blgg.permission.modules.sys.service.MenuService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
@RestController
@RequestMapping("sys/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;


    //导航菜单
    @RequestMapping("/nav")
    public R nav(){
         List<Menu> menuList =menuService.getUserMenuList(getUserId());
         return R.ok().put("menuList",menuList);
    }


    /**
     * 所有菜单列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public List<Menu> list(){
        List<Menu> menuList = menuService.selectList(null);
        for(Menu menu: menuList){
            Menu parentMenu = menuService.selectById(menu.getParentId());
            if(parentMenu != null){
                menu.setParentName(parentMenu.getName());
            }
        }

        return menuList;
    }


    /**
     * 选择菜单(添加、修改菜单)
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public R select(){
        //查询列表数据
        List<Menu> menuList = menuService.queryNotButtonList();

        //添加顶级菜单
        Menu root = new Menu();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);

        return R.ok().put("menuList", menuList);
    }


    /**
     * 菜单信息
     */
    @RequestMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public R info(@PathVariable("menuId") Long menuId){
        Menu menu = menuService.selectById(menuId);
        return R.ok().put("menu", menu);
    }


    /**
     * 保存
     */
    @SysLog("保存菜单")
    @RequestMapping("/save")
    @RequiresPermissions("sys:menu:save")
    public R save(@RequestBody Menu menu){
        //数据校验
        verifyForm(menu);

        menuService.insert(menu);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改菜单")
    @RequestMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public R update(@RequestBody Menu menu){
        //数据校验
        verifyForm(menu);

        menuService.updateById(menu);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除菜单")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:menu:delete")
    public R delete(long menuId){
        if(menuId <= 31){
            return R.error("系统菜单，不能删除");
        }

        //判断是否有子菜单或按钮
        List<Menu> menuList = menuService.queryListParentId(menuId);
        if(menuList.size() > 0){
            return R.error("请先删除子菜单或按钮");
        }

        menuService.delete(menuId);

        return R.ok();
    }



    /**
     * 验证参数是否正确
     */
    private void verifyForm(Menu menu){
        if(StringUtils.isBlank(menu.getName())){
            throw new BLGGException("菜单名称不能为空");
        }

        if(menu.getParentId() == null){
            throw new BLGGException("上级菜单不能为空");
        }

        //菜单
        if(menu.getType() == Constant.MenuType.MENU.getValue()){
            if(StringUtils.isBlank(menu.getUrl())){
                throw new BLGGException("菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if(menu.getParentId() != 0){
            Menu parentMenu = menuService.selectById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if(menu.getType() == Constant.MenuType.CATALOG.getValue() ||
                menu.getType() == Constant.MenuType.MENU.getValue()){
            if(parentType != Constant.MenuType.CATALOG.getValue()){
                throw new BLGGException("上级菜单只能为目录类型");
            }
            return ;
        }

        //按钮
        if(menu.getType() == Constant.MenuType.BUTTON.getValue()){
            if(parentType != Constant.MenuType.MENU.getValue()){
                throw new BLGGException("上级菜单只能为菜单类型");
            }
            return ;
        }
    }





}

