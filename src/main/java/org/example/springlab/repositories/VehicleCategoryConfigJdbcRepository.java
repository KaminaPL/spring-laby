package org.example.springlab.repositories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.springlab.db.JdbcConnectionManager;
import org.example.springlab.models.VehicleCategoryConfig;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class VehicleCategoryConfigJdbcRepository implements VehicleCategoryConfigRepository {

    private final Gson gson = new Gson();
    private final DataSource dataSource;

    public VehicleCategoryConfigJdbcRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        String stm = "CREATE TABLE IF NOT EXISTS vehicle_configs (category TEXT PRIMARY KEY, attributes JSONB NOT NULL)";
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
    public List<VehicleCategoryConfig> getAll() {
        List<VehicleCategoryConfig> configList = new ArrayList<>();
        String stm = "SELECT * FROM vehicle_configs";
        Connection con = DataSourceUtils.getConnection(dataSource);
        try(PreparedStatement pstm = con.prepareStatement(stm)) {
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
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return configList;
    }

    @Override
    public Optional<VehicleCategoryConfig> findByCategory(String category) {
        String stm = "SELECT * FROM vehicle_configs WHERE category = ?";
        Connection con = DataSourceUtils.getConnection(dataSource);
        try(PreparedStatement pstm = con.prepareStatement(stm)) {
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
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return Optional.empty();
    }
}
