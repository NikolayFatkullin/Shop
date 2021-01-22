package com.shop.security.impl;

import com.shop.exception.AuthenticationException;
import com.shop.lib.Inject;
import com.shop.lib.Service;
import com.shop.model.Driver;
import com.shop.security.AuthenticationService;
import com.shop.service.DriverService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private DriverService driverService;

    @Override
    public Driver login(String login, String password) throws AuthenticationException {
        Driver driverFromDb = driverService.findByLogin(login).orElseThrow(() ->
                new AuthenticationException("Incorrect username or password"));
        if (driverFromDb.getPassword().equals(password)) {
            return driverFromDb;
        }
        throw new AuthenticationException("Incorrect username or password");
    }
}
