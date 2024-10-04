package com.univent.controller;

import com.univent.Entity.User;
import com.univent.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ProfileController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextArea bioField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField mobileNumberField;
    @FXML
    private ComboBox<String> universityComboBox;
    @FXML
    private Label profilePictureLabel;
    @FXML
    private ImageView profileImageView;
    @FXML
    private Label nidFrontLabel;
    @FXML
    private Label nidBackLabel;

    private File profilePictureFile;
    private File nidFrontFile;
    private File nidBackFile;

    private UserService userService = new UserService();
    private String loggedInUsername;

    private int loggedInUserId;

    public void initialize(String username) {
        this.loggedInUsername = username;

        // Fetch user data from the database
        User user = userService.getUserByUsername(username);
        // Set the data to the profile fields
        if (user != null) {
            usernameField.setText(user.getUsername());
            emailField.setText(user.getEmail());
            passwordField.setPromptText("Enter new password"); // Password should be set as prompt to avoid displaying it in plaintext
            fullNameField.setText(user.getFullName() != null ? user.getFullName() : "");
            bioField.setText(user.getBio() != null ? user.getBio() : "");
            addressField.setText(user.getAddress() != null ? user.getAddress() : "");
            mobileNumberField.setText(user.getMobileNumber() != null ? user.getMobileNumber() : "");

            // Populate the university dropdown list and set the selected value
            universityComboBox.getItems().clear(); // Clear existing items to avoid duplicates
            universityComboBox.getItems().addAll(
                    "Ahsanullah University of Science and Technology",
                    "Bangladesh University of Engineering and Technology (BUET)",
                    "Dhaka University",
                    "Jahangirnagar University",
                    "United International University"
                    // Add more universities as needed
            );
            universityComboBox.setValue(user.getUniversity() != null ? user.getUniversity() : "");

            // Set profile picture information
            if (user.getProfilePicture() != null) {
                profilePictureLabel.setText(user.getProfilePicture());
                profileImageView.setImage(new Image(new File(user.getProfilePicture()).toURI().toString()));
            } else {
                profilePictureLabel.setText("No file chosen");
                profileImageView.setImage(null); // Clear the image view when no picture is available
            }

            // Set NID information
            if (user.getNidFront() != null) {
                nidFrontLabel.setText(user.getNidFront());
            } else {
                nidFrontLabel.setText("No file chosen");
            }

            if (user.getNidBack() != null) {
                nidBackLabel.setText(user.getNidBack());
            } else {
                nidBackLabel.setText("No file chosen");
            }
        } else {
            System.err.println("Error: User not found in the database.");
        }
    }



    @FXML
    private void handleProfilePictureUploadClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Picture");
        profilePictureFile = fileChooser.showOpenDialog(new Stage());

        if (profilePictureFile != null) {
            profilePictureLabel.setText(profilePictureFile.getName());
            profileImageView.setImage(new Image(profilePictureFile.toURI().toString()));
        }
    }

    @FXML
    private void handleNIDFrontUploadClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose NID Front Side");
        nidFrontFile = fileChooser.showOpenDialog(new Stage());

        if (nidFrontFile != null) {
            nidFrontLabel.setText(nidFrontFile.getName());
        }
    }

    @FXML
    private void handleNIDBackUploadClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose NID Back Side");
        nidBackFile = fileChooser.showOpenDialog(new Stage());

        if (nidBackFile != null) {
            nidBackLabel.setText(nidBackFile.getName());
        }
    }

    @FXML
    private void handleSaveChangesClick() {
        // Fetch updated values from fields
        String fullName = fullNameField.getText();
        String password = passwordField.getText();
        String bio = bioField.getText();
        String address = addressField.getText();
        String mobileNumber = mobileNumberField.getText();
        String university = universityComboBox.getValue();

        // Update the user profile
        boolean isUpdated = userService.updateProfile(
                loggedInUsername,
                fullName,
                password,
                bio,
                address,
                mobileNumber,
                university,
                profilePictureFile != null ? profilePictureFile.getPath() : null,
                nidFrontFile != null ? nidFrontFile.getPath() : null,
                nidBackFile != null ? nidBackFile.getPath() : null
        );

        if (isUpdated) {
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Profile Update");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Profile updated successfully!");
            successAlert.showAndWait();
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Profile Update");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Failed to update profile. Please try again.");
            errorAlert.showAndWait();
        }
    }
}
