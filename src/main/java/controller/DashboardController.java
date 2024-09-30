package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.io.IOException;

public class DashboardController {

    @FXML
    private AnchorPane contentArea;

    @FXML
    public void initialize() {
        // By default, load the dashboard home page
        loadPage("Dashboard");
    }

    @FXML
    public void handleDashboardClick() {
        loadPage("Dashboard");
    }

    @FXML
    public void handleAllEventsClick() {
        loadPage("AllEvents");
    }

    @FXML
    public void handleAddEventClick() {
        loadPage("AddEvent");
    }

    @FXML
    public void handleProfileClick() {
        loadPage("Profile");
    }

    @FXML
    public void handleAddEventButtonClick() {
        loadPage("AddEvent");
    }

    private void loadPage(String page) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + page + ".fxml"));
            AnchorPane pane = loader.load();

            // Get the controller of the loaded page and pass the DashboardController to it
            if (loader.getController() instanceof BaseController) {
                BaseController controller = loader.getController();
                controller.setDashboardController(this);
            }

            contentArea.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
