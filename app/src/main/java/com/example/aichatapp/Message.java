package com.example.aichatapp;

public class Message {

    private String message;
    private String auth;

    public Message(String message, String auth) {
        this.message = message;
        this.auth = auth;
    }

    public Message() {
    }


    public String getMessage() {
        return message;
    }

    public String getAuth() {
        return auth;
    }
}
