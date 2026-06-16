package org.example.springlab.repositories;

import com.google.gson.Gson;
import org.example.springlab.models.User;
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
import java.util.Optional;

@Repository
@Profile("jdbc")
public class UserJdbcRepository implements UserRepository {

    private final Gson gson = new Gson();
    private final DataSource dataSource;

    public UserJdbcRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        String stm = "CREATE TABLE IF NOT EXISTS users (id TEXT PRIMARY KEY, " +
        "login TEXT UNIQUE, password TEXT NOT NULL, role TEXT NOT NULL)";
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
    public Optional<User> findById(String id) {
        String stm = "SELECT * FROM users WHERE id = ?";
        Connection con = DataSourceUtils.getConnection(dataSource);
        try(PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.setString(1, id);
            try(ResultSet rs = pstm.executeQuery()) {
                if(rs.next()) {
                    User user = new User(
                            rs.getString("id"),
                            rs.getString("login"),
                            rs.getString("password"),
                            rs.getString("role")
                    );
                    return Optional.of(user);
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
    public Optional<User> findByLogin(String login) {
        String stm = "SELECT * FROM users WHERE login = ?";
        Connection con = DataSourceUtils.getConnection(dataSource);
        try(PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.setString(1, login);
            try(ResultSet rs = pstm.executeQuery()) {
                if(rs.next()) {
                    User user = new User(
                            rs.getString("id"),
                            rs.getString("login"),
                            rs.getString("password"),
                            rs.getString("role")
                    );
                    return Optional.of(user);
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
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        String stm = "SELECT * FROM users";
        Connection con = DataSourceUtils.getConnection(dataSource);
        try(PreparedStatement pstm = con.prepareStatement(stm)) {
            try(ResultSet rs = pstm.executeQuery()) {
                while(rs.next()) {
                   userList.add(new User(
                           rs.getString("id"),
                           rs.getString("login"),
                           rs.getString("passwword"),
                           rs.getString("role")
                   ));
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return userList;
    }

    @Override
    public void add(User user) {
        String stm = "INSERT INTO users (id, login, password, role) VALUES (?, ?, ?, ?)";
        Connection con = DataSourceUtils.getConnection(dataSource);
        try(PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.setString(1, user.getId());
            pstm.setString(2, user.getLogin());
            pstm.setString(3, user.getPassword());
            pstm.setString(4, user.getRole());
            pstm.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }

    @Override
    public void removeById(String id) {
        String stm = "DELETE users WHERE id = ?";
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
    public void update(User user) {

    }

    @Override
    public void save() {

    }
}
