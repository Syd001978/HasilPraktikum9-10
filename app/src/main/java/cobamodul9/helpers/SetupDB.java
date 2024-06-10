package cobamodul9.helpers;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupDB {
    private static final String URL = "jdbc:sqlite:pw.db";

    private SetupDB() {}

    public static void setup() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            // Create folder table
            stmt.execute("CREATE TABLE IF NOT EXISTS folder (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);");

            // Create userdata table
            stmt.execute("CREATE TABLE IF NOT EXISTS userdata (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, fullname TEXT);");

            // Create unique index on userdata
            stmt.execute("CREATE UNIQUE INDEX IF NOT EXISTS user_username_IDX ON userdata (username);");

            // Create passwordstore table
            stmt.execute("CREATE TABLE IF NOT EXISTS passwordstore (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, username TEXT, password TEXT, hashkey TEXT, score REAL, category INTEGER, " +
                    "user_id INTEGER, folder_id INTEGER, CONSTRAINT passwordstore_user_FK FOREIGN KEY (user_id) " +
                    "REFERENCES userdata(id) ON DELETE RESTRICT ON UPDATE RESTRICT, " +
                    "CONSTRAINT passwordstore_folder_FK FOREIGN KEY (folder_id) REFERENCES folder(id) " +
                    "ON DELETE SET NULL ON UPDATE SET NULL);");

            // Create additional table
            stmt.execute("CREATE TABLE IF NOT EXISTS additional (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "entry_key TEXT, entry_value TEXT, is_password INTEGER, password_id INTEGER, " +
                    "CONSTRAINT additional_passwordstore_FK FOREIGN KEY (password_id) REFERENCES passwordstore(id) " +
                    "ON DELETE CASCADE ON UPDATE CASCADE);");

            System.out.println("Database setup completed.");
        } catch (SQLException e) {
            System.out.println("Error while setting up database: " + e.getMessage());
        }
    }
}



