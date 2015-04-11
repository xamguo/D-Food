package com.example.sam.d_food.business.deliveryman;

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
    public void addDeliveryman(){
        db.insertDeliveryman(this.userName, this.password, this.address);
    }
    public DeliverymanInfo selectDeliveryman(String userName){
        DeliverymanInfo deliveryMan=db.getDeliveryman(userName);
        return deliveryMan;
    }
    public List<String> selectAllDeliveryman(){
        List<String> Deliverymen=db.getAllDeliveryman();
        return Deliverymen;
    }
    public void UpdateDeliveryman(DeliverymanInfo deliveryMan){
        db.updateDeliveryman(deliveryMan);
    }
    public void deleteDeliveryman(String userName){
        db.deleteDeliveryman(userName);
    }
}
