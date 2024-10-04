package com.univent.services;

import com.univent.Entity.Attendee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import database.Database;

public class AttendeeService {

    // Get all attendees for a specific event
    public List<Attendee> getAttendeesByEventId(int eventId) {
        List<Attendee> attendees = new ArrayList<>();
        String sql = "SELECT * FROM registrations WHERE event_id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Attendee attendee = new Attendee();
                attendee.setRegistrationId(rs.getInt("id"));
                attendee.setName(rs.getString("name"));
                attendee.setEmail(rs.getString("email"));
                attendee.setMobileNumber(rs.getString("mobile_number"));
                attendee.setUniversity(rs.getString("university"));
                attendee.setEventId(rs.getInt("event_id"));
                attendee.setAttendanceStatus(rs.getString("attendance_status")); // Assuming attendance status is stored in the table
                attendees.add(attendee);
            }

        } catch (SQLException e) {
            System.err.println("Database query error while fetching attendees: " + e.getMessage());
        }

        return attendees;
    }

    // Update attendance status for an attendee
    public boolean updateAttendanceStatus(int registrationId, String status) {
        String sql = "UPDATE registrations SET attendance_status = ? WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, registrationId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Database update error: " + e.getMessage());
            return false;
        }
    }
}
