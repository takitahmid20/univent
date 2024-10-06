package com.univent.controller;

import com.univent.services.RegistrationService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

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

    @FXML
    private Button registerButton;

    private int eventId; // Store the event ID to link registration to an event
    private RegistrationService registrationService = new RegistrationService();

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    @FXML
    private void handleRegisterButtonClick() {
        // Validate the input fields
        String name = nameField.getText();
        String email = emailField.getText();
        String mobileNumber = mobileNumberField.getText();
        String university = universityComboBox.getValue();
        String seat = seatComboBox.getValue();

        if (name.isEmpty() || email.isEmpty() || mobileNumber.isEmpty() || university == null || seat == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill in all fields.");
            return;
        }

        // Call the registration service to save the data
        boolean isRegistered = registrationService.registerUser(eventId, name, email, mobileNumber, university, Integer.parseInt(seat));

        if (isRegistered) {
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "You have successfully registered for the event!");
            closePopup();
        } else {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "An error occurred during registration. Please try again.");
        }
    }

    private void closePopup() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
