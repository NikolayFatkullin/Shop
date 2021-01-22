package com.shop.dao.impl;

import com.shop.dao.DriverDao;
import com.shop.exception.DataProcessingException;
import com.shop.lib.Dao;
import com.shop.model.Driver;
import com.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class DriverDaoJdbcImpl implements DriverDao {
    @Override
    public Driver create(Driver driver) {
        String create = "INSERT INTO drivers (name, license_number) values(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(create,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, driver.getName());
            statement.setString(2, driver.getLicenseNumber());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                driver.setId(resultSet.getLong(1));
            }
            return driver;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't write driver into data base:" + driver, ex);
        }
    }

    @Override
    public Optional<Driver> get(Long id) {
        String select = "SELECT * FROM drivers WHERE id = ? "
                + "AND deleted = false";
        Driver driver = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(select)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                driver = getDriver(resultSet);
            }

            return Optional.ofNullable(driver);
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't find driver by id " + id, ex);
        }
    }

    @Override
    public List<Driver> getAll() {
        String selectAll = "SELECT * FROM drivers WHERE deleted = false";
        List<Driver> drivers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(selectAll)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                drivers.add(getDriver(resultSet));
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get all drivers", ex);
        }
        return drivers;
    }

    @Override
    public Driver update(Driver driver) {
        String update = "UPDATE drivers SET name = ?, license_number = ? "
                + "WHERE id = ? AND deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, driver.getName());
            statement.setString(2, driver.getLicenseNumber());
            statement.setLong(3, driver.getId());
            statement.executeUpdate();
            return driver;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't update driver:" + driver, ex);
        }
    }

    @Override
    public boolean delete(Long id) {
        String delete = "UPDATE drivers SET deleted = true "
                + "WHERE id = ?";
        int result;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(delete)) {
            statement.setLong(1, id);
            result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't delete driver with id:" + id, ex);
        }
    }

    private Driver getDriver(ResultSet resultSet) {
        try {
            Long driverId = resultSet.getObject("id", Long.class);
            String name = resultSet.getString("name");
            String licenseNumber = resultSet.getString("license_number");
            Driver driver = new Driver(name, licenseNumber);
            driver.setId(driverId);
            return driver;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't create driver from ResultSet", ex);
        }
    }
}
