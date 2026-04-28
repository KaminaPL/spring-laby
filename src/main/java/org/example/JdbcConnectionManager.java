package org.example;

import lombok.Singular;
import java.sql.DriverManager;

public class JdbcConnectionManager {

    private static JdbcConnectionManager instance = null;
    private String url;

    private JdbcConnectionManager() {
        url = System.getenv("DB_URL");
        if(url == null) {
            throw new RuntimeException("DB_URL not set.");
        }
    }

    public static JdbcConnectionManager getInstance() {
        if (instance == null) {
            instance = new JdbcConnectionManager();
        }
        return instance;
    }




}
