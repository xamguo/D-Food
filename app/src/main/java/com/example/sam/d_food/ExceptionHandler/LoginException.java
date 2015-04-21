package com.example.sam.d_food.ExceptionHandler;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Rodrigue on 4/12/2015.
 */
public class LoginException extends Exception {

    private String errorMessage = "";
    private String errorCode = "";

    public LoginException(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public LoginException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public LoginException(Throwable cause) {
        super(cause);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
        this.errorMessage = message;
    }

    public void logIntoFile(String fileName) {
        File log = new File(fileName);
        Date date = new Date();
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(log, true));
            out.append(errorCode + "	" + errorMessage + "	" + new Timestamp(date.getTime()) + "\n");
            out.close();
        } catch (IOException e) {
            System.out.println("COULD NOT LOG!!");
            Log.i("Exception", e.getMessage());
        }
    }
}
