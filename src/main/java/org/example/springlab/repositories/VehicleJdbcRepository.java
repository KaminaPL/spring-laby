package org.example.springlab.repositories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.springlab.models.Vehicle;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Profile("jdbc")
public class VehicleJdbcRepository implements VehicleRepository {

    private final Gson gson = new Gson();
    private final DataSource dataSource;

    public VehicleJdbcRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        String stm = "CREATE TABLE IF NOT EXISTS vehicles (id TEXT PRIMARY KEY, category TEXT NOT NULL," +
                "brand TEXT NOT NULL, model TEXT NOT NULL, year INT NOT NULL, price NUMERIC NOT NULL, attributes JSONB)";
        Connection con = DataSourceUtils.getConnection(dataSource);
        try(PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }

    @Override
    public Optional<Vehicle> findById(String id) {
        String stm = "SELECT * FROM vehicles WHERE id = ?";
        Connection con = DataSourceUtils.getConnection(dataSource);
        try(PreparedStatement pstm = con.prepareStatement(stm)) {
                pstm.setString(1, id);
                try(ResultSet rs = pstm.executeQuery()) {
                    if(rs.next()) {
                        String dbattr = rs.getString("attributes");
                        Map<String, Object> attr = gson.fromJson(dbattr,
                                new TypeToken<>(){});
                        return Optional.of(new Vehicle(
                                rs.getString("id"),
                                rs.getString("category"),
                                rs.getString("brand"),
                                rs.getString("model"),
                                rs.getInt("year"),
                                rs.getFloat("price"),
                                attr
                        ));
                    }
                }
        } catch(SQLException e) {
                e.printStackTrace();
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return Optional.empty();
    }

    @Override
    public List<Vehicle> getAll() {
        List<Vehicle> vehicleList = new ArrayList<>();
        String stm = "SELECT * FROM vehicles";
        Connection con = DataSourceUtils.getConnection(dataSource);
        try(PreparedStatement pstm = con.prepareStatement(stm)) {
            try(ResultSet rs = pstm.executeQuery()) {
                while(rs.next()) {
                    String dbattr = rs.getString("attributes");
                    Map<String, Object> attr = gson.fromJson(dbattr,
                            new TypeToken<Map<String, Object>>(){});
                    vehicleList.add(new Vehicle(
                            rs.getString("id"),
                            rs.getString("category"),
                            rs.getString("brand"),
                            rs.getString("model"),
                            rs.getInt("year"),
                            rs.getDouble("price"),
                            attr
                    ));
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return vehicleList;
    }

    @Override
    public void add(Vehicle vehicle) {
        if(vehicle.getId() == null || vehicle.getId().isBlank()) {
            vehicle.setId(UUID.randomUUID().toString());
            while(findById(vehicle.getId()).isPresent()) {
                vehicle.setId(UUID.randomUUID().toString());
            }
        } else {
            removeById(vehicle.getId());
        }
        String stm = "INSERT INTO vehicles (id, category, brand, model, year, price, attributes) VALUES" +
                "(?, ?, ?, ?, ?, ?, ?::jsonb)";
        Connection con = DataSourceUtils.getConnection(dataSource);
        try(PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.setString(1, vehicle.getId());
            pstm.setString(2, vehicle.getCategory());
            pstm.setString(3, vehicle.getBrand());
            pstm.setString(4, vehicle.getModel());
            pstm.setInt(5, vehicle.getYear());
            pstm.setDouble(6, vehicle.getPrice());
            pstm.setString(7, gson.toJson(vehicle.getAttributes()));
            pstm.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }

    @Override
    public void removeById(String id) {
        String stm = "DELETE FROM vehicles WHERE id = ?";
        Connection con = DataSourceUtils.getConnection(dataSource);
        try(PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.setString(1, id);
            pstm.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }

    @Override
    public void save() {

    }
}

