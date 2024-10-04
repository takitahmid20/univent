package com.univent.controller;

import com.univent.Entity.User;
import com.univent.services.UserService;
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
            User loggedInUser = userService.getUserByUsername(username);
            if (loggedInUser != null) {
                userService.loginUser(loggedInUser); // Now, the user object contains the ID and can be used later

                messageLabel.setText("Hello " + username + ", login successful! Welcome.");
                messageLabel.setStyle("-fx-text-fill: green;");

                try {
                    // Load the Dashboard.fxml file
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Dashboard.fxml"));
                    AnchorPane pane = loader.load();

                    // Get the DashboardController and set the logged-in user
                    DashboardController dashboardController = loader.getController();
                    dashboardController.setUserService(userService); // Set the userService for further operations
                    dashboardController.handleLogin(userService.getLoggedInUsername());

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
        try {
            // Load the SignUp.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminLogin.fxml"));
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