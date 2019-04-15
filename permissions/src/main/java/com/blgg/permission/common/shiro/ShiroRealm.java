package com.blgg.permission.common.shiro;

import com.blgg.permission.common.utils.Constant;
import com.blgg.permission.modules.sys.dao.MenuMapper;
import com.blgg.permission.modules.sys.dao.UserMapper;
import com.blgg.permission.modules.sys.entity.Menu;
import com.blgg.permission.modules.sys.entity.User;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 *
 * @ClassName ShiroRealm
 * @Description  配置自己的realm认证
 * @Author xiaobo
 * @Date 2018/10/13/013 2:05
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    //授权（验证权限时调用）
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user= (User) principalCollection.getPrimaryPrincipal();
        Long userId=user.getUserId();

        List<String> permissionList;

        //超级管理员，拥有最高权限
        if (userId==Constant.SUPER_ADMIN){
            List<Menu> menuList=menuMapper.selectList(null);
            permissionList=new ArrayList<>(menuList.size());
            for (Menu menu:menuList){
                permissionList.add(menu.getPerms());
            }
        }else {
            permissionList=userMapper.queryAllPerms(userId);
        }

        //用户权限列表
        Set<String> permsSet=new HashSet<>();
        for (String perms:permissionList){
            if (StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }


    //认证（登录时调用）
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;

        //查询用户信息
        User user=new User();
        user.setUsername(token.getUsername());
        user=userMapper.selectOne(user);

        //账号不存在
        if (user==null){
            throw new UnknownAccountException("账号或密码不正确");
        }

        //账号锁定
        if (user.getStatus()==0){
            throw new LockedAccountException("你的账号已被锁定，请联系管理员");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
        return info;
    }


    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtil.hashAlgorithmName);
        shaCredentialsMatcher.setHashIterations(ShiroUtil.hashIterations);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }
}
