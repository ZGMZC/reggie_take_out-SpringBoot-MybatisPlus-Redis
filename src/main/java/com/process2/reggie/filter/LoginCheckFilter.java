package com.process2.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.process2.reggie.common.BaseContext;
import com.process2.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配器 支持通配符
    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)  servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();

        //不需要处理的请求
        String[] urls=new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        boolean check = check(urls, requestURI);
        if(check==true){
            filterChain.doFilter(request,response);
            return;
        }
        if(request.getSession().getAttribute("employee")!=null){
            Long empId=(Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request,response);
            return;
        }
        if(request.getSession().getAttribute("user")!=null){
            Long userId=(Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request,response);
            return;
        }

        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    public boolean check(String[] urls,String requestURI){
        for(String url:urls){
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match) return true;
        }
        return false;
    }
}
