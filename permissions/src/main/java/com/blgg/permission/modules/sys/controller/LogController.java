package com.blgg.permission.modules.sys.controller;


import com.blgg.permission.common.utils.PageUtils;
import com.blgg.permission.common.utils.R;
import com.blgg.permission.modules.sys.service.LogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
@Controller
@RequestMapping("/sys/log")
public class LogController {

    @Autowired
    private LogService logService;

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("sys:log:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = logService.queryPage(params);

        return R.ok().put("page", page);
    }

}

