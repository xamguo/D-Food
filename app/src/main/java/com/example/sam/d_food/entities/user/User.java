package com.example.sam.d_food.entities.user;

/**
 * Created by Sam on 4/11/2015.
 */
public class User {

    private static String address;
    private static double longitude;
    private static double latitude;

    public User() {
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        User.address = address;
    }

    public static double getLongitude() {
        return longitude;
    }

    public static void setLongitude(double longitude) {
        User.longitude = longitude;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        User.latitude = latitude;
    }
}
