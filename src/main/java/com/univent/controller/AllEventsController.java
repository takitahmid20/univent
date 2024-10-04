package com.univent.controller;

import com.univent.Entity.Event;
import com.univent.services.EventService;
import com.univent.services.UserService;
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
    private VBox tableView;  // VBox that acts as the table view container

    private final EventService eventService = new EventService();
    private UserService userService;

    // Setter method to set UserService instance from the previous controller
    public void setUserService(UserService userService) {
        this.userService = userService;
        System.out.println("UserService has been set in AllEventsController.");

        loadUserEvents();
    }

    // Method to load events for the logged-in user
    private void loadUserEvents() {
        if (userService == null) {
            System.err.println("UserService is not set. Ensure it is passed from the previous controller.");
            return;
        }

        // Get the logged-in user's ID (assuming userService has a method for this)
        int loggedInUserId = userService.getLoggedInUserId();
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
            populateEventTable(userEvents);
        }
    }

    @FXML
    public void initialize() {

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


    private void handleEditButtonClick(Event event) {
        System.out.println("Editing event: " + event.getTitle());
        // Add your edit event logic here
    }

    private void handleDeleteButtonClick(Event event) {
        System.out.println("Deleting event: " + event.getTitle());
        // Add your delete event logic here
    }

    private void handleAttendeesButtonClick(Event event) {
        System.out.println("Viewing attendees for event: " + event.getTitle());
        // Add your attendees viewing logic here
    }

    public void handleEditButtonClick(ActionEvent actionEvent) {

    }

    public void handleDeleteButtonClick(ActionEvent actionEvent) {

    }

    public void handleAttendeesButtonClick(ActionEvent actionEvent) {

    }
}








// -----------
//-----------
//------------











//package com.univent.controller;
//
//import com.univent.Entity.Event;
//import com.univent.services.EventService;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.TableCell;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//
//import java.io.File;
//import java.util.List;
//
//public class AllEventsController extends BaseController {
//
//    @FXML
//    private TableView<Event> eventTableView;
//
//    @FXML
//    private TableColumn<Event, String> imageColumn;
//
//    @FXML
//    private TableColumn<Event, String> titleColumn;
//
//    @FXML
//    private TableColumn<Event, String> categoryColumn;
//
//    @FXML
//    private TableColumn<Event, String> createdDateColumn;
//
//    @FXML
//    private TableColumn<Event, Void> editColumn;
//
//    @FXML
//    private TableColumn<Event, Void> deleteColumn;
//
//    @FXML
//    private TableColumn<Event, Void> attendeesColumn;
//
//    private final EventService eventService = new EventService();
//
////    @FXML
////    public void initialize() {
////        loadAllEvents();
////    }
//
//    private void loadAllEvents() {
//        // Fetch events from EventService
//        List<Event> eventList = eventService.getAllEvents();
//        ObservableList<Event> observableEventList = FXCollections.observableArrayList(eventList);
//
//        // Set data to the table view
////        eventTableView.setItems(observableEventList);
//
//        // Configure columns
//        imageColumn.setCellFactory(column -> new TableCell<Event, String>() {
//            private final ImageView imageView = new ImageView();
//
//            @Override
//            protected void updateItem(String imagePath, boolean empty) {
//                super.updateItem(imagePath, empty);
//
//                if (empty || imagePath == null || imagePath.isEmpty()) {
//                    setGraphic(null);
//                } else {
//                    File imageFile = new File(imagePath);
//                    Image image = imageFile.exists() ? new Image(imageFile.toURI().toString()) : new Image("/images/event-placeholder.jpg");
//                    imageView.setImage(image);
//                    imageView.setFitHeight(80);
//                    imageView.setFitWidth(120);
//                    imageView.setPreserveRatio(true);
//                    setGraphic(imageView);
//                }
//            }
//        });
//
//        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
//        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
//        createdDateColumn.setCellValueFactory(cellData -> cellData.getValue().createdDateProperty());
//
//        // Add Edit button to each row
//        editColumn.setCellFactory(column -> new TableCell<Event, Void>() {
//            private final Button editButton = new Button("Edit");
//
//            {
//                editButton.setOnAction(e -> handleEditButtonClick(getTableView().getItems().get(getIndex())));
//                editButton.getStyleClass().add("edit-button");
//            }
//
//            @Override
//            protected void updateItem(Void item, boolean empty) {
//                super.updateItem(item, empty);
//                if (empty) {
//                    setGraphic(null);
//                } else {
//                    setGraphic(editButton);
//                }
//            }
//        });
//
//        // Add Delete button to each row
//        deleteColumn.setCellFactory(column -> new TableCell<Event, Void>() {
//            private final Button deleteButton = new Button("Delete");
//
//            {
//                deleteButton.setOnAction(e -> handleDeleteButtonClick(getTableView().getItems().get(getIndex())));
//                deleteButton.getStyleClass().add("delete-button");
//            }
//
//            @Override
//            protected void updateItem(Void item, boolean empty) {
//                super.updateItem(item, empty);
//                if (empty) {
//                    setGraphic(null);
//                } else {
//                    setGraphic(deleteButton);
//                }
//            }
//        });
//
//        // Add View Attendees button to each row
//        attendeesColumn.setCellFactory(column -> new TableCell<Event, Void>() {
//            private final Button attendeesButton = new Button("View Attendees");
//
//            {
//                attendeesButton.setOnAction(e -> handleAttendeesButtonClick(getTableView().getItems().get(getIndex())));
//                attendeesButton.getStyleClass().add("attendees-button");
//            }
//
//            @Override
//            protected void updateItem(Void item, boolean empty) {
//                super.updateItem(item, empty);
//                if (empty) {
//                    setGraphic(null);
//                } else {
//                    setGraphic(attendeesButton);
//                }
//            }
//        });
//    }
//
//    @FXML
//    private void handleEditButtonClick(Event event) {
//        // Logic for editing an event
//        System.out.println("Edit button clicked for event: " + event.getTitle());
//        // Implement the logic to open the edit event page
//    }
//
//    @FXML
//    private void handleDeleteButtonClick(Event event) {
//        // Logic for deleting an event
//        System.out.println("Delete button clicked for event: " + event.getTitle());
//        boolean deleted = eventService.deleteEvent(event.getId());
//        if (deleted) {
//            eventTableView.getItems().remove(event);
//        } else {
//            System.out.println("Error deleting the event: " + event.getTitle());
//        }
//    }
//
//    @FXML
//    private void handleAttendeesButtonClick(Event event) {
//        // Logic for viewing attendees of an event
//        System.out.println("View attendees button clicked for event: " + event.getTitle());
//        // Implement the logic to open the attendees list for the selected event
//    }
//
//    public void handleEditButtonClick(ActionEvent actionEvent) {
//
//    }
//
//    public void handleDeleteButtonClick(ActionEvent actionEvent) {
//
//    }
//
//    public void handleAttendeesButtonClick(ActionEvent actionEvent) {
//
//    }
//}
