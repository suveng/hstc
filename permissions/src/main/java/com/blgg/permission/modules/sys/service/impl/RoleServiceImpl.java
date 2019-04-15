package com.blgg.permission.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.blgg.permission.common.annotation.DataFilter;
import com.blgg.permission.common.utils.Constant;
import com.blgg.permission.common.utils.PageUtils;
import com.blgg.permission.common.utils.Query;
import com.blgg.permission.modules.sys.entity.Dept;
import com.blgg.permission.modules.sys.entity.Role;
import com.blgg.permission.modules.sys.dao.RoleMapper;
import com.blgg.permission.modules.sys.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private RoleDeptService roleDeptService;
    @Autowired
    private UserRoleService userRoleService;


    //角色列表
    @Override
    @DataFilter(subDept = true,user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String roleName= (String) params.get("roleName");

        Page<Role> page=this.selectPage(
                new Query<Role>(params).getPage(),
                new EntityWrapper<Role>()
                .like(StringUtils.isNotBlank(roleName),"role_name", roleName)
                .addFilterIfNeed(params.get(Constant.SQL_FILTER)!=null, (String) params.get(Constant.SQL_FILTER))
        );

        for (Role role:page.getRecords()){
            Dept dept=deptService.selectById(role.getDeptId());
            if (dept!=null){
                role.setDeptName(dept.getName());
            }
        }
        return new PageUtils(page);
    }

    //保存角色
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Role role) {
        role.setCreateTime(new Date());
        this.insert(role);

        //保存角色与菜单关系
        roleMenuService.saveOrUpdate(role.getRoleId(),role.getMenuIdList());

        //保存角色与部门关系
        roleDeptService.saveOrUpdate(role.getRoleId(),role.getDeptIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Role role) {
        this.updateAllColumnById(role);

        //更新角色与菜单关系
        roleMenuService.saveOrUpdate(role.getRoleId(),role.getMenuIdList());

        //更新角色与部门关系
        roleDeptService.saveOrUpdate(role.getRoleId(),role.getDeptIdList());
    }

    @Override
    public void deleteBatch(Long[] roleIds) {
        //删除角色
        this.deleteBatchIds(Arrays.asList(roleIds));

        //删除角色与菜单关联
        roleMenuService.deleteBatch(roleIds);

        //删除角色与部门关联
        roleDeptService.deleteBatch(roleIds);

        //删除角色与用户关联
        userRoleService.deleteBatch(roleIds);
    }


}
