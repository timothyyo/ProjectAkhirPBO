/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Asus
 */
public class Kendaraan {
    private int id;               // id disesuaikan dengan tipe int
    private String namaMobil;     // nama mobil (varchar 25) disesuaikan dengan nama kolom "nama_mobil"
    private String kapasitas;     // kapasitas (varchar 20)
    private String merek;         // merek (varchar 35)
    private double harga;         // harga sewa (ubah menjadi double untuk menyimpan harga sewa)
    private String status;        // status (varchar 20)

    // Constructor disesuaikan dengan kolom-kolom dalam tabel
    public Kendaraan(int id, String namaMobil, String kapasitas, String merek, double harga, String status) {
        this.id = id;
        this.namaMobil = namaMobil;
        this.kapasitas = kapasitas;
        this.merek = merek;
        this.harga = harga;
        this.status = status;
    }

    // Getters dan Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaMobil() {
        return namaMobil;
    }

    public void setNamaMobil(String namaMobil) {
        this.namaMobil = namaMobil;
    }

    public String getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(String kapasitas) {
        this.kapasitas = kapasitas;
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Override toString (Untuk Debugging atau tampilan ComboBox)
    @Override
    public String toString() {
        return "mobil{" +
               "id=" + id +
               ", namaMobil='" + namaMobil + '\'' +
               ", kapasitas='" + kapasitas + '\'' +
               ", merek='" + merek + '\'' +
               ", harga=" + harga +
               ", status='" + status + '\'' +
               '}';
    }
}