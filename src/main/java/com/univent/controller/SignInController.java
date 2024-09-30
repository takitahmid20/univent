package com.univent.controller;

import com.univent.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignInController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Label messageLabel;

    private UserService userService = new UserService();

    @FXML
    private void handleSignInButton() {
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
}
