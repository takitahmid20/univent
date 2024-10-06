package com.univent.services;

import com.univent.Entity.Event;
import database.Database;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventService {

    // Create a new event in the database
    public void createEvent(String title, String description, File featureImage, String location, String startDate,
                            String startTime, String endDate, String endTime, String organizer, File organizerLogo,
                            String category, double price, int authorId, String authorName) { // Added authorName parameter
        String sql = "INSERT INTO events (title, description, feature_image, location, start_date, start_time, end_date, end_time, organizer, organizer_logo, category, price, author_id, author_name) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; // Updated SQL query to include author_name

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, featureImage != null ? featureImage.getPath() : null);
            pstmt.setString(4, location);
            pstmt.setString(5, startDate);
            pstmt.setString(6, startTime);
            pstmt.setString(7, endDate);
            pstmt.setString(8, endTime);
            pstmt.setString(9, organizer);
            pstmt.setString(10, organizerLogo != null ? organizerLogo.getPath() : null);
            pstmt.setString(11, category);
            pstmt.setDouble(12, price);
            pstmt.setInt(13, authorId); // Set the author ID
            pstmt.setString(14, authorName); // Set the author name

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Event successfully created!");
            }

        } catch (SQLException e) {
            System.out.println("Database insert error: " + e.getMessage());
        }
    }

    public boolean deleteEvent(int eventId) {
        String sql = "DELETE FROM events WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);
            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Database delete error: " + e.getMessage());
            return false;
        }
    }

    // Fetch all events from the database
    public List<Event> getAllEvents() {
        List<Event> eventList = new ArrayList<>();
        String sql = "SELECT * FROM events";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Event event = new Event();
                event.setId(rs.getInt("id"));
                event.setTitle(rs.getString("title"));
                event.setDescription(rs.getString("description"));
                event.setFeatureImage(rs.getString("feature_image"));
                event.setLocation(rs.getString("location"));
                event.setStartDate(rs.getString("start_date"));
                event.setStartTime(rs.getString("start_time"));
                event.setEndDate(rs.getString("end_date"));
                event.setEndTime(rs.getString("end_time"));
                event.setOrganizer(rs.getString("organizer"));
                event.setCategory(rs.getString("category"));
                event.setPrice(rs.getDouble("price"));
                event.setAuthorId(rs.getInt("author_id")); // Fetch the author's ID
                event.setAuthorName(rs.getString("author_name")); // Fetch the author's name
                eventList.add(event);
                System.out.println("Fetched event: ID=" + event.getId() + ", Title=" + event.getTitle());
            }
        } catch (SQLException e) {
            System.out.println("Database query error: " + e.getMessage());
        }

        return eventList;
    }

    // Fetch a single event by ID from the database
    public Event getEventById(int id) {
        String sql = "SELECT * FROM events WHERE id = ?";
        Event event = null;
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                event = new Event();
                event.setId(rs.getInt("id"));
                event.setTitle(rs.getString("title"));
                event.setDescription(rs.getString("description"));
                event.setFeatureImage(rs.getString("feature_image"));
                event.setLocation(rs.getString("location"));
                event.setStartDate(rs.getString("start_date"));
                event.setStartTime(rs.getString("start_time"));
                event.setEndDate(rs.getString("end_date"));
                event.setEndTime(rs.getString("end_time"));
                event.setOrganizer(rs.getString("organizer"));
                event.setOrganizerLogo(rs.getString("organizer_logo"));
                event.setCategory(rs.getString("category"));
                event.setPrice(rs.getDouble("price"));
                event.setAuthorId(rs.getInt("author_id")); // Fetch the author's ID
                event.setAuthorName(rs.getString("author_name")); // Fetch the author's name
            }
        } catch (SQLException e) {
            System.err.println("Database error while fetching the event: " + e.getMessage());
        }

        return event;
    }

    public List<Event> getEventsByAuthorId(int authorId) {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE author_id = ?"; // Adjust the query based on your table structure

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, authorId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Event event = new Event();
                event.setId(rs.getInt("id"));
                event.setTitle(rs.getString("title"));
                event.setDescription(rs.getString("description"));
                event.setFeatureImage(rs.getString("feature_image"));
                event.setLocation(rs.getString("location"));
                event.setStartDate(rs.getString("start_date"));
                event.setStartTime(rs.getString("start_time"));
                event.setEndDate(rs.getString("end_date"));
                event.setEndTime(rs.getString("end_time"));
                event.setOrganizer(rs.getString("organizer"));
                event.setOrganizerLogo(rs.getString("organizer_logo"));
                event.setCategory(rs.getString("category"));
                event.setPrice(rs.getDouble("price"));
                event.setAuthorId(rs.getInt("author_id")); // Fetch the author's ID
                event.setCreatedDate(rs.getString("created_date"));

                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public int getTotalEvents() {
        String sql = "SELECT COUNT(*) FROM events";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching total events: " + e.getMessage());
        }
        return 0;
    }

    public int getTotalRegistrations() {
        String sql = "SELECT COUNT(*) FROM registrations";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching total registrations: " + e.getMessage());
        }
        return 0;
    }

    public List<Event> getUpcomingEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE start_date >= CURRENT_DATE ORDER BY start_date ASC";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Event event = new Event();
                event.setId(rs.getInt("id"));
                event.setTitle(rs.getString("title"));
                event.setStartDate(rs.getString("start_date"));
                event.setAuthorName(rs.getString("author_name")); // Fetch the author's name
                events.add(event);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching upcoming events: " + e.getMessage());
        }
        return events;
    }

    public String getEventNameById(int eventId) {
        String sql = "SELECT title FROM events WHERE id = ?";
        String eventName = null;

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                eventName = rs.getString("title");
            }
        } catch (SQLException e) {
            System.err.println("Database query error while fetching event name: " + e.getMessage());
        }

        return eventName;
    }
}
