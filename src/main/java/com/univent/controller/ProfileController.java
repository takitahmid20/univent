package com.univent.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import com.univent.services.UserService;

public class ProfileController extends BaseController {

    @FXML
    private ImageView profileImageView;

    @FXML
    private Button uploadProfilePicButton;

    @FXML
    private Button saveProfileButton;

    @FXML
    private Label profileMessageLabel;

    private DashboardController dashboardController;
    private UserService userService = new UserService();

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    @FXML
    public void handleUploadProfilePicClick() {
        System.out.println("Upload Profile Picture button clicked");
        // Logic to upload profile picture
    }

    @FXML
    public void handleDashboardClick() {
        if (dashboardController != null) {
            dashboardController.handleDashboardClick();
        } else {
            System.out.println("DashboardController not initialized");
        }
    }

    @FXML
    public void handleAllEventsClick() {
        if (dashboardController != null) {
            dashboardController.handleAllEventsClick();
        } else {
            System.out.println("DashboardController not initialized");
        }
    }

    @FXML
    public void handleAddEventClick() {
        if (dashboardController != null) {
            dashboardController.handleAddEventClick();
        } else {
            System.out.println("DashboardController not initialized");
        }
    }

    @FXML
    public void handleSaveProfileClick() {
        System.out.println("Save Profile button clicked");
        // Logic to save the profile details
    }
}
