package com.example.lib;

public class State {

    String state;
    String time;
    String date;
    String chat;

    public State() {
    }

    public State(String state, String time, String date, String chat) {
        this.state = state;
        this.time = time;
        this.date = date;
        this.chat = chat;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }
}
