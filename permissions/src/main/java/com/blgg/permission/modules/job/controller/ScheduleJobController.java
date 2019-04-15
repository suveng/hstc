package com.blgg.permission.modules.job.controller;


import com.blgg.permission.common.annotation.SysLog;
import com.blgg.permission.common.utils.PageUtils;
import com.blgg.permission.common.utils.R;
import com.blgg.permission.common.utils.ValidatorUtils;
import com.blgg.permission.modules.job.entity.ScheduleJob;
import com.blgg.permission.modules.job.service.ScheduleJobService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * <p>
 * 定时任务 前端控制器
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-16
 */
@RestController
@RequestMapping("sys/schedule")
public class ScheduleJobController {

    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
	 * 定时任务列表
	 */
    @RequestMapping("/list")
    @RequiresPermissions("sys:schedule:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = scheduleJobService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 定时任务信息
     */
    @RequestMapping("/info/{jobId}")
    @RequiresPermissions("sys:schedule:info")
    public R info(@PathVariable("jobId") Long jobId){
        ScheduleJob schedule = scheduleJobService.selectById(jobId);

        return R.ok().put("schedule", schedule);
    }

    /**
     * 保存定时任务
     */
    @SysLog("保存定时任务")
    @RequestMapping("/save")
    @RequiresPermissions("sys:schedule:save")
    public R save(@RequestBody ScheduleJob scheduleJob){
        ValidatorUtils.validateEntity(scheduleJob);

        scheduleJobService.save(scheduleJob);

        return R.ok();
    }

    /**
     * 修改定时任务
     */
    @SysLog("修改定时任务")
    @RequestMapping("/update")
    @RequiresPermissions("sys:schedule:update")
    public R update(@RequestBody ScheduleJob scheduleJob){
        ValidatorUtils.validateEntity(scheduleJob);

        scheduleJobService.update(scheduleJob);

        return R.ok();
    }

    /**
     * 删除定时任务
     */
    @SysLog("删除定时任务")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:schedule:delete")
    public R delete(@RequestBody Long[] jobIds){
        scheduleJobService.deleteBatch(jobIds);

        return R.ok();
    }

    /**
     * 立即执行任务
     */
    @SysLog("立即执行任务")
    @RequestMapping("/run")
    @RequiresPermissions("sys:schedule:run")
    public R run(@RequestBody Long[] jobIds){
        scheduleJobService.run(jobIds);

        return R.ok();
    }

    /**
     * 暂停定时任务
     */
    @SysLog("暂停定时任务")
    @RequestMapping("/pause")
    @RequiresPermissions("sys:schedule:pause")
    public R pause(@RequestBody Long[] jobIds){
        scheduleJobService.pause(jobIds);

        return R.ok();
    }

    /**
     * 恢复定时任务
     */
    @SysLog("恢复定时任务")
    @RequestMapping("/resume")
    @RequiresPermissions("sys:schedule:resume")
    public R resume(@RequestBody Long[] jobIds){
        scheduleJobService.resume(jobIds);

        return R.ok();
    }

}

