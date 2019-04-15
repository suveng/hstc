package com.blgg.permission.modules.sys.service;

import com.blgg.permission.common.utils.PageUtils;
import com.blgg.permission.modules.sys.entity.Role;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
public interface RoleService extends IService<Role> {

    //角色列表
    PageUtils queryPage(Map<String,Object> params);

    //保存角色
    void save(Role role);

    //修改角色
    void update(Role role);

    //删除角色
    void deleteBatch(Long[] roleIds);

}
