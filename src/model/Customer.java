/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Asus
 */
public class Customer extends Pengguna {
    private double saldo;
    private String alamat;
    private String email;

    public Customer(int id, String nama, String password, String status, double saldo, String alamat, String email) {
        super(id, nama, password, status);
        this.saldo = saldo;
        this.alamat = alamat;
        this.email = email;
    }

    // Getter dan Setter
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public String getAlamat() { return alamat; }
    public String getEmail() { return email; }
}