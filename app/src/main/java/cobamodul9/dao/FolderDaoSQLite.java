/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cobamodul9.dao;

/**
 *
 * @author ASUS
 */
import cobamodul9.entities.Folder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FolderDaoSQLite implements FolderDAO {
    private final Connection conn;

    public FolderDaoSQLite(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int createFolder(String foldername) {
        int id = 0;
        String query = "INSERT INTO folder (name) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, foldername);
            stmt.executeUpdate();
            id = stmt.getGeneratedKeys().getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public ArrayList<Folder> listAllFolders() {
        ArrayList<Folder> folders = new ArrayList<>();
        String query = "SELECT * FROM folder";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Folder folder = new Folder(rs.getInt("id"), rs.getString("name"));
                folders.add(folder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return folders;
    }
    
    @Override
    public String getFolderNameById(int folderId) { //add
    String folderName = null;
    String query = "SELECT name FROM folder WHERE id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, folderId);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                folderName = rs.getString("name");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return folderName;
}

}
