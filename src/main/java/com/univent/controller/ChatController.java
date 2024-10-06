package com.univent.controller;


import com.univent.services.ChatClient;
import com.univent.services.ChatService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;

public class ChatController {

    @FXML
    private TextArea chatArea;
    @FXML
    private TextField messageField;
    @FXML
    private Button sendButton;
    @FXML
    private Label eventTitleLabel;
    @FXML
    private Label organizerNameLabel;

    private ChatClient chatClient;
    private String eventId;
    private String userId; // Current user ID
    private String organizerId; // Organizer ID

    public void initialize(String eventId, String eventTitle, String organizerName) {
        this.eventId = eventId;

        // Set the event title and organizer name in the chat popup
        eventTitleLabel.setText(eventTitle);
        organizerNameLabel.setText("Chatting with: " + organizerName);

        try {
            // Connect to the chat server
            chatClient = new ChatClient("localhost", 8889);
            chatClient.sendMessage("User connected for event: " + eventId); // Inform server about the event chat

            // Start a background thread to receive messages
            new Thread(() -> {
                try {
                    while (true) {
                        String message = chatClient.receiveMessage();
                        Platform.runLater(() -> chatArea.appendText(message + "\n")); // Update UI on JavaFX thread
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            String fullMessage = organizerId + ":" + "User: " + message;
            chatClient.sendMessage(fullMessage);
            chatArea.appendText("You: " + message + "\n");
            messageField.clear();

            // Save message to the database
            saveMessageToDatabase(eventId, userId, organizerId, message);
        }
    }

    private void saveMessageToDatabase(String eventId, String userId, String organizerId, String message) {
        // Database service to save the chat messages
        // For this, you need a service to insert the message into the `chat_messages` table
        ChatService.saveChatMessage(eventId, userId, message, "user");
    }

    public void closeChat() {
        try {
            chatClient.close();  // Closing the chat client and resources
        } catch (IOException e) {
            System.err.println("Error closing the chat client: " + e.getMessage());
        }
    }

}
