package com.blgg.permission.common.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

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
 * @ClassName ShiroTag
 * @Description shiro权限标签
 * @Author xiaobo
 * @Date 2018/10/13/013 2:52
 */
@Component
public class ShiroTag {
    //是否拥有该标签
    public boolean hasPermission(String permission) {
        Subject subject = SecurityUtils.getSubject();
        return subject != null && subject.isPermitted(permission);
    }
}
