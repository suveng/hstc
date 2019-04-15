package com.blgg.permission;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@MapperScan(basePackages = {"com.blgg.permission.modules.*.dao"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class PermissionApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PermissionApplication.class, args);
    }




}
