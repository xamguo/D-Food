/*
* Search exception, when searching for the restaurants or dishes
* */
package com.example.sam.d_food.ExceptionHandler;

import android.app.Activity;
import android.widget.Toast;

public class SearchException extends Exception {

    public SearchException(Activity activity) {
        Toast.makeText(activity,
                "Search Failed", Toast.LENGTH_SHORT).show();
    }

    public SearchException(String detailMessage, Activity activity) {
        super(detailMessage);
        Toast.makeText(activity,
                "Search Failed", Toast.LENGTH_SHORT).show();
    }
}
