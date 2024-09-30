package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtility {
    private static final String URL = "jdbc:mysql://localhost:8889/univent_db"; // Replace `univent` with your database name
    private static final String USER = "root"; // Replace `root` with your database username
    private static final String PASSWORD = "root"; // Replace `password` with your database password

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
}
