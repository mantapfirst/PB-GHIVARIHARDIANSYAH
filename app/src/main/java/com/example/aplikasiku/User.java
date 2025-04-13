package com.example.aplikasiku;

public class User {
    public String username;
    public String email;
    public String nim;

    public User() {
        // Konstruktor kosong yang diperlukan oleh Firebase
    }

    public User(String username, String email, String nim) {
        this.username = username;
        this.email = email;
        this.nim = nim;
    }
}