package com.example.damstatusmonitoringsystem;

public class LOGUser {

    private int id;
    private String username, email, gender;

    public LOGUser(int id, String username, String email, String gender) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }
}