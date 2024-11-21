/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author Asus
 */
public class Transaction {
    private String bayar;
    private String denda;
    private String status;
    private String tanggalKeluar;
    private String tanggalKembali;
    private int penggunaId;
    private int mobilId;

    public Transaction(String bayar, String denda, String status, String tanggalKeluar, String tanggalKembali, int penggunaId, int mobilId) {
        this.bayar = bayar;
        this.denda = denda;
        this.status = status;
        this.tanggalKeluar = tanggalKeluar;
        this.tanggalKembali = tanggalKembali;
        this.penggunaId = penggunaId;
        this.mobilId = mobilId;
    }

    public String getBayar() {
        return bayar;
    }

    public String getDenda() {
        return denda;
    }

    public String getStatus() {
        return status;
    }

    public String getTanggalKeluar() {
        return tanggalKeluar;
    }

    public String getTanggalKembali() {
        return tanggalKembali;
    }

    public int getPenggunaId() {
        return penggunaId;
    }

    public int getMobilId() {
        return mobilId;
    }
}