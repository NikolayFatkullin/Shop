package com.shop.controller.drivers;

import com.shop.lib.Injector;
import com.shop.service.DriverService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteDriversController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.shop");
    private final DriverService driverService = (DriverService) injector
            .getInstance(DriverService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long driverId = Long.valueOf(req.getParameter("id"));
        driverService.delete(driverId);
        resp.sendRedirect(req.getContextPath() + "/drivers/all");
    }
}
