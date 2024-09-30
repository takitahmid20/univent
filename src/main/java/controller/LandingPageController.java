package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LandingPageController extends BaseController {

    @FXML
    private Button signUpButton;

    @FXML
    private Button signInButton;

    @FXML
    private TextField eventSearchField;

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
        String searchText = eventSearchField.getText();
        System.out.println("Searching for: " + searchText);
        // Implement search functionality here if needed
    }

    @FXML
    private void handleJoinNowButtonClick() {
        System.out.println("Join Now Clicked for Event");
        // Implement join event functionality here if needed
    }
}
