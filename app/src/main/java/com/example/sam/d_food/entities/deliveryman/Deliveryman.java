package com.example.sam.d_food.entities.deliveryman;

/**
 * Created by Sam on 4/11/2015.
 */
public class Deliveryman {
    private String userName;
    private String password;
    private String address;

    public Deliveryman() {
    }

    public Deliveryman(String userName, String password, String address) {
        this.userName = userName;
        this.password = password;
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserInfo" +
                "userName:'" + userName + '\'' +
                ", password:'" + password + '\'' +
                ", address:'" + address + '\'';
    }
}
