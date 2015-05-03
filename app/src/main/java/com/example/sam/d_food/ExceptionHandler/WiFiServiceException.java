/*
* WIFI disabled exception, this app is web based.
* */
package com.example.sam.d_food.ExceptionHandler;

public class WiFiServiceException extends Exception{
    public WiFiServiceException(String mss) {
        super(mss);
    }
}
