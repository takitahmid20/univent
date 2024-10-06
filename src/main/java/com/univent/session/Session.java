package com.univent.session;

public class Session {

    // Singleton instance
    private static Session instance;

    // Session variables (admin and user status)
    private boolean isAdminLoggedIn;
    private boolean isUserLoggedIn;  // New variable for user login status
    private String loggedInUsername; // Store the logged-in username
    private int loggedInUserId;      // Store the logged-in user ID

    // Private constructor for Singleton pattern
    private Session() {
        this.isAdminLoggedIn = false;
        this.isUserLoggedIn = false;
        this.loggedInUsername = null;
        this.loggedInUserId = 0;  // Initialize userId to 0
    }

    // Method to get the singleton instance
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    // Method to get the logged-in user ID
    public int getLoggedInUserId() {
        return this.loggedInUserId;
    }

    // Method to set admin login status
    public void setAdminLoggedIn(boolean loggedIn) {
        this.isAdminLoggedIn = loggedIn;
    }

    // Method to check if admin is logged in
    public boolean isAdminLoggedIn() {
        return this.isAdminLoggedIn;
    }

    // Method to set user login status
    public void setUserLoggedIn(boolean loggedIn, String username, int userId) {
        this.isUserLoggedIn = loggedIn;
        this.loggedInUsername = username;
        this.loggedInUserId = userId;
    }

    // Method to check if a user is logged in
    public boolean isUserLoggedIn() {
        return this.isUserLoggedIn;
    }

    // Get the logged-in username
    public String getLoggedInUsername() {
        return this.loggedInUsername;
    }

    // Method to log out both user and admin
    public void logout() {
        this.isAdminLoggedIn = false;
        this.isUserLoggedIn = false;
        this.loggedInUsername = null;
        this.loggedInUserId = -1;

        System.out.println("Session cleared for both admin and user.");
    }
}
