package org.example.repositories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.db.JdbcConnectionManager;
import org.example.models.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VehicleJdbcRepository implements VehicleRepository {

    private List<Vehicle> vehicleList;
    private final Gson gson = new Gson();

    public VehicleJdbcRepository() {
        String stm = "CREATE IF NOT EXISTS vehicles (id TEXT PRIMARY KEY, category TEXT NOT NULL, brand TEXT NOT NULL, " +
                "model TEXT NOT NULL, year INT NOT NULL, price NUMERIC NOT NULL, attributes JSONB)";
        try(Connection con = JdbcConnectionManager.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Vehicle> findById(String id) {
        String stm = "SELECT * FROM vehicles WHERE id = ?";
        try(Connection con = JdbcConnectionManager.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(stm)) {
                pstm.setString(1, id);
                try(ResultSet rs = pstm.executeQuery()) {
                    if(rs.next()) {
                        String dbattr = rs.getString("attributes");
                        Map<String, Object> attr = gson.fromJson(dbattr,
                                new TypeToken<Map<String, Object>>(){});
                        Vehicle vehicle = new Vehicle(
                                rs.getString("id"),
                                rs.getString("category"),
                                rs.getString("brand"),
                                rs.getString("model"),
                                rs.getInt("year"),
                                rs.getFloat("price"),
                                attr
                        );
                        return Optional.of(vehicle);
                    }
                }
        } catch(SQLException e) {
                e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Vehicle> getAll() {
        List<Vehicle> vehicleList = new ArrayList<>();
        String stm = "SELECT * FROM vehicles";
        try(Connection con = JdbcConnectionManager.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(stm)) {
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
        }
        return vehicleList;
    }

    @Override
    public void add(Vehicle vehicle) {
        String stm = "INSERT INTO vehicles (id, category, brand, model, year, price, attributes) VALUES" +
                "(?, ?, ?, ?, ?, ?, ?::jsonb)";
        try(Connection con = JdbcConnectionManager.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.setString(1, vehicle.getId());
            pstm.setString(2, vehicle.getBrand());
            pstm.setString(3, vehicle.getModel());
            pstm.setInt(4, vehicle.getYear());
            pstm.setDouble(5, vehicle.getPrice());
            pstm.setString(6, gson.toJson(vehicle.getAttributes()));
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeById(String id) {

    }

    @Override
    public void save() {

    }
}

