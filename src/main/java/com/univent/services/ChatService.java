package com.univent.services;

import com.univent.Entity.Event;
import database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatService {

    // Save the chat message to the database without organizerId
    public static void saveChatMessage(String eventId, String userId, String message, String sender) {
        String sql = "INSERT INTO chat_messages (event_id, user_id, message, sender) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(eventId));
            pstmt.setInt(2, Integer.parseInt(userId));
            pstmt.setString(3, message);
            pstmt.setString(4, sender);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve chat history for a specific event (with userId but without organizerId)
    public static List<String> getChatHistory(String eventId, String userId) {
        List<String> chatHistory = new ArrayList<>();
        String sql = "SELECT message, sender FROM chat_messages WHERE event_id = ? AND user_id = ? ORDER BY timestamp ASC";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.parseInt(eventId));
            pstmt.setInt(2, Integer.parseInt(userId));

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String sender = rs.getString("sender");
                String message = rs.getString("message");
                chatHistory.add(sender + ": " + message);  // You can format the message as desired
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chatHistory;
    }
}
