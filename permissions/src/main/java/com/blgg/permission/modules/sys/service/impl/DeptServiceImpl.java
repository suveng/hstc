package com.blgg.permission.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.blgg.permission.common.utils.Constant;
import com.blgg.permission.modules.sys.entity.Dept;
import com.blgg.permission.modules.sys.dao.DeptMapper;
import com.blgg.permission.modules.sys.service.DeptService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 部门管理 服务实现类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
@Service("deptService")
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    //部门列表
    @Override
    public List<Dept> queryList(HashMap<String, Object> stringObjectHashMap) {
        List<Dept> deptList=this.selectList(new EntityWrapper<Dept>()
        .addFilterIfNeed(stringObjectHashMap.get(Constant.SQL_FILTER)!=null,
                (String) stringObjectHashMap.get(Constant.SQL_FILTER)));

        for (Dept dept:deptList){
            Dept parentDept=this.selectById(dept.getParentId());
            if (parentDept!=null){
                dept.setParentName(parentDept.getName());
            }
        }
        return deptList;
    }


    //上级部门
    @Override
    public List<Long> queryDetpIdList(Long parentId) {
        return baseMapper.queryDeptIdList(parentId);
    }

    @Override
    public List<Long> getSubDeptIdList(Long deptId) {
        //部门及子部门ID列表
        List<Long> deptIdList = new ArrayList<>();

        //获取子部门ID
        List<Long> subIdList = queryDetpIdList(deptId);
        getDeptTreeList(subIdList, deptIdList);

        return deptIdList;
    }


    /**
     * 递归
     */
    private void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList) {
        for (Long deptId : subIdList) {
            List<Long> list = queryDetpIdList(deptId);
            if (list.size() > 0) {
                getDeptTreeList(list, deptIdList);
            }

            deptIdList.add(deptId);
        }
    }


}
