package com.example.aplikasiku.model;

public class Keuangan {
    public String id;
    public String keterangan;
    public String jumlah;

    public Keuangan() {}

    public Keuangan(String id, String keterangan, String jumlah) {
        this.id = id;
        this.keterangan = keterangan;
        this.jumlah = jumlah;
    }
}
