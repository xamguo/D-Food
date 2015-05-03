/*
* Task means the deliveryman task
* The tasks for deliveryman is here
* */
package com.example.sam.d_food.entities.deliveryman;

public class Task {
    String UserName;    //deliveryman name
    double latitude;   //the latitude of the delivery address
    double longitude;  //the longitude of the delivery address
    String name;        //the task name

    public Task(String userName, double latitude, double longitude, String name) {
        UserName = userName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
