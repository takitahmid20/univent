package com.univent.controller;

import com.univent.services.UserService;
import com.univent.session.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class SidebarController {



    private UserService userService = new UserService(); // Declare the UserService variable

    @FXML
    private Label eventsLabel;

    @FXML
    private VBox sidebarContainer;

    @FXML
    private Button logoutButton;

    @FXML
    private void handleDashboardClick(MouseEvent event) {
        switchToPage("/view/Dashboard.fxml", "Dashboard");
    }

    @FXML
    private void handleProfileClick(MouseEvent event) {
        switchToPage("/view/Profile.fxml", "Profile");
    }

    @FXML
    private void handleEventsClick(MouseEvent event) {
//        switchToPage("/view/AllEvents.fxml", "Events");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AllEvents.fxml"));
            AnchorPane pane = loader.load();

            // Get the controller and set the user service
            AllEventsController allEventsController = loader.getController();
            allEventsController.setLoggedInUsername(Session.getInstance().getLoggedInUsername()); // Pass the UserService
            allEventsController.setUserService(userService);

            // Load the new scene
            Stage stage = (Stage) eventsLabel.getScene().getWindow();
            stage.setScene(new Scene(pane));
            stage.setTitle("All Events");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading AllEvents.fxml: " + e.getMessage());
        }
    }

    @FXML
    private void handleLogoutClick() {
        System.out.println("Logout Button Clicked");

        // Clear the session to log the user out
        Session.getInstance().logout();
        System.out.println("User logged out successfully.");
        // Log out and return to landing page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignIn.fxml"));
            Parent pane = loader.load(); // Changed to Parent

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            if (stage == null) {
                System.err.println("Error: Unable to get the current stage.");
                return;
            }
            stage.setScene(new Scene(pane)); // Changed from ScrollPane to Parent
            stage.setTitle("Sign In");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogoClick(MouseEvent event) {
        // Redirect to the landing page when the logo is clicked
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LandingPage.fxml"));
            Parent pane = loader.load(); // Changed to Parent

            Stage stage = (Stage) sidebarContainer.getScene().getWindow();
            stage.setScene(new Scene(pane)); // Changed from ScrollPane to Parent
            stage.setTitle("Landing Page");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchToPage(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane pane = loader.load(); // Changed to Parent

            Stage stage = (Stage) sidebarContainer.getScene().getWindow();
            stage.setScene(new Scene(pane)); // Changed from ScrollPane to Parent
            stage.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
