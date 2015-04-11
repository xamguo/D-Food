package com.example.sam.d_food.business.user;

import com.example.sam.d_food.integration.database.DatabaseConnector;

import java.util.List;

/**
 * Created by Rodrigue on 4/11/2015.
 */
public class CRUD {
    private String userName;
    private String password;
    private String address;
    private DatabaseConnector db;

    public CRUD(String userName, String password, String address, DatabaseConnector db) {
        this.userName = userName;
        this.password = password;
        this.address = address;
        this.db = db;
    }
    public void addUser(){
        db.insertUser(this.userName, this.password, this.address);
    }
    public UserInfo selectUser(String userName){
        UserInfo user=db.getUser(userName);
        return user;
    }
    public List<String> selectAllUsers(){
        List<String> users=db.getAllUsers();
        return users;
    }
    public void UpdateUser(UserInfo user){
        db.updateUser(user);
    }
    public void deleteUser(String userName){
        db.deleteUser(userName);
    }
}
