/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Asus
 */
public class Admin extends Pengguna {
    private String departemen;

    public Admin(int id, String nama, String password, String status, String departemen) {
        super(id, nama, password, status);
        this.departemen = departemen;
    }

    public String getDepartemen() { return departemen; }
}