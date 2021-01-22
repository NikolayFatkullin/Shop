package com.shop.security;

import com.shop.exception.AuthenticationException;
import com.shop.model.Driver;

public interface AuthenticationService {
    Driver login(String login, String password) throws AuthenticationException;
}
