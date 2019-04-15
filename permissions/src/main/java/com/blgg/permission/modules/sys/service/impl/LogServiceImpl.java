package com.blgg.permission.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.blgg.permission.common.utils.PageUtils;
import com.blgg.permission.common.utils.Query;
import com.blgg.permission.modules.sys.entity.Log;
import com.blgg.permission.modules.sys.dao.LogMapper;
import com.blgg.permission.modules.sys.service.LogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key= (String) params.get("key");

        Page<Log> page=this.selectPage(
                new Query<Log>(params).getPage(),
                new EntityWrapper<Log>().like(StringUtils.isNotBlank(key)
                ,"username",key)
        );

        return new PageUtils(page);
    }
}
