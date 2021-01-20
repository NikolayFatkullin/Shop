package com.shop.controller.manufacturer;

import com.shop.lib.Injector;
import com.shop.model.Manufacturer;
import com.shop.service.ManufacturerService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetAllManufacturerController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.shop");
    private final ManufacturerService manufacturerService = (ManufacturerService) injector
            .getInstance(ManufacturerService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Manufacturer> manufacturerList = manufacturerService.getAll();
        req.setAttribute("manufacturers", manufacturerList);
        req.getRequestDispatcher("/WEB-INF/views/manufacturer/all-manufacturers.jsp")
                .forward(req, resp);
    }
}
