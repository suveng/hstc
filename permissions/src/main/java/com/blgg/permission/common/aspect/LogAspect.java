package com.blgg.permission.common.aspect;

import com.blgg.permission.common.annotation.SysLog;
import com.blgg.permission.common.utils.HttpContextUtils;
import com.blgg.permission.common.utils.IPUtils;
import com.blgg.permission.modules.sys.entity.Log;
import com.blgg.permission.modules.sys.entity.User;
import com.blgg.permission.modules.sys.service.LogService;
import com.google.gson.Gson;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

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
 * @ClassName LogAspect
 * @Description 系统日志，切面处理类
 * @Author xiaobo
 * @Date 2018/10/15/015 3:47
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private LogService logService;

    //退出系统
        @Pointcut("execution(* com.blgg.permission.modules.sys.controller.LoginController.logout(..))")
        public void logoutCell(){

    }

    @Pointcut("@annotation(com.blgg.permission.common.annotation.SysLog)")
    public void logPointCut(){

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime=System.currentTimeMillis();
        //执行方法
        Object result = joinPoint.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        saveSysLog(joinPoint, time);

        return result;
    }


    @Before(value = "logoutCell()")
    public void logoutLog(JoinPoint joinPoint){
        long beginTime=System.currentTimeMillis();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

       Log log=new Log();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method=signature.getMethod();
        //请求的方法头
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.setMethod(className + "." + methodName + "()");
        //请求的参数
        Object[] args = joinPoint.getArgs();
        try{
            String params = new Gson().toJson(args[0]);
            log.setParams(params);
        }catch (Exception e){
        }

        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //设置IP地址
        log.setIp(IPUtils.getIpAddr(request));

        //用户名
        String username = ((User) SecurityUtils.getSubject().getPrincipal()).getUsername();
        log.setUsername(username);

        log.setOperation("退出系统");

        log.setTime(time);
        log.setCreateDate(new Date());
        //保存系统日志
        logService.insert(log);
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint,long time){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method =signature.getMethod();

        Log log=new Log();
        SysLog sysLog=method.getAnnotation(SysLog.class);
        if (sysLog!=null){
            log.setOperation(sysLog.value());
        }

        //请求的方法头
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.setMethod(className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        try{
            String params = new Gson().toJson(args[0]);
            log.setParams(params);
        }catch (Exception e){
        }


        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //设置IP地址
        log.setIp(IPUtils.getIpAddr(request));

        //用户名
        String username = ((User) SecurityUtils.getSubject().getPrincipal()).getUsername();
        log.setUsername(username);

        log.setTime(time);
        log.setCreateDate(new Date());
        //保存系统日志
        logService.insert(log);
            }
}
