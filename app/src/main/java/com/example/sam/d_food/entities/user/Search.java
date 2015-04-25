package com.example.sam.d_food.entities.user;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.example.sam.d_food.ws.remote.DataService;

public class Search {
    boolean mBound = false;
    Activity activity;
    String pirceLevel;
    String location;

    public Search(Activity activity) {
        this.activity = activity;
    }

    public void search(double longitude, double latitude, String price, String location) {
        this.location = location;
        pirceLevel = price;
        bindService(String.valueOf(longitude),String.valueOf(latitude), price);
    }

    public void unbindService() {
        if (mBound) {
            activity.unbindService(mConnection);
            mBound = false;
            Log.v("Search class","unbindService");
        }
    }

    private void bindService(String longitude, String latitude, String price) {
        if(location.equals("Your Location") || location == null) {
            location = "CMU";
        }
        Intent intent = new Intent(activity, DataService.class);
        intent.putExtra("Mode","Search");
        intent.putExtra("longitude",longitude);
        intent.putExtra("latitude",latitude);
        intent.putExtra("price",price);
        intent.putExtra("location",location);

        mBound = true;
        boolean b = activity.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        Log.v("connected service",String.valueOf(b));
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            mBound = true;
            Log.v("connected service","xiao");
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
}