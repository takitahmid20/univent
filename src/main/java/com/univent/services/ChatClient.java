package com.univent.services;

import java.io.*;
import java.net.*;

public class ChatClient {
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private ChatListener listener;

    public ChatClient(String serverAddress, int port) throws IOException {
        socket = new Socket(serverAddress, port);
        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Start a thread to listen for incoming messages
        new Thread(() -> {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println("Message received from server: " + message);
                    // Handle received messages here
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendMessage(String message) {
        writer.println(message);  // Send message to the server
    }

    public void setChatListener(ChatListener listener) {
        this.listener = listener;
    }

    // Interface to handle receiving messages
    public interface ChatListener {
        void onMessageReceived(String message);
    }

    // Method to receive messages (Optional if you are using a listener-based approach)
    public String receiveMessage() throws IOException {
        return reader.readLine();  // Read the incoming message from the server
    }

    // Close the connection
    public void close() throws IOException {
        reader.close();
        writer.close();
        socket.close();
    }
}
