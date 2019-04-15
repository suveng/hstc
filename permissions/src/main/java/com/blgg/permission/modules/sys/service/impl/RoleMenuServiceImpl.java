package com.blgg.permission.modules.sys.service.impl;

import com.blgg.permission.modules.sys.entity.RoleMenu;
import com.blgg.permission.modules.sys.dao.RoleMenuMapper;
import com.blgg.permission.modules.sys.service.RoleMenuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 服务实现类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {



    //查询角色对应菜单
    @Override
    public List<Long> queryMenuIdList(long roleId) {
        return baseMapper.queryMenuIdList(roleId);
    }

    //保存角色与菜单关系
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
        //先删除角色与菜单关系
        deleteBatch(new Long[]{roleId});

        if(menuIdList.size() == 0){
            return ;
        }

        //保存角色与菜单关系
        List<RoleMenu> list = new ArrayList<>(menuIdList.size());
        for(Long menuId : menuIdList){
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(roleId);

            list.add(roleMenu);
        }
        this.insertBatch(list);
    }

    @Override
    public int deleteBatch(Long[] roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }


}
