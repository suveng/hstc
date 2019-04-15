package com.blgg.permission.modules.sys.controller;


import com.blgg.permission.common.utils.PageUtils;
import com.blgg.permission.common.utils.R;
import com.blgg.permission.common.utils.ValidatorUtils;
import com.blgg.permission.modules.sys.entity.Dict;
import com.blgg.permission.modules.sys.service.DictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.Map;

/**
 * <p>
 * 数据字典表 前端控制器
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
@RestController
@RequestMapping("/sys/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    //字典列表
    @RequestMapping("/list")
    @RequiresPermissions("sys:dict:list")
    public R list(@RequestParam Map<String,Object> params){
        PageUtils page=dictService.queryPage(params);

        return R.ok().put("page",page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:dict:info")
    public R info(@PathVariable("id") Long id){
        Dict dict = dictService.selectById(id);

        return R.ok().put("dict", dict);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dict:save")
    public R save(@RequestBody Dict dict){
        //校验类型
        ValidatorUtils.validateEntity(dict);

        dictService.insert(dict);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dict:update")
    public R update(@RequestBody Dict dict){
        //校验类型
        ValidatorUtils.validateEntity(dict);

        dictService.updateById(dict);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dict:delete")
    public R delete(@RequestBody Long[] ids){
        dictService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }



}

