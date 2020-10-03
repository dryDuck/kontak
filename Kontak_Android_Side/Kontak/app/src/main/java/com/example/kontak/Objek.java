package com.example.kontak;

public class Objek {
    private String id = "", namaKontak = "", nomorTelpon = "", jabatan = "", alamat = "";

    Objek(String id, String namaKontak, String nomorTelpon, String jabatan, String alamat){
        this.id = id;
        this.namaKontak = namaKontak;
        this.nomorTelpon = nomorTelpon;
        this.jabatan = jabatan;
        this.alamat = alamat;

    }

    public String getNamaKontak() {
        return namaKontak;
    }

    public String getNomorTelpon() {
        return nomorTelpon;
    }

    public String getJabatan() {
        return jabatan;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getId() {
        return id;
    }
}
