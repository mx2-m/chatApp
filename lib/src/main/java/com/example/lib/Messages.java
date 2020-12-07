package com.example.lib;

import java.util.List;

public class Messages {

    private List<Message> messages;
    private String userID;


    public Messages(List<Message> messages, String userID) {
        this.messages = messages;
        this.userID = userID;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public void add(Message message) {

        messages.add(message);
    }

    public void remove(int position) {
        messages.remove(position);
    }

    public void edit(int position, Message message) {
        messages.set(position, message);
    }


}
