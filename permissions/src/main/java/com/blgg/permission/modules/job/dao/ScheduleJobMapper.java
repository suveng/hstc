package com.blgg.permission.modules.job.dao;

import com.blgg.permission.modules.job.entity.ScheduleJob;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.Map;

/**
 * <p>
 * 定时任务 Mapper 接口
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-16
 */
public interface ScheduleJobMapper extends BaseMapper<ScheduleJob> {
    /**
     * 批量更新状态
     */
    int updateBatch(Map<String, Object> map);

}
