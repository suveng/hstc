package com.blgg.permission.modules.sys.service;

import com.blgg.permission.modules.sys.entity.UserRole;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 服务类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
public interface UserRoleService extends IService<UserRole> {

    //根据用户ID获取角色ID
    List<Long> queryRoleIdList(Long userId);

    //保存用户与角色关系
    void saveOrUpdate(Long userId, List<Long> roleIdList);

    //删除用户与角色关系
    int deleteBatch(Long[] roleIds);
}
