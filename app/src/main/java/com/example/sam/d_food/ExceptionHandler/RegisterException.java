/*
 * Register exception, can be fail or name is already taken
  * */
package com.example.sam.d_food.ExceptionHandler;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.io.PrintStream;

public class RegisterException extends Exception {

    public RegisterException(String detailMessage, Activity activity) {
        super(detailMessage);
        Log.e("RegisterException",detailMessage);
        Toast.makeText(activity,
                detailMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void printStackTrace(PrintStream err) {
        super.printStackTrace(err);
    }
}
