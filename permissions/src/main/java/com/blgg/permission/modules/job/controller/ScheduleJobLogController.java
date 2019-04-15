package com.blgg.permission.modules.job.controller;


import com.blgg.permission.common.utils.PageUtils;
import com.blgg.permission.common.utils.R;
import com.blgg.permission.modules.job.entity.ScheduleJobLog;
import com.blgg.permission.modules.job.service.ScheduleJobLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 定时任务日志 前端控制器
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-16
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController {

    @Autowired
    private ScheduleJobLogService scheduleJobLogService;


    /**
     * 定时任务日志列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:schedule:log")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = scheduleJobLogService.queryPage(params);

        R page1 = R.ok().put("page", page);
        return page1;
    }

    /**
     * 定时任务日志信息
     */
    @RequestMapping("/info/{logId}")
    public R info(@PathVariable("logId") Long logId){
        ScheduleJobLog log = scheduleJobLogService.selectById(logId);

        return R.ok().put("log", log);
    }

}

