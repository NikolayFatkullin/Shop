package com.shop.web.filter;

import com.shop.lib.Injector;
import com.shop.service.DriverService;
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
    private static final Injector injector = Injector.getInstance("com.shop");
    private final DriverService driverService = (DriverService)
            injector.getInstance(DriverService.class);

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
        Long driverId = (Long) httpServletRequest.getSession().getAttribute("driver_id");
        if (driverId == null || driverService.get(driverId) == null) {
            httpServletResponse.sendRedirect("/login");
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    public void destroy() {

    }
}
