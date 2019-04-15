package com.blgg.permission.modules.sys.service.impl;

import com.blgg.permission.modules.sys.entity.RoleDept;
import com.blgg.permission.modules.sys.dao.RoleDeptMapper;
import com.blgg.permission.modules.sys.service.RoleDeptService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色与部门对应关系 服务实现类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
@Service
public class RoleDeptServiceImpl extends ServiceImpl<RoleDeptMapper, RoleDept> implements RoleDeptService {

    //查询角色对应部门
    @Override
    public List<Long> queryDeptIdList(Long[] longs) {
        return baseMapper.queryDeptIdList(longs);
    }

    //
    @Override
    public int deleteBatch(Long[] roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }

    @Override
    public void saveOrUpdate(Long roleId, List<Long> deptIdList) {
        //先删除角色与部门关系
        deleteBatch(new Long[]{roleId});

        if(deptIdList.size() == 0){
            return ;
        }

        //保存角色与菜单关系
        List<RoleDept> list = new ArrayList<>(deptIdList.size());
        for(Long deptId : deptIdList){
            RoleDept roleDept = new RoleDept();
            roleDept.setDeptId(deptId);
            roleDept.setRoleId(roleId);

            list.add(roleDept);
        }
        this.insertBatch(list);
    }
}
