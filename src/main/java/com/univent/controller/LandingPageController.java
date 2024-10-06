package com.univent.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.univent.services.UserService;
import com.univent.Entity.Event;
import com.univent.services.EventService;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import java.util.List;


public class LandingPageController extends BaseController {

    @FXML
    private Button signUpButton;

    @FXML
    private Button signInButton;

    @FXML
    private Button usernameButton;

    @FXML
    private TextField eventSearchField;

    @FXML
    private GridPane allEventsGrid;

    @FXML
    private Button joinNowButton;

    @FXML
    private Button chatButton;

    private boolean isLoggedIn = false;

//    @FXML
//    private String eventTitle;

    private final EventService eventService = new EventService();
    private final UserService userService = new UserService();
    private String loggedInUsername = null;

    @FXML
    public void initialize() {
        loadAllEvents();

        // Example logic to determine if a user is logged in
        if (userService.isLoggedIn()) {
            loggedInUsername = userService.getLoggedInUsername(); // Assume a method to get logged-in username
            setLoggedInState(true, loggedInUsername);
        } else {
            setLoggedInState(false, null);
        }
    }

    private void setLoggedInState(boolean isLoggedIn, String username) {
        if (isLoggedIn) {
            signInButton.setVisible(false);
            signUpButton.setVisible(false);
            usernameButton.setVisible(true);
            usernameButton.setText(username);
        } else {
            signInButton.setVisible(true);
            signUpButton.setVisible(true);
            usernameButton.setVisible(false);
        }
    }

    @FXML
    private void handleSignUpButtonClick() {
        System.out.println("Sign Up Button Clicked");
        // Switch to SignUp scene
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        switchToScene("/view/SignUp.fxml", stage);
    }

    @FXML
    private void handleSignInButtonClick() {
        System.out.println("Sign In Button Clicked");
        // Switch to SignIn scene
        Stage stage = (Stage) signInButton.getScene().getWindow();
        switchToScene("/view/SignIn.fxml", stage);
    }

    @FXML
    private void handleUsernameButtonClick() {
        System.out.println("Username Clicked");
        // Switch to Dashboard scene
        Stage stage = (Stage) usernameButton.getScene().getWindow();
        switchToScene("/view/Dashboard.fxml", stage);
    }

    @FXML
    private void handleHomeClick() {
        // Logic to handle Home click
        System.out.println("Home Clicked");
    }

    @FXML
    private void handleAboutUsClick() {
        // Logic to handle About Us click
        System.out.println("About Us Clicked");
    }

    @FXML
    private void handleAllEventsClick() {
        // Logic to handle All Events click
        System.out.println("All Events Clicked");
    }



    @FXML
    private void handleSearchButtonClick() {
        String searchText = eventSearchField.getText().toLowerCase().trim();
        System.out.println("Searching for: " + searchText);

        // Load filtered events
        if (searchText.isEmpty()) {
            loadAllEvents(); // Load all events if search text is empty
        } else {
            loadFilteredEvents(searchText);
        }
    }

    private void loadFilteredEvents(String searchText) {
        // Clear the grid before loading new results
        allEventsGrid.getChildren().clear();

        // Get all events from EventService
        List<Event> allEvents = eventService.getAllEvents(); // Assuming this method returns all events

        int column = 0;
        int row = 0;

        for (Event event : allEvents) {
            // Filter events based on title, description, or location
            if (event.getTitle().toLowerCase().contains(searchText) ||
                    (event.getDescription() != null && event.getDescription().toLowerCase().contains(searchText)) ||
                    (event.getLocation() != null && event.getLocation().toLowerCase().contains(searchText))) {

                VBox eventBox = createEventBox(event);
                allEventsGrid.add(eventBox, column++, row);

                // Move to the next row after 5 columns
                if (column == 5) {
                    column = 0;
                    row++;
                }
            }
        }

        if (allEventsGrid.getChildren().isEmpty()) {
            System.out.println("No events found matching the search criteria.");
            Label noEventsLabel = new Label("No events found matching your search criteria.");
            allEventsGrid.add(noEventsLabel, 0, 0, 5, 1);
        }
    }

    private VBox createEventBox(Event event) {


        VBox eventBox = new VBox();
        eventBox.setPadding(new Insets(10));
        eventBox.setSpacing(10);
        eventBox.getStyleClass().add("event-box");

        // Image for Event
        ImageView eventImage = new ImageView();
        String imagePath = event.getFeatureImage();
        try {
            if (imagePath != null && !imagePath.isEmpty()) {
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    eventImage.setImage(new Image(imageFile.toURI().toString()));
                } else {
                    eventImage.setImage(new Image("/images/banner.png")); // Fallback image
                }
            } else {
                eventImage.setImage(new Image("/images/banner.png")); // Fallback image
            }
        } catch (Exception e) {
            eventImage.setImage(new Image("/images/banner.png")); // Handle invalid path
        }
        eventImage.setFitHeight(150);
        eventImage.setFitWidth(200);
        eventImage.setPickOnBounds(true);
        eventImage.setPreserveRatio(true);

        // Make image clickable
        eventImage.setOnMouseClicked(e -> handleEventClick(event));

        // Event title
        Label eventTitle = new Label(event.getTitle());
        eventTitle.getStyleClass().add("event-title");

        // Make title clickable
        eventTitle.setOnMouseClicked(e -> handleEventClick(event));

        // Event Date
        Label eventDate = new Label("Date: " + event.getStartDate());
        eventDate.getStyleClass().add("event-date");

        // Event Organizer
        Label eventOrganizer = new Label("Organizer: " + event.getOrganizer());
        eventOrganizer.getStyleClass().add("event-organizer");

        // Join Now Button
        Button joinNowButton = new Button("Join Now");
        joinNowButton.getStyleClass().add("join-now-button");
        joinNowButton.setUserData(event); // Set the associated event object
        joinNowButton.setOnAction(this::handleJoinNowButtonClick);  // Pass event object here

        // Add all elements to the event box
        eventBox.getChildren().addAll(eventImage, eventTitle, eventDate, eventOrganizer, joinNowButton);

        return eventBox;
    }

    @FXML
    private void handleJoinNowButtonClick(ActionEvent actionEvent) {
        // Get the button that was clicked
        Button sourceButton = (Button) actionEvent.getSource();

        // Retrieve the event object from the button's user data
        Event event = (Event) sourceButton.getUserData();

        // Ensure that the event object is not null
        if (event == null || event.getId() == 0) {
            System.err.println("Error: No valid event selected for registration.");
            return;
        }

        System.out.println("Registering for event ID: " + event.getId());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/JoinEventPopup.fxml"));
            Parent root = loader.load();

            // Get the controller and pass the event ID
            JoinEventPopupController controller = loader.getController();
            controller.setEventId(event.getId()); // Pass the event ID

            // Create a new stage for the popup
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Join Event");
            stage.initModality(Modality.APPLICATION_MODAL); // Ensure it blocks interaction with other windows
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading JoinEventPopup.fxml: " + e.getMessage());
        }
    }



    private void loadAllEvents() {
        allEventsGrid.getChildren().clear();
        // Get all events from the EventService
        List<com.univent.Entity.Event> eventList = eventService.getAllEvents(); // Correct type
        int column = 0;
        int row = 0;

        for (Event event : eventList) {
            if (event.getId() == 0) {
                System.err.println("Warning: Event ID is 0, event may not be properly initialized: " + event.getTitle());
                continue; // Skip events with invalid ID
            }
            // Correct type
            VBox eventBox = createEventBox(event); // Pass the correct type
            allEventsGrid.add(eventBox, column++, row);

            // Move to the next row after 3 columns
            if (column == 5) {
                column = 0;
                row++;
            }
        }
    }

    // LandingPageController.java




    // Method to handle event click and switch to the event details page
    private void handleEventClick(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EventDetails.fxml"));
            ScrollPane pane = loader.load();

            EventDetailsController controller = loader.getController();
            controller.setEvent(event); // Pass the clicked event to the details page

            // Switch to the event details scene
            Stage stage = (Stage) eventSearchField.getScene().getWindow(); // Use an existing UI element to get the window
            stage.getScene().setRoot(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
