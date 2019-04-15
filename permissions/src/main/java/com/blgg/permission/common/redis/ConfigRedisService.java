package com.blgg.permission.common.redis;

import com.blgg.permission.common.redis.RedisUtils;
import com.blgg.permission.modules.sys.entity.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 *
 * @ClassName ConfigRedisService
 * @Description 系统配置Redis
 * @Author xiaobo
 * @Date 2018/10/15/015 5:29
 */
@Component
public class ConfigRedisService {

    @Autowired
    private RedisUtils redisUtils;


    public void saveOrUpdate(Config config) {
        if(config == null){
            return ;
        }
        String key = RedisKeys.getSysConfigKey(config.getParamKey());
        redisUtils.set(key, config);
    }

    public void delete(String configKey) {
        String key = RedisKeys.getSysConfigKey(configKey);
        redisUtils.delete(key);
    }

    public Config get(String configKey){
        String key = RedisKeys.getSysConfigKey(configKey);
        return redisUtils.get(key, Config.class);
    }

}
