package com.blgg.permission.modules.job.service;

import com.blgg.permission.common.utils.PageUtils;
import com.blgg.permission.modules.job.entity.ScheduleJobLog;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 定时任务日志 服务类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-16
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLog> {

    PageUtils queryPage(Map<String, Object> params);

}
