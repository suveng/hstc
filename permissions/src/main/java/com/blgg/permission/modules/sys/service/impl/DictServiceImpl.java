package com.blgg.permission.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.blgg.permission.common.utils.PageUtils;
import com.blgg.permission.common.utils.Query;
import com.blgg.permission.modules.sys.entity.Dict;
import com.blgg.permission.modules.sys.dao.DictMapper;
import com.blgg.permission.modules.sys.service.DictService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 数据字典表 服务实现类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    //字典列表
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name= (String) params.get("name");
        Page<Dict> page=this.selectPage(
                new Query<Dict>(params).getPage(),
                new EntityWrapper<Dict>().like(
                        StringUtils.isNotBlank(name),"name",name
                )
        );

        return new PageUtils(page);
    }
}
