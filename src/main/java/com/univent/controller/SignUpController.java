package com.univent.controller;

import com.univent.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignUpController {

    private static final Logger logger = Logger.getLogger(SignUpController.class.getName());

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signUpButton;

    @FXML
    private Label messageLabel;

    @FXML
    private Label signInLabel;

    @FXML
    private ImageView logoImageView;

    private final UserService userService = new UserService();

    @FXML
    public void handleSignUpButton() {
        System.out.println("Sign Up Button Clicked");

        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        // Validate all fields
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please fill in all fields.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Validate email format
        if (!isValidEmail(email)) {
            messageLabel.setText("Please enter a valid email address.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Validate password strength
        if (password.length() < 6) {
            messageLabel.setText("Password must be at least 6 characters long.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Check if username or email already exists
        if (userService.isUsernameTaken(username)) {
            messageLabel.setText("Username already exists. Please choose another.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (userService.isEmailTaken(email)) {
            messageLabel.setText("An account with this email already exists. Please use a different email.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Register the user
        boolean isRegistered = userService.registerUser(username, email, password);
        if (isRegistered) {
            System.out.println("Registration successful");
            messageLabel.setText("Registration successful! Please log in.");
            messageLabel.setStyle("-fx-text-fill: green;");

            // Switch to the SignIn page after successful registration
            switchToSignInPage();
        } else {
            System.out.println("Registration failed");
            messageLabel.setText("Registration failed. Please try again later.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    public void handleSignInLabelClick(MouseEvent event) {
        System.out.println("Sign In Label Clicked");
        switchToSignInPage();
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

    private void switchToSignInPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignIn.fxml"));
            AnchorPane pane = loader.load();
            Stage stage = (Stage) signUpButton.getScene().getWindow();
            stage.getScene().setRoot(pane);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while switching to the SignIn scene", e);
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Utility method to validate email format
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}
