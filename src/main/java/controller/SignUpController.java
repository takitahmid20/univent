package controller;

import services.UserService;
import utils.DatabaseUtility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignUpController {

    private static final Logger logger = Logger.getLogger(SignUpController.class.getName());

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signUpButton;

    @FXML
    private Label messageLabel;

    private final UserService userService = new UserService();

    @FXML
    private void handleSignUpButton() {
        System.out.println("Sign Up Button Clicked");

        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        // Validate all fields
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("Validation failed: empty fields detected");
            messageLabel.setText("Please fill in all fields.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Register the user
        boolean isRegistered = userService.registerUser(username, email, password, "User");
        if (isRegistered) {
            System.out.println("Registration successful");
            messageLabel.setText("Registration successful! Please log in.");
            messageLabel.setStyle("-fx-text-fill: green;");

            // Switch to the SignIn page after successful registration
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignIn.fxml"));
                if (loader == null) {
                    logger.log(Level.SEVERE, "FXML resource for SignIn not found!");
                    return;
                }
                AnchorPane pane = loader.load();
                Stage stage = (Stage) signUpButton.getScene().getWindow();
                stage.getScene().setRoot(pane);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "An error occurred while switching to the SignIn scene", e);
            }

        } else {
            System.out.println("Username already exists");
            messageLabel.setText("Username already exists. Please choose another.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }
}



//    private void handleSignInLabelClick(){
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("./resources/view/SignIn.fxml"));
//            AnchorPane pane = loader.load();
//
//            // Get the controller of the loaded page and pass the DashboardController to it
////            if (loader.getController() instanceof BaseController) {
////                BaseController controller = loader.getController();
////                controller.setDashboardController(this);
////            }
////
////            contentArea.getChildren().setAll(pane);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

