/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Asus
 */
public abstract class Pengguna {
    private int id;
    private String nama;
    private String password;
    private String status;

    public Pengguna(int id, String nama, String password, String status) {
        this.id = id;
        this.nama = nama;
        this.password = password;
        this.status = status;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public String getNama() { return nama; }
    public String getPassword() { return password; }
    public String getStatus() { return status; }
}
