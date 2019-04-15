package com.blgg.permission.modules.sys.controller;


import com.blgg.permission.common.annotation.SysLog;
import com.blgg.permission.common.utils.PageUtils;
import com.blgg.permission.common.utils.R;
import com.blgg.permission.common.utils.ValidatorUtils;
import com.blgg.permission.modules.sys.entity.Role;
import com.blgg.permission.modules.sys.service.RoleDeptService;
import com.blgg.permission.modules.sys.service.RoleMenuService;
import com.blgg.permission.modules.sys.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
@RestController
@RequestMapping("/sys/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleDeptService roleDeptService;

    @Autowired
    private RoleMenuService roleMenuService;


    //角色列表
    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    public R list(@RequestParam Map<String,Object> params){
        PageUtils page=roleService.queryPage(params);
        return R.ok().put("page",page);
    }


    //选择角色（添加，修改菜单）
    @RequestMapping("/select")
    @RequiresPermissions("sys:role:select")
    public R select(){
        List<Role> list=roleService.selectList(null);
        return R.ok().put("list",list);
    }

    //角色信息
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public R info(@PathVariable("roleId") Long roleId){
        Role role=roleService.selectById(roleId);

        //查询角色对应的菜单
        List<Long> menuIdList=roleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);

        //查询角色对应的部门
        List<Long> deptIdList=roleDeptService.queryDeptIdList(new Long[]{roleId});
        role.setDeptIdList(deptIdList);

        return R.ok().put("role",role);
    }


    /**
     * 保存角色
     */
    @SysLog("保存角色")
    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    public R save(@RequestBody Role role){
        ValidatorUtils.validateEntity(role);

        roleService.save(role);

        return R.ok();
    }

    /**
     * 修改角色
     */
    @SysLog("修改角色")
    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    public R update(@RequestBody Role role){
        ValidatorUtils.validateEntity(role);

        roleService.update(role);

        return R.ok();
    }

    /**
     * 删除角色
     */
    @SysLog("删除角色")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public R delete(@RequestBody Long[] roleIds){
        roleService.deleteBatch(roleIds);

        return R.ok();
    }

}

