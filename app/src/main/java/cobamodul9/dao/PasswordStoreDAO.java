/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cobamodul9.dao;

import cobamodul9.entities.PasswordStore;
import cobamodul9.entities.UserData;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public interface PasswordStoreDAO {
    public int addPassword(PasswordStore newPassword, UserData user);
    public ArrayList<PasswordStore> listPassword(UserData user);
    public ArrayList<PasswordStore> findPassword(String name, UserData user);
    public int updatePass(PasswordStore changedPass);
    public int deletePass(PasswordStore deletedPass);
}
