package com.shop.dao.impl;

import com.shop.dao.DriverDao;
import com.shop.db.Storage;
import com.shop.lib.Dao;
import com.shop.model.Driver;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class DriverDaoImpl implements DriverDao {
    @Override
    public Driver create(Driver driver) {
        Storage.addDriver(driver);
        return driver;
    }

    @Override
    public Optional<Driver> get(Long id) {
        return Storage.drivers.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Driver> getAll() {
        return Storage.drivers;
    }

    @Override
    public Driver update(Driver driver) {
        IntStream.range(0, Storage.drivers.size())
                .filter(i -> Storage.drivers.get(i).getId().equals(driver.getId()))
                .findFirst()
                .ifPresent(i -> Storage.drivers.set(i, driver));
        return driver;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.drivers.removeIf(d -> d.getId().equals(id));
    }
}
