package com.univent.controller;

import com.univent.services.RegistrationService;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.univent.Entity.Registration;
import javafx.event.ActionEvent;

public class JoinEventPopupController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField mobileNumberField;

    @FXML
    private ComboBox<String> universityComboBox;

    @FXML
    private ComboBox<String> seatComboBox;

    private int eventId; // Store the event ID to link registration to an event

    // Service to handle registration in the database
    private RegistrationService registrationService = new RegistrationService();

    // Method to set the event ID when opening the popup
    public void setEventId(int eventId) {
        this.eventId = eventId;
        System.out.println("Event ID set for registration: " + eventId);
    }

    @FXML
    private void handleRegisterButtonClick() {
        System.out.println("Attempting to register for event ID: " + eventId);

        // Validate event ID
        if (eventId <= 0) {
            System.err.println("Error: Invalid event ID.");
            showAlert(AlertType.ERROR, "Registration Failed", "Invalid event ID. Please try again.");
            return;
        }

        // Collect user input
        String name = nameField.getText();
        String email = emailField.getText();
        String mobileNumber = mobileNumberField.getText();
        String university = universityComboBox.getValue();
        String seat = seatComboBox.getValue();

        // Validate user input
        if (name.isEmpty() || email.isEmpty() || mobileNumber.isEmpty() || university == null || seat == null) {
            showAlert(AlertType.ERROR, "Validation Error", "Please fill in all fields.");
            return;
        }

        // Save registration data to the database
        boolean isRegistered = registrationService.registerUser(eventId, name, email, mobileNumber, university, Integer.parseInt(seat));

        if (isRegistered) {
            showAlert(AlertType.INFORMATION, "Registration Successful", "You have successfully registered for the event!");

            // Close the popup after successful registration
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();
        } else {
            showAlert(AlertType.ERROR, "Registration Failed", "An error occurred during registration. Please try again.");
        }
    }

    // Utility method to show alert messages
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
