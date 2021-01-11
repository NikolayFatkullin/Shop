package com.shop.service.impl;

import com.shop.dao.ManufacturerDao;
import com.shop.lib.Inject;
import com.shop.lib.Service;
import com.shop.model.Manufacturer;
import com.shop.service.ManufacturerService;
import java.util.List;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    @Inject
    ManufacturerDao manufacturerDao;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        manufacturerDao.create(manufacturer);
        return manufacturer;
    }

    @Override
    public Manufacturer get(Long id) {
        return manufacturerDao.get(id).get();
    }

    @Override
    public List<Manufacturer> getAll() {
        return manufacturerDao.getAll();
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        manufacturerDao.update(manufacturer);
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        return manufacturerDao.delete(id);
    }
}
