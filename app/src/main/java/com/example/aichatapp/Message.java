package com.example.aichatapp;

public class Message {

    private String message;
    private String Auth;

    public Message(String message, String auth) {
        this.message = message;
        Auth = auth;
    }

    public Message() {
    }


    public String getMessage() {
        return message;
    }

    public String getAuth() {
        return Auth;
    }
}
