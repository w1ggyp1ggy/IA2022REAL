package com.example.myapplication;

public class User {
    private String email;
    private String type;
    private String uid;
    private String club;

    public User(String email, String type, String uid, String club) {
        this.email = email;
        this.type = type;
        this.uid = uid;
        this.club = club;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public User(String email, String type, String uid) {
        this.email = email;
        this.type = type;
        this.uid = uid;
    }
    public User()
    {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String email, String type) {
        this.email = email;
        this.type = type;
    }
}
