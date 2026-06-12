package org.example.springlab.repositories;

import com.google.gson.Gson;
import org.example.springlab.db.JdbcConnectionManager;
import org.example.springlab.models.Rental;
import org.example.springlab.models.User;
import org.example.springlab.models.Vehicle;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Repository
@Profile("jdbc")
public class RentalJdbcRepository implements RentalRepository {

    private final Gson gson = new Gson();
    private final DataSource dataSource;

    public RentalJdbcRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        String stm = "CREATE TABLE IF NOT EXISTS rentals (id TEXT PRIMARY KEY," +
                "vehicle_id TEXT NOT NULL, user_id TEXT NOT NULL, rent_date TEXT NOT NULL, return_date TEXT, " +
                "FOREIGN KEY (vehicle_id) REFERENCES vehicles(id) ON DELETE CASCADE, " +
                "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE)";
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
    public List<Rental> getAll() {
        List<Rental> rentalList = new ArrayList<>();
        String stm = "SELECT * FROM rentals";
        Connection con = DataSourceUtils.getConnection(dataSource);
        try(PreparedStatement pstm = con.prepareStatement(stm)) {
            try(ResultSet rs = pstm.executeQuery()) {
                while(rs.next()) {
                    rentalList.add(new Rental(
                            rs.getString("id"),
                            new Vehicle(
                                    rs.getString("vehicle_id"),
                                    "",
                                    "",
                                    "",
                                    0,
                                    0.0,
                                    new HashMap<>()
                            ),
                            new User(
                                    rs.getString("user_id"),
                                    "",
                                    "",
                                    ""
                            ),
                            rs.getString("rent_date"),
                            rs.getString("return_date")
                    ));
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return rentalList.stream().filter(Rental::isActive).toList();
    }

    @Override
    public Optional<Rental> findById(String id) {
        String stm = "SELECT * FROM rentals WHERE id = ?";
        Connection con = DataSourceUtils.getConnection(dataSource);
        try(PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.setString(1, id);
            try(ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Rental(
                            rs.getString("id"),
                            new Vehicle(
                                    rs.getString("vehicle_id"),
                                    "",
                                    "",
                                    "",
                                    0,
                                    0.0,
                                    new HashMap<>()
                            ),
                            new User(
                                    rs.getString("user_id"),
                                    "",
                                    "",
                                    ""
                            ),
                            rs.getString("rent_date"),
                            rs.getString("return_date")
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
    public Optional<Rental> findByVehicleId(String vehicleId) {
        String stm = "SELECT * FROM rentals WHERE vehicle_id = ? AND return_date IS NULL";
        Connection con = DataSourceUtils.getConnection(dataSource);
        try(PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.setString(1, vehicleId);
            try(ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Rental(
                            rs.getString("id"),
                            new Vehicle(
                                    rs.getString("vehicle_id"),
                                    "",
                                    "",
                                    "",
                                    0,
                                    0.0,
                                    new HashMap<>()
                            ),
                            new User(
                                    rs.getString("user_id"),
                                    "",
                                    "",
                                    ""
                            ),
                            rs.getString("rent_date"),
                            null
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
    public Optional<Rental> findByUserId(String userId) {
        String stm = "SELECT * FROM rentals WHERE user_id = ? AND return_date IS NULL";
        Connection con = DataSourceUtils.getConnection(dataSource);
        try(PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.setString(1, userId);
            try(ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Rental(
                            rs.getString("id"),
                            new Vehicle(
                                    rs.getString("vehicle_id"),
                                    "",
                                    "",
                                    "",
                                    0,
                                    0.0,
                                    new HashMap<>()
                            ),
                            new User(
                                    rs.getString("user_id"),
                                    "",
                                    "",
                                    ""
                            ),
                            rs.getString("rent_date"),
                            null
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
    public Optional<Rental> findByIdAndReturnDateIsNull(String id) {
        String stm = "SELECT * FROM rentals WHERE id = ? AND return_date IS NULL";
        Connection con = DataSourceUtils.getConnection(dataSource);
        try(PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.setString(1, id);
            try(ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Rental(
                            rs.getString("id"),
                            new Vehicle(
                                    rs.getString("vehicle_id"),
                                    "",
                                    "",
                                    "",
                                    0,
                                    0.0,
                                    new HashMap<>()
                            ),
                            new User(
                                    rs.getString("user_id"),
                                    "",
                                    "",
                                    ""
                            ),
                            rs.getString("rent_date"),
                            null
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
    public void add(Rental rental) {
        if(rental.getId() == null || rental.getId().isBlank()) {
            rental.setId(UUID.randomUUID().toString());
            while(findById(rental.getId()).isPresent()) {
                rental.setId(UUID.randomUUID().toString());
            }
        }
        String stm = "INSERT INTO rentals (id, vehicle_id, user_id, rent_date, return_date)" +
                "VALUES (?, ?, ?, ?, ?)";
        Connection con = DataSourceUtils.getConnection(dataSource);
        try(PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.setString(1, rental.getId());
            pstm.setString(2, rental.getVehicle().getId());
            pstm.setString(3, rental.getUser().getId());
            pstm.setString(4, rental.getRentDateTime());
            pstm.setString(5, null);
            pstm.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeById(String id) {
        String stm = "UPDATE rentals SET return_date = ? WHERE id = ?";
        Connection con = DataSourceUtils.getConnection(dataSource);
        try(PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.setString(1, LocalDate.now().toString());
            pstm.setString(2, id);
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
