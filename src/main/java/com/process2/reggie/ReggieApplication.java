package com.process2.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j  //日志文件
@SpringBootApplication  //快捷配置启动类

/*1、Servlet 三大组件 Servlet、Filter、Listener 在传统项目中需要在 web.xml 中进行相应的配置。
Servlet 3.0 开始在 javax.servlet.annotation 包下提供 3 个对应的 @WebServlet、@WebFilter、@WebListener 注解来简化操作。
2、@WebServlet、@WebFilter、@WebListener 写在对应的 Servlet、Filter、Listener 类上作为标识，
从而不需要在 web.xml 中进行配置了。
3、Spring Boot 应用中这三个注解默认是不被扫描的，需要在项目启动类上
添加 @ServletComponentScan 注解, 表示对 Servlet 组件扫描。
*/
@ServletComponentScan
@EnableTransactionManagement    //开启事务支持
@EnableCaching //@EnableCaching注解是spring framework中的注解驱动的缓存管理功能
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class,args);
    }
}
