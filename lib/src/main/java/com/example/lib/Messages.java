package com.example.lib;

import java.util.List;

public class Messages {

    private String message;
    private String sender;
    private String reciver;
    private String viewed;
    private String date;
    private String time;


    public Messages(String message, String sender, String reciver, String viewed, String date, String time) {
        this.message = message;
        this.sender = sender;
        this.reciver = reciver;
        this.viewed = viewed;
        this.date = date;
        this.time = time;
    }

    public Messages() {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getViewed() {
        return viewed;
    }

    public void setViewed(String viewed) {
        this.viewed = viewed;
    }

    @Override
    public String toString() {
        return "Messages{" +
                "message='" + message + '\'' +
                ", sender='" + sender + '\'' +
                ", reciver='" + reciver + '\'' +
                ", viewed='" + viewed + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}

