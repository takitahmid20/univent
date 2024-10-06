package com.univent.chat;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {

    private static Map<String, PrintWriter> clientWriters = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        System.out.println("Chat server started...");
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (true) {
                new Handler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Handler extends Thread {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private String clientId;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Read client ID (user or organizer)
                clientId = in.readLine();
                synchronized (clientWriters) {
                    clientWriters.put(clientId, out); // Add client to the list
                }

                // Broadcast received messages to the appropriate recipient
                String message;
                while ((message = in.readLine()) != null) {
                    String[] messageParts = message.split(":", 2); // Format: recipientId:message
                    if (messageParts.length == 2) {
                        String recipientId = messageParts[0];
                        String msg = messageParts[1];
                        sendMessageToClient(recipientId, msg);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Remove client from the list on disconnect
                synchronized (clientWriters) {
                    if (clientId != null) {
                        clientWriters.remove(clientId);
                    }
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void sendMessageToClient(String recipientId, String message) {
            PrintWriter writer = clientWriters.get(recipientId);
            if (writer != null) {
                writer.println(message);
                writer.flush();
            }
        }
    }
}
