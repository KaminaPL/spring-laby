package org.example.repositories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.db.JdbcConnectionManager;
import org.example.models.Vehicle;
import org.example.models.VehicleCategoryConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VehicleCategoryConfigJdbcRepository implements VehicleCategoryConfigRepository {

    private final Gson gson = new Gson();

    public VehicleCategoryConfigJdbcRepository() {
        String stm = "CREATE TABLE IF NOT EXISTS vehicle_configs (category TEXT PRIMARY KEY, attributes JSONB NOT NULL)";
        try(Connection con = JdbcConnectionManager.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public List<VehicleCategoryConfig> getAll() {
        List<VehicleCategoryConfig> configList = new ArrayList<>();
        String stm = "SELECT * FROM vehicle_configs";
        try(Connection con = JdbcConnectionManager.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(stm)) {
            try(ResultSet rs = pstm.executeQuery()) {
                while(rs.next()) {
                    String dbattr = rs.getString("attributes");
                    Map<String, String> attr = gson.fromJson(dbattr,
                            new TypeToken<>(){});
                    configList.add(new VehicleCategoryConfig(
                            rs.getString("category"),
                            attr
                    ));
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return configList;
    }

    public Optional<VehicleCategoryConfig> findByCategory(String category) {
        String stm = "SELECT * FROM vehicle_configs WHERE category = ?";
        try(Connection con = JdbcConnectionManager.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.setString(1, category);
            try(ResultSet rs = pstm.executeQuery()) {
                if(rs.next()) {
                    String dbattr = rs.getString("attributes");
                    Map<String, String> attr = gson.fromJson(dbattr,
                            new TypeToken<>(){});
                    return Optional.of(new VehicleCategoryConfig(
                            category,
                            attr
                    ));
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
