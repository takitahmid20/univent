package com.univent.controller;

import com.univent.Entity.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class EventDetailsController extends BaseController {

    @FXML
    private Label homeLabel;

    @FXML
    private ImageView eventImageView;

    @FXML
    private ImageView organizerImageView;

    @FXML
    private Label eventTitleLabel;

    @FXML
    private Label eventLocationLabel;

    @FXML
    private Label eventDateLabel;

    @FXML
    private Label eventTimeLabel;

    @FXML
    private Label eventPriceLabel;

    @FXML
    private Label eventOrganizerLabel;

    @FXML
    private Label eventDescriptionLabel;

    @FXML
    private Button joinNowButton;

    @FXML
    private Button signUpButton;

    @FXML
    private Button signInButton;

    @FXML
    private Button usernameButton;

    @FXML
    private Button joinPriceButton;  // Update this to match the button ID in the FXML file

    private Event selectedEvent;

    // Method to set the event details dynamically
    public void setEvent(Event event) {
        this.selectedEvent = event;
        if (event == null) {
            System.err.println("Error: Event is null.");
            return;
        }

        // Set event details to the respective UI components
        if (eventTitleLabel != null) {
            eventTitleLabel.setText(event.getTitle());
        } else {
            System.err.println("Error: eventTitleLabel is not initialized.");
        }

        if (eventLocationLabel != null) {
            eventLocationLabel.setText("Location: " + event.getLocation());
        } else {
            System.err.println("Error: eventLocationLabel is not initialized.");
        }

        if (eventDateLabel != null) {
            eventDateLabel.setText("Date: " + event.getStartDate());
        } else {
            System.err.println("Error: eventDateLabel is not initialized.");
        }

        if (eventTimeLabel != null) {
            eventTimeLabel.setText("Time: " + event.getStartTime() + " - " + event.getEndTime());
        } else {
            System.err.println("Error: eventTimeLabel is not initialized.");
        }

        if (eventPriceLabel != null) {
            eventPriceLabel.setText("Price: " + (event.getPrice() > 0 ? "$" + event.getPrice() : "Free"));
        } else {
            System.err.println("Error: eventPriceLabel is not initialized.");
        }

        if (eventOrganizerLabel != null) {
            eventOrganizerLabel.setText("Organizer: " + event.getOrganizer());
        } else {
            System.err.println("Error: eventOrganizerLabel is not initialized.");
        }

        if (eventDescriptionLabel != null) {
            eventDescriptionLabel.setText(event.getDescription());
        } else {
            System.err.println("Error: eventDescriptionLabel is not initialized.");
        }

        // Load the event image
        // Load the event image
        if (eventImageView != null) {
            try {
                String imagePath = event.getFeatureImage();
                if (imagePath != null && !imagePath.isEmpty()) {
                    File imageFile = new File(imagePath);
                    if (imageFile.exists()) {
                        String imageUri = imageFile.toURI().toString();
                        System.out.println("Loading image with URI: " + imageUri);
                        eventImageView.setImage(new Image(imageUri));
                        System.out.println("Image successfully loaded into ImageView.");

                    } else {
                        System.err.println("Error: Image file does not exist at path: " + imagePath);
                    }
                } else {
                    eventImageView.setImage(null);
                    System.err.println("Error: Image path is null or empty.");
                }
            } catch (Exception e) {
                System.err.println("Error occurred while setting the image: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("Error: eventImageView is not initialized.");
        }

        // Load the organizer logo dynamically
        if (organizerImageView != null) {
            try {
                String organizerLogoPath = event.getOrganizerLogo();
                if (organizerLogoPath != null && !organizerLogoPath.isEmpty()) {
                    File imageFile = new File(organizerLogoPath);
                    if (imageFile.exists()) {
                        String imageUri = imageFile.toURI().toString();
                        System.out.println("Loading organizer logo with URI: " + imageUri);
                        organizerImageView.setImage(new Image(imageUri));
                        System.out.println("Organizer logo successfully loaded into ImageView.");
                    } else {
                        System.err.println("Error: Organizer logo file does not exist at path: " + organizerLogoPath);
                    }
                } else {
                    organizerImageView.setImage(null);
                    System.err.println("Error: Organizer logo path is null or empty.");
                }
            } catch (Exception e) {
                System.err.println("Error occurred while setting the organizer logo: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("Error: organizerImageView is not initialized.");
        }



    }

    @FXML
    public void handleAllEventsClick(MouseEvent event) {
        System.out.println("All Events Clicked");
        // Switch to All Events scene
        Stage stage = (Stage) homeLabel.getScene().getWindow();
        switchToScene("/view/LandingPage.fxml", stage);
    }

    @FXML
    public void handleAboutUsClick(MouseEvent event) {
        System.out.println("About Us Clicked");
        // Logic to switch to the About Us page if needed
    }

    @FXML
    public void handleHomeClick(MouseEvent event) {
        System.out.println("Home Clicked");
        // Switch to the Landing Page scene
        Stage stage = (Stage) homeLabel.getScene().getWindow();
        switchToScene("/view/LandingPage.fxml", stage);
    }

    @FXML
    public void handleSignUpButtonClick(ActionEvent actionEvent) {
        System.out.println("Sign Up Button Clicked");
        // Switch to SignUp scene
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        switchToScene("/view/SignUp.fxml", stage);
    }

    @FXML
    public void handleSignInButtonClick(ActionEvent actionEvent) {
        System.out.println("Sign In Button Clicked");
        // Switch to SignIn scene
        Stage stage = (Stage) signInButton.getScene().getWindow();
        switchToScene("/view/SignIn.fxml", stage);
    }

    @FXML
    public void handleUsernameButtonClick(ActionEvent actionEvent) {
        System.out.println("Username Button Clicked");
        // Switch to Dashboard scene
        Stage stage = (Stage) usernameButton.getScene().getWindow();
        switchToScene("/view/Dashboard.fxml", stage);
    }

    @FXML
    public void handleJoinNowButtonClick(ActionEvent actionEvent) {
        System.out.println("Join Now Button Clicked");
        // Implement functionality for joining the event
    }

    @FXML
    private void handleJoinPriceButtonClick() {
        if (selectedEvent == null || selectedEvent.getId() == 0) {
            System.err.println("Error: No valid event selected for registration.");
            return;
        }

        // Debug message to verify event
        System.out.println("Registering for event ID from Event Details Page: " + selectedEvent.getId());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/JoinEventPopup.fxml"));
            ScrollPane root = loader.load();

            // Pass the event ID to the join popup controller
            JoinEventPopupController controller = loader.getController();
            controller.setEventId(selectedEvent.getId());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Join Event");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
