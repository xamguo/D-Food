package com.example.sam.d_food.ExceptionHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by Weiwei on 5/2/2015.
 */
public class DownloadDataException extends Exception{
    public void print (Activity act) {
        AlertDialog alertDialog = new AlertDialog.Builder(act).create();
        alertDialog.setTitle("Download Failed");
        alertDialog.setMessage("Download Failed");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
