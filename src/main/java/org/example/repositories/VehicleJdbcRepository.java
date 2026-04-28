package org.example.repositories;

import org.example.JdbcConnectionManager;
import org.example.models.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class VehicleJdbcRepository implements VehicleRepository {

    private List<Vehicle> vehicleList;

    public VehicleJdbcRepository() {
        try(Connection connection = JdbcConnectionManager.getInstance().getConnection()) {

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    Optional<Vehicle> findById(String id) {

    }

    @Override
    List<Vehicle> getAll() {

    }

    @Override
    void add(Vehicle vehicle) {

    }

    @Override
    void removeById(String id) {

    }

    @Override
    void save() {

    }
}

