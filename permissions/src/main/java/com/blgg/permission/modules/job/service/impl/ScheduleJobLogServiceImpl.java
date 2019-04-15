package com.blgg.permission.modules.job.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.blgg.permission.common.utils.PageUtils;
import com.blgg.permission.common.utils.Query;
import com.blgg.permission.modules.job.entity.ScheduleJobLog;
import com.blgg.permission.modules.job.dao.ScheduleJobLogMapper;
import com.blgg.permission.modules.job.service.ScheduleJobLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 定时任务日志 服务实现类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-16
 */
@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogMapper, ScheduleJobLog> implements ScheduleJobLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String jobId = (String)params.get("jobId");

        Page<ScheduleJobLog> page = this.selectPage(
                new Query<ScheduleJobLog>(params).getPage(),
                new EntityWrapper<ScheduleJobLog>().like(StringUtils.isNotBlank(jobId),"job_id", jobId)
        );

        return new PageUtils(page);
    }

}
