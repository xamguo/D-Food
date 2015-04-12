package com.example.sam.d_food.business.data;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.sam.d_food.integration.service.Data;
import com.example.sam.d_food.presentation.main_page.HomePageActivity;
import com.example.sam.d_food.presentation.restaurant_page.RestaurantResultListActivity;

public class DishReceiver extends BroadcastReceiver {

    Data dataReceived;
    Activity activity;
    Intent intent;

    public DishReceiver(Activity a, Class<?> cls) {
        activity = a;
        intent = new Intent(a,cls);
    }

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        // TODO Auto-generated method stub

        if(RestaurantResultListActivity.dialog.isShowing()) {
            RestaurantResultListActivity.dialog.dismiss();
        }

        //dataReceived = (Data) arg1.getSerializableExtra("SearchResult");
        intent.putExtra("position",1);
        activity.startActivity(intent);
    }
}