package com.example.sam.d_food.business.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.sam.d_food.presentation.main_page.HomePageActivity;
import com.example.sam.d_food.presentation.restaurant_page.RestaurantResultListActivity;

/**
 * Created by Sam on 4/11/2015.
 */
public class SearchReceiver extends BroadcastReceiver {

    int dataReceived;
    Activity activity;
    Intent intent;

    public SearchReceiver(Activity a, Class<?> cls) {
        activity = a;
        intent = new Intent(a,cls);
    }

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        // TODO Auto-generated method stub

        if(HomePageActivity.dialog.isShowing()) {
            HomePageActivity.dialog.dismiss();
        }

        dataReceived = arg1.getIntExtra("DATAPASSED", 0);
        activity.startActivity(intent);
    }
}