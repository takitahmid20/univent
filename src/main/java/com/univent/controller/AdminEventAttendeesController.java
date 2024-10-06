package com.univent.controller;

import com.univent.Entity.Attendee;
import com.univent.services.AttendeeService;
import com.univent.services.EventService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

public class AdminEventAttendeesController {

    @FXML
    private VBox attendeesList;

    @FXML
    private Label eventTitleLabel;

    private final AttendeeService attendeeService = new AttendeeService();
    private final EventService eventService = new EventService();
    private int eventId;

    // Method to set event ID and load attendees
    public void setEventId(int eventId) {
        this.eventId = eventId;
        loadAttendees();
    }

    // Method to load attendees based on the event ID
    private void loadAttendees() {
        // Fetch the event name from the EventService
        String eventName = eventService.getEventNameById(eventId);
        if (eventName != null) {
            eventTitleLabel.setText("Attendees List for Event: " + eventName);
        } else {
            eventTitleLabel.setText("Attendees List for Event: Unknown");
        }

        List<Attendee> attendees = attendeeService.getAttendeesByEventId(eventId);

        if (attendees != null && !attendees.isEmpty()) {
            attendeesList.getChildren().clear(); // Clear previous attendees

            for (Attendee attendee : attendees) {
                HBox attendeeBox = createAttendeeBox(attendee);
                attendeesList.getChildren().add(attendeeBox);
            }
        } else {
            Label noAttendeesLabel = new Label("No attendees registered for this event.");
            noAttendeesLabel.getStyleClass().add("no-attendees-label");
            attendeesList.getChildren().add(noAttendeesLabel);
        }
    }

    // Method to create an attendee row
    private HBox createAttendeeBox(Attendee attendee) {
        HBox attendeeBox = new HBox(20);
        attendeeBox.getStyleClass().add("attendee-box");

        Label nameLabel = new Label("Name: " + attendee.getName());
        nameLabel.getStyleClass().add("attendee-name");

        Label emailLabel = new Label("Email: " + attendee.getEmail());
        emailLabel.getStyleClass().add("attendee-email");

        Label phoneLabel = new Label("Phone: " + attendee.getMobileNumber());
        phoneLabel.getStyleClass().add("attendee-phone");

        Label universityLabel = new Label("University: " + attendee.getUniversity());
        universityLabel.getStyleClass().add("attendee-university");

        ComboBox<String> attendanceStatusComboBox = new ComboBox<>();
        attendanceStatusComboBox.getItems().addAll("Registered", "Attended", "Did Not Attend");
        attendanceStatusComboBox.setValue(attendee.getAttendanceStatus() != null ? attendee.getAttendanceStatus() : "Registered");
        attendanceStatusComboBox.setOnAction(e -> updateAttendanceStatus(attendee, attendanceStatusComboBox.getValue()));

        attendeeBox.getChildren().addAll(nameLabel, emailLabel, phoneLabel, universityLabel, attendanceStatusComboBox);

        return attendeeBox;
    }

    private void updateAttendanceStatus(Attendee attendee, String status) {
        attendee.setAttendanceStatus(status);
        boolean updated = attendeeService.updateAttendanceStatus(attendee.getRegistrationId(), status);
        if (updated) {
            System.out.println("Updated status for attendee: " + attendee.getName() + " to " + status);
        } else {
            System.err.println("Failed to update status for attendee: " + attendee.getName());
        }
    }

    // Handle navigation events
    public void handleDashboardClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminDashboard.fxml"));
            AnchorPane pane = loader.load();

            Stage stage = (Stage) ((Label) event.getSource()).getScene().getWindow(); // Use the event to get the current stage
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading page: " + e.getMessage());
        }

    }

    public void handleAllEventsClick(ActionEvent event) {
        // Logic to navigate to All Events page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminAllEventsPage.fxml"));
            AnchorPane pane = loader.load();

            Stage stage = (Stage) ((Label) event.getSource()).getScene().getWindow(); // Use the event to get the current stage
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading page: " + e.getMessage());
        }
    }

    public void handleAllUsersClick(ActionEvent event) {
        // Logic to navigate to All Users page
    }

    public void handleRegisteredEventsClick(ActionEvent event) {
        // Logic to navigate to Registered Events page
    }

    public void handleLogoutButtonClick(ActionEvent actionEvent) {
        // Logic to logout the user
    }

}
