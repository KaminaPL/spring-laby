package org.example.db;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnectionManager {

    private static JdbcConnectionManager instance = null;
    private final String url;

    private JdbcConnectionManager() {
        Dotenv dotenv = Dotenv.load();
        url = dotenv.get("DB_URL");
        if (url == null) {
            throw new RuntimeException("DB_URL not set.");
        }
    }

    public static JdbcConnectionManager getInstance() {
        if (instance == null) {
            instance = new JdbcConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
