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
/*@WebFilter注解将一个实现了javax.servlet.Filter接口的类定义为过滤器，
这样我们在web应用中使用过滤器时，也不再需要在web.xml文件中配置过滤器的相关描述信息了
filterName  指定过滤器的 name 属性，等价于 <filter-name>
value  该属性等价于 urlPatterns 属性。但是两者不应该同时使用。
urlPatterns  指定一组过滤器的 URL 匹配模式。等价于 <url-pattern> 标签。
servletNames  指定过滤器将应用于哪些 Servlet。取值是 @WebServlet 中的 name 属性的取值，或者是 web.xml中<servlet-name> 的取值。
*/
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
            // 设置当前线程id
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request,response);
            return;
        }
        if(request.getSession().getAttribute("user")!=null){
            Long userId=(Long) request.getSession().getAttribute("user");
            // 设置当前线程id
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request,response);
            return;
        }
        //匹配失败
        //response.getWriter().writer（）,只能打印输出文本格式的（包括html标签），不可以打印对象
        //JSON.toJSONString则是将对象转化为Json字符串。在前后台的传输过程中，Json字符串是相当常用的
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
