package com.blgg.permission.modules.job.service;

import com.blgg.permission.common.utils.PageUtils;
import com.blgg.permission.modules.job.entity.ScheduleJob;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 定时任务 服务类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-16
 */
public interface ScheduleJobService extends IService<ScheduleJob> {
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存定时任务
     */
    void save(ScheduleJob scheduleJob);

    /**
     * 更新定时任务
     */
    void update(ScheduleJob scheduleJob);

    /**
     * 批量删除定时任务
     */
    void deleteBatch(Long[] jobIds);

    /**
     * 批量更新定时任务状态
     */
    int updateBatch(Long[] jobIds, int status);

    /**
     * 立即执行
     */
    void run(Long[] jobIds);

    /**
     * 暂停运行
     */
    void pause(Long[] jobIds);

    /**
     * 恢复运行
     */
    void resume(Long[] jobIds);

}
