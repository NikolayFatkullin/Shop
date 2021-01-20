package com.shop.dao.impl;

import com.shop.dao.CarDao;
import com.shop.exception.DataProcessingException;
import com.shop.lib.Dao;
import com.shop.model.Car;
import com.shop.model.Driver;
import com.shop.model.Manufacturer;
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
public class CarDaoJdbcImpl implements CarDao {
    @Override
    public Car create(Car car) {
        String create = "INSERT INTO cars (manufacturer_id, model) values(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(create,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, car.getManufacturer().getId());
            statement.setString(2, car.getModel());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                car.setId(resultSet.getLong(1));
            }
            return car;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't write car into data base:" + car, ex);
        }
    }

    @Override
    public Optional<Car> get(Long id) {
        String select = "SELECT c.id AS car_id, c.model,m.id AS manufacturer_id, m.name "
                + "AS manufacturer_name, m.country AS manufacturer_country, d.id AS driver_id, "
                + "d.name AS driver_name, d.license_number FROM cars c\n"
                + "LEFT JOIN manufacturer m ON m.id = c.manufacturer_id\n"
                + "LEFT JOIN cars_drivers cd ON cd.car_id = c.id \n"
                + "LEFT JOIN drivers d ON d.id = cd.driver_id AND d.deleted = false\n"
                + "WHERE c.deleted = false AND c.id = ?";
        Car car = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(select,
                        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                car = getCar(resultSet);
            }

            return Optional.ofNullable(car);
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't find car by id " + id, ex);
        }
    }

    @Override
    public List<Car> getAll() {
        String select = "SELECT c.id AS car_id, c.model,m.id AS manufacturer_id, m.name "
                + "AS manufacturer_name, m.country AS manufacturer_country, d.id AS driver_id, "
                + "d.name AS driver_name, d.license_number FROM cars c\n"
                + "LEFT JOIN manufacturer m ON m.id = c.manufacturer_id\n"
                + "LEFT JOIN cars_drivers cd ON cd.car_id = c.id \n"
                + "LEFT JOIN drivers d ON d.id = cd.driver_id AND d.deleted = false\n"
                + "WHERE c.deleted = false";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(select,
                        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cars.add(getCar(resultSet));
            }
            return cars;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't find cars", ex);
        }
    }

    @Override
    public Car update(Car car) {
        String updateCar = "UPDATE cars SET manufacturer_id = ?, model = ? WHERE id = ?";
        String deleteConnections = "DELETE FROM cars_drivers WHERE car_id = ?";
        String updateDrivers = "INSERT INTO cars_drivers (driver_id, car_id) values (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(updateCar);
                PreparedStatement statementDelete = connection.prepareStatement(deleteConnections);
                PreparedStatement statementUpdate = connection.prepareStatement(updateDrivers)) {
            statement.setLong(1, car.getManufacturer().getId());
            statement.setString(2, car.getModel());
            statement.setLong(3, car.getId());
            statementDelete.setLong(1, car.getId());
            statement.executeUpdate();
            statementDelete.executeUpdate();
            statementUpdate.setLong(2, car.getId());
            for (Driver driver : car.getDrivers()) {
                statementUpdate.setLong(1, driver.getId());
                statementUpdate.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new DataProcessingException("Cant update car: " + car, ex);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        String delete = "UPDATE cars SET deleted = true "
                + "WHERE id = ?";
        int result;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(delete)) {
            statement.setLong(1, id);
            result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't delete car with id:" + id, ex);
        }
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        String getAll = "SELECT c.id AS car_id, c.model,m.id AS manufacturer_id, m.name "
                + "AS manufacturer_name, m.country AS manufacturer_country, d.id AS driver_id, "
                + "d.name AS driver_name, d.license_number FROM cars c\n"
                + "LEFT JOIN manufacturer m ON m.id = c.manufacturer_id\n"
                + "LEFT JOIN cars_drivers cd ON cd.car_id = c.id \n"
                + "LEFT JOIN drivers d ON d.id = cd.driver_id\n"
                + "WHERE c.deleted = false AND d.id = ? AND d.deleted = false;\n";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(getAll,
                        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setLong(1, driverId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cars.add(getCar(resultSet));
            }
            return cars;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't find cars", ex);
        }
    }

    private Car getCar(ResultSet resultSet) {
        try {
            Long carId = resultSet.getObject("car_id", Long.class);
            String carModel = resultSet.getString("model");
            Manufacturer manufacturer = getManufacturer(resultSet);
            List<Driver> drivers = getDrivers(resultSet, carId);
            Car car = new Car(carModel, manufacturer);
            car.setId(carId);
            car.setDrivers(drivers);
            return car;
        } catch (SQLException ex) {
            throw new DataProcessingException("Cant create car from ResultSet", ex);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) {
        try {
            Long manufacturerId = resultSet.getObject("manufacturer_id", Long.class);
            String manufacturerName = resultSet.getString("manufacturer_name");
            String manufacturerCountry = resultSet.getString("manufacturer_country");
            Manufacturer manufacturer = new Manufacturer(manufacturerName, manufacturerCountry);
            manufacturer.setId(manufacturerId);
            return manufacturer;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't create manufacturer from ResultSet", ex);
        }
    }

    private List<Driver> getDrivers(ResultSet resultSet, Long carId) {
        List<Driver> drivers = new ArrayList<>();
        try {
            do {
                Driver driver = getDriver(resultSet);
                if (driver.getId() != null) {
                    drivers.add(driver);
                }
            } while (resultSet.next() && resultSet.getObject("car_id", Long.class).equals(carId));
            resultSet.previous();
            return drivers;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't create Drivers list from ResultSet", ex);
        }
    }

    private Driver getDriver(ResultSet resultSet) {
        try {
            Long driverId = resultSet.getObject("driver_id", Long.class);
            String driverName = resultSet.getString("driver_name");
            String licenseNumber = resultSet.getString("license_number");
            Driver driver = new Driver(driverName, licenseNumber);
            driver.setId(driverId);
            return driver;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't creat driver from ResultSet", ex);
        }
    }
}
