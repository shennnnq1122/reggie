package com.shq.filter;

import com.alibaba.fastjson.JSON;
import com.shq.common.BaseContext;
import com.shq.common.R;
import com.shq.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1.获取本次请求的URI
        String requestURI = request.getRequestURI();

        log.info("拦截到请求{}",requestURI);

        //定义不需要处理的请求路径

        String[] urls = {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/login"
        };

        //2.判断本次请求是否合理
        boolean check = check(urls, requestURI);

        //3.如果不需要处理，则直接放行
        if(check){
            log.info("不需要处理，{}",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        //4.判断登录状态，如果已经登录，则直接放行
        if(request.getSession().getAttribute("employee")!=null){
            log.info("登录状态，用户为{}",request.getSession().getAttribute("employee"));
            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request,response);
            return;
        }
        if(request.getSession().getAttribute("userId")!=null){
            log.info("登录状态，用户为{}",request.getSession().getAttribute("userId"));
            Long userId = (Long) request.getSession().getAttribute("userId");
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request,response);
            return;
        }

        //如果未登录则反回未登录结果，通过输出流的方式向客户端页面响应数据
        log.info("用户未登录：{}",request.getRequestURI());
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));

    }

    private boolean check(String[] urls,String requesURI){
        for (String url:urls) {
            boolean match = PATH_MATCHER.match(url, requesURI);
            if(match){
                return true;
            }
        }
        return false;
    }


}
