package com.example.sam.d_food.entities.user;

import com.example.sam.d_food.ws.remote.DatabaseConnector;

/**
 * Created by Sam on 4/11/2015.
 */
public class Login {
    private String userName;
    private String password;
    private DatabaseConnector db;

    public Login(String userName, String password, DatabaseConnector db) {
        this.userName = userName;
        this.password = password;
        this.db = db;
    }
    public Boolean authenticate(){
        Boolean exist=false;
        if (db.checkUser(this.userName, this.password)){
            exist=true;
        }
        return exist;
    }
}
