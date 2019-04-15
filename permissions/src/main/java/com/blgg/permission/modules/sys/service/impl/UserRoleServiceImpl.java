package com.blgg.permission.modules.sys.service.impl;

import com.blgg.permission.common.utils.MapUtils;
import com.blgg.permission.modules.sys.entity.UserRole;
import com.blgg.permission.modules.sys.dao.UserRoleMapper;
import com.blgg.permission.modules.sys.service.UserRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 服务实现类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public List<Long> queryRoleIdList(Long userId) {
        return baseMapper.queryRoleIdList(userId);
    }

    @Override
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        //先删除用户与角色关系
        this.deleteByMap(new MapUtils().put("user_id",userId));

        if (roleIdList==null||roleIdList.size()==0){
            return;
        }

        //保存用户与角色关系
        List<UserRole> list=new ArrayList<>(roleIdList.size());
        for (Long roleId:roleIdList){
            UserRole userRole=new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);;

            list.add(userRole);
        }
        this.insertBatch(list);
    }

    //删除用户与角色关系
    @Override
    public int deleteBatch(Long[] roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }
}
