/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cobamodul9.dao;

import cobamodul9.entities.UserData;

/**
 *
 * @author ASUS
 */
public interface UserDAO {
    public int register(String username, String password, String fullname);
    public UserData login(String username, String password);
    public int update(int id, String fullname);
    public int update(int id, String password, String fullname);
    public int delete(int id);
}
