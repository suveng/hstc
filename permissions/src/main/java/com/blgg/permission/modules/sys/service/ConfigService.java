package com.blgg.permission.modules.sys.service;

import com.blgg.permission.common.utils.PageUtils;
import com.blgg.permission.modules.sys.entity.Config;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 系统配置信息表 服务类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
public interface ConfigService extends IService<Config> {

    //分页列表
    PageUtils queryPage(Map<String,Object> params);

    /**
     * 保存配置信息
     */
    public void save(Config config);

    /**
     * 更新配置信息
     */
    public void update(Config config);

//    /**
//     * 根据key，更新value
//     */
//    public void updateValueByKey(String key, String value);

    /**
     * 删除配置信息
     */
    public void deleteBatch(Long[] ids);
}
