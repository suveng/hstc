package com.blgg.permission.modules.job.task;

import com.blgg.permission.modules.sys.entity.User;
import com.blgg.permission.modules.sys.service.UserService;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @ClassName TestTask
 * @Description
 * @Author xiaobo
 * @Date 2018/10/16/016 1:18
 */
@Component("testTask")
public class TestTask {
    private Logger logger=LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    public void test1(String params){
        logger.info("我是带参数的test1方法，正在被执行，参数为："+params);

        try{
            Thread.sleep(1000L);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        User user=userService.selectById(1L);
        System.out.println(ToStringBuilder.reflectionToString(user));
    }

    public void test2(){
        logger.info("我是不带参数的test2方法，正在被执行！");
    }


}
