package com.univent.controller;

import com.univent.services.EventService;
import com.univent.session.Session;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AddEventController {

    @FXML
    private TextField eventTitleField;
    @FXML
    private TextArea eventDescriptionField;
    @FXML
    private TextField eventLocationField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private TextField startTimeField;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField endTimeField;
    @FXML
    private TextField organizerNameField;
    @FXML
    private ComboBox<String> eventCategoryComboBox;
    @FXML
    private TextField priceField;
    @FXML
    private Label featureImageLabel;
    @FXML
    private Label organizerLogoLabel;

    private File featureImageFile;
    private File organizerLogoFile;

    private EventService eventService = new EventService();

    // Add a field to store the logged-in username
    private String loggedInUsername;
    public void setLoggedInUsername(String username) {
        this.loggedInUsername = username;
        System.out.println("Logged in username set to: " + this.loggedInUsername); // Debugging line to ensure it is set correctly
    }


    @FXML
    private void handleImageUploadClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Event Feature Image");
        featureImageFile = fileChooser.showOpenDialog(new Stage());

        if (featureImageFile != null) {
            featureImageLabel.setText(featureImageFile.getName());
        }
    }

    @FXML
    private void handleLogoUploadClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Organizer Logo");
        organizerLogoFile = fileChooser.showOpenDialog(new Stage());

        if (organizerLogoFile != null) {
            organizerLogoLabel.setText(organizerLogoFile.getName());
        }
    }

    // This method allows setting the logged-in username from the DashboardController


    @FXML
    private void handleCreateEventClick() {
        // Check if loggedInUsername is correctly set
        if (loggedInUsername == null) {
            System.err.println("Error: loggedInUsername is null when creating the event");
            return;
        }

        // Validate the form
        if (eventTitleField.getText().isEmpty() ||
                eventDescriptionField.getText().isEmpty() ||
                eventLocationField.getText().isEmpty() ||
                startDatePicker.getValue() == null ||
                startTimeField.getText().isEmpty() ||
                endDatePicker.getValue() == null ||
                endTimeField.getText().isEmpty() ||
                organizerNameField.getText().isEmpty() ||
                eventCategoryComboBox.getValue() == null ||
                priceField.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the required fields.");
            alert.showAndWait();
            return;
        }

        // Validate price input
        double price;
        try {
            price = Double.parseDouble(priceField.getText());
            if (price < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText(null);
            alert.setContentText("Price must be a valid non-negative number.");
            alert.showAndWait();
            return;
        }

        // Get the logged-in user's ID from the session
        int authorId = Session.getInstance().getLoggedInUserId();

        // Call the service to create the event
        eventService.createEvent(
                eventTitleField.getText(),
                eventDescriptionField.getText(),
                featureImageFile,
                eventLocationField.getText(),
                startDatePicker.getValue().toString(),
                startTimeField.getText(),
                endDatePicker.getValue().toString(),
                endTimeField.getText(),
                organizerNameField.getText(),
                organizerLogoFile,
                eventCategoryComboBox.getValue(),
                price,
                authorId,               // Pass the logged-in user's ID as the author ID
                loggedInUsername        // Pass the logged-in username here
        );

        // Show success alert
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Event has been successfully created!");
        successAlert.showAndWait();

        // Clear form fields after successful creation
        clearFormFields();
    }


    // Method to clear form fields after creating an event
    private void clearFormFields() {
        eventTitleField.clear();
        eventDescriptionField.clear();
        eventLocationField.clear();
        startDatePicker.setValue(null);
        startTimeField.clear();
        endDatePicker.setValue(null);
        endTimeField.clear();
        organizerNameField.clear();
        eventCategoryComboBox.setValue(null);
        priceField.clear();
        featureImageLabel.setText("No file chosen");
        organizerLogoLabel.setText("No file chosen");
        featureImageFile = null;
        organizerLogoFile = null;
    }
}
