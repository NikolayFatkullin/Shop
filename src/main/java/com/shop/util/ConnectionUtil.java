package com.shop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", USER);
        dbProperties.put("password", PASSWORD);
        String url = "jdbc:mysql://localhost:3306/shop?serverTimezone=UTC";
        try {
            return DriverManager.getConnection(url, dbProperties);
        } catch (SQLException ex) {
            throw new RuntimeException("Can't connect to DB", ex);
        }
    }
}
