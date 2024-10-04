package com.univent.controller;

import com.univent.Entity.Event;
import com.univent.services.EventService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AdminAllEventsController {

    @FXML
    private FlowPane eventsFlowPane;

    private final EventService eventService = new EventService();

    @FXML
    public void initialize() {
        loadAllEvents();
    }

    // Method to load all events into the FlowPane
    private void loadAllEvents() {
        List<Event> allEvents = eventService.getAllEvents();

        if (allEvents != null && !allEvents.isEmpty()) {
            eventsFlowPane.getChildren().clear(); // Clear existing children to avoid duplication

            for (Event event : allEvents) {
                VBox eventBox = createEventBox(event);
                eventsFlowPane.getChildren().add(eventBox);
            }
        }
    }

    // Method to create a VBox representing an event box
    private VBox createEventBox(Event event) {
        VBox eventBox = new VBox(10); // Spacing between elements
        eventBox.setPrefSize(300, 350);
        eventBox.getStyleClass().add("event-box");

        ImageView eventImage = new ImageView();
        eventImage.setFitHeight(150);
        eventImage.setFitWidth(280);
        eventImage.setPickOnBounds(true);
        eventImage.setPreserveRatio(true);

        String imagePath = event.getFeatureImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                eventImage.setImage(new Image(imageFile.toURI().toString()));
            } else {
                eventImage.setImage(new Image("/images/event-placeholder.jpg"));
            }
        } else {
            eventImage.setImage(new Image("/images/event-placeholder.jpg"));
        }

        Label eventTitle = new Label(event.getTitle());
        eventTitle.getStyleClass().add("event-title");

        Label eventOrganizer = new Label("Organizer: " + event.getOrganizer());
        eventOrganizer.getStyleClass().add("event-organizer");

        Label eventDate = new Label("Date: " + event.getStartDate());
        eventDate.getStyleClass().add("event-date");

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(javafx.geometry.Pos.CENTER);

        Button editButton = new Button("Edit");
        editButton.getStyleClass().add("edit-button");
        editButton.setOnAction(e -> handleEditButtonClick(event));

        Button deleteButton = new Button("Delete");
        deleteButton.getStyleClass().add("delete-button");
        deleteButton.setOnAction(e -> handleDeleteButtonClick(event));

        Button attendeesButton = new Button("Attendees");
        attendeesButton.getStyleClass().add("attendees-button");
        attendeesButton.setOnAction(e -> handleAttendeesButtonClick(event));

        buttonBox.getChildren().addAll(editButton, deleteButton, attendeesButton);

        eventBox.getChildren().addAll(eventImage, eventTitle, eventOrganizer, eventDate, buttonBox);

        return eventBox;
    }

    // Handle edit button click event
    private void handleEditButtonClick(Event event) {
        System.out.println("Editing event: " + event.getTitle());
        // Logic for editing the event can be added here
    }

    // Handle delete button click event
    private void handleDeleteButtonClick(Event event) {
        System.out.println("Deleting event: " + event.getTitle());
        // Logic for deleting the event can be added here
    }

    // Handle attendees button click event


    public void handleDashboardClick(MouseEvent event) {

    }

    public void handleAllEventsClick(MouseEvent event) {

    }

    public void handleAllUsersClick(MouseEvent event) {

    }

    public void handleRegisteredEventsClick(MouseEvent event) {

    }

    public void handleLogoutButtonClick(ActionEvent actionEvent) {

    }

    public void handleEditButtonClick(ActionEvent actionEvent) {

    }

    public void handleDeleteButtonClick(ActionEvent actionEvent) {

    }



    public void handleAttendeesButtonClick(Event event) {
        System.out.println("Viewing attendees for event: " + event.getTitle());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminEventAttendeesPage.fxml"));
            AnchorPane attendeesPage = loader.load();

            // Get the AttendeesPageController
            AdminEventAttendeesController attendeesPageController = loader.getController();
            attendeesPageController.setEventId(event.getId());

            // Load the new page in the current stage
            Stage stage = (Stage) eventsFlowPane.getScene().getWindow();
            Scene scene = new Scene(attendeesPage);
            stage.setScene(scene);
            stage.setTitle("Attendees for Event: " + event.getTitle());

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading AttendeesPage.fxml: " + e.getMessage());
        }

    }

    public void handleAttendeesButtonClick(ActionEvent actionEvent) {

    }
}
