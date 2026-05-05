package org.example.repositories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.db.JdbcConnectionManager;
import org.example.models.User;
import org.example.models.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserJdbcRepository implements UserRepository {

    private final Gson gson = new Gson();

    public UserJdbcRepository() {
        String stm = "CREATE TABLE IF NOT EXISTS users (id TEXT PRIMARY KEY" +
        "login TEXT UNIQUE, password TEXT NOT NULL, role TEXT NOT NULL)";
        try(Connection con = JdbcConnectionManager.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<User> findByLogin(String login) {
        String stm = "SELECT * FROM users WHERE LOGIN = ?";
        try(Connection con = JdbcConnectionManager.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.setString(1, login);
            try(ResultSet rs = pstm.executeQuery()) {
                if(rs.next()) {
                    User user = new User(
                            rs.getString("id"),
                            rs.getString("login"),
                            rs.getString("passwword"),
                            rs.getString("role")
                    );
                    return Optional.of(user);
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        String stm = "SELECT * FROM users";
        try(Connection con = JdbcConnectionManager.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(stm)) {
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
        }
        return userList;
    }

    public void add(User user) {
        String stm = "INSERT INTO users (id, login, password, role) VALUES (?, ?, ?, ?)";
        try(Connection con = JdbcConnectionManager.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.setString(1, user.getId());
            pstm.setString(2, user.getLogin());
            pstm.setString(3, user.getPassword());
            pstm.setString(4, user.getRole());
            pstm.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeByLogin(String login) {
        String stm = "DELETE users WHERE login = ?";
        try(Connection con = JdbcConnectionManager.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.setString(1, login);
            pstm.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(User user) {

    }

    public void save() {

    }
}
