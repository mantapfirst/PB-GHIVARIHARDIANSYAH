package com.example.aplikasiku.model;

public class Todo {
    public String id;
    public String isi;
    public boolean selesai;

    public Todo() {}

    public Todo(String id, String isi, boolean selesai) {
        this.id = id;
        this.isi = isi;
        this.selesai = selesai;
    }
}
