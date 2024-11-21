/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import javax.swing.JOptionPane;
import model.Pengguna;
import model.Admin;
import model.Customer;
import view.AdminMenuGUI;
import view.CustMenuGUI;
/**
 *
 * @author Asus
 */
public class LoginController {
    private RentalService rentalService; // Menggunakan instance, bukan static

    // Konstruktor
    public LoginController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    // Metode login untuk mengecek username dan password
    public void login(String username, String password) {
        // Cari pengguna berdasarkan username dan password menggunakan RentalService
        Pengguna user = rentalService.login(username, password);

        if (user != null) {
            // Jika pengguna ditemukan dan login berhasil
            String name = user.getNama();
            String capitalizedName = name.substring(0, 1).toUpperCase() + name.substring(1);
            JOptionPane.showMessageDialog(null, "Login successful. Welcome, " + capitalizedName + "!");
            
            if (user instanceof Admin) {
                // Jika pengguna adalah Admin, buka AdminMenuGUI
                new AdminMenuGUI(rentalService).setVisible(true);
            } else if (user instanceof Customer) {
                // Jika pengguna adalah Customer, buka CustMenuGUI
                new CustMenuGUI(rentalService).setVisible(true);
            }
        } else {
            // Jika login gagal, tampilkan pesan error
            System.out.println("Login failed: User not found");
            JOptionPane.showMessageDialog(null, "Incorrect username or password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}