package com.example.sam.d_food.ExceptionHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import com.example.sam.d_food.R;

/**
 * Created by Weiwei on 5/2/2015.
 */
public class GPSServiceException extends Exception {
    Context TD_Context;


    public void print(Activity act) {
        TD_Context = act.getApplication();
        AlertDialog.Builder dialog = new AlertDialog.Builder(TD_Context);
        dialog.setMessage(TD_Context.getResources().getString(R.string.gps_error));
        dialog.setPositiveButton(TD_Context.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub

            }
        });
        dialog.setNegativeButton(TD_Context.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub

            }
        });
        dialog.show();
    }
}
