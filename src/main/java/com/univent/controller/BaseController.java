package com.univent.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseController {

    protected DashboardController dashboardController;
    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    // Updated switchToScene method to use the button to get the current stage
    protected void switchToScene(String fxmlPath, Stage button) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Get the current stage using the button reference
            Stage stage = (Stage) button.getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(root, 1440, 900));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}