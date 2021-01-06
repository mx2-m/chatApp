package com.example.lib;

public class Requests {
    String state;
    String idChat;

    public Requests() {
    }

    public Requests(String state, String idChat) {
        this.state = state;
        this.idChat = idChat;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIdChat() {
        return idChat;
    }

    public void setIdChat(String idChat) {
        this.idChat = idChat;
    }
}
