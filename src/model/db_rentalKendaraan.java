/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Asus
 */
public class db_rentalKendaraan {
    
    // Koneksi database
    private static final String URL = "jdbc:mysql://localhost/db_rentalmobil";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
    // Method untuk mendapatkan koneksi
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            throw e;
        }
    }

    // Method untuk pengujian koneksi database
    public void testDatabaseConnection() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            // Mendapatkan koneksi database
            connection = db_rentalKendaraan.getConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "SHOW TABLES";
            resultSet = statement.executeQuery(query);
            
            System.out.println("Daftar tabel dalam database:");
            while (resultSet.next()) {
                System.out.println("- " + resultSet.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println("SQLException saat menjalankan query: " + ex.getMessage());
        } finally {
            // Menutup koneksi dan resource
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
                System.out.println("Koneksi database ditutup.");
            } catch (SQLException ex) {
                System.out.println("SQLException saat menutup koneksi: " + ex.getMessage());
            }
        }
    }
}