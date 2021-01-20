package com.shop.controller.drivers;

import com.shop.lib.Injector;
import com.shop.model.Driver;
import com.shop.service.DriverService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AllDriversController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.shop");
    private final DriverService driverService = (DriverService) injector
            .getInstance(DriverService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Driver> driverList = driverService.getAll();
        req.setAttribute("drivers", driverList);
        req.getRequestDispatcher("/WEB-INF/views/drivers/all.jsp").forward(req, resp);
    }
}
