package com.shop.dao.impl;

import com.shop.dao.ManufacturerDao;
import com.shop.exceptions.DataProcessingException;
import com.shop.lib.Dao;
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
public class ManufacturerDaoJdbcImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String create = "INSERT INTO manufacturer (manufacturer_name, manufacturer_country) "
                + "VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement(create, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                manufacturer.setId(resultSet.getLong(1));
            }
            statement.close();
            resultSet.close();
            return manufacturer;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't create manufacturer to the DB"
                    + manufacturer, ex);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String select = "SELECT * FROM manufacturer WHERE id = ? "
                + "AND manufacturer_deleted = false";
        Manufacturer manufacturer = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(select);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
            resultSet.close();
            return Optional.ofNullable(manufacturer);
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't find manufacturer by id " + id, ex);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String selectAll = "SELECT * FROM manufacturer WHERE manufacturer_deleted = false";
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(selectAll);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(getManufacturer(resultSet));
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't get all manufacturers", ex);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String update = "UPDATE manufacturer SET manufacturer_name = ?, manufacturer_country = ? "
                + "WHERE id = ? AND manufacturer_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
            statement.close();
            return manufacturer;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't update manufacturer:" + manufacturer, ex);
        }
    }

    @Override
    public boolean delete(Long id) {
        String delete = "UPDATE manufacturer SET manufacturer_deleted = true "
                + "WHERE id = ?";
        int result;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(delete);
            statement.setLong(1, id);
            result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't delete manufacturer with id:" + id, ex);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) {
        try {
            Long manufacturerId = resultSet.getObject("id", Long.class);
            String name = resultSet.getObject("manufacturer_name", String.class);
            String country = resultSet.getObject("manufacturer_country", String.class);
            Manufacturer manufacturer = new Manufacturer(name, country);
            manufacturer.setId(manufacturerId);
            return manufacturer;
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't create manufacturer from ResultSet", ex);
        }
    }
}
