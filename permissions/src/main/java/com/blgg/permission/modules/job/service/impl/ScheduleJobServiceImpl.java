package com.blgg.permission.modules.job.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.blgg.permission.common.utils.Constant;
import com.blgg.permission.common.utils.PageUtils;
import com.blgg.permission.common.utils.Query;
import com.blgg.permission.common.utils.ScheduleUtils;
import com.blgg.permission.modules.job.entity.ScheduleJob;
import com.blgg.permission.modules.job.dao.ScheduleJobMapper;
import com.blgg.permission.modules.job.service.ScheduleJobService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * <p>
 * 定时任务 服务实现类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-16
 */
@Service("scheduleJobService")
public class ScheduleJobServiceImpl extends ServiceImpl<ScheduleJobMapper, ScheduleJob> implements ScheduleJobService {

    @Autowired
    private Scheduler scheduler;


    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init(){
        List<ScheduleJob> scheduleJobList = this.selectList(null);
        for(ScheduleJob scheduleJob : scheduleJobList){
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            //如果不存在，则创建
            if(cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            }else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }




    //定时任务列表
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String beanName = (String)params.get("beanName");

        Page<ScheduleJob> page = this.selectPage(
                new Query<ScheduleJob>(params).getPage(),
                new EntityWrapper<ScheduleJob>().like(StringUtils.isNotBlank(beanName),"bean_name", beanName)
        );

        return new PageUtils(page);
    }

    //保存定时任务
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ScheduleJob scheduleJob) {
        scheduleJob.setCreateTime(new Date());
        scheduleJob.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
        this.insert(scheduleJob);

        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
    }

    //修改定时任务
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ScheduleJob scheduleJob) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);

        this.updateById(scheduleJob);
    }

    //删除定时任务
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] jobIds) {
        for(Long jobId : jobIds){
            ScheduleUtils.deleteScheduleJob(scheduler, jobId);
        }

        //删除数据
        this.deleteBatchIds(Arrays.asList(jobIds));
    }

    //批量更新定时任务状态
    @Override
    public int updateBatch(Long[] jobIds, int status) {
        Map<String, Object> map = new HashMap<>();
        map.put("list", jobIds);
        map.put("status", status);
        return baseMapper.updateBatch(map);
    }

    //立即执行任务
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(Long[] jobIds) {
        for(Long jobId : jobIds){
            ScheduleUtils.run(scheduler, this.selectById(jobId));
        }
    }

    //暂停定时任务
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pause(Long[] jobIds) {
        for(Long jobId : jobIds){
            ScheduleUtils.pauseJob(scheduler, jobId);
        }

        updateBatch(jobIds, Constant.ScheduleStatus.PAUSE.getValue());
    }

    //恢复定时任务
    @Override
    public void resume(Long[] jobIds) {
        for(Long jobId : jobIds){
            ScheduleUtils.resumeJob(scheduler, jobId);
        }

        updateBatch(jobIds, Constant.ScheduleStatus.NORMAL.getValue());
    }
}
