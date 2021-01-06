package com.example.lib;

import java.util.List;

public class Messages {

    private String message;
    private String send;
    private String recived;
    private String viewed;

    public Messages(String message, String send, String recived, String viewed) {
        this.message = message;
        this.send = send;
        this.recived = recived;
        this.viewed = viewed;
    }

    public Messages() {
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getRecived() {
        return recived;
    }

    public void setRecived(String recived) {
        this.recived = recived;
    }

    public String getViewed() {
        return viewed;
    }

    public void setViewed(String viewed) {
        this.viewed = viewed;
    }
}

