package com.blgg.permission.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.blgg.permission.common.annotation.DataFilter;
import com.blgg.permission.common.annotation.SysLog;
import com.blgg.permission.common.shiro.ShiroUtil;
import com.blgg.permission.common.utils.*;
import com.blgg.permission.modules.sys.entity.Dept;
import com.blgg.permission.modules.sys.entity.User;
import com.blgg.permission.modules.sys.dao.UserMapper;
import com.blgg.permission.modules.sys.service.DeptService;
import com.blgg.permission.modules.sys.service.UserRoleService;
import com.blgg.permission.modules.sys.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private DeptService deptService;

    //找出所有菜单ID
    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    //数据分页
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String username= (String) params.get("username");

        Page<User> page = this.selectPage(
                new Query<User>(params).getPage(),
                new EntityWrapper<User>()
                        .like(StringUtils.isNotBlank(username),"username", username)
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
                );

        for(User user : page.getRecords()){
            Dept dept = deptService.selectById(user.getDeptId());
            user.setDeptName(dept.getName());
        }

        return new PageUtils(page);
    }

    //修改密码
    @Override
    public boolean updatePassword(Long userId, String oldPassword, String newPassword) {
        User user=new User();
        user.setPassword(newPassword);
        return this.update(user,new EntityWrapper<User>().eq("user_id",userId).eq("password",oldPassword));
    }

   //保存用户
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(User user) {
        user.setCreateTime(new Date());
        //sha256加密
        String salt=RandomStringUtils.randomAlphanumeric(20);
        user.setSalt(salt);
        user.setPassword(ShiroUtil.sha256(user.getPassword(),user.getSalt()));
        this.insert(user);

        //保存用户与角色关系
        userRoleService.saveOrUpdate(user.getUserId(),user.getRoleIdList());
    }


    //修改用户
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(User user) {
        if (StringUtils.isBlank(user.getPassword())){
            user.setPassword(null);
        }else {
            user.setPassword(ShiroUtil.sha256(user.getPassword(),user.getSalt()));
        }
        this.updateById(user);

        //保存用户与角色关系
        userRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }


}
