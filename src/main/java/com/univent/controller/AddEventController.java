package com.univent.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import com.univent.services.EventService;

public class AddEventController extends BaseController {

    @FXML
    private TextField eventNameField;

    @FXML
    private TextField eventLocationField;

    @FXML
    private TextField eventDateField;

    @FXML
    private Button saveEventButton;

    private EventService eventService = new EventService();
    private DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    @FXML
    public void handleSaveEventButtonClick() {
        String eventName = eventNameField.getText();
        String eventLocation = eventLocationField.getText();
        String eventDate = eventDateField.getText();

        if (eventName.isEmpty() || eventLocation.isEmpty() || eventDate.isEmpty()) {
            System.out.println("All fields must be filled out");
            return;
        }

        boolean isSaved = eventService.addEvent(eventName, eventLocation, eventDate);
        if (isSaved) {
            System.out.println("Event successfully saved");
        } else {
            System.out.println("Failed to save the event");
        }
    }

    @FXML
    public void handleDashboardClick() {
        if (dashboardController != null) {
            dashboardController.handleDashboardClick();
        }
    }

    @FXML
    public void handleAllEventsClick() {
        if (dashboardController != null) {
            dashboardController.handleAllEventsClick();
        }
    }

    @FXML
    public void handleProfileClick() {
        if (dashboardController != null) {
            dashboardController.handleProfileClick();
        }
    }
}
