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
            Parent root = FXMLLoader.load(getClass().getResource("/view/SignUp.fxml"));

            // Set the title and the scene
            primaryStage.setTitle("Sign Up Form");
            primaryStage.setScene(new Scene(root, 600, 400));

            // Show the primary stage
            primaryStage.show();
        } catch (Exception e) {
            // Handle any exceptions that occur
            e.printStackTrace();
            System.out.println("Error loading FXML file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }
}