package com.univent.controller;

import com.univent.services.UserService;
import com.univent.session.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;

public class DashboardController extends BaseController {

    @FXML
    private AnchorPane contentArea;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label allEventsLabel;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button logoutButton;
    private UserService userService = new UserService();
    private String username;
    private String loggedInUsername;
    //usernameLabel

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @FXML
    public void initialize() {
        // Fetch the logged-in username from the session and display it
        String username = Session.getInstance().getLoggedInUsername();
        int userId = Session.getInstance().getLoggedInUserId();

        welcomeLabel.setText("Welcome, " + username + " (ID: " + userId + ")");
    }

//    @FXML
//    public void initialize() {
//        // Load only once without causing a loop
//        if (!isDashboardLoaded) {
//            loadPage("Dashboard");
//            isDashboardLoaded = true;
//        }
//    }


    public void setUsername(String username) {
        System.out.println("Welcome "+username);
        usernameLabel.setText("Welcome "+username);
    }

    @FXML
    public void handleDashboardClick() {
        loadPage("Dashboard");
    }

    @FXML
    public void handleAllEventsClick() {
        loadPage("AllEvents");

    }

//    @FXML
//    private void handleAllEventsClick() {
//        System.out.println("Attempting to load All Events page...");
//
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AllEvents.fxml"));
//            AnchorPane pane = loader.load();
//
//            // Debug statement
//            System.out.println("Loaded AllEvents.fxml successfully.");
//
//            AllEventsController controller = loader.getController();
//            controller.setDashboardController(this); // Set the DashboardController reference if needed
//
//            // Use an existing component to get the current stage
//            Stage stage = (Stage) allEventsLabel.getScene().getWindow();
//            Scene scene = new Scene(pane);
//            stage.setScene(scene);
//            stage.setTitle("All Events");
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Error loading AllEvents.fxml: " + e.getMessage());
//        }
//    }


    public void handleLogin(String username) {
        this.loggedInUsername = username;
        System.out.println("Logged in username set to: " + this.loggedInUsername);
    }

    @FXML
    public void handleCreateEventButtonClick() {

        String username = Session.getInstance().getLoggedInUsername();

        if (username == null) {
            System.err.println("Error: loggedInUsername is null before loading AddEvent.fxml");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddEvent.fxml"));
            Parent root = loader.load();

            AddEventController addEventController = loader.getController();
            addEventController.setLoggedInUsername(username); // Ensure this matches exactly


            contentArea.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading AddEvent.fxml: " + e.getMessage());
        }
    }




//    @FXML
//    public void handleCreateEventButtonClick(){
//        System.out.println("CLicked on Create event button");
//        loadPage("AddEvent");
//    }

    @FXML
    public void handleProfileClick() {
        loadPage("Profile");
    }



    private boolean isDashboardLoaded = false;

    private void loadPage(String page) {
        try {
            System.out.println("Attempting to load FXML from path: /view/" + page + ".fxml");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + page + ".fxml"));
            if (loader.getLocation() == null) {
                System.err.println("Error: FXML file not found at path: /view/" + page + ".fxml");
                return;
            }

            Parent pane = loader.load();
            if (page.equals("AllEvents")) {
                AllEventsController controller = loader.getController();
                controller.setUserService(userService); // Pass the userService to AllEventsController
            }
            // Assuming you have a way to replace the content area with the loaded page
            contentArea.getChildren().setAll(pane);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load FXML file: " + page);
        }
    }



    @FXML
    private void handleLogoutButtonClick() {
        System.out.println("Logout Button Clicked");

        // Ensure the UserService is initialized
        if (userService == null) {
            System.err.println("Error: UserService is not initialized.");
            return;
        }

        // Log out the user using UserService (clearing session)
        userService.logout();  // You need to ensure UserService has a logout method
        System.out.println("User logged out successfully.");

        try {
            // Load the LandingPage.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LandingPage.fxml"));
            ScrollPane pane = loader.load();

            // Get the current stage
            Stage stage = (Stage) logoutButton.getScene().getWindow();  // Assuming logoutButton is not null and tied to UI
            if (stage == null) {
                System.err.println("Error: Unable to get the current stage.");
                return;
            }

            // Set the new scene
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Landing Page");

            // Clear any logged-in user state in the session or user service
            loggedInUsername = null;  // This should clear the username or session-specific data.

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading LandingPage.fxml: " + e.getMessage());
        }
    }



}

