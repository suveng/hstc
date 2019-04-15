package com.blgg.permission.modules.sys.controller;


import com.blgg.permission.common.annotation.SysLog;
import com.blgg.permission.common.shiro.ShiroUtil;
import com.blgg.permission.common.utils.*;
import com.blgg.permission.modules.sys.entity.User;
import com.blgg.permission.modules.sys.service.UserRoleService;
import com.blgg.permission.modules.sys.service.UserService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
@RestController
@RequestMapping("/sys/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;


    //所有用户列表
    @RequestMapping("/list")
    public R list(@RequestParam Map<String,Object> params){
        PageUtils page=userService.queryPage(params);
        return R.ok().put("page",page);
    }


    //获取登录用户的信息
    @RequestMapping("/info")
    public R info(){
        return R.ok().put("user",getUser());
    }


    //修改登录用户密码
    @SysLog("修改密码")
    @RequestMapping("/password")
    public R modifyPassword(String password,String newPassword){
        DataUtils.isBlank(newPassword,"新密码不能为空");

        //旧密码
        password=ShiroUtil.sha256(password,getUser().getSalt());

        //新密码
        newPassword=ShiroUtil.sha256(newPassword,getUser().getSalt());

        //更新密码
        boolean flag=userService.updatePassword(getUserId(),password,newPassword);
        if (!flag){
            return R.error("原密码不正确");
        }
        return R.ok();
    }

    /**
     * 用户信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public R info(@PathVariable("userId") Long userId){
        User user = userService.selectById(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = userRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return R.ok().put("user", user);
    }


    /**
     * 新增用户
     */
    @SysLog("保存用户")
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    public R save(@RequestBody User user){
        ValidatorUtils.validateEntity(user, AddGroup.class);

        userService.save(user);

        return R.ok();
    }


    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public R update(@RequestBody User user){
        ValidatorUtils.validateEntity(user, UpdateGroup.class);

        userService.update(user);

        return R.ok();
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public R delete(@RequestBody Long[] userIds){
        if(ArrayUtils.contains(userIds, 1L)){
            return R.error("系统管理员不能删除");
        }

        if(ArrayUtils.contains(userIds, getUserId())){
            return R.error("当前用户不能删除");
        }

        userService.deleteBatchIds(Arrays.asList(userIds));

        return R.ok();
    }



}

