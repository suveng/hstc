package com.blgg.permission.modules.sys.service;

import com.blgg.permission.common.utils.PageUtils;
import com.blgg.permission.modules.sys.entity.User;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
public interface UserService extends IService<User> {

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

    //分页
    PageUtils queryPage(Map<String,Object> params);

    //修改密码
    boolean updatePassword(Long userId, String oldPassword, String newPassword);

    //保存用户
    void save(User user);

    //修改用户
    void update(User user);
}
