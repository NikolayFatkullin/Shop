package com.shop.db;

import com.shop.model.Car;
import com.shop.model.Driver;
import com.shop.model.Manufacturer;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static final List<Manufacturer> manufacturers = new ArrayList<>();
    public static final List<Car> cars = new ArrayList<>();
    public static final List<Driver> drivers = new ArrayList<>();
    public static Long manufacturerId = 0L;
    public static Long carId = 0L;
    public static Long driverId = 0L;

    public static void addManufacturer(Manufacturer manufacturer) {
        manufacturerId++;
        manufacturer.setId(manufacturerId);
        manufacturers.add(manufacturer);
    }

    public static void addCar(Car car) {
        carId++;
        car.setId(carId);
        cars.add(car);
    }

    public static void addDriver(Driver driver) {
        driverId++;
        driver.setId(driverId);
        drivers.add(driver);
    }
}
