package com.univent.services;

import database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    // Register a new user
    public boolean registerUser(String username, String email, String password, String user) {
        String sql = "INSERT INTO users (username, email, password, user_role) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password); // Password should be hashed before storing (recommended)
//            pstmt.setString(4, userRole);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Registration failed: " + e.getMessage());
            return false; // Username may already exist or other SQL error
        }
    }

    // Authenticate a user
    public boolean authenticateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password); // Compare hashed password in production
            ResultSet rs = pstmt.executeQuery();

            return rs.next(); // Returns true if a user with the given username and password exists

        } catch (SQLException e) {
            System.out.println("Authentication failed: " + e.getMessage());
            return false;
        }
    }

    public boolean updateProfile(String username, String email, String password) {
        return false;
    }
}
