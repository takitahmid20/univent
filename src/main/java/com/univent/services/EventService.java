package com.univent.services;

import java.util.ArrayList;
import java.util.List;

public class EventService {
    private List<Event> eventList = new ArrayList<>();

    // Method to add a new event
    public boolean addEvent(String name, String location, String date) {
        // Check if an event with the same name already exists
        for (Event event : eventList) {
            if (event.getName().equalsIgnoreCase(name)) {
                return false; // Event already exists
            }
        }
        // Add new event
        Event newEvent = new Event(name, location, date);
        eventList.add(newEvent);
        return true;
    }
}
