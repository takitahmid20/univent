package com.univent.Entity;

public class User {

    private int id;  // Add ID field to uniquely identify each user
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String bio;
    private String address;
    private String mobileNumber;
    private String university;
    private String profilePicture;
    private String nidFront;
    private String nidBack;

    // Getters and Setters for ID
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    // Getters and Setters for other fields
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getUniversity() { return university; }
    public void setUniversity(String university) { this.university = university; }

    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }

    public String getNidFront() { return nidFront; }
    public void setNidFront(String nidFront) { this.nidFront = nidFront; }

    public String getNidBack() { return nidBack; }
    public void setNidBack(String nidBack) { this.nidBack = nidBack; }
}
