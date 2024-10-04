package com.univent.services;

import com.univent.Entity.Registration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import database.Database;

public class RegistrationService {



    public boolean registerUser(int eventId, String name, String email, String mobileNumber, String university, int seat) {
        if (eventId <= 0) {
            System.err.println("Invalid event ID for registration: " + eventId);
            return false; // Don't proceed with invalid event ID
        }

        String sql = "INSERT INTO registrations (event_id, name, email, mobile_number, university, seat) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setString(4, mobileNumber);
            pstmt.setString(5, university);
            pstmt.setInt(6, seat);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Database insert error: " + e.getMessage());
            return false;
        }
    }
}
