/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import model.Customer;
import model.Admin;
import model.Pengguna;
import model.Kendaraan;
import model.Transaction;
import model.db_rentalKendaraan;

import java.sql.*;
import java.util.*;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;
/**
 *
 * @author Asus
 */
public class RentalService {
    private db_rentalKendaraan db;
    private List<Kendaraan> daftarKendaraan;
    private Customer currentUser;
    private List<Transaction> transactions;

    public RentalService() {
        daftarKendaraan = new ArrayList<>();
        transactions = new ArrayList<>();
        db = new db_rentalKendaraan();
        loadKendaraanFromDatabase();
    }

    public List<Kendaraan> getDaftarKendaraan() {
        return daftarKendaraan;
    }

    public Customer getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Customer currentUser) {
        this.currentUser = currentUser;
    }

    public void createCar(Kendaraan kendaraan) {
        String query = "INSERT INTO mobil (nama_mobil, kapasitas, merek, harga_sewa, status) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, kendaraan.getNamaMobil());
            stmt.setString(2, kendaraan.getKapasitas());
            stmt.setString(3, kendaraan.getMerek());
            stmt.setDouble(4, kendaraan.getHarga());
            stmt.setString(5, kendaraan.getStatus());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Car Added Successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding car: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void updateCar(Kendaraan kendaraan) {
        String query = "UPDATE mobil SET nama_mobil = ?, kapasitas = ?, merek = ?, harga_sewa = ?, status = ? WHERE id = ?";
        Connection conn = null;
        try {
            conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, kendaraan.getNamaMobil());
            stmt.setString(2, kendaraan.getKapasitas());
            stmt.setString(3, kendaraan.getMerek());
            stmt.setDouble(4, kendaraan.getHarga());
            stmt.setString(5, kendaraan.getStatus());
            stmt.setInt(6, kendaraan.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Car Updated Successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating car: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteCar(int carId) {
        String query = "DELETE FROM mobil WHERE id = ?";
        Connection conn = null;
        try {
            conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, carId);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Car Deleted Successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting car: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public Pengguna login(String username, String password) {
        String query = "SELECT * FROM pengguna WHERE nama = ? AND password = ?";
        Connection conn = null;
        try {
            conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String status = rs.getString("status");
                if ("admin".equalsIgnoreCase(status)) {
                    return new Admin(
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getString("password"),
                        status,
                        getAdminDepartemen(rs.getInt("id"))
                    );
                } else if ("customer".equalsIgnoreCase(status)) {
                    Customer customer = getCustomerById(rs.getInt("id"));
                    setCurrentUser(customer);  // Set currentUser
                    return customer;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String getAdminDepartemen(int adminId) {
        String query = "SELECT departemen FROM admin WHERE id = ?";
        Connection conn = null;
        try {
            conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, adminId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("departemen");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Customer getCustomerById(int customerId) {
        String query = "SELECT p.nama, p.password, p.status, c.saldo, c.alamat, c.email FROM pengguna p " +
                       "JOIN customer c ON p.id = c.id WHERE p.id = ?";
        Connection conn = null;
        try {
            conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(
                    customerId,                   
                    rs.getString("nama"),
                    rs.getString("password"),
                    rs.getString("status"),
                    rs.getDouble("saldo"),
                    rs.getString("alamat"),
                    rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean register(String username, String password, String role, String address, String email, double saldo) {
        String insertPenggunaSQL = "INSERT INTO pengguna (nama, password, status) VALUES (?, ?, ?)";
        String insertCustomerSQL = "INSERT INTO customer (id, saldo, alamat, email) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = db.getConnection();
            PreparedStatement stmt1 = conn.prepareStatement(insertPenggunaSQL, Statement.RETURN_GENERATED_KEYS);
            stmt1.setString(1, username);
            stmt1.setString(2, password);
            stmt1.setString(3, "customer"); // Status default "customer"
            int rowsAffected = stmt1.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = stmt1.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    PreparedStatement stmt2 = conn.prepareStatement(insertCustomerSQL);
                    stmt2.setInt(1, userId);
                    stmt2.setDouble(2, saldo); // saldo default 0
                    stmt2.setString(3, address);
                    stmt2.setString(4, email);
                    stmt2.executeUpdate();
                    return true; // Registrasi berhasil
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false; // Jika terjadi kesalahan
    }

    private void loadKendaraanFromDatabase() {
        String query = "SELECT * FROM mobil";
        Connection conn = null;
        try {
            conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Ambil kapasitas sebagai string dan pastikan itu tidak null atau kosong
                String kapasitasStr = rs.getString("kapasitas");
                if (kapasitasStr == null || kapasitasStr.trim().isEmpty()) {
                    System.err.println("Kapasitas kosong atau null untuk mobil ID: " + rs.getInt("id"));
                    continue; // Lewati data ini jika kapasitas tidak valid
                }

                int kapasitas = 0;
                try {
                    kapasitas = Integer.parseInt(kapasitasStr); // Konversi kapasitas menjadi integer
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing kapasitas: " + kapasitasStr);
                    continue; // Lewati data ini jika ada error saat parsing kapasitas
                }

                // Proses harga sewa dengan pengecekan serupa
                double harga = 0.0;
                try {
                    harga = Double.parseDouble(rs.getString("harga_sewa"));
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing harga: " + rs.getString("harga_sewa"));
                    continue; // Lewati data ini jika ada error saat parsing harga
                }

                // Membuat objek kendaraan dan menambahkannya ke daftar
                Kendaraan kendaraan = new Kendaraan(
                    rs.getInt("id"),
                    rs.getString("nama_mobil"),
                    String.valueOf(kapasitas), // Konversi kapasitas kembali ke string jika diperlukan
                    rs.getString("merek"),
                    harga,
                    rs.getString("status")
                );
                daftarKendaraan.add(kendaraan);
            }
        } catch (SQLException e) {
            System.err.println("Error loading kendaraan: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Kendaraan> getAvailableCars() {
        List<Kendaraan> availableCars = new ArrayList<>();
        try (Connection conn = db.getConnection()) {
            String query = "SELECT * FROM mobil WHERE status = 'Available'";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Membuat objek Kendaraan berdasarkan data yang diambil dari database
                Kendaraan kendaraan = new Kendaraan(
                    rs.getInt("id"),           // id kendaraan
                    rs.getString("nama_mobil"), // nama kendaraan
                    rs.getString("kapasitas"),  // kapasitas kendaraan
                    rs.getString("merek"),      // merek kendaraan
                    rs.getDouble("harga_sewa"), // harga kendaraan
                    rs.getString("status")      // status kendaraan
                );
                availableCars.add(kendaraan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableCars;
    }
    
    public String searchCar(String filter, String keyword) {
        List<Kendaraan> daftarKendaraan = getDaftarKendaraan(); // Ambil semua kendaraan
        StringBuilder hasil = new StringBuilder();

        for (Kendaraan kendaraan : daftarKendaraan) {
            boolean match = false;

            // Cocokkan berdasarkan filter
            if (filter.equals("Car Name") && kendaraan.getNamaMobil().toLowerCase().contains(keyword.toLowerCase())) {
                match = true;
            } else if (filter.equals("Seats") && String.valueOf(kendaraan.getKapasitas()).equals(keyword)) {
                match = true;
            }

            // Jika cocok, tambahkan ke hasil
            if (match) {
                hasil.append("ID: ").append(kendaraan.getId()).append("\n")
                     .append("Car Name: ").append(kendaraan.getNamaMobil()).append("\n")
                     .append("Capacity: ").append(kendaraan.getKapasitas()).append("\n")
                     .append("Brand: ").append(kendaraan.getMerek()).append("\n")
                     .append("Price: ").append(kendaraan.getHarga()).append("\n")
                     .append("Status: ").append(kendaraan.getStatus()).append("\n\n");
            }
        }

        // Jika tidak ada kendaraan yang ditemukan
        if (hasil.length() == 0) {
            return "No vehicles found with the given criteria.";
        }

        return hasil.toString();
    }

    // Mengembalikan mobil berdasarkan ID transaksi
    public boolean returnCar(String transactionId) {
        for (Transaction transaction : transactions) {
            if (String.valueOf(transaction.getId()).equals(transactionId) && transaction.getStatus().equals("Rented")) {
                // Update status transaksi menjadi "Returned"
                transaction.setStatus("Returned");
                return true; // Pengembalian mobil berhasil
            }
        }
        return false; // Tidak ditemukan transaksi atau status tidak cocok
    }
    
        // **Menambahkan metode untuk menyortir kendaraan**
    public List<Kendaraan> sortKendaraan(String sortBy) {
        List<Kendaraan> daftarKendaraan = getDaftarKendaraan();

        if (sortBy.equals("Car Name")) {
            daftarKendaraan.sort(Comparator.comparing(Kendaraan::getNamaMobil)); // Mengurutkan berdasarkan nama
        } else if (sortBy.equals("Seats")) {
            daftarKendaraan.sort(Comparator.comparingInt(k -> {
                // Mengonversi kapasitas menjadi int, menghapus karakter non-digit terlebih dahulu
                String kapasitasStr = k.getKapasitas().replaceAll("[^\\d]", ""); 
                return Integer.parseInt(kapasitasStr);  // Mengonversi kapasitas ke integer
            }));
        }

        return daftarKendaraan;
    }
    
    // Menambahkan transaksi baru
    public boolean rentCar(int carId, String selectedDuration, double totalPrice) {
        Kendaraan kendaraan = getCarDetails(carId);
        if (kendaraan == null || !kendaraan.getStatus().equalsIgnoreCase("available")) {
            return false; // Mobil tidak tersedia
        }

        // Menentukan tanggal keluar dan kembali berdasarkan durasi
        LocalDateTime tanggalKeluar = LocalDateTime.now();
        LocalDateTime tanggalKembali = tanggalKeluar.plusHours(getDurationInHours(selectedDuration));

        // Membuat transaksi baru
        Transaction transaksi = new Transaction(
            totalPrice, 0, "Rented", tanggalKeluar, tanggalKembali, currentUser.getId(), carId
        );

        // Menambahkan transaksi ke database
        boolean success = addTransaction(transaksi);
        if (success) {
            kendaraan.setStatus("rented"); // Update status mobil menjadi "rented"
            return true;
        }
        return false;
    }

    // Menghitung durasi dalam jam berdasarkan pilihan pengguna
    private int getDurationInHours(String selectedDuration) {
        switch (selectedDuration) {
            case "6 Hours":
                return 6;
            case "24 Hours":
                return 24;
            case "2 Days":
                return 48;
            case "4 Days":
                return 96;
            case "1 Week":
                return 168;
            default:
                return 0; // Durasi tidak valid
        }
    }

    // Mendapatkan detail kendaraan berdasarkan ID
    public Kendaraan getCarDetails(int carId) {
        for (Kendaraan kendaraan : daftarKendaraan) {
            if (kendaraan.getId() == carId) {
                return kendaraan;
            }
        }
        return null; // Mobil tidak ditemukan
    }

    // Menambahkan transaksi ke database
    public boolean addTransaction(Transaction transaksi) {
        try (Connection conn = db_rentalKendaraan.getConnection()) {
            String query = "INSERT INTO transaksi (bayar, denda, status, tanggal_keluar, tanggal_kembali, pengguna_id, mobil_id) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setDouble(1, transaksi.getBayar());
            ps.setDouble(2, transaksi.getDenda());
            ps.setString(3, transaksi.getStatus());
            ps.setTimestamp(4, Timestamp.valueOf(transaksi.getTanggalKeluar()));
            ps.setTimestamp(5, Timestamp.valueOf(transaksi.getTanggalKembali()));
            ps.setInt(6, transaksi.getPenggunaId());
            ps.setInt(7, transaksi.getMobilId());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTransactionStatus(int transactionId, String newStatus) {
        try (Connection conn = db_rentalKendaraan.getConnection()) {
            String query = "UPDATE transaksi SET status = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, newStatus);
            ps.setInt(2, transactionId);

            return ps.executeUpdate() > 0; // True jika berhasil
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Jika terjadi kesalahan
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection conn = db_rentalKendaraan.getConnection()) {
            String query = "SELECT * FROM transaksi";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Pastikan tipe data sesuai dengan konstruktor di Transaction
                Transaction transaction = new Transaction(
                    rs.getDouble("bayar"), // Total bayar (double)
                    rs.getDouble("denda"), // Denda (double)
                    rs.getString("status"), // Status (String)
                    rs.getTimestamp("tanggal_keluar").toLocalDateTime(), // Tanggal keluar (LocalDateTime)
                    rs.getTimestamp("tanggal_kembali").toLocalDateTime(), // Tanggal kembali (LocalDateTime)
                    rs.getInt("pengguna_id"), // ID pengguna (int)
                    rs.getInt("mobil_id") // ID mobil (int)
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public List<Transaction> getTransactionsByUser(int penggunaId) {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection conn = db_rentalKendaraan.getConnection()) {
            String query = "SELECT * FROM transaksi WHERE pengguna_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, penggunaId); // Set parameter pengguna_id
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Pastikan tipe data sesuai dengan konstruktor di Transaction
                Transaction transaction = new Transaction(
                    rs.getDouble("bayar"), // Total bayar (double)
                    rs.getDouble("denda"), // Denda (double)
                    rs.getString("status"), // Status (String)
                    rs.getTimestamp("tanggal_keluar").toLocalDateTime(), // Tanggal keluar (LocalDateTime)
                    rs.getTimestamp("tanggal_kembali").toLocalDateTime(), // Tanggal kembali (LocalDateTime)
                    rs.getInt("pengguna_id"), // ID pengguna (int)
                    rs.getInt("mobil_id") // ID mobil (int)
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
    
    public boolean deleteTransaction(int transactionId) {
        try {
            Connection conn = db_rentalKendaraan.getConnection();
            Statement stmt = conn.createStatement();
            int rowsAffected = stmt.executeUpdate("DELETE FROM transaksi WHERE id = " + transactionId);
            conn.close();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void updateSaldo(int userId, double topUpAmount) throws SQLException {
        try (Connection conn = db_rentalKendaraan.getConnection()) {
            String query = "SELECT saldo FROM customer WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double currentSaldo = rs.getDouble("saldo");
                double newSaldo = currentSaldo + topUpAmount;

                String updateQuery = "UPDATE customer SET saldo = ? WHERE id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setDouble(1, newSaldo);
                updateStmt.setInt(2, userId);
                updateStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Gagal memperbarui saldo.");
        }
    }
    
}