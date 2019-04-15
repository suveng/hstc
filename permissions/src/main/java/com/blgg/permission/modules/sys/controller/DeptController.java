package com.blgg.permission.modules.sys.controller;


import com.blgg.permission.common.utils.Constant;
import com.blgg.permission.common.utils.R;
import com.blgg.permission.modules.sys.entity.Dept;
import com.blgg.permission.modules.sys.service.DeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 部门管理 前端控制器
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
@RestController
@RequestMapping("/sys/dept")
public class DeptController extends BaseController{

    @Autowired
    private DeptService deptService;


    //部门列表
    @RequestMapping("/list")
    @RequiresPermissions("sys:dept:list")
    public List<Dept> list(){
        List<Dept> deptList=deptService.queryList(new HashMap<String,Object>());
        return deptList;
    }


    //选择部门（添加，修改菜单）
    @RequestMapping("/select")
    @RequiresPermissions("sys:dept:select")
    public R select(){
        List<Dept> deptList=deptService.queryList(new HashMap<String, Object>());

        //添加一级部门
        if (getUserId()==Constant.SUPER_ADMIN){
            Dept root=new Dept();
            root.setDeptId(0L);
            root.setName("一级部门");
            root.setParentId(-1L);
            root.setOpen(true);
            deptList.add(root);
        }

        return R.ok().put("deptList",deptList);
    }


    //上级部门Id（管理员则是0）
    @RequestMapping("/info")
    @RequiresPermissions("sys:dept:list")
    public R info(){
        long deptId = 0;
        if(getUserId() != Constant.SUPER_ADMIN){
            List<Dept> deptList = deptService.queryList(new HashMap<String, Object>());
            Long parentId = null;
            for(Dept dept : deptList){
                if(parentId == null){
                    parentId = dept.getParentId();
                    continue;
                }

                if(parentId > dept.getParentId().longValue()){
                    parentId = dept.getParentId();
                }
            }
            deptId = parentId;
        }

        return R.ok().put("deptId", deptId);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{deptId}")
    @RequiresPermissions("sys:dept:info")
    public R info(@PathVariable("deptId") Long deptId){
        Dept dept = deptService.selectById(deptId);

        return R.ok().put("dept", dept);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dept:save")
    public R save(@RequestBody Dept dept){
        deptService.insert(dept);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dept:update")
    public R update(@RequestBody Dept dept){
        deptService.updateById(dept);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dept:delete")
    public R delete(Long deptId){
        //判断是否有子部门
        List<Long> deptList = deptService.queryDetpIdList(deptId);
        if(deptList.size() > 0){
            return R.error("请先删除子部门");
        }

        deptService.deleteById(deptId);

        return R.ok();
    }




}

