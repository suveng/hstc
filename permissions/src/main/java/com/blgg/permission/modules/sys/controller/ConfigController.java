package com.blgg.permission.modules.sys.controller;


import com.blgg.permission.common.annotation.SysLog;
import com.blgg.permission.common.utils.PageUtils;
import com.blgg.permission.common.utils.R;
import com.blgg.permission.common.utils.ValidatorUtils;
import com.blgg.permission.modules.sys.entity.Config;
import com.blgg.permission.modules.sys.service.ConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * <p>
 * 系统配置信息表 前端控制器
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
@RestController
@RequestMapping("/sys/config")
public class ConfigController extends BaseController{

    @Autowired
    private ConfigService configService;

    //所有配置列表
    @RequestMapping("/list")
    @RequiresPermissions("sys:config:list")
    public R list(@RequestParam Map<String,Object> params){
        PageUtils page=configService.queryPage(params);

        return R.ok().put("page",page);
    }

    //配置信息
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        Config config=configService.selectById(id);

        return R.ok().put("config",config);
    }


    /**
     * 保存配置
     */
    @SysLog("保存配置")
    @RequestMapping("/save")
    @RequiresPermissions("sys:config:save")
    public R save(@RequestBody Config config){
        ValidatorUtils.validateEntity(config);

        configService.save(config);

        return R.ok();
    }

    /**
     * 修改配置
     */
    @SysLog("修改配置")
    @RequestMapping("/update")
    @RequiresPermissions("sys:config:update")
    public R update(@RequestBody Config config){
        ValidatorUtils.validateEntity(config);

        configService.update(config);

        return R.ok();
    }

    /**
     * 删除配置
     */
    @SysLog("删除配置")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:config:delete")
    public R delete(@RequestBody Long[] ids){
        configService.deleteBatch(ids);

        return R.ok();
    }




}

