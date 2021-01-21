package com.shop.controller.car.driver;

import com.shop.lib.Injector;
import com.shop.service.CarService;
import com.shop.service.DriverService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddDriverToCarController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.shop");
    private final CarService carService = (CarService) injector
            .getInstance(CarService.class);
    private final DriverService driverService = (DriverService) injector
            .getInstance(DriverService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/car/driver/add.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long carId = Long.valueOf(req.getParameter("car_id"));
        Long driverId = Long.valueOf(req.getParameter("driver_id"));
        carService.addDriverToCar(driverService.get(driverId), carService.get(carId));
        resp.sendRedirect(req.getContextPath() + "/car/all-cars");
    }
}
