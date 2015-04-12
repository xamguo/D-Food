package com.example.sam.d_food.business.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.sam.d_food.integration.service.Data;
import com.example.sam.d_food.presentation.main_page.HomePageActivity;
import com.example.sam.d_food.presentation.restaurant_page.RestaurantResultListActivity;

public class SearchReceiver extends BroadcastReceiver {

    Data dataReceived;
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

        //dataReceived = (Data) arg1.getSerializableExtra("SearchResult");
        activity.startActivity(intent);
    }
}