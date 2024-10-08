package com.univent.services;

import com.univent.Entity.Event;
import com.univent.session.Session;
import database.Database;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.univent.Entity.User;

public class UserService {

    private String loggedInUsername;
    private static UserService instance;  // Singleton instance for UserService
    private User loggedInUser;

//    private final UserService userService = UserService.getInstance();

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public void loginUser(User user) {
        if (user != null) {
            this.loggedInUser = user;
            this.loggedInUsername = user.getUsername(); // Ensure this line is there
            System.out.println("User logged in: " + user.getUsername());
        } else {
            System.err.println("Error: User object is null while logging in.");
        }
    }


    public int getLoggedInUserId() {
        if (loggedInUser != null) {
            // If User does not have an ID, you need to add an `id` field to `User` class
            return Session.getInstance().getLoggedInUserId();
        } else {
            System.err.println("Error: No user is logged in.");
            return 0;
        }
    }



    // Register a new user
    public boolean registerUser(String username, String email, String password) {
        // Check if username or email already exists
        if (isUsernameTaken(username)) {
            System.out.println("Registration failed: Username already taken");
            return false;
        }

        if (isEmailTaken(email)) {
            System.out.println("Registration failed: Email already in use");
            return false;
        }

        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password); // Password should be hashed before storing (recommended)

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Registration failed: " + e.getMessage());
            return false;
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

            if (rs.next()) {
                // User authenticated successfully
                loggedInUsername = username;
                Session.getInstance().setUserLoggedIn(true, username, rs.getInt("id")); // Make sure to fetch ID
                return true;
            }

            return false;

        } catch (SQLException e) {
            System.out.println("Authentication failed: " + e.getMessage());
            return false;
        }
    }

    // Check if a username is already taken
    public boolean isUsernameTaken(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            return rs.next(); // Returns true if a user with the given username exists

        } catch (SQLException e) {
            System.out.println("Error checking username: " + e.getMessage());
            return true; // Assume username is taken in case of an error
        }
    }

    // Check if an email is already registered
    public boolean isEmailTaken(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            return rs.next(); // Returns true if a user with the given email exists

        } catch (SQLException e) {
            System.out.println("Error checking email: " + e.getMessage());
            return true; // Assume email is taken in case of an error
        }
    }

    // Check if a user is logged in
    public boolean isLoggedIn() {
        return loggedInUsername != null;
    }

    public void setLoggedInUsername(String username) {
        this.loggedInUsername = username;
    }

    // Get the logged-in username
    public String getLoggedInUsername() {
        return loggedInUsername;
    }




    // Update user profile (not implemented yet)
    public boolean updateProfile(String username, String email, String password) {
        // Update profile logic goes here
        return false;
    }

    // ------------- Profile.fxml started from here

    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        User user = null;

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("User found with username: " + rs.getString("username"));
                user = new User();
                user.setId(rs.getInt("id"));  // Fetch and set the user ID
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setFullName(rs.getString("full_name"));
                user.setBio(rs.getString("bio"));
                user.setAddress(rs.getString("address"));
                user.setMobileNumber(rs.getString("mobile_number"));
                user.setUniversity(rs.getString("university"));
                user.setProfilePicture(rs.getString("profile_picture"));
                user.setNidFront(rs.getString("nid_front"));
                user.setNidBack(rs.getString("nid_back"));
            } else {
                System.out.println("No user found with username: " + username);
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace(); // Print the complete stack trace for debugging
        }

        return user;
    }




    public boolean updateProfile(String username, String fullName, String password, String bio, String address,
                                 String mobileNumber, String university, String profilePicture,
                                 String nidFront, String nidBack) {
        String sql = "UPDATE users SET full_name = ?, password = ?, bio = ?, address = ?, " +
                "mobile_number = ?, university = ?, profile_picture = ?, nid_front = ?, nid_back = ? " +
                "WHERE username = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fullName);
            pstmt.setString(2, password); // Make sure you hash the password if necessary
            pstmt.setString(3, bio);
            pstmt.setString(4, address);
            pstmt.setString(5, mobileNumber);
            pstmt.setString(6, university);
            pstmt.setString(7, profilePicture);
            pstmt.setString(8, nidFront);
            pstmt.setString(9, nidBack);
            pstmt.setString(10, username); // Username is used to locate the record

            int rowsAffected = pstmt.executeUpdate();

            // Check if any rows were updated
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database error: " + e.getMessage());
            return false;
        }
    }


    public int getTotalUsers() {
        String sql = "SELECT COUNT(*) FROM users";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching total users: " + e.getMessage());
        }
        return 0;
    }

    public List<Event> getEventsByAuthorId(int userId) {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE author_id = ?"; // Adjust your SQL according to your database structure

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Event event = new Event();
                event.setId(rs.getInt("id")); // Assuming you have an ID field
                event.setTitle(rs.getString("title"));
                event.setCategory(rs.getString("category"));
                event.setFeatureImage(rs.getString("feature_image")); // Assuming you have this field
                event.setStartDate(rs.getString("start_date")); // Adjust according to your database structure
                event.setAuthorId(rs.getInt("author_id")); // Assuming you have an author_id field
                events.add(event);
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
        return events;
    }


    public void logout() {
        // Clear the session
        com.univent.session.Session.getInstance().logout();
        System.out.println("Session cleared successfully.");
    }



}
