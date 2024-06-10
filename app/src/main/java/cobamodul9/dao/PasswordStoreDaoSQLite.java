/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cobamodul9.dao;

import cobamodul9.entities.PasswordStore;
import cobamodul9.entities.UserData;
import cobamodul9.entities.Folder;
import cobamodul9.helpers.Encryptor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordStoreDaoSQLite implements PasswordStoreDAO {
    private final Connection conn;

    public PasswordStoreDaoSQLite(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int addPassword(PasswordStore newPassword, UserData user) {
        int id = 0;
        String query = "INSERT INTO passwordstore (name, username, password, hashkey, score, category, user_id, folder_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newPassword.name);
            stmt.setString(2, newPassword.username);
            stmt.setString(3, newPassword.getEncPassword());
            stmt.setString(4, newPassword.hashkey);
            stmt.setDouble(5, newPassword.getScore());
            stmt.setInt(6, newPassword.getCategoryCode());
            stmt.setInt(7, user.id);
            stmt.setInt(8, newPassword.folder != null ? newPassword.folder.id : 0);
            stmt.executeUpdate();
            id = stmt.getGeneratedKeys().getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
public ArrayList<PasswordStore> listPassword(UserData user) {
    ArrayList<PasswordStore> passwords = new ArrayList<>();
    String query = "SELECT ps.*, f.id AS folder_id, f.name AS folder_name " +
                   "FROM passwordstore ps " +
                   "LEFT JOIN folder f ON ps.folder_id = f.id " +
                   "WHERE ps.user_id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, user.id);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // Ambil data folder (jika ada)
                int folderId = rs.getInt("folder_id");
                String folderName = rs.getString("folder_name");
                Folder folder = (folderId != 0) ? new Folder(folderId, folderName) : null;

                PasswordStore password = new PasswordStore(rs.getInt("id"), rs.getString("name"),
                        rs.getString("username"), rs.getString("password"), rs.getString("hashkey"),
                        rs.getDouble("score"), rs.getInt("category"), folder); 
                passwords.add(password);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return passwords;
}

    @Override
    public ArrayList<PasswordStore> findPassword(String name, UserData user) {
        ArrayList<PasswordStore> passwords = new ArrayList<>();
        String query = "SELECT * FROM passwordstore WHERE name LIKE ? AND user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + name + "%");
            stmt.setInt(2, user.id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PasswordStore password = new PasswordStore(rs.getInt("id"), rs.getString("name"),
                            rs.getString("username"), rs.getString("password"), rs.getString("hashkey"),
                            rs.getDouble("score"), rs.getInt("category"), null); // Folder info not retrieved
                    passwords.add(password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passwords;
    }

    @Override
    public int updatePass(PasswordStore changedPass) {
        int rowsAffected = 0;
        String query = "UPDATE passwordstore SET name = ?, username = ?, password = ?, hashkey = ?, score = ?, category = ?, folder_id = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, changedPass.name);
            stmt.setString(2, changedPass.username);
            stmt.setString(3, changedPass.getEncPassword());
            stmt.setString(4, changedPass.hashkey);
            stmt.setDouble(5, changedPass.getScore());
            stmt.setInt(6, changedPass.getCategoryCode());
            stmt.setInt(7, changedPass.folder != null ? changedPass.folder.id : 0);
            stmt.setInt(8, changedPass.id);
            rowsAffected = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int deletePass(PasswordStore deletedPass) {
        int rowsAffected = 0;
        String query = "DELETE FROM passwordstore WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, deletedPass.id);
            rowsAffected = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }
}