package com.example.sam.d_food.entities.user;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.sam.d_food.ws.remote.Data;
import com.example.sam.d_food.presentation.main_page.HomePageActivity;

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

        //dataReceived = (Data) arg1.getSerializableExtra("SearchResult");
        activity.startActivity(intent);
    }
}