package com.univent.controller;

import com.univent.Entity.Event;
import com.univent.services.EventService;
import com.univent.services.UserService;
import com.univent.session.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.List;

public class AllEventsController extends BaseController {

    @FXML
    private ImageView eventImage; // Reference to event image
    @FXML
    private Label titleLabel;      // Reference to title label
    @FXML
    private Label categoryLabel;    // Reference to category label
    @FXML
    private Label createdDateLabel; // Reference to created date label
    @FXML
    private Button editButton;      // Reference to edit button
    @FXML
    private Button deleteButton;     // Reference to delete button
    @FXML
    private Button attendeesButton;

    @FXML
    private VBox tableView;  // VBox that acts as the table view container

    private final EventService eventService = new EventService();
    private UserService userService;
    private String loggedInUsername;

    // Setter method to set UserService instance from the previous controller
    public void setUserService(UserService userService) {
        this.userService = userService;
        System.out.println("UserService has been set in AllEventsController.");
        loadUserEvents();
    }
    public void setLoggedInUsername(String username) {
        this.loggedInUsername = username;
        System.out.println("Logged-in username set: " + username);
        loadUserEvents();
    }


    // Method to load events for the logged-in user
    private void loadUserEvents() {

        if (userService == null) {
            System.err.println("UserService is not set. Ensure it is passed from the previous controller.");
            return;
        }

        // Get the logged-in user's ID
        int loggedInUserId = Session.getInstance().getLoggedInUserId();
        if (loggedInUserId == 0) {
            System.err.println("No user is logged in.");
            return;
        }

        // Fetch only the events created by the logged-in user
        List<Event> userEvents = eventService.getEventsByAuthorId(loggedInUserId);
        if (userEvents == null || userEvents.isEmpty()) {
            System.out.println("No events found for author with ID: " + loggedInUserId);
        } else {
            System.out.println("Number of events found for author with ID '" + loggedInUserId + "': " + userEvents.size());
            for (Event event : userEvents) {
                System.out.println("Event Title: " + event.getTitle());
                System.out.println("Event Category: " + event.getCategory());
                System.out.println("Event Created Date: " + event.getCreatedDate());
                // Add more fields as needed
            }
            populateEventTable(userEvents);
        }
    }

    // Method to populate the event table with data
    private void populateEventTable(List<Event> events) {
        // Clear existing rows to avoid duplication
        tableView.getChildren().clear();

        for (Event event : events) {
            // Create a new HBox for each event row
            HBox eventRow = new HBox(20);
            eventRow.getStyleClass().add("table-row");

            // Create and add ImageView for event image
            ImageView eventImage = new ImageView();
            eventImage.setFitHeight(80.0);
            eventImage.setFitWidth(120.0);
            eventImage.setPickOnBounds(true);
            eventImage.setPreserveRatio(true);

            // Load the image from the path stored in the database
            String imagePath = event.getFeatureImage();
            if (imagePath != null && !imagePath.isEmpty()) {
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    eventImage.setImage(new Image(imageFile.toURI().toString()));
                } else {
                    // Set a fallback image if the image path is invalid
                    eventImage.setImage(new Image("/images/banner.jpg"));
                }
            } else {
                // Set a fallback image if the image path is null or empty
                eventImage.setImage(new Image("/images/banner.jpg"));
            }

            // Create and add Labels and Buttons for event properties
            Label titleLabel = new Label(event.getTitle() != null ? event.getTitle() : "N/A");
            titleLabel.setPrefWidth(200.0);
            titleLabel.getStyleClass().add("data-label");

            Label categoryLabel = new Label(event.getCategory() != null ? event.getCategory() : "N/A");
            categoryLabel.setPrefWidth(150.0);
            categoryLabel.getStyleClass().add("data-label");

            Label createdDateLabel = new Label(event.getStartDate() != null ? event.getStartDate() : "N/A");
            createdDateLabel.setPrefWidth(150.0);
            createdDateLabel.getStyleClass().add("data-label");

            Button editButton = new Button("Edit");
            editButton.setPrefWidth(100.0);
            editButton.getStyleClass().add("edit-button");
            editButton.setOnAction(e -> handleEditButtonClick(event));

            Button deleteButton = new Button("Delete");
            deleteButton.setPrefWidth(100.0);
            deleteButton.getStyleClass().add("delete-button");
            deleteButton.setOnAction(e -> handleDeleteButtonClick(event));

            Button attendeesButton = new Button("View Attendees");
            attendeesButton.setPrefWidth(120.0);
            attendeesButton.getStyleClass().add("attendees-button");
            attendeesButton.setOnAction(e -> handleAttendeesButtonClick(event));

            // Add all elements to the event row
            eventRow.getChildren().addAll(eventImage, titleLabel, categoryLabel, createdDateLabel, editButton, deleteButton, attendeesButton);

            // Add the row to the table view (VBox)
            tableView.getChildren().add(eventRow);
        }
    }

    // Handlers for button actions
    private void handleEditButtonClick(Event event) {
        System.out.println("Editing event: " + event.getTitle());
        // Logic for editing the event
    }

    private void handleDeleteButtonClick(Event event) {
        System.out.println("Deleting event: " + event.getTitle());
        // Logic for deleting the event
    }

    private void handleAttendeesButtonClick(Event event) {
        System.out.println("Viewing attendees for event: " + event.getTitle());
        // Logic for viewing attendees of the event
    }

    @FXML
    public void initialize() {
        // Initialization logic if needed
    }

    public void handleEditButtonClick(ActionEvent actionEvent) {

    }

    public void handleDeleteButtonClick(ActionEvent actionEvent) {

    }

    public void handleAttendeesButtonClick(ActionEvent actionEvent) {

    }
}
