package com.univent.controller;

import com.univent.session.Session;

import com.univent.Entity.User;
import com.univent.services.UserService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SignInController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Label messageLabel;

    @FXML
    private Label signUpLabel;

    @FXML
    private ImageView logoImageView;

    @FXML
    private Button adminSignInButton;

    private UserService userService = new UserService();

    @FXML
    public void initialize() {
        // Defer the check for user login until after the scene is fully loaded
        Platform.runLater(() -> {
            // Check if the user is already logged in
            if (Session.getInstance().isUserLoggedIn()) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Dashboard.fxml"));
                    AnchorPane pane = loader.load();
                    Stage stage = (Stage) signInButton.getScene().getWindow(); // After the scene is loaded, signInButton's scene will not be null
                    Scene scene = new Scene(pane);
                    stage.setScene(scene);
                    stage.setTitle("Dashboard");
                } catch (IOException e) {
                    e.printStackTrace();
                    messageLabel.setText("Error loading Dashboard.");
                    messageLabel.setStyle("-fx-text-fill: red;");
                }
            }
        });
    }



    private void redirectToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Dashboard.fxml"));
            AnchorPane pane = loader.load();
            Stage stage = (Stage) signInButton.getScene().getWindow();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Dashboard");
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Error loading Dashboard.");
        }
    }

    @FXML
    private void handleSignInButton() {
        System.out.println("Clicked on sign in button");
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate all fields
        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please fill in all fields.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Authenticate the user
        boolean isAuthenticated = userService.authenticateUser(username, password);
        if (isAuthenticated) {

            // Get the logged-in user by username (here is where we get the loggedInUser object)
            User loggedInUser = userService.getUserByUsername(username);

            if (loggedInUser != null) {
                System.out.println("Logged in user ID: " + loggedInUser.getId());
                // Store the user information in the session (including ID and username)
                Session.getInstance().setUserLoggedIn(true, username, loggedInUser.getId());

                messageLabel.setText("Hello " + username + ", login successful! Welcome.");
                messageLabel.setStyle("-fx-text-fill: green;");

                try {
                    // Load the Dashboard.fxml file
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Dashboard.fxml"));
                    AnchorPane pane = loader.load();

                    // Get the DashboardController and pass the logged-in user
                    DashboardController dashboardController = loader.getController();
                    dashboardController.setUserService(userService);  // Pass userService
                    dashboardController.handleLogin(loggedInUser.getUsername());  // Pass the username to the dashboard

                    // Get the current stage
                    Stage stage = (Stage) signInButton.getScene().getWindow();

                    // Set the new scene and update title
                    Scene scene = new Scene(pane);
                    stage.setScene(scene);
                    stage.setTitle("Dashboard");

                } catch (IOException e) {
                    e.printStackTrace();
                    messageLabel.setText("Error loading Dashboard page.");
                    messageLabel.setStyle("-fx-text-fill: red;");
                }
            } else {
                messageLabel.setText("Error: User not found after authentication.");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        } else {
            messageLabel.setText("Invalid username or password.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }







    @FXML
    private void handleSignUpLabelClick() {
        try {
            // Load the SignUp.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignUp.fxml"));
            AnchorPane pane = loader.load();

            // Get the current stage
            Stage stage = (Stage) signInButton.getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle("Sign Up");
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Error loading SignUp page.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    public void handleAdminSignInButton() {
        if (Session.getInstance().isAdminLoggedIn()) {
            try {
                // Load the SignUp.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminDashboard.fxml"));
                AnchorPane pane = loader.load();

                // Get the current stage
                Stage stage = (Stage) adminSignInButton.getScene().getWindow();

                // Set the new scene
                Scene scene = new Scene(pane);
                stage.setScene(scene);
                stage.setTitle("Admin Sign In");
            } catch (IOException e) {
                e.printStackTrace();
                messageLabel.setText("Error loading Admin Sign In page.");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        }
        else {
            // If not logged in, go to Admin Login page
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminLogin.fxml"));
                AnchorPane pane = loader.load();

                Stage stage = (Stage) adminSignInButton.getScene().getWindow();
                Scene scene = new Scene(pane);
                stage.setScene(scene);
                stage.setTitle("Admin Login");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void handleLogoClick() {
        System.out.println("Logo clicked, navigating to landing page.");

        try {
            // Load the landing page FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LandingPage.fxml"));
            ScrollPane pane = loader.load();
            Stage stage = (Stage) logoImageView.getScene().getWindow();
            stage.getScene().setRoot(pane);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading LandingPage.fxml: " + e.getMessage());
        }
    }


}