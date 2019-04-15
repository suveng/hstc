package com.blgg.permission.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.blgg.permission.common.redis.ConfigRedisService;
import com.blgg.permission.common.utils.PageUtils;
import com.blgg.permission.common.utils.Query;
import com.blgg.permission.modules.sys.entity.Config;
import com.blgg.permission.modules.sys.dao.ConfigMapper;
import com.blgg.permission.modules.sys.service.ConfigService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;

/**
 * <p>
 * 系统配置信息表 服务实现类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    @Autowired
    private ConfigRedisService configRedisService;

    //分页列表
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String paramKey = (String) params.get("paramKey");

        Page<Config> page=this.selectPage(
                new Query<Config>(params).getPage(),
                new EntityWrapper<Config>().like(StringUtils.isNotBlank(paramKey)
                ,"paramKey",paramKey).eq(
                        "status",1
                )
        );

        return new PageUtils(page);
    }

    //保存配置
    @Override
    public void save(Config config) {
        this.insert(config);
//        configRedisService.saveOrUpdate(config);
    }

    //修改配置
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Config config) {
        this.updateAllColumnById(config);
//        configRedisService.saveOrUpdate(config);
    }

    //删除配置
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] ids) {
        for (Long id:ids){
            Config config=this.selectById(id);
//            configRedisService.delete(config.getParamKey());
        }

        this.deleteBatchIds(Arrays.asList(ids));
    }
}
