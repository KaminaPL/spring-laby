package org.example.repositories;

import com.google.gson.Gson;
import org.example.db.JdbcConnectionManager;
import org.example.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserJdbcRepository implements UserRepository {

    private final Gson gson = new Gson();
    private List<User> userList = new ArrayList<>();

    public UserJdbcRepository() {
        String stm = "CREATE IF NOT EXISTS users (id TEXT PRIMARY KEY" +
        "login TEXT NOT NULL, password TEXT NOT NULL, role TEXT NOT NULL)";
        try(Connection con = JdbcConnectionManager.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement(stm)) {
            pstm.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<User> findByLogin(String login) {

    }

    public List<User> getAll() {

    }

    public void add(User user) {

    }

    public void removeByLogin(String login) {

    }

    public void update(User user) {

    }

    public void save() {

    }
}
