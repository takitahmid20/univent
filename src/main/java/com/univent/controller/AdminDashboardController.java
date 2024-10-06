package com.univent.controller;

import com.univent.Entity.Event;
import com.univent.services.EventService;
import com.univent.services.UserService;
import com.univent.session.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AdminDashboardController {

    @FXML
    private Label totalUsersLabel;

    @FXML
    private Label totalEventsLabel;

    @FXML
    private Label totalRegisteredLabel;

    @FXML
    private TableView<Event> upcomingEventsTable;

    @FXML
    private TableColumn<Event, String> titleColumn;

    @FXML
    private TableColumn<Event, String> imageColumn;

    @FXML
    private TableColumn<Event, String> eventDateColumn;

    @FXML
    private TableColumn<Event, String> authorColumn;

    @FXML
    private AnchorPane contentArea;

    private final UserService userService = UserService.getInstance();
    private final EventService eventService = new EventService();

    @FXML
    public void initialize() {
        loadStatistics();
        loadUpcomingEvents();
        if (!Session.getInstance().isAdminLoggedIn()) {
            redirectToAdminLogin();
        }
    }

    private void redirectToAdminLogin() {
        try {
            // Load the AdminLogin.fxml page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminLogin.fxml"));
            AnchorPane root = loader.load();

            // Get the current stage and set the new scene (Admin Login Page)
            Stage stage = (Stage) totalUsersLabel.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Admin Login");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load statistics
    private void loadStatistics() {
        int totalUsers = userService.getTotalUsers();
        int totalEvents = eventService.getTotalEvents();
        int totalRegistrations = eventService.getTotalRegistrations();

        totalUsersLabel.setText(String.valueOf(totalUsers));
        totalEventsLabel.setText(String.valueOf(totalEvents));
        totalRegisteredLabel.setText(String.valueOf(totalRegistrations));
    }

    // Method to load upcoming events into the table
    private void loadUpcomingEvents() {
        // Set up the columns in the table
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("featureImage"));
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("authorName"));

        // Fetch upcoming events from the database
        List<Event> upcomingEvents = eventService.getUpcomingEvents();

        if (upcomingEvents != null) {
            ObservableList<Event> eventData = FXCollections.observableArrayList(upcomingEvents);
            upcomingEventsTable.setItems(eventData);
        }
    }

    // Handle navigation to All Events page
    @FXML
    private void handleAllEventsClick() {
//        loadPage("AdminAllEventsPage");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminAllEventsPage.fxml"));
            ScrollPane page = loader.load();
            contentArea.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading page AdminAllEventsPage");
        }
    }

    // Handle navigation to All Users page
    @FXML
    private void handleAllUsersClick() {
        loadPage("AllUsers");
    }

    // Handle navigation to Registered Events page
    @FXML
    private void handleRegisteredEventsClick() {
        loadPage("RegisteredEvents");
    }

    // Load the specified page into the center of the dashboard
    private void loadPage(String pageName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + pageName + ".fxml"));
            AnchorPane page = loader.load();
            contentArea.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading page: " + pageName);
        }
    }

    public void handleDashboardClick(MouseEvent event) {
        // Add code to handle dashboard click if needed
    }

    public void handleLogoutButtonClick(ActionEvent actionEvent) {
        // Add code to handle logout if needed
    }
}
