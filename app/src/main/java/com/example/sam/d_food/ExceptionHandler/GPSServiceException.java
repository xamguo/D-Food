/*
 * GPS disabled exception, when tracking the order
  * */
package com.example.sam.d_food.ExceptionHandler;

public class GPSServiceException extends Exception {
    public GPSServiceException(String mss) {
        super(mss);
    }
}
