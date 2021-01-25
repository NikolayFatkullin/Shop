package com.shop.security.impl;

import com.shop.exception.AuthenticationException;
import com.shop.lib.Inject;
import com.shop.lib.Service;
import com.shop.model.Driver;
import com.shop.security.AuthenticationService;
import com.shop.service.DriverService;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private DriverService driverService;

    @Override
    public Driver login(String login, String password) throws AuthenticationException {
        Optional<Driver> driverFromDB = driverService.findByLogin(login);
        if (driverFromDB.isPresent() && driverFromDB.get().getPassword().equals(password)) {
            return driverFromDB.get();
        }
        throw new AuthenticationException("Incorrect username or password");
    }
}
