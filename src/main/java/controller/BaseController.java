package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseController {

    protected void switchToScene(String fxmlPath, Stage stage) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Get the current stage
            stage = (Stage) root.getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(root, 1440, 900));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDashboardController(DashboardController dashboardController) {

    }
}
