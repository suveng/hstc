package com.blgg.permission.common.aspect;

import com.blgg.permission.common.annotation.DataFilter;
import com.blgg.permission.common.exception.BLGGException;
import com.blgg.permission.common.shiro.ShiroUtil;
import com.blgg.permission.common.utils.Constant;
import com.blgg.permission.modules.sys.entity.User;
import com.blgg.permission.modules.sys.service.DeptService;
import com.blgg.permission.modules.sys.service.RoleDeptService;
import com.blgg.permission.modules.sys.service.UserRoleService;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * @ClassName DataFilterAspect
 * @Description 数据过滤，切面处理类
 * @Author xiaobo
 * @Date 2018/10/15/015 4:17
 */
@Aspect
@Component
public class DataFilterAspect {
    @Autowired
    private DeptService deptService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleDeptService roleDeptService;

    @Pointcut("@annotation(com.blgg.permission.common.annotation.DataFilter)")
    public void dataFilterCut() {

    }

    @Before("dataFilterCut()")
    public void dataFilter(JoinPoint point) throws Throwable {
        Object params = point.getArgs()[0];
        if(params != null && params instanceof Map){
            User user = ShiroUtil.getUserEntity();

            //如果不是超级管理员，则进行数据过滤
            if(user.getUserId() != Constant.SUPER_ADMIN){
                Map map = (Map)params;
                map.put(Constant.SQL_FILTER, getSQLFilter(user, point));
            }

            return ;
        }

        throw new BLGGException("数据权限接口，只能是Map类型参数，且不能为NULL");
    }

    /**
     * 获取数据过滤的SQL
     */
    private String getSQLFilter(User user, JoinPoint point){
        MethodSignature signature = (MethodSignature) point.getSignature();
        DataFilter dataFilter = signature.getMethod().getAnnotation(DataFilter.class);
        //获取表的别名
        String tableAlias = dataFilter.tableAlias();
        if(StringUtils.isNotBlank(tableAlias)){
            tableAlias +=  ".";
        }

        //部门ID列表
        Set<Long> deptIdList = new HashSet<>();

        //用户角色对应的部门ID列表
        List<Long> roleIdList = userRoleService.queryRoleIdList(user.getUserId());
        if(roleIdList.size() > 0){
            List<Long> userDeptIdList = roleDeptService.queryDeptIdList(roleIdList.toArray(new Long[roleIdList.size()]));
            deptIdList.addAll(userDeptIdList);
        }

        //用户子部门ID列表
        if(dataFilter.subDept()){
            List<Long> subDeptIdList = deptService.getSubDeptIdList(user.getDeptId());
            deptIdList.addAll(subDeptIdList);
        }

        StringBuilder sqlFilter = new StringBuilder();
        sqlFilter.append(" (");

        if(deptIdList.size() > 0){
            sqlFilter.append(tableAlias).append(dataFilter.deptId()).append(" in(").append(StringUtils.join(deptIdList, ",")).append(")");
        }

        //没有本部门数据权限，也能查询本人数据
        if(dataFilter.user()){
            if(deptIdList.size() > 0){
                sqlFilter.append(" or ");
            }
            sqlFilter.append(tableAlias).append(dataFilter.userId()).append("=").append(user.getUserId());
        }

        sqlFilter.append(")");

        return sqlFilter.toString();
    }
}
