package com.example.lib;

import java.time.LocalDateTime;


public class Message {

    private String text;
    private LocalDateTime time;
    private String user;

    public Message(String text, String user) {
        this.text = text;
        this.time = LocalDateTime.now();
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", time=" + time +
                ", user='" + user + '\'' +
                '}';
    }
}
