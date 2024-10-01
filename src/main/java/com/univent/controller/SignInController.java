package com.univent.controller;

import com.univent.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
            messageLabel.setText("Login successful! Welcome.");
            messageLabel.setStyle("-fx-text-fill: green;");
            // Redirect to the next page (e.g., user dashboard)
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
}