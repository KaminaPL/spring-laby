package org.example.repositories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.db.JdbcConnectionManager;
import org.example.models.Rental;
import org.example.models.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RentalJdbcRepository implements RentalRepository {

    private final Gson gson = new Gson();

    public RentalJdbcRepository() {
        String stm = "CREATE TABLE IF NOT EXISTS rentals (id TEXT PRIMARY KEY," +
                "vehicle_id TEXT NOT NULL, user_id TEXT NOT NULL, rent_date TEXT NOT NULL, return_date TEXT, " +
                "FOREIGN KEY (vehicle_id) REFERENCES vehicles(id) ON DELETE CASCADE, " +
                "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE)";
        try(Connection con = JdbcConnectionManager.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Rental> getAll() {
        List<Rental> rentalList = new ArrayList<>();
        String stm = "SELECT * FROM rentals";
        try(Connection con = JdbcConnectionManager.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(stm)) {
            try(ResultSet rs = pstm.executeQuery()) {
                while(rs.next()) {
                    rentalList.add(new Rental(
                        rs.getString("id"),
                        rs.getString("vehicle_id"),
                        rs.getString("user_id"),
                        rs.getString("rent_date"),
                        rs.getString("return_date")
                    ));
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return rentalList;
    }

    @Override
    public Optional<Rental> findById(String id) {
        String stm = "SELECT * FROM rentals WHERE id = ?";
        try(Connection con = JdbcConnectionManager.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.setString(1, id);
            try(ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Rental(
                            rs.getString("id"),
                            rs.getString("vehicle_id"),
                            rs.getString("user_id"),
                            rs.getString("rent_date"),
                            rs.getString("return_date")
                    ));
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Rental> findByVehicleId(String vehicleId) {
        String stm = "SELECT * FROM rentals WHERE vehicle_id = ?";
        try(Connection con = JdbcConnectionManager.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.setString(1, vehicleId);
            try(ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Rental(
                            rs.getString("id"),
                            rs.getString("vehicle_id"),
                            rs.getString("user_id"),
                            rs.getString("rent_date"),
                            rs.getString("return_date")
                    ));
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Rental> findByUserId(String userId) {
        String stm = "SELECT * FROM rentals WHERE user_id = ?";
        try(Connection con = JdbcConnectionManager.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.setString(1, userId);
            try(ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Rental(
                            rs.getString("id"),
                            rs.getString("vehicle_id"),
                            rs.getString("user_id"),
                            rs.getString("rent_date"),
                            rs.getString("return_date")
                    ));
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Rental> findByIdAndReturnDateIsNull(String id) {
        String stm = "SELECT * FROM rentals WHERE id = ?";
        try(Connection con = JdbcConnectionManager.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.setString(1, id);
            try(ResultSet rs = pstm.executeQuery()) {
                if (rs.next() && rs.getString("return_date_time").isBlank()) {
                    return Optional.of(new Rental(
                            rs.getString("id"),
                            rs.getString("vehicle_id"),
                            rs.getString("user_id"),
                            rs.getString("rent_date"),
                            rs.getString("return_date")
                    ));
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void add(Rental rental) {
        if(rental.getId() == null || rental.getId().isBlank()) {
            rental.setId(UUID.randomUUID().toString());
            while(findById(rental.getId()).isPresent()) {
                rental.setId(UUID.randomUUID().toString());
            }
        } else {
            removeById(rental.getId());
        }
        String stm = "INSERT INTO rentals (id, vehicle_id, user_id, rent_date, return_date)" +
                "VALUES (?, ?, ?, ?, ?)";
        try(Connection con = JdbcConnectionManager.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.setString(1, rental.getId());
            pstm.setString(2, rental.getVehicleId());
            pstm.setString(3, rental.getUserId());
            pstm.setString(4, rental.getRentDateTime());
            pstm.setString(5, rental.getReturnDateTime());
            pstm.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeById(String id) {
        String stm = "DELETE FROM vehicles WHERE id = ?";
        try(Connection con = JdbcConnectionManager.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.setString(1, id);
            pstm.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {

    }
}
