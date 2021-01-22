package com.shop.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationFilter implements Filter {
    private static final String DRIVER_ID = "driver_id";

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String url = httpServletRequest.getServletPath();
        if (url.equals("/login") || url.equals("/drivers/create")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        Long driverId = (Long) httpServletRequest.getSession().getAttribute(DRIVER_ID);
        if (driverId == null) {
            httpServletResponse.sendRedirect("/login");
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    public void destroy() {

    }
}
