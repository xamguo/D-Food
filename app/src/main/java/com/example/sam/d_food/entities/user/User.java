/*
* User information class, for both deliveryman and customer.
* */
package com.example.sam.d_food.entities.user;

public class User {
    private static String address;      //user address
    private static int id;              //user id
    private static int deliverymanID = -1;  //deliveryman id, used in customer mode to track the deliveryman
    private static boolean beepSign;       //beep sign, to control the alarm
    private static String name;             //user name
    private static double longitude;       //user longitude
    private static double latitude;        //user latitude

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

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        User.id = id;
    }

    public static String getName() {
        return name;
    }

    public static int getDeliverymanID() {
        return deliverymanID;
    }

    public static void setDeliverymanID(int deliverymanID) {
        User.deliverymanID = deliverymanID;
    }

    public static boolean isBeepSign() {
        return beepSign;
    }

    public static void setBeepSign(boolean beepSign) {
        User.beepSign = beepSign;
    }

    public static void setName(String name) {
        User.name = name;
    }
}
