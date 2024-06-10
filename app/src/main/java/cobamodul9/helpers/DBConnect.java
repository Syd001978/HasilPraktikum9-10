/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cobamodul9.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class DBConnect {

    private static final String URL = "jdbc:sqlite:pw.db";
    private static Connection instance;

    private DBConnect() {}

    public static Connection connect() throws SQLException {
        if (instance == null || instance.isClosed()) {
            try {
                instance = DriverManager.getConnection(URL);
                // System.out.println("Koneksi ke SQLite berhasil.");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return instance;
    }


}
