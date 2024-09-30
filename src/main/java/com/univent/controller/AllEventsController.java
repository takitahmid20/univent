package com.univent.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AllEventsController extends BaseController {

    @FXML
    private Button joinNowButton;

    private DashboardController dashboardController;

    @FXML
    public void handleJoinNowButtonClick() {
        // Implement logic for joining an event
        System.out.println("Join Now button clicked");
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    @FXML
    public void handleDashboardClick() {
        if (dashboardController != null) {
            dashboardController.handleDashboardClick();
        }
    }

    @FXML
    public void handleAddEventClick() {
        if (dashboardController != null) {
            dashboardController.handleAddEventClick();
        }
    }

    @FXML
    public void handleProfileClick() {
        if (dashboardController != null) {
            dashboardController.handleProfileClick();
        }
    }
}
