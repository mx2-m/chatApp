package com.example.lib;

public class User {

    private String id;
    private String name;
    private String email;
    private String photo;
    private String state;
    private String date;
    private String time;
    private int requests;
    private int messages;


    public User() {
    }

    public User(String id, String name, String email, String photo, String online, String date, String time, int requests, int messages) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.photo = photo;
        this.state = online;
        this.date = date;
        this.time = time;
        this.requests = requests;
        this.messages = messages;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getRequests() {
        return requests;
    }

    public void setRequests(int requests) {
        this.requests = requests;
    }

    public int getMessages() {
        return messages;
    }

    public void setMessages(int messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                ", state='" + state + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", requests=" + requests +
                ", messages=" + messages +
                '}';
    }


}
