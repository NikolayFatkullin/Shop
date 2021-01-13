package com.shop.dao.impl;

import com.shop.dao.CarDao;
import com.shop.db.Storage;
import com.shop.lib.Dao;
import com.shop.model.Car;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Dao
public class CarDaoImpl implements CarDao {
    @Override
    public Car create(Car car) {
        Storage.addCar(car);
        return car;
    }

    @Override
    public Optional<Car> get(Long id) {
        return Storage.cars.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Car> getAll() {
        return Storage.cars;
    }

    @Override
    public Car update(Car car) {
        IntStream.range(0, Storage.cars.size())
                .filter(i -> Storage.cars.get(i).getId().equals(car.getId()))
                .findFirst()
                .ifPresent(i -> Storage.cars.set(i, car));
        return car;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.cars.removeIf(c -> c.getId().equals(id));
    }

    public List<Car> getAllByDriver(Long driverId) {
        return Storage.cars.stream()
                .filter(c -> c.getDrivers()
                        .stream().anyMatch(d -> d.getId().equals(driverId)))
                .collect(Collectors.toList());
    }
}
