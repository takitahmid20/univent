package com.univent.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class AdminLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    // Static admin credentials
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "1234";

    @FXML
    private void handleLoginButtonClick() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Check static credentials
        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            try {
                // Load the AdminDashboard.fxml page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminDashboard.fxml"));
                AnchorPane pane = loader.load();

                // Get the current stage and set the new scene
                Stage stage = (Stage) loginButton.getScene().getWindow();
                Scene scene = new Scene(pane);
                stage.setScene(scene);
                stage.setTitle("Admin Dashboard");

            } catch (IOException e) {
                e.printStackTrace();
                showError("Error loading Admin Dashboard.");
            }
        } else {
            showError("Invalid username or password.");
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}
