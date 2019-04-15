package com.blgg.permission.modules.sys.service;

import com.blgg.permission.common.utils.PageUtils;
import com.blgg.permission.modules.sys.entity.Log;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
public interface LogService extends IService<Log> {

    //分页
    PageUtils queryPage(Map<String,Object> params);
}
