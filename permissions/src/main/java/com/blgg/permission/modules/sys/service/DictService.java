package com.blgg.permission.modules.sys.service;

import com.blgg.permission.common.utils.PageUtils;
import com.blgg.permission.modules.sys.entity.Dict;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 数据字典表 服务类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
public interface DictService extends IService<Dict> {

    //列表
    PageUtils queryPage(Map<String,Object> params);
}
