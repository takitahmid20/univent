package com.univent;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the SignUp.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LandingPage.fxml"));
            Parent root = loader.load();

            // Create the scene with the loaded FXML
            Scene scene = new Scene(root);

            // Set the title and apply the scene to the primary stage
            primaryStage.setTitle("Univent | Your Ultimate Event Partner");
            primaryStage.setScene(scene);

            // Set the primary stage to be full screen and maximized
            primaryStage.setMaximized(false);
            primaryStage.setFullScreen(false);
            primaryStage.setResizable(true);  // Prevent resizing

            // Show the primary stage
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading FXML: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }
}