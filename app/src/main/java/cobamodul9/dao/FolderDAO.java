/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cobamodul9.dao;

import cobamodul9.entities.Folder;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public interface FolderDAO {
    public int createFolder(String foldername);
    public ArrayList<Folder> listAllFolders();
    public String getFolderNameById(int folderId); //add

}
