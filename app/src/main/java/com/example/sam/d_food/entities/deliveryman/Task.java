package com.example.sam.d_food.entities.deliveryman;

/**
 * Created by Sam on 4/11/2015.
 */
public class Task {
    String UserName;
    double latitude;
    double longitude;

    public Task(String userName, double latitude, double longitude) {
        UserName = userName;
        this.latitude = latitude;
        this.longitude = longitude;
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
}
