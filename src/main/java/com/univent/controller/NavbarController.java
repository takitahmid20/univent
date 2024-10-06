package com.univent.controller;

import com.univent.session.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class NavbarController {

    @FXML
    private HBox topNavbar;

    @FXML
    private Button logoutButton;

    @FXML
    public void handleLogoClick() {
        try {
            // Load the landing page FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LandingPage.fxml"));
            Parent root = loader.load();

            // Get the current stage (from the logo click event)
            Stage stage = (Stage) topNavbar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Landing Page");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDashboardClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminDashboard.fxml"));
            AnchorPane page = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(page);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAllEventsClick(MouseEvent event) {
        System.out.println("Admin All Events Click");
        try {
            System.out.println("Admin All Events Click");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminAllEventsPage.fxml"));
            ScrollPane page = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(page);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAllUsersClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminUsersPage.fxml"));
            AnchorPane page = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(page);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegisteredEventsClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminRegisteredEventsPage.fxml"));
            AnchorPane page = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(page);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogoutButtonClick() {
        // Set admin login status to false in the session (logs the admin out)
        Session.getInstance().setAdminLoggedIn(false);

        try {
            // Load the LandingPage.fxml after logging out
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LandingPage.fxml"));
            ScrollPane root = loader.load();

            // Get the current stage (window) and set the new scene (Landing Page)
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Landing Page");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
