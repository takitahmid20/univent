package com.univent.Entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;

public class Event {

    private final StringProperty title;
    private final StringProperty description;
    private final StringProperty featureImage;
    private final StringProperty location;
    private final StringProperty startDate;
    private final StringProperty startTime;
    private final StringProperty endDate;
    private final StringProperty endTime;
    private final StringProperty organizer;
    private final StringProperty organizerLogo;
    private final StringProperty category;
    private final DoubleProperty price;
    private final StringProperty authorName;
    private final StringProperty createdDate; // New property for created date

    // Adding id as a normal int field (not a property)
    private int id;

    // Constructor
    public Event() {
        this.title = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.featureImage = new SimpleStringProperty();
        this.location = new SimpleStringProperty();
        this.startDate = new SimpleStringProperty();
        this.startTime = new SimpleStringProperty();
        this.endDate = new SimpleStringProperty();
        this.endTime = new SimpleStringProperty();
        this.organizer = new SimpleStringProperty();
        this.organizerLogo = new SimpleStringProperty();
        this.category = new SimpleStringProperty();
        this.price = new SimpleDoubleProperty();
        this.authorName = new SimpleStringProperty();
        this.createdDate = new SimpleStringProperty(); // Initialize the createdDate property
    }

    // Getter and Setter methods for all fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getFeatureImage() {
        return featureImage.get();
    }

    public void setFeatureImage(String featureImage) {
        this.featureImage.set(featureImage);
    }

    public StringProperty featureImageProperty() {
        return featureImage;
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public StringProperty locationProperty() {
        return location;
    }

    public String getStartDate() {
        return startDate.get();
    }

    public void setStartDate(String startDate) {
        this.startDate.set(startDate);
    }

    public StringProperty startDateProperty() {
        return startDate;
    }

    public String getStartTime() {
        return startTime.get();
    }

    public void setStartTime(String startTime) {
        this.startTime.set(startTime);
    }

    public StringProperty startTimeProperty() {
        return startTime;
    }

    public String getEndDate() {
        return endDate.get();
    }

    public void setEndDate(String endDate) {
        this.endDate.set(endDate);
    }

    public StringProperty endDateProperty() {
        return endDate;
    }

    public String getEndTime() {
        return endTime.get();
    }

    public void setEndTime(String endTime) {
        this.endTime.set(endTime);
    }

    public StringProperty endTimeProperty() {
        return endTime;
    }

    public String getOrganizer() {
        return organizer.get();
    }

    public void setOrganizer(String organizer) {
        this.organizer.set(organizer);
    }

    public StringProperty organizerProperty() {
        return organizer;
    }

    public String getOrganizerLogo() {
        return organizerLogo.get();
    }

    public void setOrganizerLogo(String organizerLogo) {
        this.organizerLogo.set(organizerLogo);
    }

    public StringProperty organizerLogoProperty() {
        return organizerLogo;
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public String getAuthorName() {
        return authorName.get();
    }

    public void setAuthorName(String authorName) {
        this.authorName.set(authorName);
    }

    public StringProperty authorNameProperty() {
        return authorName;
    }

    public String getCreatedDate() {
        return createdDate.get();
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate.set(createdDate);
    }

    public StringProperty createdDateProperty() {
        return createdDate;
    }
}
