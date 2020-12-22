package com.example.lib;

public class State {

    String state;
    String time;
    String date;

    public State() {
    }

    public State(String state, String time, String date) {
        this.state = state;
        this.time = time;
        this.date = date;
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
}
