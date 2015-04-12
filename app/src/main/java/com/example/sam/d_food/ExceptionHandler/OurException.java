package com.example.sam.d_food.ExceptionHandler;

/**
 * Created by Rodrigue on 4/12/2015.
 */
public class OurException extends Exception {

    private String errorMessage="";
    private String errorCode="";
    public OurException(String errorMessage,String errorCode) {
        this.errorMessage=errorMessage;
        this.errorCode=errorCode;
    }

    public OurException(String message) {
        super(message);
        this.errorMessage=message;
    }

    public OurException(Throwable cause) {
        super(cause);
    }

    public OurException(String message, Throwable cause) {
        super(message, cause);
        this.errorMessage=message;
    }
}
