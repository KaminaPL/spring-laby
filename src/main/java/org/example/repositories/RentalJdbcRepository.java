package org.example.repositories;

import com.google.gson.Gson;
import org.example.db.JdbcConnectionManager;
import org.example.models.Rental;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RentalJdbcRepository implements RentalRepository {

    private final Gson gson = new Gson();

    public RentalJdbcRepository() {
        String stm = "CREATE TABLE IF NOT EXISTS rentals (id TEXT PRIMARY KEY" +
                "vehicle_id TEXT NOT NULL, user_id TEXT NOT NULL, rent_date TEXT NOT NULL, return_date TEXT" +
                "FOREIGN KEY (vehicle_id) REFERENCES vehicles(id) ON DELETE CASCADE" +
                "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE";
        try(Connection con = JdbcConnectionManager.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Rental> getAll() {

    }

    public Optional<Rental> findById(String id) {

    }

    public Optional<Rental> findByVehicleId(String id) {

    }

    public Optional<Rental> findByUserId(String id) {

    }

    public Optional<Rental> findByIdAndReturnDateIsNull(String id) {

    }

    public void add(Rental rental) {

    }

    public void removeById(String id) {

    }

    public void save() {

    }
}
